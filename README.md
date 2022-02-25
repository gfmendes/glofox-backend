### Glofox backend test develop by Guilherme Mendes 

### To start the application:
<code>./mvnw spring-boot:run</code>

### To run application tests:
<code>./mvnw surefire:test</code>

### Start the app to check swagger:
<code>http://localhost:8080/swagger-ui/index.html#/ </code>

### Services description:

#### /api/v1/bookings/

Books a member in a specific class. If the class is full then bad request error is returned. If the member is already registered in a class then a bad request is returned.To keep it simple, a member is unique identified by its 'name'.A class is unique identified as 'name'+'date'. Ex: Pilates2022-24-02

#### /api/v1/classes/
Creates one class of type 'name' per day considering interval 'startDate' and 'endDate'. If the class type already exists in a given day of the interval then this class is not created and it is marked with CONFLICT status in the service response.Requests with CONFLICT classes does not block the service execution, it will create classes that does not conflict.A request fails if start date is greater then end date. To keep it simple, a class is unique identified as name+date. Ex: Pilates2022-24-02

### Architecture highlights
- Spring-boot application compiled with java 17.
- Application is divided in Controller, Service and Repository layers.
- Controller layer exposes the service API and deal with basic DTO validation.
- Service layer translates DTOs into Entities and contains business logic for each domain (classes and booking).
- Repository layer is currently implemented using a Hashmap object.
- UT tests cover Service layer.
- Integration tests cover Controller + Service + Repository layers.

