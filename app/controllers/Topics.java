package controllers;

import models.*;
import play.data.Form;
import play.libs.Akka;
import play.mvc.*;
import views.html.topics.*;
import akka.actor.*;
import controllers.actors.*;

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
	public static Result list(long id) {
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
	public static Result create(long id) {
		return ok(create.render(formTopic, firstPost, Section.find.byId(id)));
	}

    
    
    @Security.Authenticated(Secured.class)
	public static Result save(long id) {
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
		
		
		//Akka 
		ActorSystem system = Akka.system();
		ActorRef uploader = system.actorOf(new Props(UploaderActor.class), "uploader");
		uploader.tell(new Uploader(post.id, "/public/images/post/",post, request()), uploader);
		system.stop(uploader);
		
		post.save();
		return redirect(routes.Posts.list(topic.id));
	}
	
	
	public static Result edit(long id) {
		Topic topic = Topic.find.byId(id);
		formTopic = formTopic.fill(topic);
		return ok(edit.render(topic, formTopic));
	}
	
	
	public static Result update(long id) {
		Form <Topic> filledForm = formTopic.bindFromRequest();
		if (filledForm.hasErrors())
			return badRequest();
		filledForm = formTopic.fill(Topic.find.byId(id)).bindFromRequest();
		Topic topic = filledForm.get();
		topic.update(id);
		return redirect(routes.Topics.list(id));
	}
}