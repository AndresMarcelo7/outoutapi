package outout.unit.services;

import outout.model.User;
import outout.repository.AccountRepository.IAccountRepository;
import outout.services.AuthenticationService.IAuthenticationService;
import outout.services.AuthenticationService.Impl.AuthenticationService;
import outout.view.AccountCredentials;
import outout.view.AuthenticationToken;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTests {

    @Mock
    private IAccountRepository accountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private IAuthenticationService authenticationService = new AuthenticationService();

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(authenticationService, "tokenSecret", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQSflKxwRJSMeKKF2QT4fwpMeJf36POk6yJVadQssw5cAAB123");
    }

    @Test
    public void Should_Authenticate_User() {
        //Arrange
        String username = "testme";
        String password = "testme1234567";
        User authUser = new User() {{
            setUsername(username);
            setPassword(password);
        }};
        AccountCredentials accountCredentials = new AccountCredentials() {{
            setUsername(username);
            setPassword(password);
        }};

        Mockito.when(accountRepository.getAccount(Mockito.anyString())).thenReturn(authUser);
        Mockito.when(passwordEncoder.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        //Act
        authenticationService.authenticateUser(accountCredentials);
        //Assert
        Mockito.verify(accountRepository, Mockito.times(1)).getAccount(Mockito.anyString());
        assertThat(authenticationService.authenticateUser(accountCredentials), is(notNullValue()));
    }

    @Test
    public void Should_Not_Authenticate_User_With_Wrong_Password() {
        //Arrange
        String username = "testme";
        String password = "password";
        User authUser = new User() {{
            setUsername(username);
            setPassword("testme1234567");
        }};
        AccountCredentials accountCredentials = new AccountCredentials() {{
            setUsername(username);
            setPassword(password);
        }};
        Mockito.when(accountRepository.getAccount(Mockito.anyString())).thenReturn(authUser);
        //Act
        AuthenticationToken response = authenticationService.authenticateUser(accountCredentials);
        //Assert
        Mockito.verify(accountRepository, Mockito.times(1)).getAccount(Mockito.anyString());
        assertThat(response, is(nullValue()));
    }

    @Test
    public void Should_Not_Authenticate_User_With_Wrong_Username() {
        //Arrange
        String username = "unexistentUser";
        String password = "password";
        AccountCredentials accountCredentials = new AccountCredentials() {{
            setUsername(username);
            setPassword(password);
        }};
        Mockito.when(accountRepository.getAccount(Mockito.anyString())).thenReturn(null);
        //Act
        AuthenticationToken response = authenticationService.authenticateUser(accountCredentials);
        //Assert
        Mockito.verify(accountRepository, Mockito.times(1)).getAccount(Mockito.anyString());
        assertThat(response, is(nullValue()));
    }
}
