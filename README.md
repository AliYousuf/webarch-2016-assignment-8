# Integration between Java EE and AngularJS

1.	INTRODUCTION

The goal of this assignment is to develop a small and simple application which shows an integration between Java EE 7 and AngularJS.

The application allows to handle a very simple library database where it is possible to add authors, add books with its own author, see the books present in the DB and delete a book.

AngularJS was used to display the data, extending the traditional HTML with additional custom tag attributes.

The application was written along the lines shown in this example:
http://www.radcortez.com/java-ee-7-with-angular-js-crud-rest-validations-part-2/


2.	IMPLEMENTATION

The application is composed of two entities class: **Author** and **Book**. These entities allow to model an object class into a database table by using the annotation @Entity in order to connect to the database table with the same name (or create it if it is not here yet).
For this reason, the **persistence.xml** file is essential to connect to the DB:
 
```xml
<persistence-unit name="manager">
  <jta-data-source>java:/LibraryDS</jta-data-source>
  <properties>
    <property name="hibernate.hbm2ddl.auto" value="update" />
  </properties>
</persistence-unit>
 ```
 
However, the model supported by the service includes:

```java
public class Book {
  Long id;
  String title;
  double price;
  Author author;
}
public class Author {
  Long idAuthor,
  String name;
}
```

On the other hand, the **AuthorResource** and **BookResource** classes expose the REST services respectively at the URL **resources/authors** and **resources/books**, and implement few operations.

**AuthorResource** implements the **@POST** operation **saveAuthor()** which adds a new author to the DB, and the **@GET** operation **listAuthors()** which returns the list of all the authors in JSON format, as can be seen in the picture:

```java
@GET
@Produces(MediaType.APPLICATION_JSON)
public List<Author> listAuthors() {
  Query query = entityManager.createQuery("SELECT a FROM Author a");
  return query.getResultList();
}
 ```
 
**BookResource** implements the **@GET** operation **listBooks()** which returns the list of all the books in JSON format, the **@DELETE** operation **deleteBook()** and the **@POST** operation **saveBook()** which adds a new book to the DB:

```java
@POST
public void saveBook(Book book) {
  Book bookToSave = new Book();
  bookToSave.setTitle(book.getTitle());
  bookToSave.setPrice(book.getPrice());
  bookToSave.setAuthor(book.getAuthor());
  entityManager.persist(book);
}
 ```
 
The user interface is split in four different sections and each of which is managed by its own Angular controller defined in the **library.js** file.

The four sections are the feedback messages sections, the form to add new authors, the form to add new books and the table where the books available are displayed.

Let’s see in detail one of these sections: the books available table.

The table is declared in the **index.html** file in this way:
 
```html
<div ng-controller="BookListController">
  <h3>Books available</h3>
  <table class="table" ng-init="refreshTable()">
    <tr>
      <th>Title</th>
      <th>Price</th>
      <th>Author</th>
      <th></th>
    </tr>
    <tr ng-repeat="book in books">
      <td>{{book.title}}</td>
      <td>{{book.price}} €</td>
      <td>{{book.author.name}}</td>
      <td><button class="btn btn-danger" ng-click="removeBook(book)">Delete</button></td>
    </tr>
  </table>
</div>
```
 
As can be seen, this section refers to the **BookListController** implemented in the **library.js** file:
 
```javascript
app.controller('BookListController', function ($scope, $rootScope, $http) {
	 	
  $scope.refreshTable = function () {
    $http.get('resources/books').success(function(data) {
      $scope.books = data;
    });    
  };
	
  $scope.$on('refreshTable', function () {
    $scope.refreshTable();
   });
	
  $scope.removeBook = function (book) {
    $http.delete('resources/books/'+book.id)
    .success(function (data) {
      $rootScope.$broadcast('bookDeleted');
      $scope.refreshTable();
    })
    .error(function (data) {
      $rootScope.$broadcast('error');
    });
  };
	
});
```
 
First of all, the table is initialized calling the **refreshTable()** function which sends a HTTP GET request at the URL **resources/books**. The request returns a list of books which will be assigned to the books property of the scope.

After that, **ng-repeat** allows to display all the information for each book present in books.

Each book has a delete button that calls the **removeBook(book)** function in the **BookListController** which sends an HTTP DELETE request to the service. If the request succeed calls the **bookDeleted()** function in the **alertMessagesController** which will display the message: **Book deleted successfully!**. Finally, the table will be refreshed.

However, the UI was implemented using Bootstrap and this is the final result of the entire application:
 


3.	DEPLOYMENT
To run the application is important to configure correctly the environment.

First of all, it is necessary to run Apache Derby typing the following command:

```
java  –jar  DERBY_HOME/lib/derbyrun.jar  server  start  &
 ```
 
After that, it is necessary to create a new JDBC Datasource in WildFly using the **derbyclient.jar** driver.
 
Moreover, the connection URL ensure the connection to Derby and the creation of a DB named **LibraryDB** whether it is not already present.
 
Now the war file **library-angular.war** have to be copied in the deployments folder of WildFly. Launching the **standalone.bat** (or **standalone.sh**) file, WildFly starts and deploys the file.

Now, connecting to the address http://localhost:8080/library-angular/ is possible to use the application.
