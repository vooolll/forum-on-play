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
	
	public static MailerAPI plugin;
	static User user;
	public static String from;
	
	
	public Mails(User newUser) {
		user = newUser;
	}
	
    public static void sendRegistrationLetter() {
    	plugin.setSubject(Messages.get("user.registration.letterTitle"));
    	plugin.addRecipient(user.fullName + " <" + user.email + ">");
    	plugin.addFrom(from);
    	plugin.send(registrationLetter.render(user).toString());
    }
}
