package agh.alleviation;

import agh.alleviation.presentation.CinemaApp;
import agh.alleviation.util.DataLoader;
import agh.alleviation.util.EmailSender;
import javafx.application.Application;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.spring.SpringFxWeaver;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * This application provides functionalities for a cinema multiplex. Written in Java 15.
 * <p>
 * Project can be run by executing ./gradlew bootRun command in root folder.
 * </p>
 * <p>
 * Current database state (the database is created upon starting the app and discarded upon closing the app):
 * <a href = "http://localhost:8080/h2-console">http://localhost:8080/h2-console</a>
 * </p>
 *
 * <h2>First milestone</h2>
 * Added functionalities:
 * <ul>
 *     <li>model of the application - UML class diagram and package structure</li>
 *     <li>database connectivity - PostgreSQL database accessed through H2 and JPA</li>
 *     <li>database mapping achieved through usage of repositories and services</li>
 *     <li>fist functionalities: adding users and cinema halls</li>
 *     <li>views enabling usage of those functionalities</li>
 * </ul>
 * Introduced patterns:
 * <ul>
 *     <li>Model-View-Whatever (parts of model-view-controller and model-view-presenter)</li>
 *     <li>Repository - accessing database through JPA Repository that enables maintaining persistence with database</li>
 *     <li>Dependency Injection - using Spring @Autowired annotation in services to provide repositories</li>
 *     <li>Inversion Of Control - using Spring Beans</li>
 * </ul>
 * <p>
 * Additionally in order to introduce dynamic binding between view and model components,
 * the fields in the @Entity classes were created as instances of JavaFx Property class.
 * In order to maintain compliance with JPA mapping requirements, the Externalizable interface
 * was implemented manually. The columns of tables in database no longer map to the specific class fields,
 * but to their getters and setters.
 * <p>
 * Roles of team members:
 * <ul>
 *     <li>Kamil Krzempek - view design, creation of presentation and controller layers, setting up dependencies</li>
 *     <li>Ksenia Fiodarava - model design, creation of entities, repositories and services, maintaining UML class diagram</li>
 *     <li>Anna Nosek - model design, creation of entities, repositories and services, coordination of documentation, database integration, team coordination</li>
 * </ul>
 * All team members were actively participating in creation of application structure.
 *
 * <h2>Second milestone</h2>
 * Added functionalities:
 * <ul>
 *     <li>Improved database persistence - enhanced entities, added isActive field to allow deactivation without deletion</li>
 *     <li>Management (adding, editing, deleting) of:
 *          <ul>
 *              <li>movies</li>
 *              <li>halls</li>
 *              <li>seances</li>
 *              <li>users</li>
 *          </ul>
 *     </li>
 *     <li>Authorization - through context menu</li>
 *     <li>Authentication - through login</li>
 *     <li>Registration of customers</li>
 *     <li>Cascading deactivation of objects</li>
 *     <li>Automatic synchronization of the data in views with current database changes performed through the application</li>
 * </ul>
 * Introduced patterns:
 * <ul>
 *     <li>Parts of Composite Design Pattern - CompositeObservable comprises of all active ObservableLists, allows simplified updating of the lists and propagating changes, provides a single source of truth for Components</li>
 *     <li>Observer Design Pattern - ActiveUser's field representing the logged in user of the system is observed by MenuController to determine which functionalities should be visible</li>
 *     <li>Further IOC usage - ActiveUser and ViewControllerManager are now Spring Components</li>
 * </ul>
 * Roles of team members:
 * <ul>
 *     <li>Kamil Krzempek - views and controllers for editing existing data, ObservableComposite refactoring</li>
 *     <li>Ksenia Fiodarava - views and controllers for login and registration, validation </li>
 *     <li>Anna Nosek - model changes, Entity and Service abstract classes, ObservableComposite introduction, major refactoring</li>
 * </ul>
 * All Team members were participating in creating the documentation. All of the mentioned roles were fluid and required involvement of all participants - the introduced ideas were preceded with detailed discussions. Integration of the changes was also done in a team-based fashion.
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = {"agh.alleviation.persistence"})
@EntityScan(basePackages = {"agh.alleviation.model"})
@EnableScheduling
public class AlleviationApplication {

    /**
     * The entry point of the application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Application.launch(CinemaApp.class, args);
    }

    /**
     * fxWeaver initialization
     *
     * @param applicationContext the application context
     * @return the fx weaver
     */
    @Bean
    public FxWeaver fxWeaver(ConfigurableApplicationContext applicationContext) {
        return new SpringFxWeaver(applicationContext);
    }

    /**
     * Populate data for testing
     *
     * @param dataLoader the data loader
     * @return the command line runner
     */
    @Bean
    public CommandLineRunner populateData(DataLoader dataLoader) {
        return args -> {
            dataLoader.populateUsers();
            dataLoader.populateHalls();
            dataLoader.populateMovies();
            dataLoader.populateSeances();
            dataLoader.populateOrders();
        };
    }

    @Bean
    public EmailSender getEmailSender(JavaMailSender javaMailSender) {
        return new EmailSender(javaMailSender);
    }
}

//TODO: fix editing movies, after changing movie name it doesn't show in seances as edited
