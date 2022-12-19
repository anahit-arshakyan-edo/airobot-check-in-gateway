
#inherit from the basic jboss image, the image name is filled with by the maven-resource-plugin with the docker.image.base.name variable
FROM ${docker.image.base.name}

#get module name from args and set as env variable
ARG MODULE_NAME
ENV MODULE_NAME $MODULE_NAME

#get module version from args and set as env variable
ARG MODULE_VERSION
ENV MODULE_VERSION $MODULE_VERSION

# Copy ear to JBoss deployment directory
COPY airobot-check-in-gateway.ear /opt/eap/standalone/deployments/

# Copy project resources to properties directory
COPY --chown=jboss resources /opt/odigeo/properties/${module.name}
RUN chmod -R 755 /opt/odigeo/properties/${module.name}

# Overwrite standalone.xml (ONLY FOR MODULES WITH DATASOURCES OR WITH COMMANDS.CLI)
COPY standalone.xml /opt/eap/standalone/configuration/standalone.xml
