package com.edreamsodigeo.boardingpass.airobotcheckingateway.persistence;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.SaveCheckInOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.exception.StoreException;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.BoardingPass;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.ProviderRequest;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckIn;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckInId;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.odigeo.commons.uuid.UUIDSerializer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

@Singleton
public class SaveCheckInOracleOutboundAdapter implements SaveCheckInOutboundPort {

    private static final String INSERT_CHECK_IN = "INSERT INTO AIROBOT_CHECKIN_GATEWAY_OWN.CHECK_IN (ID, REFERENCE_ID, CREATED_AT) VALUES (?,?,?)";
    private static final String INSERT_CHECK_IN_REQUEST = "INSERT INTO AIROBOT_CHECKIN_GATEWAY_OWN.CHECK_IN_REQUEST (ID, CHECK_IN_ID, PROVIDER_REQUEST_ID, CREATED_AT) VALUES (?,?,?,?)";
    private static final String INSERT_BOARDING_PASS = "INSERT INTO AIROBOT_CHECKIN_GATEWAY_OWN.BOARDING_PASS (ID, CHECK_IN_REQUEST_ID, PROVIDER_PASSENGER_JOURNEY_ID) VALUES (?,?,?)";
    private final DataSource dataSource;

    @Inject
    public SaveCheckInOracleOutboundAdapter(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(ItineraryCheckIn itineraryCheckIn) {
        saveCheckIn(itineraryCheckIn);
        saveCheckInRequests(itineraryCheckIn);
    }

    private void saveCheckIn(ItineraryCheckIn itineraryCheckIn) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(INSERT_CHECK_IN)) {
            ps.setBytes(1, UUIDSerializer.toBytes(itineraryCheckIn.id().value()));
            ps.setLong(2, itineraryCheckIn.referenceId().valueLong());
            ps.setTimestamp(3, Timestamp.from(Instant.now()));
            ps.execute();
        } catch (SQLException e) {
            throw new StoreException("Exception while saving CheckIn: " + e.getMessage(), e);
        }
    }

    private void saveCheckInRequests(ItineraryCheckIn itineraryCheckIn) {
        for (ProviderRequest providerRequest : itineraryCheckIn.providerRequests()) {
            saveCheckInRequest(itineraryCheckIn.id(), providerRequest);
            saveBoardingPasses(providerRequest);
        }
    }

    private void saveCheckInRequest(ItineraryCheckInId itineraryCheckInId, ProviderRequest providerRequest) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(INSERT_CHECK_IN_REQUEST)) {

            ps.setBytes(1, UUIDSerializer.toBytes(providerRequest.providerRequestId().value()));
            ps.setBytes(2, UUIDSerializer.toBytes(itineraryCheckInId.value()));
            ps.setString(3, providerRequest.requestId().valueString());
            ps.setTimestamp(4, Timestamp.from(Instant.now()));

            ps.execute();

        } catch (SQLException e) {
            throw new StoreException("Exception while saving CheckInRequest: " + e.getMessage(), e);
        }
    }

    private void saveBoardingPasses(ProviderRequest providerRequest) {
        try (Connection connection = dataSource.getConnection(); PreparedStatement ps = connection.prepareStatement(INSERT_BOARDING_PASS)) {

            for (BoardingPass boardingPass : providerRequest.boardingPasses()) {

                ps.setBytes(1, UUIDSerializer.toBytes(boardingPass.id().value()));
                ps.setBytes(2, UUIDSerializer.toBytes(providerRequest.providerRequestId().value()));
                ps.setLong(3, boardingPass.providerPassengerSectionId().valueLong());

                ps.addBatch();
            }

            ps.executeBatch();

        } catch (SQLException e) {
            throw new StoreException("Exception while saving BoardingPasses: " + e.getMessage(), e);
        }
    }

}
