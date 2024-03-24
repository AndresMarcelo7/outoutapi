package outout.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import outout.model.User;
import outout.services.AuthenticationService.IAuthenticationService;
import outout.view.AccountCredentials;
import outout.view.AuthenticationToken;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/authenticate")
public class AuthenticationController {

    @Autowired
    private IAuthenticationService authenticationService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<AuthenticationToken> authenticate(@Valid @RequestBody AccountCredentials ac) {
            AuthenticationToken authenticationToken = authenticationService.authenticateUser(ac);
            if (authenticationToken == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            } else {
            return new ResponseEntity<>(authenticationToken, HttpStatus.OK);
        }
    }

}
