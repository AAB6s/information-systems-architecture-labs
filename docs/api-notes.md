# TP05 — Spring MVC et SpringDoc

## A. Endpoints

Le préfixe `/api` est défini une seule fois par `server.servlet.context-path`. Les contrôleurs dépendent des interfaces de service, injectées par constructeur.

| Contrôleur | Verbe et route | Service appelé | Succès | Erreurs |
| --- | --- | --- | --- | --- |
| AgenceController | GET `/api/agences` | `IAgenceService.findAll()` | 200 | — |
| AgenceController | GET `/api/agences/{id}` | `IAgenceService.findById(id)` | 200 | 404 |
| AgenceController | POST `/api/agences` | `IAgenceService.create(entite)` | 201 | 400 |
| AgenceController | PUT `/api/agences/{id}` | `IAgenceService.update(id, entite)` | 200 | 400, 404 |
| AgenceController | DELETE `/api/agences/{id}` | `IAgenceService.deleteById(id)` | 204 | 404 |
| EmployeController | GET `/api/employes` | `IEmployeService.findAll()` | 200 | — |
| EmployeController | GET `/api/employes/{id}` | `IEmployeService.findById(id)` | 200 | 404 |
| EmployeController | POST `/api/employes` | `IEmployeService.create(entite)` | 201 | 400 |
| EmployeController | PUT `/api/employes/{id}` | `IEmployeService.update(id, entite)` | 200 | 400, 404 |
| EmployeController | DELETE `/api/employes/{id}` | `IEmployeService.deleteById(id)` | 204 | 404 |
| VehiculeController | GET `/api/vehicules` | `IVehiculeService.findAll()` | 200 | — |
| VehiculeController | GET `/api/vehicules/{id}` | `IVehiculeService.findById(id)` | 200 | 404 |
| VehiculeController | POST `/api/vehicules` | `IVehiculeService.create(entite)` | 201 | 400 |
| VehiculeController | PUT `/api/vehicules/{id}` | `IVehiculeService.update(id, entite)` | 200 | 400, 404 |
| VehiculeController | DELETE `/api/vehicules/{id}` | `IVehiculeService.deleteById(id)` | 204 | 404 |
| EquipementController | GET `/api/equipements` | `IEquipementService.findAll()` | 200 | — |
| EquipementController | GET `/api/equipements/{id}` | `IEquipementService.findById(id)` | 200 | 404 |
| EquipementController | POST `/api/equipements` | `IEquipementService.create(entite)` | 201 | 400 |
| EquipementController | PUT `/api/equipements/{id}` | `IEquipementService.update(id, entite)` | 200 | 400, 404 |
| EquipementController | DELETE `/api/equipements/{id}` | `IEquipementService.deleteById(id)` | 204 | 404 |
| ClientController | GET `/api/clients` | `IClientService.findAll()` | 200 | — |
| ClientController | GET `/api/clients/{id}` | `IClientService.findById(id)` | 200 | 404 |
| ClientController | POST `/api/clients` | `IClientService.create(entite)` | 201 | 400 |
| ClientController | PUT `/api/clients/{id}` | `IClientService.update(id, entite)` | 200 | 400, 404 |
| ClientController | DELETE `/api/clients/{id}` | `IClientService.deleteById(id)` | 204 | 404 |
| ReservationController | GET `/api/reservations` | `IReservationService.findAll()` | 200 | — |
| ReservationController | GET `/api/reservations/{id}` | `IReservationService.findById(id)` | 200 | 404 |
| ReservationController | POST `/api/reservations` | `IReservationService.create(entite)` | 201 | 400 |
| ReservationController | PUT `/api/reservations/{id}` | `IReservationService.update(id, entite)` | 200 | 400, 404 |
| ReservationController | DELETE `/api/reservations/{id}` | `IReservationService.deleteById(id)` | 204 | 404 |
| ContratController | GET `/api/contrats` | `IContratService.findAll()` | 200 | — |
| ContratController | GET `/api/contrats/{id}` | `IContratService.findById(id)` | 200 | 404 |
| ContratController | POST `/api/contrats` | `IContratService.create(entite)` | 201 | 400 |
| ContratController | PUT `/api/contrats/{id}` | `IContratService.update(id, entite)` | 200 | 400, 404 |
| ContratController | DELETE `/api/contrats/{id}` | `IContratService.deleteById(id)` | 204 | 404 |
| PaiementController | GET `/api/paiements` | `IPaiementService.findAll()` | 200 | — |
| PaiementController | GET `/api/paiements/{id}` | `IPaiementService.findById(id)` | 200 | 404 |
| MaintenanceController | GET `/api/maintenances` | `IMaintenanceService.findAll()` | 200 | — |
| MaintenanceController | GET `/api/maintenances/{id}` | `IMaintenanceService.findById(id)` | 200 | 404 |
| MaintenanceController | POST `/api/maintenances` | `IMaintenanceService.create(entite)` | 201 | 400 |
| MaintenanceController | PUT `/api/maintenances/{id}` | `IMaintenanceService.update(id, entite)` | 200 | 400, 404 |
| MaintenanceController | DELETE `/api/maintenances/{id}` | `IMaintenanceService.deleteById(id)` | 204 | 404 |

`GlobalExceptionHandler` traduit `ResourceNotFoundException` en 404 et `IllegalArgumentException` en 400. Le corps contient `statut`, `message` et `horodatage`. Un POST sur `/api/paiements` reçoit 405 : aucune méthode ne le prend en charge.

Les règles utilisent les attributs du modèle : libellé pour Équipement, dates pour Maintenance ; ces entités ne possèdent respectivement ni quantité ni coût.

## B. Annotations Jackson

| Association | Annotation | Justification |
| --- | --- | --- |
| Contrat.paiements | `@JsonManagedReference("contrat-paiements")` | Les paiements sont reçus et renvoyés dans le contrat. |
| Paiement.contrat | `@JsonBackReference("contrat-paiements")` | Jackson renseigne le contrat lors de la lecture du JSON imbriqué, puis masque ce retour à la sérialisation. |
| Agence.employes, Agence.vehicules | `@JsonIgnore` sur les listes | Employé et Véhicule sont créés séparément avec leur agence. |
| Client.reservations | `@JsonIgnore` | La réservation reçoit son client par son identifiant réel, `idClient`. |
| Vehicule.maintenances, Vehicule.reservations | `@JsonIgnore` | Maintenance et Réservation conservent leur référence propriétaire vers le véhicule. |
| Equipement.vehicules | `@JsonIgnore` | Évite le retour de la collection inverse vers les véhicules. |
| Reservation.contrat | `@JsonIgnore` | Évite la boucle Contrat → Réservation → Contrat. |

`@JsonBackReference` représente le retour d'une paire nommée avec `@JsonManagedReference` ; Jackson reconnecte l'enfant à son parent. `@JsonIgnore` ignore une propriété à la lecture et à l'écriture. Le placer sur `Reservation.client` empêcherait donc de recevoir le client.

`Contrat.paiements` utilise `cascade = ALL` et `orphanRemoval = true`. Un POST de contrat persiste ses deux paiements. La modification charge le contrat existant et recopie ses seuls attributs simples : la collection est conservée. Supprimer le contrat supprime ses paiements. Aucun contrôleur n'accède directement à un repository.

## C. Postman et Swagger

- **Postman** conserve les requêtes et vérifie les résultats avec `pm.test`. La collection AutoLoc contient les opérations nominales, les erreurs et le scénario complet de cascade. L'identifiant renvoyé est mémorisé dans l'environnement, par exemple `contratId`, puis réutilisé dans les URL.
- **SpringDoc** génère la description OpenAPI à partir du code. **Swagger UI** l'affiche et permet d'essayer les opérations : `@Tag` groupe les neuf contrôleurs, `@Operation` décrit les méthodes, `@ApiResponse` les codes et `@Parameter` l'identifiant.

Exemple : Postman vérifie que PUT conserve les deux paiements puis que DELETE les supprime. Swagger affiche les réponses 200/400/404 de ce PUT sans maintenir une documentation séparée.

Spring Boot 3.5.16 et SpringDoc 2.8.17 suivent la [matrice de compatibilité SpringDoc](https://springdoc.org/v2/#what-is-the-compatibility-matrix-of-springdoc-openapi-with-spring-boot) et la branche 2.8.x demandée.

Pour la vérification locale, Oracle occupe 8080 : l'application est lancée sur 8081 et `postman/local.postman_environment.json` utilise `baseUrl = http://localhost:8081`. Le port Spring Boot par défaut reste 8080.

## D. Anomalies SonarQube

Vérification hors interface avec le moteur et l'analyseur Java installés par SonarQube for IDE : comparaison d'une variante temporaire de ContratController avec les contrôleurs finaux, sur les règles S6813, S1128 et S4684.

| Règle | Anomalie | Correction |
| --- | --- | --- |
| S6813 | Injection du service par attribut dans une variante temporaire du contrôleur. | Champ `final` et constructeur généré par `@RequiredArgsConstructor`. |
| S1128 | Import `java.util.Map` inutilisé dans la même variante. | Suppression de l'import. |

Les contrôleurs finaux ne présentent plus S6813 ni S1128. Les 16 recommandations S4684 concernent les paramètres `@RequestBody` : les entités restent volontairement exposées comme demandé dans cet atelier. Elles seront remplacées par des objets de transfert de données (DTO) au chapitre 10.

## E. Questions

1. **Réception et routage.** Le `DispatcherServlet` reçoit les requêtes. Les correspondances enregistrées par Spring associent le chemin et le verbe HTTP à une méthode ; Spring lit ensuite les paramètres et le JSON avant de l'appeler.
2. **Dépendance du contrôleur.** `IContratService` expose le contrat métier sans imposer une implémentation. Le contrôleur traite HTTP, le service les règles métier et le repository la persistance.
3. **Codes HTTP.** 201 indique une création ; 204 une suppression réussie sans corps. Une ressource absente est un cas prévu : l'exception métier est traduite en 404, au lieu d'une erreur interne 500.
4. **Paiement.** Ses modifications passent par le contrat et les cascades. Son contrôleur n'expose que les lectures ; POST renvoie 405.
5. **Entités exposées.** L'API dépend du modèle de persistance et peut exposer des champs internes, des proxys ou des cycles. Les DTO sépareront ces modèles. `Contrat.paiements` est une composition créée avec son parent : la paire de références permet de recevoir les enfants et de renseigner leur contrat. `Agence.vehicules` est une liste inverse : les véhicules sont créés séparément avec une agence existante, donc la liste est ignorée.
