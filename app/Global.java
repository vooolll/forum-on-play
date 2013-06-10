
import play.Application;
import play.GlobalSettings;
import play.Logger;

import com.typesafe.plugin.MailerPlugin;

import controllers.actors.Mails;


public class Global extends GlobalSettings{

	
	@Override
	public void onStart(Application app) {
		
		Mails.plugin = app.plugin(MailerPlugin.class).email();
		Mails.from = app.configuration().getString("smtp.from");
		if (Mails.plugin != null) Logger.info("Mailer plugin successfully loaded");	
		if (Mails.from != null) Logger.info("Mail account is " + Mails.from);
		
	}
}
