package outout.services.AccountService;

import outout.repository.AccountRepository.Exceptions.AccountRepositoryException;
import outout.view.AccountCredentials;

public interface IAccountService {

    void createAccount(AccountCredentials accountCredentials) throws AccountRepositoryException;


}
