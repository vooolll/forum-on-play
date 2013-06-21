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

	static Form<Post> firstPost = Form.form(Post.class);
	
	/**
	 * Перенаправляет в topic\list.scala.html с List<Topic> ,
	 * 	 	который содержит все темы раздела
	 * 
	 * @return Result
	 * @param id
	 */
	public static Result list(Long id) {
		return ok(list.render(Section.find.byId(id), Topic.orderForSection(id)));
	}

	/**
	 * Action который перенаправляет в template (create.scala.html) с
	 * List<Topic> который форма для создания новой темы
	 * 
	 * @return Result
	 *
	 */
    @Security.Authenticated(Secured.class)
	public static Result create(Long id) {
		return ok(create.render(formTopic, firstPost, Section.find.byId(id)));
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
		Form<Post> filledPost = firstPost.bindFromRequest();
		if (filledTopic.hasErrors() || filledPost.hasErrors()) 
			return badRequest(create.render(filledTopic, firstPost, Section.find.byId(id)));
		Topic topic = filledTopic.get();
		Post post = filledPost.get();
		topic.section = Section.find.byId(id);
		topic.author = User.loggedUser();
		post.topic = topic;
		post.author = User.loggedUser();
		topic.save();
		post.save();
		Uploader.upload(post.id, "/public/images/post/", post);
		post.save();
		return redirect(routes.Posts.list(topic.id));
	}
	
	
	public static Result edit(Long id) {
		return TODO;
	}
}