package agh.alleviation;

import agh.alleviation.presentation.CinemaApp;
import agh.alleviation.util.DataLoader;
import javafx.application.Application;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.spring.SpringFxWeaver;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * This application provides functionalities for a cinema multiplex.
 * First completed milestone:
 * <ul>
 *     <li>model of the application - UML class diagram and package structure</li>
 *     <li>database connectivity - PostgreSQL database accessed through H2 and JPA</li>
 *     <li>database mapping achieved through usage of repositories and services</li>
 *     <li>fist functionalities: adding users and cinema halls</li>
 *     <li>views enabling usage of those functionalities</li>
 * </ul>
 * Patterns introduced in the first milestone:
 * <ul>
 *     <li>Model-View-Whatever (parts of model-view-controller and model-view-presenter)</li>
 *     <li>Repository - accessing database through JPA Repository that enables maintaining persistence with database</li>
 *     <li>Dependency Injection - using Spring @Autowired annotation in services to provide repositories</li>
 *     <li>Inversion Of Control - using Spring Beans</li>
 * </ul>
 *
 * To see current database state, visit:
 * <url>http://localhost:8080/h2-console</url>
 *
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = { "agh.alleviation.persistence" })
@EntityScan(basePackages = { "agh.alleviation.model" })
public class AlleviationApplication {


    public static void main(String[] args) {
        //		SpringApplication.run(AlleviationApplication.class, args);
        Application.launch(CinemaApp.class, args);
    }

	@Bean
    public FxWeaver fxWeaver(ConfigurableApplicationContext applicationContext) {
        return new SpringFxWeaver(applicationContext);
    }

    @Bean
    public CommandLineRunner demo(DataLoader dataLoader) {
        return args -> {
        	dataLoader.populateUsers();
        	dataLoader.populateHalls();
        	dataLoader.populateMovies();
        	dataLoader.populateSeances();
            dataLoader.populateOrders();
		};
	}
}
