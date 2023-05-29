package com.edreamsodigeo.boardingpass.airobotcheckingateway;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.GenerateReferenceIdOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ProviderReferenceId;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.jboss.resteasy.logging.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Singleton
public class GenerateReferenceIdOracleOutboundAdapter implements GenerateReferenceIdOutboundPort {

    private static final Logger LOGGER = Logger.getLogger(GenerateReferenceIdOracleOutboundAdapter.class);
    private static final String SELECT_NEXT_VAL_FROM_REFERENCE_ID_SEQ = "SELECT AIROBOT_CHECKIN_GATEWAY_OWN.REFERENCE_ID_SEQ.NEXTVAL FROM DUAL";
    private final DataSource dataSource;

    @Inject
    public GenerateReferenceIdOracleOutboundAdapter(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public ProviderReferenceId generateReferenceId() {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_NEXT_VAL_FROM_REFERENCE_ID_SEQ)) {
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return ProviderReferenceId.from(rs.getLong(1));
            }
        } catch (SQLException e) {
            LOGGER.error("Exception while selecting Next Val from ReferenceId Sequence: " + e.getMessage(), e);
            return ProviderReferenceId.notAssigned();
        }
    }

}
