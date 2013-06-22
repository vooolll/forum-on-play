package controllers;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.*;
import static play.test.Helpers.*;

import java.util.HashMap;
import java.util.Map;

import models.Section;
import models.Topic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controllers.ref.ReverseTopics;

import play.mvc.Result;

public class TopicsTest extends BaseControllerTest {
	Section s;
	ReverseTopics topicsAct = controllers.routes.ref.Topics;
	
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
		Result result = callAction(topicsAct.list(1));
		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentAsString(result)).contains("Music");
	}

	@Test
	public void testCallCreate() {
		Result result = callAction(topicsAct.create(1L),
				fakeRequest().withSession("userId", "1"));
		assertThat(status(result)).isEqualTo(OK);
	}

	// TODO test save() controller in Topic
	@Test
	public void testCallSave() {
		Result routeResult = route(fakeRequest(POST, "/topics/save/1"));
		assertThat(routeResult).isNotNull();

	}
	
	@Test
	public void testEdit() {

		Topic topicToEdit = new Topic();
		topicToEdit.title = "adsdfdafd";
		topicToEdit.save();
		Result routeResult = route(fakeRequest(GET,"/topics/edit/1"));
		assertThat(routeResult).isNotNull();
		Result result = callAction(topicsAct.edit(1));
		assertThat(status(result)).isEqualTo(OK);
	}
	
	@Test
	public void testUpdate() {
		Result routeResult = route(fakeRequest(POST,"/topics/update/1"));
		assertThat(routeResult).isNotNull();
		Result result = callAction(topicsAct.update(1));
		assertThat(status(result)).isEqualTo(BAD_REQUEST); // no data
		Map <String, String> data = new HashMap<>();
		data.put("title", "hello");
		Result result1 = callAction(topicsAct.update(1), fakeRequest().withFormUrlEncodedBody(data));
		assertThat(status(result1)).isEqualTo(SEE_OTHER);
	}
}