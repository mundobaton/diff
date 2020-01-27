# Diff API
Provides endpoints for uploading and comparing strings.
As a requirement these strings must be base64 encoded.

The string comparison may have different results according the cases where:

* If both strings are equal, the result will inform that.

* If the length of both strings is different, then result will inform that.

* If both strings are not equal but have same length, then the API will inform where the differences are and the differences lengths.

## Endpoints
1- Upload content: This endpoint will upload a base64 encoded string given an id and a side (left/right)

Method: **PUT**

URL: 

``http://hostname:port/v1/diff/:id/:side``

Body: 
```json
{
  "data": "<encoded_string>"
}
```

Returns:

- HttpStatus **200** with body message: **"content saved successfully"** when the content has been uploaded without issues.
- HttpStatus **400** with a detailed message when a validation error occurs.
- HttpStatus **500** on internal server errors

2- Get Diff: This endpoints retrieves the content comparison among two encoded strings uploaded previously.
This endpoint requires both left/right contents to be uploaded, if it's not the case, then it will inform which side is missing.

Method: **GET**

URL: 

``http://hostname:port/v1/diff/:id``

Returns:
- HttpStatus **200** with a detailed result when the content has been diffed successfully.
- HttpStatus **403** when the provided id exist but a side is still missing.
- HttpStatus **404** when the provided id does not exist.
- HttpStatus **500** on internal server errors.

## Requisites for running on local
The API holds the content into a MySQL instance, the database configuration is within the file `database.properties`
Once you have your database configured, please check on the file `db/schema.sql` which contains the database schema. 
Then just simple run the executable Main.java file, which will instantiate a new server and wire everything for you.

### Tests
The project contains both unit and integration tests.
Unit tests are within the usecases package. This package holds our business logic.

Integration tests are within the integration package.
In there you will find a file which initializes the server and performs various tests asserting the response.

### Dependencies

* Java8
* Spark routing: http://sparkjava.com/
* Google Guice (dependency injection): https://github.com/google/guice
* Hikari Connection Pool: https://github.com/brettwooldridge/HikariCP
* Jackson Mapper (https://github.com/FasterXML/jackson)
* Log4j (https://logging.apache.org/log4j/2.x/)
* Lombok (for code generation): (https://projectlombok.org/)
* JUnit (https://junit.org/junit4/)
* Mockito (testing framework): https://site.mockito.org/


