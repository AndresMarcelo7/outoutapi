package outout.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class StatelessAuthenticationFilter extends OncePerRequestFilter {

    public String tokenSecret;

    private final SecurityContextRepository repository;

    public StatelessAuthenticationFilter(final String tokenSecret, SecurityContextRepository repository) {
        this.tokenSecret = tokenSecret;
        this.repository = repository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = request;
        String token = httpServletRequest.getHeader("X-AUTH-TOKEN");
        UsernamePasswordAuthenticationToken authentication;
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        try {
            Jws<Claims> jsonWebToken = Jwts.parser()
                    .setSigningKey(tokenSecret)
                    .build().parseClaimsJws(token);

            String username = jsonWebToken.getBody().getSubject();
            UserAuthentication user = new UserAuthentication(username);
            authentication = new UsernamePasswordAuthenticationToken(user,null, user.getAuthorities());
        }
        catch(Exception exc) {
            authentication = null;
        }
        context.setAuthentication(authentication);
        this.repository.saveContext(context, httpServletRequest, response);
        SecurityContextHolder.setContext(context);
        filterChain.doFilter(request, response);
    }
}
