
# Series mini project
The purpose of this project was to learn how to implement a JPA project.

## Requirements
- Writing a JPA project with the following entities:
  - Series
  - Season
  - Episode
- Each entity should contain at least 3 attributes (id, name, title, length, releaseDate, etc), and entities should be connected  together based on these relations:
  - 1 Series has N Seasons
  - 1 Season has N Episodes
- using these JPA annotations: @Transient, @Enumerated, @ElementCollection.
- fill up the entities in a CommandLineRunner.

## Used technologies
- Spring Boot
- JUnit 4
- H2


