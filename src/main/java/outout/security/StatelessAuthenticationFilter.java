package outout.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

public class StatelessAuthenticationFilter extends GenericFilterBean {

    public String tokenSecret;
    private SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();

    public StatelessAuthenticationFilter(final String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("X-AUTH-TOKEN");
        Authentication authentication;
        try {
            Jws<Claims> jsonWebToken = Jwts.parser()
                    .setSigningKey(tokenSecret)
                    .build().parseClaimsJws(token);

            String username = jsonWebToken.getBody().getSubject();
            authentication = new UserAuthentication(username);
        }
        catch(Exception exc) {
            authentication = null;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

}
