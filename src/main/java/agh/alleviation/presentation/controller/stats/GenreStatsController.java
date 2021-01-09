package agh.alleviation.presentation.controller.stats;

import agh.alleviation.model.EntityObject;
import agh.alleviation.model.Genre;
import agh.alleviation.model.Order;
import agh.alleviation.model.Ticket;
import agh.alleviation.model.user.User;
import agh.alleviation.service.OrderService;

import java.util.*;

public class GenreStatsController extends GenericStatsController<Genre>{

    @Override
    protected Map<Genre, Integer> top10stats() {
        OrderService orderService = (OrderService) serviceManager.getService(User.class);
        List<EntityObject> orders = orderService.getAll();
        Map<Genre, Integer> genreMap = new HashMap<>();
        for (EntityObject orderObject : orders){
            Order order = (Order) orderObject;
            List<Ticket> tickets = order.getTickets();
            for (Ticket ticket : tickets){
                Genre genre = ticket.getSeance().getMovie().getGenre();
                if (genreMap.containsKey(genre)){
                    genreMap.put(genre, genreMap.get(genre) + 1);
                }
                else {
                    genreMap.put(genre, 1);
                }
            }
        }
        Map<Genre, Integer> genreMapSorted = new LinkedHashMap<>();
        genreMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .forEachOrdered(x -> genreMap.put(x.getKey(), x.getValue()));

        return genreMapSorted;
    }
}
