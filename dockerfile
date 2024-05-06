FROM openjdk:11
LABEL author=mikhail
EXPOSE ${INTERNSHIP_SERVICE_PORT}
COPY ./target/internship-0.0.1-SNAPSHOT.jar /usr/src/myapp/
ENTRYPOINT ["java", "-jar", "/usr/src/myapp/internship-0.0.1-SNAPSHOT.jar"]