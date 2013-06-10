package controllers.actors;

import java.io.Serializable;

import models.User;
import play.i18n.Messages;
import views.html.users.registrationLetter;

import com.typesafe.plugin.MailerAPI;

public class Mails implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static MailerAPI mail;
	static User destinationUser;
	static String messageFrom;
	
	
	public Mails(User user, String from, MailerAPI plugin) {
		destinationUser = user;
		mail = plugin;
        messageFrom = from;
	}
	
    public static void sendRegistrationLetter() {
    	mail.setSubject(Messages.get("user.registration.letterTitle"));
        mail.addRecipient(destinationUser.fullName + " <" + destinationUser.email + ">");
        mail.addFrom(messageFrom);
        mail.send(registrationLetter.render(destinationUser).toString());
    }
}
