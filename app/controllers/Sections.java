package controllers;

import models.*;
import play.mvc.*;
import views.html.*;

public class Sections extends Controller {
	
	/**
	 * Action который перенаправляет List<Section> на index.scala.html
	 * 
	 * @return
	 */
	public static Result list() {
		return ok(index.render(Section.all(), User.loggedUser()));
	}

}
