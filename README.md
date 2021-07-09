
## Eclipse Vert.x integration to a Spring Boot Application 


IMPLEMENTED
-----------
● People API 
  Get all Users
  ```
  GET {baseUrl}/api/users  
  ```
  Get person by id
  ```
  GET {baseUrl}/api/users/{id} 
  ```
  
  Add person
  ```
  POST {baseUrl}/api/users 
  ```
  
  POST Request Body Example
   ```JSON
     {
        "firstName": "Althea",
        "lastName": "Mabbe",
        "userName": "username",
        "password": "secret",
        "role": "personnel"
     }
   
   ```
  
● Books API
  Get all books
  ```
  GET {baseUrl}/api/books 
  ```
  Get book by id
  ```
  GET {baseUrl}/api/books/{id}  
  ```
  Add Book
  ```
  POST {baseUrl}/api/books
  ```  
   
  POST Request Body Example
  
   ```JSON
   {
        "isbn": 792272,
        "title": "Applause",
        "author": "Darby Voelker",
        "publisher": "Reilly, Moore and Huels"
    }
   ```

● Lending API
   Get all lending entries
   ```
   GET    {baseUrl}/api/lendentry 
   ```
   Get lending entry by id (A person cannot lend the same book twice)
   ```
   GET    {baseUrl}/api/lendentry/{bookId}/{userId} 
   ```
   Add lending entry
   ```
   POST   {baseUrl}/api/lendentry/ 
   ```
   
   POST Request Body Example
   
   ```JSON
   {
     "personId":4,
     "bookId":12
   }
  ```
   
 

● Dockerize app
  ```
  docker-compose -f Docker_compose.yml up
  ```

TO DO
-------
● Authentication


App Configuration
-----------------
```
Server port : 8088
```

Application Jar
---------
```
/targer/assignment-0.0.1-SNAPSHOT.jar
```

Mock Data 
----------
```
Mock_Data.sql
```
