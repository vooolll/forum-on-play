package controllers.actors;

import akka.actor.UntypedActor;

/**
 * Akka актор для отправки имейлов
 * @author Valery
 *
 */

public class MailsActor extends UntypedActor {
	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof Mails) {
			Mails.sendRegistrationLetter();
		}
	}
}