
import java.util.List;
import java.util.Map;

import models.User;

import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.Yaml;

import com.avaje.ebean.Ebean;
import com.typesafe.plugin.MailerPlugin;

import controllers.actors.Mails;


public class Global extends GlobalSettings{
	
	@Override
	public void onStart(Application app) {
		
		Mails.plugin = app.plugin(MailerPlugin.class).email();
		Mails.from = app.configuration().getString("smtp.from");
		if (Mails.plugin != null) Logger.info("Mailer plugin successfully loaded");	
		if (Mails.from != null) Logger.info("Mail account is " + Mails.from);
		InitialData.loadDataFor(app);
	}
	static class InitialData {
		@SuppressWarnings("unchecked")
		public static void loadDataFor(Application app) {
			if(Ebean.find(User.class).findRowCount() == 0) {
				Map<String,List<Object>> data = (Map<String,List<Object>>)Yaml.load("initial-data.yml");
				Ebean.save(data.get("users"));
				Ebean.save(data.get("sections"));
			}
		}
	}
}
