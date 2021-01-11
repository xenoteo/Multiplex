package agh.alleviation.util;

import agh.alleviation.model.EntityObject;
import agh.alleviation.model.Ticket;
import agh.alleviation.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A class responsible for activating emailSender every CHECK_INTERVAL_SECONDS. Emails are sent if there are 5 days
 * until the seance on any ticket of an order.
 *
 * @author Kamil Krzempek
 */
@Component
public class EmailPeriodicTask {
    private final TicketService ticketService;
    private final EmailSender emailSender;
    private final int CHECK_INTERVAL_SECONDS = 30;
    private final int SEND_NOTIFICATION_BEFORE_DAYS = 5;

    @Value("${alleviation.mail.enabled}")
    private boolean mailEnabled;

    /**
     * Instantiates an email periodic task.
     *
     * @param ticketService the ticket service
     * @param emailSender   the email sender
     */
    @Autowired
    public EmailPeriodicTask(TicketService ticketService, EmailSender emailSender) {
        this.ticketService = ticketService;
        this.emailSender = emailSender;
    }

    /**
     * Checks reservations.
     */
    @Scheduled(fixedRate = CHECK_INTERVAL_SECONDS * 1000)
    public void checkReservations() {
        List<EntityObject> tickets = ticketService.getAll().stream().filter(EntityObject::getIsActive).collect(Collectors.toList());
        System.out.printf("Checking %d tickets...\n", tickets.size());
        for (EntityObject ticketObject : tickets) {
            Ticket ticket = (Ticket) ticketObject;
            LocalDateTime seanceDate = ticket.getSeance().getDate();
            LocalDateTime currentDate = LocalDateTime.now();

            if (currentDate.isBefore(seanceDate)) {
                long daysToSeance = ChronoUnit.DAYS.between(currentDate, seanceDate);

                System.out.println("Days to seance: " + daysToSeance);
                if (daysToSeance == SEND_NOTIFICATION_BEFORE_DAYS) {
                    if (mailEnabled) {
                        System.out.println("Sending notification...");
                        emailSender.sendNotificationAboutSeance(ticket.getOrder().getUser(), ticket.getSeance());
                    } else {
                        System.out.println("Notification would be sent, but alleviation.mail.enabled is set to false");
                    }
                }
            }
        }
    }
}
