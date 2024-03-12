
FROM openjdk:17-jdk-alpine


WORKDIR /Capital-audit-Backend


COPY /Capital-audit-Backend/build/libs/*.jar /Capital-audit-Backend/CapitalAuditBackend-0.0.1-SNAPSHOT-plain.jar


EXPOSE 8090


CMD ["java", "-jar", "CapitalAuditBackend-0.0.1-SNAPSHOT-plain.jar"]
