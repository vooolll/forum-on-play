package controllers;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import play.test.FakeApplication;
import play.test.Helpers;

public class BaseControllerTest {
	public static FakeApplication app;

	@BeforeClass
	public static void startApp() {
		app = Helpers.fakeApplication(Helpers.inMemoryDatabase());
		Helpers.start(app);
	}

	@AfterClass
	public static void stopApp() {
		Helpers.stop(app);
	}
}
