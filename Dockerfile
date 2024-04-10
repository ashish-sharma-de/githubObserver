# Start with a base image containing Java runtime
FROM openjdk:17

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=build/libs/githubObserver-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} githubObserver.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/githubObserver.jar"]