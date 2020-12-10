package agh.alleviation.util;

import agh.alleviation.model.*;
import agh.alleviation.model.user.Customer;
import agh.alleviation.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * This class is responsible for populating the database with sample data at the start of the application.
 * In this moment, the persistence with some external database is not maintained in order not to create consistency problems.
 * Currently the database is created with start of the application and discarded when the application is closed.
 *
 * @author Anna Nosek
 */
@Component
public class DataLoader {

    private final HallService hallService;
    private final MovieService movieService;
    private final OrderService orderService;
    private final SeanceService seanceService;
    private final UserService userService;

    /**
     * Instantiates a new Data loader.
     *
     * @param hallService   the hall service
     * @param movieService  the movie service
     * @param orderService  the order service
     * @param seanceService the seance service
     * @param userService   the user service
     */
    @Autowired
    public DataLoader(HallService hallService,
                      MovieService movieService,
                      OrderService orderService,
                      SeanceService seanceService,
                      UserService userService){

        this.hallService = hallService;
        this.movieService = movieService;
        this.orderService = orderService;
        this.seanceService = seanceService;
        this.userService = userService;

    }


    /**
     * Populate users.
     */
    public void populateUsers(){
        userService.addUser("Mike", "mikeErl", "mike@erlang.com", UserType.ADMIN);
        userService.addUser("Joe", "joeArm", "joe@otp.com", UserType.WORKER);
        userService.addUser("Robert", "rob", "rob@erl.com", UserType.CUSTOMER);
    }

    /**
     * Populate halls.
     */
    public void populateHalls(){
        hallService.addHall(50, 1);
        hallService.addHall(100, 2);
    }


    /**
     * Populate movies.
     */
    public void populateMovies(){
        Movie movie1 = movieService.addMovie("Erlang: the movie", "fantasy", "Great movie", "Joe", "Mike,Robert");
        Movie movie2 = movieService.addMovie("Erlang: the musical", "musical", "Even greater movie", "Mike", "Joe,Robert");
    }

    /**
     * Populate seances.
     */
    public void populateSeances(){

        LocalDateTime date = LocalDateTime.of(2020, Month.DECEMBER, 12, 12, 0);
        double price = 25.00;

        Movie movie1 = movieService.getAllMovies().get(0);

        Hall hall1 = hallService.getAllHalls().get(0);

        seanceService.addSeance(movie1, hall1, date, price);
    }

    /**
     * Populate orders.
     */
    public void populateOrders(){
        Seance seance = seanceService.getAllSeances().get(0);
        Ticket ticket = orderService.addTicket(seance, seance.getPrice());
        Customer customer = userService.getAllCustomers().get(0);
        orderService.addOrder(List.of(ticket), customer);
    }






}
