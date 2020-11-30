package agh.alleviation.util;

import agh.alleviation.model.*;
import agh.alleviation.model.user.Customer;
import agh.alleviation.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * @author Anna Nosek
 * This class is responsible for populating the database with sample data at the start of the application.
 * In this moment, the persistence with some external database is not maintained in order not to create consisency problems.
 * Currently the database is created with start of the application and discarded when the application is closed.
 */
@Component
public class DataLoader {

    private HallService hallService;
    private MovieService movieService;
    private OrderService orderService;
    private SeanceService seanceService;
    private UserService userService;

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


    public void populateUsers(){
        userService.addUser("Mike", "mikeErl", "mike@erlang.com", UserType.ADMIN);
        userService.addUser("Joe", "joeArm", "joe@otp.com", UserType.WORKER);
        userService.addUser("Robert", "rob", "rob@erl.com", UserType.CUSTOMER);
    }

    public void populateHalls(){
        hallService.addHall(50, 1);
        hallService.addHall(100, 2);
    }


    public void populateMovies(){
        Genre genre1 = movieService.addGenre("fantasy");
        Genre genre2 = movieService.addGenre("musical");

        Movie movie1 = movieService.addMovie("Erlang: the movie", genre1);
        Movie movie2 = movieService.addMovie("Erlang: the musical", genre2);
    }

    public void populateSeances(){

        Date date = new Date(2020, Calendar.DECEMBER, 12, 12, 0);
        double price = 25.00;

        Movie movie1 = movieService.getAllMovies().get(0);

        Hall hall1 = hallService.getAllHalls().get(0);

        seanceService.addSeance(movie1, hall1, date, price);
    }

    public void populateOrders(){
        Seance seance = seanceService.getAllSeances().get(0);
        Ticket ticket = orderService.addTicket(seance, seance.getPrice());
        Customer customer = userService.getAllCustomers().get(0);
        orderService.addOrder(List.of(ticket), customer);

    }






}
