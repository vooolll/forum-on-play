package controllers;

import java.io.File;

import models.*;
import play.data.Form;
import play.mvc.*;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import views.html.posts.*;

public class Posts extends Controller {

	/*
	 * объект отправляемый в template который вернет объект класса Post
	 */
	static Form<Post> formPost = Form.form(Post.class);

	/**
	 * перенаправляет в posts/list.scala.html c объектом модели Topic содержащий
	 * посты этой темы
	 * 
	 * @param uri
	 * 
	 * @return Result
	 */
	public static Result list(Long id) {
		return ok(list.render(Topic.find.byId(id)));
	}

	/**
	 * перенаправляет во вью posts/create.scala.html с формой и моделью Topic
	 * 
	 * @param id
	 * @return
	 */
	public static Result create(Long id) {
		return ok(create.render(Topic.find.byId(id), formPost));
	}

	/**
	 * Проверяет правельно ли заполнена форма создает пост для какой то темы
	 * 
	 * @param uri
	 * @return Result
	 * 
	 */
	public static Result save(Long id) {
		Form<Post> filledPost = formPost.bindFromRequest();
		if (filledPost.hasErrors()) 
			return badRequest(create.render(Topic.find.byId(id), filledPost));

		Post post = filledPost.get();
		post.topic = Topic.find.byId(id);
		post.author = User.loggedUser();
		post.save();
		upload(post.id, "/public/images/post/",post);
		post.save();
		return redirect(routes.Posts.list(id));
	}

	/**
	 * Лайк ( повышение рейтинга сообщения )
	 */
	public static Result like(Long id, Long tID) {
		Post post = Post.find.byId(id);
		if(!post.usersLiked.contains(User.loggedUser()))
			post.usersLiked.add(User.loggedUser());
		post.save();
		return redirect(routes.Posts.list(tID));
	}
	
	/**
	 * Перенаправляет на вью view.scala.html
	 * @param id
	 * @return Result
	 */
	public static Result edit(Long id) {
		return TODO;
	}
	

	public static void upload(Long id, String path, Uploadable model) {
		// используем боди парсер что бы извлечь из формы файл
		MultipartFormData body = request().body().asMultipartFormData();
		FilePart picture = body.getFile("picture");
		String fileName = picture.getFilename();
		// записываем абсолютный путь в переменную
		String absolutePath = System.getProperty("user.dir") + path +  id + fileName;
		// создания манипуляции с файлом
		File file = picture.getFile();
		if (file.exists()) {
			file.renameTo(new File(absolutePath));
			// сохраняем абсолютный путь в модели
			model.setPath(id + fileName);
		} else {
			model.setPath(null);
		}
	}

}
