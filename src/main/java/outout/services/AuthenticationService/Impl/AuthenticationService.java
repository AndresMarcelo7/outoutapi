package outout.services.AuthenticationService.Impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import outout.model.User;
import outout.repository.AccountRepository.IAccountRepository;
import outout.services.AccountService.IAccountService;
import outout.services.AuthenticationService.IAuthenticationService;
import outout.view.AccountCredentials;
import outout.view.AuthenticationToken;

@Component("authenticationService")
public class AuthenticationService implements IAuthenticationService {
    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${token.secret}")
    private String tokenSecret;
    @Override
    public AuthenticationToken authenticateUser(AccountCredentials accountCredentials) {
        User user = accountRepository.getAccount(accountCredentials.getUsername());
        if (user != null && passwordEncoder.matches(accountCredentials.getPassword(), user.getPassword())) {
            AuthenticationToken authenticationToken = new AuthenticationToken();
            String jwt = Jwts.builder().signWith(SignatureAlgorithm.HS512, tokenSecret)
                    .setSubject(accountCredentials.getUsername())
                    .compact();
            authenticationToken.setToken(jwt);
            return authenticationToken;
        } else {
            return null;
        }
    }
}
