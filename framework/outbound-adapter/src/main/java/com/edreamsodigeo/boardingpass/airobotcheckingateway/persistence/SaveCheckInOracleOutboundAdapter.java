package com.edreamsodigeo.boardingpass.airobotcheckingateway.persistence;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.outboundport.SaveCheckInOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.outboundport.exception.StoreException;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.boardingpass.BoardingPass;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.checkin.itinerary.ItineraryCheckIn;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.checkin.itinerary.ItineraryCheckInId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.checkin.segment.SegmentCheckIn;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.checkin.segment.SegmentCheckInId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.passenger.Passenger;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.section.Section;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.odigeo.commons.uuid.UUIDSerializer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
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

    public void save(ItineraryCheckIn itineraryCheckIn) {
        saveCheckIn(itineraryCheckIn);
        savePassengers(itineraryCheckIn.passengers());
        saveSections(itineraryCheckIn.sections());
        saveCheckInRequests(itineraryCheckIn.id(), itineraryCheckIn.segmentCheckIns());
    }

    private void saveCheckInRequests(ItineraryCheckInId itineraryCheckInId, List<SegmentCheckIn> segmentCheckIns) {
        for (SegmentCheckIn segmentCheckIn : segmentCheckIns) {
            saveCheckInRequest(itineraryCheckInId, segmentCheckIn);
            saveBoardingPasses(itineraryCheckInId, segmentCheckIn.id(), segmentCheckIn.boardingPasses());
        }
    }

    private void saveCheckInRequest(ItineraryCheckInId itineraryCheckInId, SegmentCheckIn segmentCheckIn) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(INSERT_CHECK_IN_REQUEST)) {

            ps.setBytes(1, UUIDSerializer.toBytes(segmentCheckIn.id().value()));
            ps.setBytes(2, UUIDSerializer.toBytes(itineraryCheckInId.value()));
            ps.setString(3, segmentCheckIn.providerRequestId().valueString());
            ps.setTimestamp(4, Timestamp.from(Instant.now()));


            ps.execute();
        } catch (SQLException e) {
            throw new StoreException("Exception while saving CheckInRequest", e);
        }
    }

    private void saveSections(List<Section> sections) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(INSERT_SECTION)) {

            for (Section section : sections) {
                ps.setBytes(1, UUIDSerializer.toBytes(section.id().value()));
                ps.setLong(2, section.providerSectionId().valueLong());
                ps.setString(3, section.departureAirport().iataCode());
                ps.setString(4, section.arrivalAirport().iataCode());
                ps.setTimestamp(5, Timestamp.valueOf(section.departureDate()));
                ps.setTimestamp(6, Timestamp.valueOf(section.arrivalDate()));
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

    private void saveBoardingPasses(ItineraryCheckInId itineraryCheckInId, SegmentCheckInId segmentCheckInId, List<BoardingPass> boardingPasses) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(INSERT_BOARDING_PASS)) {

            for (BoardingPass boardingPass : boardingPasses) {

                ps.setBytes(1, UUIDSerializer.toBytes(boardingPass.id().value()));
                ps.setBytes(2, UUIDSerializer.toBytes(itineraryCheckInId.value()));
                ps.setBytes(3, UUIDSerializer.toBytes(segmentCheckInId.value()));
                ps.setBytes(4, UUIDSerializer.toBytes(boardingPass.section().id().value()));
                ps.setBytes(5, UUIDSerializer.toBytes(boardingPass.passenger().id().value()));
                ps.setLong(6, boardingPass.providerPassengerSectionId().valueLong());
                ps.setString(7, boardingPass.status().code().toString());
                ps.setString(8, boardingPass.status().reason() != null ? boardingPass.status().reason().toString() : null);
                ps.setTimestamp(9, Timestamp.from(Instant.now()));

                ps.addBatch();
            }

            ps.executeBatch();

        } catch (SQLException e) {
            throw new StoreException("Exception while saving BoardingPasses", e);
        }
    }

    private void saveCheckIn(ItineraryCheckIn itineraryCheckIn) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(INSERT_CHECK_IN)) {
            ps.setBytes(1, UUIDSerializer.toBytes(itineraryCheckIn.id().value()));
            ps.setLong(2, itineraryCheckIn.referenceId());
            ps.setTimestamp(3, Timestamp.from(Instant.now()));
            ps.execute();
        } catch (SQLException e) {
            throw new StoreException("Exception while saving CheckIn", e);
        }
    }

}
