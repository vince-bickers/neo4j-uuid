
package com.graphaware.module.uuid;

import com.graphaware.common.policy.NodeInclusionPolicy;
import com.graphaware.module.uuid.UuidModule;
import com.graphaware.runtime.config.function.StringToNodeInclusionPolicy;
import com.graphaware.runtime.module.RuntimeModule;
import com.graphaware.runtime.module.RuntimeModuleBootstrapper;
import org.neo4j.graphdb.GraphDatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class UuidBootstrapper implements RuntimeModuleBootstrapper {

    private static final Logger LOG = LoggerFactory.getLogger(UuidBootstrapper.class);

    private static final String UUID_PROPERTY = "uuidProperty";
    private static final String NODE = "node";

    @Override
    public RuntimeModule bootstrapModule(String moduleId, Map<String, String> config, GraphDatabaseService database) {
        UuidConfiguration configuration = UuidConfiguration.defaultConfiguration();

        if (config.get(UUID_PROPERTY) != null && config.get(UUID_PROPERTY).length() > 0) {
            configuration = configuration.withUuidProperty(config.get(UUID_PROPERTY));
            LOG.info("uuidProperty set to {}", configuration.getUuidProperty());
        }

        if (config.get(NODE) != null) {
            NodeInclusionPolicy policy = StringToNodeInclusionPolicy.getInstance().apply(config.get(NODE));
            LOG.info("Node Inclusion Strategy set to {}", policy);
            configuration = configuration.with(policy);
        }

        return new UuidModule(moduleId, configuration);
    }
}
