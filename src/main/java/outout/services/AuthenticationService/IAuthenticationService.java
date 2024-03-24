package outout.services.AuthenticationService;

import outout.view.AccountCredentials;
import outout.view.AuthenticationToken;

public interface IAuthenticationService {
    public AuthenticationToken authenticateUser(AccountCredentials accountCredentials);

}
