package outout.services.AccountService.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import outout.model.User;
import outout.repository.AccountRepository.Exceptions.AccountRepositoryException;
import outout.services.AccountService.IAccountService;
import outout.repository.AccountRepository.IAccountRepository;
import outout.view.AccountCredentials;


@Component("accountService")
public class AccountService implements IAccountService {

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void createAccount(AccountCredentials accountCredentials) throws AccountRepositoryException {
        User user = new User();
        user.setUsername(accountCredentials.getUsername());
        user.setPassword(passwordEncoder.encode(accountCredentials.getPassword()));
        accountRepository.createAccount(user);
    }


}
