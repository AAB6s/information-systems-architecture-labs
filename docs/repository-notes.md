# Repository Notes

## Interface Choices

All nine interfaces extend `JpaRepository<Entity, Long>` so each entity has CRUD, list, sorting, pagination, and flush operations.

| Interface | Entity |
| --- | --- |
| IAgenceRepository | Agence |
| IEmployeRepository | Employe |
| IVehiculeRepository | Vehicule |
| IEquipementRepository | Equipement |
| IClientRepository | Client |
| IReservationRepository | Reservation |
| IContratRepository | Contrat |
| IPaiementRepository | Paiement |
| IMaintenanceRepository | Maintenance |

`Repository` is a marker interface. `CrudRepository` provides basic CRUD and returns `Iterable` for collections. `ListCrudRepository` returns `List`. In Spring Data 3, `PagingAndSortingRepository` provides sorting and pagination without CRUD. `JpaRepository` includes those operations and adds JPA methods such as `flush`.

## CRUD Behavior

- `save` creates or updates an entity; use the returned entity.
- `findById` returns an `Optional` that may be empty.
- `deleteById` does nothing when the identifier does not exist.
- Batch deletion bypasses cascade and orphan removal. Neither is configured in this project.

## Verification

The application detected one repository after the initial `CrudRepository` step and nine with the final interfaces. A temporary local check created, read, updated, and deleted a contract. `mvn clean verify` passed. No demo or test code was added to the project.

SonarQube for IDE analyzed 24 Java files and reported 0 issues and 0 security hotspots. The worksheet asks for three corrected anomalies, but the analysis produced no findings to correct.
