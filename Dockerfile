FROM openjdk:8

EXPOSE 8080

ADD target/tutors-manager-demo.jar tutors-manager-demo.jar
ENTRYPOINT ["java", "-jar", "/tutors-manager-demo.jar"]


