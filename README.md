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

```mvn clean install -P build-container-image,dev,nexus``` to build the docker image

```make -f generate-docker-compose-file```  to generate the docker-compose

```docker-compose -f generated-docker-compose.yml up``` to run the container

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
docker-compose up

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
- Improve error tracking: in case of server error (due to Airobot or to a gateway bug)
  it's completely obscure what happened: you just see a 500 - unexpected error on postman
- Remove Log4j warning
- Testing exception from the CheckInController
- Improve documentation of launch and invocation process
- Validate a real message with false availability, what return in the response
  availabilies is empty or not?
- Binding the AirobotResourceModule inside the OutboundAdapterBindings
- Refactoring AirobotOutboundAdapterTest: create an explicit request

## Hexagonal Architecture Documentation

https://lucid.app/lucidspark/9d7efddd-6112-4adf-ab28-45ac205cb0b3/edit


## Comparison between manual validation and JSR validation
 
1. Manual validation in the isValid method
- This is the current approach. 
- The problem is that you don't have the warranty that the object is valid after the construction. 
- If in some other place of the code you construct that object, you could have an invalid one.

2. Manual validation in the constructor
- This approach solves the previous problem. 
- Anyway, some people prefer avoid throwing exceptions in a constructor 
(even if the issue seem only related to partially constructed instance of non-final classes
- (ex. [see Is it good practice to make the constructor throw an exception?](https://stackoverflow.com/questions/6086334/is-it-good-practice-to-make-the-constructor-throw-an-exception#:~:text=Throwing%20exceptions%20in%20a%20constructor,that%20the%20parameters%20are%20invalid).

3. Manual validation in a builder 
- This approach solves the previous problems. 
- Maybe the issue could be the duplication of parameters in the creation method or in the factory/builder. 
- You can use the builder to support creation of objects in the Object Mother.
- See [AvailabilityRequestBuilder]
- I added a [Checker]

4. Automatic validation with JSR 380
- This approach uses annotations and a validator
- Error messages
- What if we have complex validation logic?
- Not clear how to use the annotation processor. It seems that the plugin to add is
```
      <path>
          <groupId>org.hibernate.validator</groupId>
          <artifactId>hibernate-validator-annotation-processor</artifactId>
          <version>6.0.23.Final</version>
      </path>
```
in the following section of the parent pom:
```
                <plugin>
                    <artifactId>maven-compiler-plugin</artifactId>
                    <version>3.8.1</version>
                    <configuration>
                        <annotationProcessorPaths>
```

# Changelog

...
