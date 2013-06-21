package models;


import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


import org.joda.time.Interval;
import org.joda.time.Period;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

/**
 * Модель представляет сущность сообщения пользвателся
 * 
 * @author Валерий        
 */

@Entity
public class Post extends Model{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	
	@Id
	public long id;
	/**
	 * Текст поста
	 */
	@Required
	@Column(columnDefinition = "TEXT")
	public String text;
	/**
	 * Тема к которой отностся пост
	 */
	@ManyToOne
	public Topic topic;
	/**
	 * Коментарии на пост
	 */
	@OneToMany
	public List <Comment> comments;
	/**
	 * Пользователь отправивший сообщение
	 */
	@ManyToOne
	public User author;
		
	
	@ManyToMany
	public List <User> usersLiked;
	
	/**
	 * Путь к прикрепленнной картинке
	 */
	public String attachedImagePath;
	/**
	 * Время отправки
	 */
	public Date createdAt = new Date();
	/**
	 * utility из ebean для поиска и выборки из базы данных
	 */
	
	public static Finder<Long, Post> find = new Finder<Long, Post>(Long.class,
			Post.class);

	public void setPath(String path) {
		this.attachedImagePath = path;
	}
	
	public static String getPeriod(Date date) {
		
		Interval interval = new Interval(date.getTime(), new Date().getTime());
		Period period = interval.toPeriod();
		
		String strPeriod = "";
		if 		(period.getMonths() != 0)	strPeriod += String.valueOf(period.getMonths()) + " мес.";
		else if (period.getWeeks() != 0) 	strPeriod += String.valueOf(period.getWeeks()) + " нед.";
		else if (period.getDays() != 0) 	strPeriod += String.valueOf(period.getDays()) + " д.";
		else if (period.getHours() != 0) 	strPeriod += String.valueOf(period.getHours()) + " ч.";
		else if (period.getMinutes() != 0) 	strPeriod += String.valueOf(period.getMinutes()) + " мин.";
		else if (period.getSeconds() != 0) 	strPeriod += String.valueOf(period.getSeconds()) + " сек";
		else 								strPeriod = "0";
		
		return strPeriod + " назад";
	}
	
	@Override
	public String toString() {
		return id + " " + text;
	}

	public void setText(String text) {this.text = text;}
	
}
