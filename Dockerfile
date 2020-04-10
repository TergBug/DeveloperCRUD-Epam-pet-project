FROM openjdk:8

ENV APP_JAR_NAME="target/DeveloperCRUD-1.0-RELEASED-jar-with-dependencies.jar"
ENV JAVA_ORTS="-Xms512m -Xmx512m"

COPY target target
COPY src/main/webapp src/main/webapp

EXPOSE 8080

ENTRYPOINT exec java $JAVA_ORTS -jar $APP_JAR_NAME