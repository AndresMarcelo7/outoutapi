package outout.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextRepository;
import outout.security.StatelessAuthenticationFilter;

import jakarta.servlet.Filter;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private Environment environment;

    @Autowired
    private SecurityContextRepository repository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .anyRequest().authenticated()
                )
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .addFilterBefore(statelessAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers((request) -> {
            String path = request.getServletPath();
            return (path.startsWith("/authenticate") ||
                    path.startsWith("/swagger-ui.html") ||
                    path.startsWith("/swagger") ||
                    path.startsWith("/swagger-ui") ||
                    path.startsWith("/v3/api-docs") ||
                    path.startsWith("/webjars/") ||
                    path.startsWith("/swagger-resources/") ||
                    path.startsWith("/account/create") ||
                    path.startsWith("/error") );
        });
    }


    private Filter statelessAuthenticationFilter() {
        return new StatelessAuthenticationFilter(environment.getProperty("token.secret"), repository);
    }
}
