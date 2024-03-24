package outout.unit.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import outout.model.User;
import outout.repository.AccountRepository.IAccountRepository;
import outout.services.AuthenticationService.IAuthenticationService;
import outout.services.AuthenticationService.Impl.AuthenticationService;
import outout.view.AccountCredentials;
import outout.view.AuthenticationToken;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceTests {

    @Mock
    private IAccountRepository accountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;


    @InjectMocks
    private IAuthenticationService authenticationService = new AuthenticationService();

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(authenticationService, "tokenSecret", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQSflKxwRJSMeKKF2QT4fwpMeJf36POk6yJVadQssw5cAAB123");
    }

    @Test
    public void Should_Authenticate_User() {
        //Arrange

        String username = "testme";
        String password = "testme1234567";
        User authUser = new User (){
            {
                setUsername(username);
                setPassword(password);
            }
        };
        AccountCredentials accountCredentials = new AccountCredentials() {
            {
                setUsername(username);
                setPassword(password);
            }
        };

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
        User authUser = new User (){
            {
                setUsername(username);
                setPassword("testme1234567");
            }
        };
        AccountCredentials accountCredentials = new AccountCredentials() {
            {
                setUsername(username);
                setPassword(password);
            }
        };
        Mockito.when(accountRepository.getAccount(Mockito.anyString())).thenReturn(authUser);
        //Act
        AuthenticationToken response =  authenticationService.authenticateUser(accountCredentials);
        //Assert
        Mockito.verify(accountRepository, Mockito.times(1)).getAccount(Mockito.anyString());
        assertThat(response, is(nullValue()));
    }

    @Test
    public void Should_Not_Authenticate_User_With_Wrong_Username() {
        //Arrange
        String username = "unexistentUser";
        String password = "password";
        AccountCredentials accountCredentials = new AccountCredentials() {
            {
                setUsername(username);
                setPassword(password);
            }
        };
        Mockito.when(accountRepository.getAccount(Mockito.anyString())).thenReturn(null);
        //Act
        AuthenticationToken response =  authenticationService.authenticateUser(accountCredentials);
        //Assert
        Mockito.verify(accountRepository, Mockito.times(1)).getAccount(Mockito.anyString());
        assertThat(response, is(nullValue()));
    }

}
