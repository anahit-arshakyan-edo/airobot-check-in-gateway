package com.edreamsodigeo.boardingpass.airobotcheckingateway.persistence;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Airline;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Airport;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.DocumentType;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.BoardingPassOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.BoardingPass;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.BoardingPassId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.Delivery;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.DeliveryId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.Error;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.Format;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.Status;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.journey.Journey;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.journey.JourneyId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Document;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Passenger;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.PassengerId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.SeatPreference;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.persistence.exception.NonUniqueStoreException;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.persistence.exception.StoreException;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class OracleBoardingPassOutboundAdapter implements BoardingPassOutboundPort {

    private static final String INSERT_BOARDING_PASS = "INSERT INTO AIROBOT_CHECKIN_GATEWAY_OWN.BOARDING_PASS (DELIVERY_ID, JOURNEY_ID, PASSENGER_ID, STATUS, URL, FORMAT) VALUES (?,?,?,?,?,?,?)";
    private static final String INSERT_BOARDING_PASS_ERROR = "INSERT INTO AIROBOT_CHECKIN_GATEWAY_OWN.BOARDING_PASS_ERROR (DELIVERY_ID, JOURNEY_ID, PASSENGER_ID, ERROR_CODE) VALUES (?,?,?,?,?)";
    private static final String INSERT_BOARDING_PASS_DELIVERY = "INSERT INTO AIROBOT_CHECKIN_GATEWAY_OWN.BOARDING_PASS_DELIVERY (DELIVERY_REQUEST_ID, BRAND, LANG, COUNTRY, BOOKING_DATE, BOOKING_EMAIL, DELIVERY_EMAIL) VALUES (?,?,?,?,?,?,?)";
    private static final String INSERT_PASSENGER = "INSERT INTO AIROBOT_CHECKIN_GATEWAY_OWN.PASSENGER (PASSENGER_ID, FIRST_NAME, LAST_NAME, GENDER, DATE_OF_BIRTH, NATIONALITY, DOCUMENT_TYPE, DOCUMENT_NUMBER, DOCUMENT_COUNTRY, DOCUMENT_ISSUE_DATE, DOCUMENT_EXPIRATION_DATE, SEAT_PREFERENCE) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String INSERT_JOURNEY = "INSERT INTO AIROBOT_CHECKIN_GATEWAY_OWN.JOURNEY (JOURNEY_ID, AIRLINE, DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DEPARTURE_DATE, ARRIVAL_DATE, FLIGHT_NUMBER, BOOKING_ID, PNR, TICKET_NUMBER) VALUES (?,?,?,?,?,?,?,?,?,?)";

    private static final String GET_BOARDING_PASS_BY_REQUEST_ID = "SELECT * FROM AIROBOT_CHECKIN_GATEWAY_OWN.BOARDING_PASS BP JOIN AIROBOT_CHECKIN_GATEWAY_OWN.BOARDING_PASS_DELIVERY BP_DELIVERY ON BP.DELIVERY_ID = BP_DELIVERY.DELIVERY_REQUEST_ID JOIN AIROBOT_CHECKIN_GATEWAY_OWN.PASSENGER BP_PASSENGER ON BP.PASSENGER_ID = BP_PASSENGER.PASSENGER_ID JOIN AIROBOT_CHECKIN_GATEWAY_OWN.JOURNEY BP_JOURNEY ON BP.JOURNEY_ID = BP_JOURNEY.JOURNEY_ID WHERE DELIVERY_REQUEST_ID.ID=?";

    private final DataSource dataSource;

    @Inject
    public OracleBoardingPassOutboundAdapter(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(BoardingPass boardingPass) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_BOARDING_PASS)) {

            saveBoardingPassDelivery(boardingPass.delivery(), conn);
            saveJourney(boardingPass.journey(), conn);
            savePassenger(boardingPass.passenger(), conn);

            ps.setString(1, boardingPass.id().deliveryId().value());
            ps.setString(2, boardingPass.id().journeyId().value());
            ps.setString(3, boardingPass.id().passengerId().value());
            ps.setString(4, boardingPass.status().description());
            ps.setString(5, boardingPass.url());
            ps.setString(6, boardingPass.format().description());
            ps.executeUpdate();

            if (boardingPass.isFailed()) {
                saveError(boardingPass.error(), conn);
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            throw new NonUniqueStoreException("BoardingPass already stored", e);
        } catch (SQLException e) {
            throw new StoreException("Exception while saving BoardingPass", e);
        }
    }

    private void saveBoardingPassDelivery(Delivery delivery, Connection connection) {
        try (PreparedStatement ps = connection.prepareStatement(INSERT_BOARDING_PASS_DELIVERY)) {
            ps.setString(1, delivery.id().value());
            ps.setString(2, delivery.brand());
            ps.setString(3, delivery.lang());
            ps.setString(4, delivery.country());
            ps.setTimestamp(5, Timestamp.valueOf(delivery.bookingDate()));
            ps.setString(6, delivery.bookingEmail());
            ps.setString(7, delivery.boardingPassDeliveryEmail());
            ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new NonUniqueStoreException("BoardingPassDelivery already stored", e);
        } catch (SQLException e) {
            throw new StoreException("Exception while saving BoardingPassDelivery", e);
        }
    }

    private void saveJourney(Journey journey, Connection connection) {
        try (PreparedStatement ps = connection.prepareStatement(INSERT_JOURNEY)) {
            ps.setString(1, journey.id().value());
            ps.setString(2, journey.airline().iataCode());
            ps.setString(3, journey.departureAirport().iataCode());
            ps.setString(4, journey.arrivalAirport().iataCode());
            ps.setTimestamp(5, Timestamp.valueOf(journey.departureDate()));
            ps.setTimestamp(6, Timestamp.valueOf(journey.arrivalDate()));
            ps.setInt(7, journey.flightNumber());
            ps.setInt(8, journey.bookingId());
            ps.setString(9, journey.pnr());
            ps.setString(10, journey.ticketNumber());
            ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new NonUniqueStoreException("Journey already stored", e);
        } catch (SQLException e) {
            throw new StoreException("Exception while saving Journey", e);
        }
    }

    private void savePassenger(Passenger passenger, Connection connection) {
        try (PreparedStatement ps = connection.prepareStatement(INSERT_PASSENGER)) {
            ps.setString(1, passenger.id().value());
            ps.setString(2, passenger.name());
            ps.setString(3, passenger.surname());
            ps.setString(4, passenger.gender());
            ps.setString(5, passenger.dateOfBirth());
            ps.setString(6, passenger.nationality());
            ps.setString(7, passenger.document().type().description());
            ps.setString(8, passenger.document().number());
            ps.setString(9, passenger.document().country());
            ps.setString(10, passenger.document().issueDate());
            ps.setString(11, passenger.document().expireDate());
            ps.setString(12, passenger.seatPreference().description());
            ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new NonUniqueStoreException("Passenger already stored", e);
        } catch (SQLException e) {
            throw new StoreException("Exception while saving Passenger", e);
        }
    }

    private void saveError(Error error, Connection connection) {
        try (PreparedStatement ps = connection.prepareStatement(INSERT_BOARDING_PASS_ERROR)) {
            ps.setString(1, error.deliveryId().value());
            ps.setString(2, error.journeyId().value());
            ps.setString(3, error.passengerId().value());
            ps.setString(4, error.errorCode());
            ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new NonUniqueStoreException("BoardingPassError already stored", e);
        } catch (SQLException e) {
            throw new StoreException("Exception while saving BoardingPassError", e);
        }
    }


    public List<BoardingPass> getBoardingPasses(String deliveryRequestId) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(GET_BOARDING_PASS_BY_REQUEST_ID)) {
            ps.setString(1, deliveryRequestId);
            try (ResultSet rs = ps.executeQuery()) {
                List<BoardingPass> boardingPasses = new ArrayList<>();
                if (rs.next()) {
                    boardingPasses.add(map(rs));
                }
                return boardingPasses;
            }
        } catch (SQLException e) {
            throw new StoreException("Exception during getBoardingPass: deliveryRequestId = " + deliveryRequestId, e);
        }
    }

    private BoardingPass map(ResultSet rs) throws SQLException {

        Delivery delivery = mapToDelivery(rs);
        Journey journey = mapToJourney(rs);
        Passenger passenger = mapToPassenger(rs);
        Error error = mapToError(rs);

        Status status = Status.valueOf(rs.getString("STATUS"));
        String url = rs.getString("URL");
        Format format = Format.valueOf(rs.getString("FORMAT"));

        return new BoardingPass(
                new BoardingPassId(delivery.id(), passenger.id(), journey.id()),
                delivery,
                passenger,
                journey,
                status,
                url,
                format,
                error
                );
    }

    private Delivery mapToDelivery(ResultSet rs) throws SQLException {
        return new Delivery(
                DeliveryId.from(rs.getString("DELIVERY_REQUEST_ID")),
                rs.getString("BRAND"),
                rs.getString("LANG"),
                rs.getString("COUNTRY"),
                rs.getTimestamp("BOOKING_DATE").toLocalDateTime(),
                rs.getString("BOOKING_EMAIL"),
                rs.getString("DELIVERY_EMAIL")
        );
    }

    private Journey mapToJourney(ResultSet rs) throws SQLException {
        return new Journey(
                JourneyId.from(rs.getString("JOURNEY_ID")),
                Airline.create(rs.getString("AIRLINE")),
                Airport.create(rs.getString("DEPARTURE_AIRPORT")),
                Airport.create(rs.getString("ARRIVAL_AIRPORT")),
                rs.getTimestamp("DEPARTURE_DATE").toLocalDateTime(),
                rs.getTimestamp("ARRIVAL_DATE").toLocalDateTime(),
                rs.getInt("FLIGHT_NUMBER"),
                rs.getInt("BOOKING_ID"),
                rs.getString("PNR"),
                rs.getString("TICKET_NUMBER")
        );
    }

    private Passenger mapToPassenger(ResultSet rs) throws SQLException {
        return new Passenger(
                PassengerId.from(rs.getString("PASSENGER_ID")),
                rs.getString("FIRST_NAME"),
                rs.getString("LAST_NAME"),
                rs.getString("GENDER"),
                rs.getString("DATE_OF_BIRTH"),
                rs.getString("NATIONALITY"),

                new Document(
                        DocumentType.valueOf(rs.getString("DOCUMENT_TYPE")),
                        rs.getString("DOCUMENT_NUMBER"),
                        rs.getString("DOCUMENT_COUNTRY"),
                        rs.getString("DOCUMENT_ISSUE_DATE"),
                        rs.getString("DOCUMENT_EXPIRATION_DATE")),

                SeatPreference.valueOf(rs.getString("SEAT_PREFERENCE"))
                );
    }

    private Error mapToError(ResultSet rs) throws SQLException {
        return new Error(
                PassengerId.from(rs.getString("PASSENGER_ID")),
                JourneyId.from(rs.getString("JOURNEY_ID")),
                DeliveryId.from(rs.getString("DELIVERY_ID")),
                rs.getString("ERROR_CODE")
        );
    }

}
