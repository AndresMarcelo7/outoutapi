package outout.integration.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.AbstractServletWebServerFactory;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class TestContext {

    @Bean
    public AbstractServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.setPort(9000);
        //factory.addErrorPages(new ErrorPage(HttpStatus.404, "/notfound.html"));
        return factory;
    }
}
