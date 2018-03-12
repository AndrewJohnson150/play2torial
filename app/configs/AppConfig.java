package configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//tells it where to scan and inject.
// If injection is not working, check to make sure  the
// directory is here!
@Configuration
@ComponentScan({
    "controllers", "services"
})

public class AppConfig {

}