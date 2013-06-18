package models;

import java.io.IOException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.config.dbplatform.H2Platform;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.server.ddl.DdlGenerator;
import play.GlobalSettings;
import play.test.FakeApplication;
import play.test.Helpers;


public class BaseModelTest {
	
	
	public static FakeApplication app;

	@BeforeClass
	public static void startApp() {	
		app = Helpers.fakeApplication(new Global());
		Helpers.start(app);
	}

	@AfterClass
	public static void stopApp() {
		Helpers.stop(app);
	}

	@Before
	public void dropCreateDb() throws IOException {
		String serverName = "default";
		EbeanServer server = Ebean.getServer(serverName);
		ServerConfig config = new ServerConfig();
		DdlGenerator ddl = new DdlGenerator((SpiEbeanServer) server,
				new H2Platform(), config);
		// Drop
		ddl.runScript(false, ddl.generateDropDdl());
		// Create
		ddl.runScript(false, ddl.generateCreateDdl());
	}
}


class Global extends GlobalSettings{
	
}
