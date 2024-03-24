package outout.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import outout.model.User;
import outout.repository.AccountRepository.Exceptions.AccountRepositoryException;
import outout.services.AccountService.IAccountService;
import outout.view.AccountCreationResult;
import outout.view.AccountCredentials;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/account/create")
public class CreateAccountController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private IAccountService accountService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ResponseEntity<AccountCreationResult> createAccount(@Valid @RequestBody AccountCredentials ac) {
        AccountCreationResult result = new AccountCreationResult();
        List<String> errors = new ArrayList<>();
        try {
            accountService.createAccount(ac);
            result.setSuccessful(true);
        } catch (AccountRepositoryException e) {
            result.setSuccessful(false);
            errors.add(e.getMessage());
            result.setErrors(errors);
            return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result,HttpStatus.OK);

    }

}
