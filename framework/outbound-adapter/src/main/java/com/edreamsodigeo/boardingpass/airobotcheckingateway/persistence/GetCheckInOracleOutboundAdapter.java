package com.edreamsodigeo.boardingpass.airobotcheckingateway.persistence;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.GetCheckInOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.exception.StoreException;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.ProviderRequestMetadata;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.RequestId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.BoardingPassId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.ProviderRequestId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.BoardingPassMetadata;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckInId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ProviderPassengerSectionId;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.odigeo.commons.uuid.UUIDSerializer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class GetCheckInOracleOutboundAdapter implements GetCheckInOutboundPort {

    private static final String SELECT_PROVIDER_REQUESTS_BY_CHECK_IN_ID = "SELECT * FROM AIROBOT_CHECKIN_GATEWAY_OWN.CHECK_IN_REQUEST WHERE CHECK_IN_ID = ?";
    private static final String SELECT_BOARDING_PASSES_BY_PROVIDER_REQUEST_ID = "SELECT * FROM AIROBOT_CHECKIN_GATEWAY_OWN.BOARDING_PASS WHERE CHECK_IN_REQUEST_ID = ?";
    private final DataSource dataSource;

    @Inject
    public GetCheckInOracleOutboundAdapter(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public List<ProviderRequestMetadata> getProviderRequestsMetadata(ItineraryCheckInId checkInId) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_PROVIDER_REQUESTS_BY_CHECK_IN_ID)) {
            ps.setBytes(1, UUIDSerializer.toBytes(checkInId.value()));
            try (ResultSet rs = ps.executeQuery()) {
                List<ProviderRequestMetadata> providerRequestsMetadata = new ArrayList<>();
                while (rs.next()) {
                    ProviderRequestMetadata providerRequestMetadata = ProviderRequestMetadata.from(
                            ProviderRequestId.from(UUIDSerializer.fromBytes((rs.getBytes("ID")))),
                            RequestId.from(rs.getString("PROVIDER_REQUEST_ID"))
                    );

                    providerRequestsMetadata.add(providerRequestMetadata);
                }
                return providerRequestsMetadata;
            }
        } catch (SQLException e) {
            throw new StoreException("Exception while selecting Provider Requests Metadata: " + e.getMessage(), e);
        }
    }

    @Override
    public List<BoardingPassMetadata> getBoardingPassesMetadata(ProviderRequestId providerRequestId) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_BOARDING_PASSES_BY_PROVIDER_REQUEST_ID)) {
            ps.setBytes(1, UUIDSerializer.toBytes(providerRequestId.value()));
            try (ResultSet rs = ps.executeQuery()) {
                List<BoardingPassMetadata> boardingPassesMetadata = new ArrayList<>();
                while (rs.next()) {
                    BoardingPassMetadata boardingPassMetadata = BoardingPassMetadata.from(
                            BoardingPassId.from(UUIDSerializer.fromBytes((rs.getBytes("ID")))),
                            ProviderPassengerSectionId.from(rs.getLong("PROVIDER_PASSENGER_JOURNEY_ID"))
                    );

                    boardingPassesMetadata.add(boardingPassMetadata);
                }
                return boardingPassesMetadata;
            }
        } catch (SQLException e) {
            throw new StoreException("Exception while selecting Boarding Passes Metadata: " + e.getMessage(), e);
        }
    }

}
