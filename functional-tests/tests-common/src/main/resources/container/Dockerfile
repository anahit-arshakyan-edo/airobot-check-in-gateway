
#inherit from the basic jboss image, the image name is filled with by the maven-resource-plugin with the docker.image.base.name variable
FROM ${module.name}

COPY --chown=jboss functional-test-properties /opt/odigeo/properties/${module.name}
RUN chmod -R 755 /opt/odigeo/properties/${module.name}

COPY standalone.xml /opt/eap/standalone/configuration/standalone.xml

