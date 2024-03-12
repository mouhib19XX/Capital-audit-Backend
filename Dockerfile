
FROM openjdk:17-jdk-alpine


WORKDIR /Capital-audit-Backend


COPY /Capital-audit-Backend/build/libs/CapitalAuditBackend-0.0.1-SNAPSHOT-plain.jar /app/CapitalAuditBackend-0.0.1-SNAPSHOT-plain.jar


EXPOSE 8090


CMD ["java", "-jar", "CapitalAuditBackend-0.0.1-SNAPSHOT-plain.jar"]
