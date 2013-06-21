package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
/**
 * Модель представляет собой сущность раздела на форуме
 * 
 * @author Валерий
 *
 */

@Entity
public class Section extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Id раздела
     */
	@Id
	public long id;

    /**
     * Название раздела
     */
	@Required
	public String name;

    /**
     * Описание раздела
     */
	@Required
	public String description;
	
	
	/**
	 * Лист топиков относящихся к разделу
	 */
	@OneToMany
	public List <Topic> topics;

	/**
	 * Утилита для поиска
	 */
	public static Finder<Long, Section> find = new Finder<Long, Section>(
			Long.class, Section.class);
	
	
	public static List <Section> all() {
		return find.all();
	}
	
	public Long getId() { return id; }
	
	public String getName() { return name; }
	
	public List<Topic> getTopics() { return topics; }
}
