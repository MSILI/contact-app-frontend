FROM openjdk:11
COPY target/contacts-api-0.0.1-SNAPSHOT.jar contacts-backend-0.0.1.jar
ENTRYPOINT ["java","-jar","/contacts-backend-0.0.1.jar"]