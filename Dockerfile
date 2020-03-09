FROM openjdk:8-jre-alpine

COPY ./build/libs/inventory-management-1.0.jar app.jar
EXPOSE 8088
RUN echo "java -jar $APP_JAR_FILE --spring.config.location=file:./application.yml"
ENTRYPOINT ["java", "-jar", "app.jar"]