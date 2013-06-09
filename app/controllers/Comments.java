package controllers;



import models.Comment;
import models.Post;
import models.User;


import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.comments.*;

public class Comments extends Controller {
	
	/**
	 * форма которая сохранит объект класса Comment
	 */
	public static Form<Comment> formComment = Form.form(Comment.class);
	
	/**
	 * Переводит на вью-форму post/create.scala.html
	 * @param id
	 * @return Result
	 */
	public static Result create(Long id) {
		return ok(create.render(Post.find.byId(id),formComment));
	}
	/**
	 * Сохраняет в базе данных и перенаправляет на post/list.scala.html
	 * @param id
	 * @return Result
	 */
	public static Result save(Long id) {
		Form <Comment> filledForm = formComment.bindFromRequest();
		if (filledForm.hasErrors())
			return badRequest(create.render(Post.find.byId(id), filledForm));	
		
		Comment comment = filledForm.get();
		comment.post = Post.find.byId(id);
		comment.author = User.loggedUser();
		comment.save();	
		return redirect(routes.Posts.list(Post.find.byId(id).topic.id));
	}	
}
