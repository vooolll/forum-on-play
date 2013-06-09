package controllers;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.callAction;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.status;
import models.Section;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import play.mvc.Result;

public class TopicsTest extends BaseControllerTest{
	Section s;
	@Before
	public void testSetUp() {
		s = new Section();
	    s.name = "Music";
	    s.id = 1L;
	    s.description = "section for music";
	    s.save();
	}
	@After
	public void tearDown() {
		s.delete();
	}
	
	@Test
	public void testCallList() {
		Result result = callAction(controllers.routes.ref.Topics.list(1L));
	    assertThat(status(result)).isEqualTo(OK);
	    assertThat(contentAsString(result)).contains("Music");
	}
	
	@Test
	public void testCallCreate() {
		Result result = callAction(controllers.routes.ref.Topics.create(1L),
				fakeRequest().withSession("userId", "1"));
		assertThat(status(result)).isEqualTo(OK); 
	}
	
	
	
}
