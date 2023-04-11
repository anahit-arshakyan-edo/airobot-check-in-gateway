package com.edreamsodigeo.boardingpass.airobotcheckingateway.persistence;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.SaveCheckInOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.exception.StoreException;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.BoardingPass;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.CheckIn;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.CheckInId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.CheckInRequest;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Passenger;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.Section;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.odigeo.commons.uuid.UUIDSerializer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;

@Singleton
public class SaveCheckInOracleOutboundAdapter implements SaveCheckInOutboundPort {

    private static final String INSERT_CHECK_IN = "INSERT INTO AIROBOT_CHECKIN_GATEWAY_OWN.CHECK_IN (ID, REFERENCE_ID, CREATED_AT) VALUES (?,?,?)";
    private static final String INSERT_CHECK_IN_REQUEST = "INSERT INTO AIROBOT_CHECKIN_GATEWAY_OWN.CHECK_IN_REQUEST (ID, CHECK_IN_ID, PROVIDER_REQUEST_ID, CREATED_AT) VALUES (?,?,?,?)";
    private static final String INSERT_BOARDING_PASS = "INSERT INTO AIROBOT_CHECKIN_GATEWAY_OWN.BOARDING_PASS (ID, CHECK_IN_ID, CHECK_IN_REQUEST_ID, SECTION_ID, PASSENGER_ID, PROVIDER_PASSENGER_JOURNEY_ID, STATUS_CODE, STATUS_REASON, UPDATED_AT) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String INSERT_PASSENGER = "INSERT INTO AIROBOT_CHECKIN_GATEWAY_OWN.PASSENGER (ID, PROVIDER_PASSENGER_ID, NAME, LAST_NAME, DATE_OF_BIRTH) VALUES (?,?,?,?,?)";
    private static final String INSERT_SECTION = "INSERT INTO AIROBOT_CHECKIN_GATEWAY_OWN.SECTION (ID, PROVIDER_SECTION_ID, DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DEPARTURE_DATE, ARRIVAL_DATE, FLIGHT_NUMBER, MARKETING_CARRIER, OPERATING_CARRIER, PNR) VALUES (?,?,?,?,?,?,?,?,?,?)";
    private final DataSource dataSource;

    @Inject
    public SaveCheckInOracleOutboundAdapter(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(CheckIn checkIn) {
        saveCheckIn(checkIn);
        saveCheckInRequests(checkIn.id(), checkIn.checkInRequests());
        savePassengers(checkIn.passengers());
        saveSections(checkIn.sections());
        saveBoardingPasses(checkIn.id(), checkIn.boardingPasses());
    }

    private void saveCheckInRequests(CheckInId checkInId, List<CheckInRequest> checkInRequests) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(INSERT_CHECK_IN_REQUEST)) {

            for (CheckInRequest checkInRequest : checkInRequests) {
                ps.setBytes(1, UUIDSerializer.toBytes(checkInRequest.id().value()));
                ps.setBytes(2, UUIDSerializer.toBytes(checkInId.value()));
                ps.setString(3, checkInRequest.providerRequestId());
                ps.setObject(4, Instant.now());
                ps.addBatch();
            }

            ps.executeBatch();
        } catch (SQLException e) {
            throw new StoreException("Exception while saving CheckInRequests", e);
        }
    }

    private void saveSections(List<Section> sections) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(INSERT_SECTION)) {

            for (Section section : sections) {
                ps.setBytes(1, UUIDSerializer.toBytes(section.id().value()));
                ps.setLong(2, section.providerSectionId().valueLong());
                ps.setString(3, section.departureAirport().iataCode());
                ps.setString(4, section.arrivalAirport().iataCode());
                ps.setObject(5, section.departureDate());
                ps.setObject(6, section.arrivalAirport());
                ps.setInt(7, section.flightNumber());
                ps.setString(8, section.marketingCarrier().iataCode());
                ps.setString(9, section.operatingCarrier().iataCode());
                ps.setString(10, section.pnr());
                ps.addBatch();
            }

            ps.executeBatch();

        } catch (SQLException e) {
            throw new StoreException("Exception while saving Sections", e);
        }
    }

    private void savePassengers(List<Passenger> passengers) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(INSERT_PASSENGER)) {

            for (Passenger passenger : passengers) {
                ps.setBytes(1, UUIDSerializer.toBytes(passenger.id().value()));
                ps.setLong(2, passenger.providerPassengerId().valueLong());
                ps.setString(3, passenger.name());
                ps.setString(4, passenger.lastName());
                ps.setObject(5, passenger.dateOfBirth());
                ps.addBatch();
            }

            ps.executeBatch();

        } catch (SQLException e) {
            throw new StoreException("Exception while saving Passengers", e);
        }
    }

    private void saveBoardingPasses(CheckInId checkInId, List<BoardingPass> boardingPasses) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(INSERT_BOARDING_PASS)) {

            for (BoardingPass boardingPass : boardingPasses) {

                ps.setBytes(1, UUIDSerializer.toBytes(boardingPass.id().value()));
                ps.setBytes(2, UUIDSerializer.toBytes(checkInId.value()));
                ps.setBytes(3, UUIDSerializer.toBytes(boardingPass.checkInRequest().id().value()));
                ps.setBytes(4, UUIDSerializer.toBytes(boardingPass.section().id().value()));
                ps.setBytes(5, UUIDSerializer.toBytes(boardingPass.passenger().id().value()));
                ps.setLong(6, boardingPass.providerPassengerJourneyId().valueLong());
                ps.setString(7, boardingPass.status().code().toString());
                ps.setString(8, boardingPass.status().reason() != null ? boardingPass.status().reason().toString() : null);
                ps.setObject(9, Instant.now());

                ps.addBatch();
            }

            ps.executeBatch();

        } catch (SQLException e) {
            throw new StoreException("Exception while saving BoardingPasses", e);
        }
    }

    private void saveCheckIn(CheckIn checkIn) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(INSERT_CHECK_IN)) {
            ps.setBytes(1, UUIDSerializer.toBytes(checkIn.id().value()));
            ps.setLong(2, checkIn.referenceId());
            ps.setObject(3, Instant.now());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new StoreException("Exception while saving CheckIn", e);
        }
    }

}
