package controllers;

import models.*;
import play.data.*;
import play.mvc.*;
import views.html.topics.*;

public class Topics extends Controller {

	/*
	 * объект отправляемый в template который заполнит объект класса Topic
	 */
	static Form<Topic> formTopic = Form.form(Topic.class);

	
	/**
	 * Перенаправляет в topic\list.scala.html с List<Topic> ,
	 * 	 	который содержит все темы раздела
	 * 
	 * @return Result
	 * @param id
	 */
	public static Result list(Long id) {
		return ok(list.render(Section.find.byId(id), Topic.order()));
	}

	/**
	 * Action который перенаправляет в template (create.scala.html) с
	 * List<Topic> который форма для создания новой темы
	 * 
	 * @return Result
	 *s
	 */
    @Security.Authenticated(Secured.class)
	public static Result create(Long id) {
		return ok(create.render(formTopic, Section.find.byId(id)));
	}

	/**
	 * Проверяет правельно ли заполнена форма, создает тему и
	 * перенаправляет в метод Topics.list(id)
	 * 
	 * @return Result
	 * 
	 */
	public static Result save(Long id) {
		Form<Topic> filledTopic = formTopic.bindFromRequest();
		if (filledTopic.hasErrors()) 
			return badRequest(create.render(filledTopic, Section.find.byId(id)));
		Topic topic = filledTopic.get();
		topic.section = Section.find.byId(id);
		topic.author = User.loggedUser();
		topic.save();
		return redirect(routes.Topics.list(id));
	}
	
	
	public static Result edit(Long id) {
		return TODO;
	}
}