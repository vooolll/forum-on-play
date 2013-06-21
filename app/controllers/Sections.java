package controllers;

import models.*;
import play.mvc.*;
import views.html.*;

public class Sections extends Controller {
	
	public static Result list() {
		return ok(index.render(Section.all(), User.loggedUser()));
	}
	
}
