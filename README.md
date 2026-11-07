# AutoLoc — TP06

DTO pour les réponses, validation des entrées et gestion des erreurs dans `GlobalExceptionHandler`.

Pour tester sans MySQL :

```bash
mvn clean verify
mvn spring-boot:run "-Dspring-boot.run.profiles=demo" "-Dspring-boot.run.arguments=--server.port=18081"
```

API : `http://localhost:18081/api` · [Collection Postman](postman/AutoLoc-TP06.postman_collection.json) · [Environnement](postman/tp06-demo.postman_environment.json)
