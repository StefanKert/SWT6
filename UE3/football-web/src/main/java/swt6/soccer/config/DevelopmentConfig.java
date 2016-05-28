package swt6.soccer.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import swt6.soccer.config.DatabaseInitializer;

@Configuration
@Profile("dev")
public class DevelopmentConfig {
    @Bean
    public CommandLineRunner databaseInitializer() {
        return new DatabaseInitializer();
    }
}
