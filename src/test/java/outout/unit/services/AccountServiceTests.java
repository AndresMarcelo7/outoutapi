package outout.unit.services;

import outout.model.User;
import outout.repository.AccountRepository.Exceptions.AccountRepositoryException;
import outout.repository.AccountRepository.IAccountRepository;
import outout.services.AccountService.IAccountService;
import outout.services.AccountService.Impl.AccountService;
import outout.view.AccountCredentials;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTests {

    @Mock
    private IAccountRepository accountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private IAccountService accountService = new AccountService();

    @Test
    public void Should_Create_Account() throws AccountRepositoryException {
        //Arrange
        Mockito.doNothing().when(accountRepository).createAccount(Mockito.any(User.class));
        AccountCredentials accountCredentials = new AccountCredentials();
        accountCredentials.setUsername("testme");
        accountCredentials.setPassword("testme");
        //Act
        accountService.createAccount(accountCredentials);
        //Assert
        Mockito.verify(accountRepository, Mockito.times(1)).createAccount(Mockito.any(User.class));
    }

    @Test
    public void Should_Not_Create_Account() throws AccountRepositoryException {
        //Arrange
        Mockito.doThrow(new AccountRepositoryException("User Already Exists!")).when(accountRepository).createAccount(Mockito.any(User.class));
        AccountCredentials accountCredentials = new AccountCredentials();
        accountCredentials.setUsername("testme");
        accountCredentials.setPassword("testme");
        //Act
        try {
            accountService.createAccount(accountCredentials);
            fail("Should throw AccountRepositoryException");
        } catch (AccountRepositoryException e) {
            //Assert
            Mockito.verify(accountRepository, Mockito.times(1)).createAccount(Mockito.any(User.class));
        }
    }

    @Test
    public void Should_Not_Create_Account_When_Password_Encode_Fails() throws AccountRepositoryException {
        //Arrange
        Mockito.doThrow(new AccountRepositoryException("Password Encoding Failed!")).when(accountRepository).createAccount(Mockito.any(User.class));
        AccountCredentials accountCredentials = new AccountCredentials();
        accountCredentials.setUsername("testme");
        accountCredentials.setPassword("testme");
        //Act
        try {
            accountService.createAccount(accountCredentials);
        } catch (Exception e) {
            //Assert
            assertEquals(AccountRepositoryException.class, e.getClass());
        }
    }
}
