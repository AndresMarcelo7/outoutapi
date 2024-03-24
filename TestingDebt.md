# Testing Debt Documentation

## Overview
This project contains only integration tests for controller layer, due to high coupling between the controller and the service layer. The service layer is not tested, and the integration tests are not implemented.

> [!NOTE] 
> :mega: Given this situation, it is necessary to do a refactoring to decouple the controller and the service layer, and implement integration tests.
> Even though there are no tests for the service layer, the project contains a good coverage.
![img.png](img/CoverageBefore.png)

## Testing Debt :gear:
- Integration tests are implemented but are not clear.
- There are no Unit tests that cover service or model layer.
- There are no performance tests
- There are no security tests (Injections, XSS, etc)

> [!IMPORTANT]
> For this exersice, we are going to focus on unit testing service layer and complete the coverage on some model classes.

## Testing Strategy :shield:
- **Refactoring**: Refactor the code to decouple the controller, the service and the repository layer to be able to make unit tests.

- **Unit Testing**: Implement unit tests for the service (Business logic) layer.

## Results :chart_with_upwards_trend:
- **Refactoring**: The refactoring was successful, and the controller and the service layer are decoupled. you can see that in [services](src%2Fmain%2Fjava%2Foutout%2Fservices) folder and in [repository](src%2Fmain%2Fjava%2Foutout%2Frepository) folder, there are injected using Springs `@Autowired` Dependency Injection.
- **Unit Testing**: I implemented unit tests for the service layer in [unit tests](src%2Ftest%2Fjava%2Foutout%2Funit), and we improved the overall coverage. ![img.png](img/CoverageAfterTestUpdates.png)
> [!NOTE]
> The service layer is tested using `Mockito` and `JUnit` for the repository layer.
  - In [AccountServiceTests.java](src%2Ftest%2Fjava%2Foutout%2Funit%2Fservices%2FAccountServiceTests.java) The `AccountService` is tested to validate the business logic: Create account only if the username does not exist, and the password is not empty.
  - In [AuthenticationServiceTests.java](src%2Ftest%2Fjava%2Foutout%2Funit%2Fservices%2FAuthenticationServiceTests.java) The `AuthenticationService` is tested to validate the business logic: Authenticate user only if the username and password are correct.
  - In [SuggestionServiceTests.java](src%2Ftest%2Fjava%2Foutout%2Funit%2Fservices%2FSuggestionServiceTests.java) The `SuggestionService` is tested to validate the business logic: Create a suggestion only if the restaurant has < 2 reviews and if the user has not reviewed the restaurant before.
- **Integration Testing**: I updated the integration tests for the controller layer in [integration tests](src%2Ftest%2Fjava%2Foutout%2Fintegration), and now, the integration tests have more sense and are more clear validating status codes.
