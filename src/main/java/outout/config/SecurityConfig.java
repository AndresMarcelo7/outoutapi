package outout.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.authentication.AuthenticationManagerBeanDefinitionParser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import outout.security.StatelessAuthenticationFilter;

import jakarta.servlet.Filter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    @Autowired
    private Environment environment;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .anyRequest().authenticated()
                )
                .addFilterBefore(statelessAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
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
                    path.startsWith("/account/create"));
        });
    }

    private Filter statelessAuthenticationFilter() {
        return new StatelessAuthenticationFilter(environment.getProperty("token.secret"));
    }
}
