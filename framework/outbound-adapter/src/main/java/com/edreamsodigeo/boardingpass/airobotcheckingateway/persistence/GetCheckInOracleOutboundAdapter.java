package com.edreamsodigeo.boardingpass.airobotcheckingateway.persistence;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.GetCheckInOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.exception.StoreException;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ItineraryCheckInId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.segment.ProviderRequestId;
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

    private static final String SELECT_PROVIDER_REQUEST_IDS_BY_CHECK_IN_ID = "SELECT PROVIDER_REQUEST_ID FROM AIROBOT_CHECKIN_GATEWAY_OWN.CHECK_IN_REQUEST WHERE CHECK_IN_ID = ?";
    private final DataSource dataSource;

    @Inject
    public GetCheckInOracleOutboundAdapter(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public List<ProviderRequestId> getProviderRequestIds(ItineraryCheckInId checkInId) {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_PROVIDER_REQUEST_IDS_BY_CHECK_IN_ID)) {
            ps.setBytes(1, UUIDSerializer.toBytes(checkInId.value()));
            try (ResultSet rs = ps.executeQuery()) {
                List<ProviderRequestId> providerRequestIds = new ArrayList<>();
                while (rs.next()) {
                    providerRequestIds.add(ProviderRequestId.from((rs.getString("PROVIDER_REQUEST_ID"))));
                }
                return providerRequestIds;
            }
        } catch (SQLException e) {
            throw new StoreException("Exception while selecting Provider Request Ids: " + e.getMessage(), e);
        }
    }
}
