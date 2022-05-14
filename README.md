# spring-boot-library by Luigi Seraponte

###The task
Create a library system that is capable of adding new books, removing books and allowing members to loan/return a book. Each book should have an author, category and title. 
For the sake of expediency it is not a requirement to have data persisted when the application is no longer running. 
 
Business rules 
- no need to authenticate the user (we can assume they are members and are already authenticated)
- maximum number of books loaned at any time is 3 per user
- if a member has any outstanding loaned books, they cannot loan any more until all books returned.
- there is only 1 copy of each book
- each book can have more than one category 

###How to run the App
The App code brings in a Maven Wrapper, then there is no need to have a MAVEN_HOME set on the machine. 
To run the application navigate to the root folder of the project from your command line interpreter, then run the following command:
- For Unix-like machines: ```./mvnw spring-boot:run```
- For Windows machines: ```mvnw.cmd spring-boot:run```

If want to make use of the JAR file run the following command: 
- For Unix-like machines: ```./mvnw clean install```
- For Windows machines: ```mvnw.cmd clean install```

this way the library app .jar file will be created in "target" folder.

###How to use the Library App
The App will run on an embedded Tomcat and is reachable on ```localhost:8080``` when run locally.

Attached to the repository there is a Postman Collection to send some requests to the enpoints, in particular the "Populate with Books" request will add few books to the library to get started.

Use the Postman collection to perform operations like:
- Book Addition
- Book Deletion
- Book Loan
- Book Return
- Author Addition

The REST endpoints available are:
- /book/add a POST endpoint which accepts a ```BooksDto``` as request body (more books can be saved at once).
- /book/delete a DELETE endpoint which accepts a ```BookAuthorDto``` as request body to delete a book based on title/author.
- /book/add a POST endpoint which accepts a ```LoanReturnBookDto``` as request body (the DTO contains a String user which would be used to uniquely track loans/returns and to keep track of books loaned).
- /book/add a POST endpoint which accepts a ```LoanReturnBookDto``` as request body (endpoint used to return books to the library. Same DTO as loan endpoint is required).
- /author/add a POST endpoint which accepts an ```Author``` object as request body.
