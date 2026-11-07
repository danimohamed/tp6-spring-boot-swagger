# TP 6 : Spring Boot et Swagger — Documentation d'API avec springdoc-openapi

## Structure du projet : packages `entity`, `repository`, `service`, `controller`, `config` et `pom.xml`
![Structure du projet](images/structure.png)

## Configuration MySQL : `application.properties` + profil `h2` pour lancer sans base
![Configuration de la base de donnees](images/config.png)

## Entite `Student` et `StudentRepository` : `JpaRepository` + requete JPQL `findNbrStudentByYear()`
![Entite et repository](images/entity.png)

## Demarrage : `Started StudentManagementApplication` sur le port `8080`
![Demarrage de l'application](images/startup.png)

## Endpoints REST : `POST /students/save` -> 201, `GET /students/all` -> 200, `GET /students/count` -> 5
![Endpoints REST](images/endpoints.png)

## `GET /students/byYear` : repartition par annee de naissance -> `[[1985,2],[1990,2],[1998,1]]`
![Repartition par annee](images/byYear.png)

## Validation : `POST /students/save` avec un nom vide -> 400 `ProblemDetail`
![Validation des donnees](images/validation.png)

## Swagger UI : documentation generee automatiquement par springdoc-openapi
![Interface Swagger UI](images/swagger-ui.png)

## Execution depuis Swagger : `GET /students/all` -> 200 OK
![Execution d'un endpoint depuis Swagger](images/swagger-execute.png)

## Tests unitaires et d'integration : `Tests run: 11, Failures: 0` — `BUILD SUCCESS`
![Tests unitaires](images/tests.png)