# Policy Management Web App

Spring Boot/JPA customer, policy and claim API with validated inputs, paginated policies, AGENT/ADJUSTER/ADMIN authorization, an explicit claim state machine, and unit tests. Runs on H2 for local use or PostgreSQL through configuration.

## Run

Java 17 and Maven: `mvn test`, then `mvn spring-boot:run`. Set AGENT_PASSWORD, ADJUSTER_PASSWORD and ADMIN_PASSWORD before use outside a local demo. HTTP Basic credentials are required on all /api endpoints.
