package controllers;

import java.io.File;

import models.Post;

import play.db.ebean.Model;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;

public class Uploader{

	public static void upload(Long id, String path, Model model) {
		Post post = (Post) model;
		MultipartFormData body = Controller.request().body().asMultipartFormData();
		FilePart picture = body.getFile("picture"); 
		String fileName = picture.getFilename();
		// записываем абсолютный путь в переменную
		String absolutePath = System.getProperty("user.dir") + path +  id + fileName;
		// создания манипуляции с файлом
		File file = picture.getFile();
		if (file.exists()) {
			file.renameTo(new File(absolutePath));
			// сохраняем абсолютный путь в модели
			post.setPath(id + fileName);
		} else {
			post.setPath(null);
		}
	}
}
