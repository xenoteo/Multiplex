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
 * This application provides functionalities for a cinema multiplex. Written in Java 15.
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
 * Additionally in order to introduce dynamic binding between view and model components,
 * the fields in the @Entity classes were created as instances of JavaFx Property class.
 * In order to maintain compliance with JPA mapping requirements, the Externalizable interface
 * was implemented manually. The columns of tables in database no longer map to the specific class fields,
 * but to their getters and setters.
 * <p>
 * Project can be run by executing ./gradlew bootRun command in root folder.
 * </p>
 * <p>
 * Current database state (the database is created upon starting the app and discarded upon closing the app):
 * <a href = "http://localhost:8080/h2-console">http://localhost:8080/h2-console</a>
 * </p>
 * <p>
 * Roles of team members (m1):
 * <ul>
 *     <li>Kamil Krzempek - view design, creation of presentation and controller layers, setting up dependencies</li>
 *     <li>Ksenia Fiodarava - model design, creation of entities, repositories and services, maintaining UML class diagram</li>
 *     <li>Anna Nosek - model design, creation of entities, repositories and services, coordination of documentation, database integration, team coordination</li>
 * </ul>
 * All team members were actively participating in creation of application structure.
 * <p>
 * Class Diagram:
 * <img alt="class diagram" src="Model.png" style="width:100%">
 * </p>
 *
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = { "agh.alleviation.persistence" })
@EntityScan(basePackages = { "agh.alleviation.model" })
public class AlleviationApplication {


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        //		SpringApplication.run(AlleviationApplication.class, args);
        Application.launch(CinemaApp.class, args);
    }

    /**
     * Fx weaver fx weaver.
     *
     * @param applicationContext the application context
     * @return the fx weaver
     */
    @Bean
    public FxWeaver fxWeaver(ConfigurableApplicationContext applicationContext) {
        return new SpringFxWeaver(applicationContext);
    }

    /**
     * Demo command line runner.
     *
     * @param dataLoader the data loader
     * @return the command line runner
     */
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

	//TODO: refreshing
    //TODO: context menu
    //TODO: removing halls
}
