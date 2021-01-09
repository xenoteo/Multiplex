package agh.alleviation.util;

import agh.alleviation.model.Seance;
import agh.alleviation.model.user.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * A class responsible for sending emails to users from the cinema's assigned email account.
 *
 * @author Kamil Krzempek
 */

public class EmailSender {
    private final JavaMailSender javaMailSender;
    private final String FROM_ADDRESS = "alleviationproject@gmail.com";

    public EmailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendNotificationAboutSeance(User user, Seance seance) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM_ADDRESS);
        message.setTo(user.getEmail());
        message.setSubject(String.format("Notification about upcoming seance: %s", seance.getMovie().getName()));
        message.setText(String.format(
            "Hello %s!\nThis is a friendly reminder about upcoming seance you have reservation for. \"%s\" seance will take place on %s in hall number %s of our cinema. Be sure to get there on time!\nRegards, Alleviation Cinema Team",
            user.getName(),
            seance.getMovie().getName(),
            seance.getDate().toString(),
            seance.getHall().getNumber()
        ));
        javaMailSender.send(message);
    }
}
