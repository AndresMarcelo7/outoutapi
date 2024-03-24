package outout.repository.AccountRepository;

import outout.model.User;
import outout.repository.AccountRepository.Exceptions.AccountRepositoryException;


public interface IAccountRepository {

    public void createAccount(User user) throws AccountRepositoryException;

    public User getAccount(String username);

}
