# Alleviation
A desktop application for managing the cinema multiplex. Project for "Object-Oriented Technologies" course.

## Description
A desktop application for managing the cinema multiplex.  
  
The users can register and login having different roles and authorities. The movies repertuar is shown for users and there is a possibility to buy tickets for movies. The movie can be liked or disliked by the user. The search of movies and filtering the results is available. There are also statistics available.

## Tech used
- JDK15
- Spring Boot
- H2 Database Engine
- PostgreSQL
- JavaFX
- JavaDocs

## Execution of the project

### Prerequisites
- JDK version 15

### Running the project
Project can be run by executing `./gradlew bootRun` command in the root folder.

## Database
Current database state (the database is created upon starting the app and discarded upon closing the app): http://localhost:8080/h2-console.

## Documentation
Documentation is generated from JavaDocs, and can be accessed by opening `./doc/index.html` file in a browser.

## Features

### Roles defined:
- Customer
- Cinema worker
- Administrator

### General features for all the roles:
- Login and registration
- Viewing the movies repertuar (the list of movie seances available)
- Search for the movie and filtering the results
- Buying tickets for the seances
- Viewing the basket
- Viewing the orders history
- Access to statistics measured by the number of tickets bought. There are available statistics by:
  - Movies 
  - Movie genres
  - Users
  - Seance time
  - Days of week
  - Months

### Additional features available for cinema workers and admins:
- Viewing the whole movie list, adding new movies as well as editing or deleting them.
- Adding/editing/removing the seances.
- Viewing the hall list, adding/editing/removing the halls.

### Additional features available for admins only:
- Viewing the user list, adding/editing/removing the users.

## Screenshots
All screenshots made from an admin account. 
  
### Login/Register window.
The first view of the application. <br>
<a href="https://imgur.com/94R9ooL"><img src="https://i.imgur.com/94R9ooL.png" title="source: imgur.com" width=25%/></a>
  
### Viewing the seance list
<a href="https://imgur.com/IhyRDjg"><img src="https://i.imgur.com/IhyRDjg.png" title="source: imgur.com" /></a>
  
### Viewing the basket
<a href="https://imgur.com/zDvBHay"><img src="https://i.imgur.com/zDvBHay.png" title="source: imgur.com" /></a>
  
### Viewing the statistics
Statistics by movie. <br>
<a href="https://imgur.com/glhSs9D"><img src="https://i.imgur.com/glhSs9D.png" title="source: imgur.com" /></a>
  
### Viewing the hall list
<a href="https://imgur.com/8oms2NN"><img src="https://i.imgur.com/8oms2NN.png" title="source: imgur.com" /></a>
  
### Viewing the user list
<a href="https://imgur.com/NBlSmN1"><img src="https://i.imgur.com/NBlSmN1.png" title="source: imgur.com" /></a>
