package controllers.actors;

import akka.actor.UntypedActor;

public class MailsActor extends UntypedActor {
	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof Mails) {
			Mails.sendRegistrationLetter();
		}
	}
}