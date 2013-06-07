package controllers;

import static org.fest.assertions.Assertions.*;
import static play.test.Helpers.*;

import org.junit.Test;

import play.mvc.Result;


public class SectionsTest extends BaseControllerTest {
	
    @Test
    public void testRootRoute() {
        Result result = routeAndCall(fakeRequest(GET, "/"));
        assertThat(result).isNotNull();
    }

    @Test
    public void testBadRoute() {
        Result result = routeAndCall(fakeRequest(GET, "/bad"));
        assertThat(result).isNull();
    }
    
	@Test
	public void testCallIndex() {
		Result result = callAction(controllers.routes.ref.Sections.list());
	    assertThat(status(result)).isEqualTo(OK);
	}
}
