# AutoLoc — TP05

Java 17 · Spring Boot 3.5.16 · SpringDoc 2.8.17 · MySQL (`localhost:3307`, `autoloc_db`).

Définir `AUTOLOC_DB_PASSWORD`, puis :

```bash
mvn spring-boot:run "-Dspring-boot.run.arguments=--server.port=8081"
```

API : `http://localhost:8081/api` · Swagger : `/api/swagger-ui.html` · OpenAPI : `/api/v3/api-docs`.

Importer les deux fichiers de [postman](postman), sélectionner **local**, puis lancer la collection **AutoLoc**.

[Note de l’atelier](docs/api-notes.md) · Avant le rendu : `mvn clean`.
