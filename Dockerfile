FROM openjdk:11
ADD /target/demoWithTests-2.7.3.jar demo-hillel-app.jar

ENTRYPOINT ["java", "-jar", "demo-hillel-app.jar"]