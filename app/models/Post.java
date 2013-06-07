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
public class Post extends Model implements Uploadable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	
	@Id
	public Long id;
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

	@Override
	public void setPath(String path) {
		this.attachedImagePath = path;
	}
	
	public static String getFormatedDate(Date d) {
		String formatedDate = "";
		Interval interval = new Interval(d.getTime(), new Date().getTime());
		Period period = interval.toPeriod();
		
		if (period.getMonths() != 0) {
			formatedDate += String.valueOf(period.getMonths()) + " мес.";
		} else if (period.getWeeks() != 0) {
			formatedDate += String.valueOf(period.getWeeks()) + " нед.";
		} else if (period.getDays() != 0) {
			formatedDate += String.valueOf(period.getDays()) + " д.";
		} else if (period.getHours() != 0) {
			formatedDate += String.valueOf(period.getHours()) + " ч.";
		} else if (period.getMinutes() != 0) {
			formatedDate += String.valueOf(period.getMinutes()) + " мин.";
		} else if (period.getSeconds() != 0) {
			formatedDate += String.valueOf(period.getSeconds()) + " сек";
		} else {
			formatedDate = "0";
		}
		
		return formatedDate + " назад";
	}
	
	@Override
	public String toString() {
		return id + " " + text;
	}
	
	public void setText(String newText) {
		text = newText;
	}
}
