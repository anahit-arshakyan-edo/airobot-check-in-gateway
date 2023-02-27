# Airobot Check In Gateway

This application is intended to work as an intermediate tier between the CheckIn service, that is in charge of the whole
online-check-in process, and Airobot, a service provider for generating the boarding passes. The idea is to abstract the
boarding pass generation function, from the perspective of the CheckIn service, to make the latter able to work with
different providers.

**Order of Drivers**

- Alexandre
- Anahit
- Davide
- Walter

## Preconditions

- ```/usr/libexec/java_home -V``` to list java versions installed, and the path of each

- ensure you have jdk 11 on your machine (if not, install it)

  **Note for MacOS**
  If you have a MacOS with M1 processor you should use the x86 version downloaded from Azul:
  https://www.azul.com/downloads/?version=java-11-lts&os=macos&architecture=x86-64-bit&package=jdk
  (choose the .dmg package)


- ```mvn -v``` to verify that jdk 11 is used by maven this should match the ```echo $JAVA_HOME```


- edit ```~./.zshrc``` in case of need, to set the JAVA_HOME env variable to the jdk 11 path by adding
  ``` export JAVA_HOME="<jdk 11 path>$JAVA_HOME"```


- if you prefere using Intellij instead of terminal, set the JDK in File/Project Structure.

## Build process and Launch the application

- from the project root:

```mvn clean install``` to compile and launch tests

```make -f generate-docker-compose-file```  to generate the docker-compose

```mvn clean install -P build-container-image,dev,nexus``` to build the docker image

```docker-compose up``` to run the container

```docker network create local-qa-network``` in case of need

## Verifying test coverage
In the Maven tab, check the followings and then execute ```clean install ``` on a specific module,:
  or on the parent module: 

  [x] run-code-checks
  [x] sonar
  [x] sonar-analysis

Look at ```/target/site/jacoco/index.html``` for the report. 

## Configuring a REST client

- See the **SampleIntegrationTest** for an example of how to build a client with ServiceBuilder for an integration test

- create an **XxxResource** interface that represents the API to call, and call it ex..:
  ```
  public interface AirobotResource
  ```

- create a **XxxResourceModule** class, and add the code to create the client in the getServiceConfiguration method;
  ex.:
    ```
    public class AirobotResourceModule extends DefaultRestUtilsModule<AirobotResource> {
  
        public AirobotResourceModule(ServiceNotificator... listeners) {
            super(AirobotResource.class, listeners);
        }
        
        @Override
        protected ServiceConfiguration<AirobotResource> getServiceConfiguration(Class<AirobotResource> serviceContractClass) {
            ServiceConfiguration.Builder<AirobotResource> builder = new ServiceConfiguration.Builder(AirobotResource.class);
            builder.withRegisterBuildInProviders(false);
            builder.withJacksonVersion(JacksonVersion.V2);
            ServiceConfiguration<AirobotResource> dataExtractorServiceConfiguration = builder.build();
            dataExtractorServiceConfiguration.getFactory().registerProviderInstance(new DateConverter());
            return dataExtractorServiceConfiguration;
        }
    }
    ```

- call **ConfigurationEngine.init(new XxxResourceModule())**

  in the ContextListener.initConfigurationEngine (called by the startUpApplication method)
  register the module, ex.:

    ```
    public class ContextListener extends EdoContextListener {
        ...
        private void initConfigurationEngine(ServiceNotificator... serviceNotificators) {
        // Add any other dependencies' modules here
        ConfigurationEngine.init(
                new AirobotResourceModule((serviceNotificators)),
                ...
                // other modules;
        }
    }
    ```

  This in turn allows to get an instance of the concrete class for the specified type:
    ```
    object = ConfigurationEngine.getInstance(XxxResource.class);
    ```

- add a XxxResource.properties within the following folder (create it eventually):
  ```/packaging/container/src/main/conf/dev/resources/com/odigeo/rest/utils/url/configuration```
  then add the content, with the right values for host and service:
  ```
    protocol = https
    host = demo.host.sample
    port = 443
    service = /api/v2
  ```

## Commands


```
docker brew install docker // remove all previous installation, expecially Docker desktop
docker brew install docker-compose

colima ls
colima stop <name>
colima delete <name>
colima start --cpu 4 --memory 8 --disk 60 <name>
colima start <name>

sudo ln -sfn /Users/<username>/.colima/default/docker.sock /var/run/docker.sock

docker context ls
export DOCKER_HOST=unix:///Users/walter.nuccio/.colima/docker/docker.sock
docker ps

mvn clean install -P build-container-image 

docker network create local-qa-network
docker compose up

docker rmi <image id>
docker ps -a // shows all the containers, even the stopped ones
docker rm <container id>docker
```

## Health Check
After running the container, try the following urls from the browser:

Check the build version:
http://localhost:8080/airobot-check-in-gateway/engineering/build

Check if the service is up:
http://localhost:8080/airobot-check-in-gateway/engineering/ping

Health check for airobot service:
http://localhost:8080/airobot-check-in-gateway/engineering/healthcheck

## Get Availability endpoint
http://localhost:8080/airobot-check-in-gateway/check-in/v1/availability

See also `airobot_gateway.postman_collection.json` for a Postman collection to use.


##TODO

- Testing exception from the CheckInController
- improve documentation of launch and invocation process
- validate a real message with false availability, what return in the response
  availabilies is empty or not?
- Binding the AirobotResourceModule inside the OutboundAdapterBindings

## Hexagonal Architecture Documentation

https://lucid.app/lucidspark/9d7efddd-6112-4adf-ab28-45ac205cb0b3/edit

# Changelog

...
