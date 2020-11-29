package agh.alleviation.util;

import agh.alleviation.model.user.Admin;
import agh.alleviation.model.user.User;
import agh.alleviation.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author Anna Nosek
 * This class is responsible for populating the database at the start of the application.
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



}
