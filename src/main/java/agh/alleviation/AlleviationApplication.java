package agh.alleviation;

import agh.alleviation.model.*;
import agh.alleviation.model.user.Customer;
import agh.alleviation.model.user.User;
import agh.alleviation.presentation.CinemaApp;
import agh.alleviation.service.*;
import agh.alleviation.util.UserType;
import javafx.application.Application;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.spring.SpringFxWeaver;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    public CommandLineRunner demo(MovieService movieService, UserService userService, HallService hallService, SeanceService seanceService, OrderService orderService) {
        return args -> {

			String name = "Erlang: The Movie";
			movieService.addMovie(name);

			String userName = "Mike";
			String userMail = "mike@erlang.com";
			String login = "helloJoe";
			UserType type = UserType.CUSTOMER;
			userService.addUser(userName, login, userMail, type);
			User user = userService.getUserByLogin("helloJoe");
			System.out.println(user.idProperty().get());
			//			Movie movie = movieService.findMovie(name);
			//
			//			int capacity = 50;
			//			hallService.addHall(capacity);
			//			Hall hall = hallService.findHallsByCapacity(capacity).get(0);
			//
			//			Date date = new Date(2020, Calendar.DECEMBER, 12, 12, 0);
			//			double price = 25.00;
			//
			//			seanceService.addSeance(movie, hall, date, price);
			//			Seance seance = seanceService.getAllSeances().get(0);
			//
			//			orderService.addTicket(seance, price);
			//			Ticket ticket = orderService.getAllTickets().get(0);
			//
			//			orderService.addOrder(List.of(ticket), (Customer) user);

			//			for (Order o : orderService.getAllOrders()){
			//				System.out.printf("Order %d, user %s:\n", o.getId(), o.getCustomer().getName());
			//
			//				for (Ticket t : o.getTickets()){
			//					Seance s = t.getSeance();
			//					System.out.printf("ticket %d: %s, hall %d, %s, %f\n",
			//							t.getId(),
			//							s.getMovie().getName(),
			//							s.getHall().getId(),
			//							s.getDate().toString(),
			//							t.getPrice());
			//				}
			//				System.out.println();
			//			}
		};
	}
}
