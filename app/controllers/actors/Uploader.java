package controllers.actors;

import java.io.File;
import java.io.Serializable;

import models.Post;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Http.Request;

public class Uploader implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static long id;
	static String path;
	static Post post;
	static Request request;
	
	public Uploader(long newId, String newPath, Post newPost, Request newRequest) {
	    id = newId;
		path = newPath;
		post = newPost;
		request = newRequest;
	}

	public static void upload() {
		MultipartFormData body = request.body().asMultipartFormData();
		FilePart picture = body.getFile("picture"); 
		String fileName = picture.getFilename();
		System.out.println(fileName);
		// записываем абсолютный путь в переменную
		String absolutePath = System.getProperty("user.dir") + path +  id + fileName;
		System.out.println(absolutePath);
		// создания манипуляции с файлом
		File file = picture.getFile();
		if (file.exists()) {
			file.renameTo(new File(absolutePath));
			// сохраняем абсолютный путь в модели
			post.setPath(id + fileName);
		} else {
			post.setPath(null);
		}
		post.save();
	}
}
