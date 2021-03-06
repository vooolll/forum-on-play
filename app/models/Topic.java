package models;


import java.util.Date;
import java.util.List;

import javax.persistence.*;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

/**
 * Модель представляет собой сущность раздела на форуме.
 * Глвные разделы форума
 * (может создавать только администратор)
 */
@Entity
//@Table(name = "topic")
public class Topic extends Model {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
     * Код темы
     */
    @Id
    public long id;

	
	/**
	 * Название
	 */
	@Required
	public String title;
    /**
     * Раздел, в который входит этот топик
     */
	
    @ManyToOne
	public Section section;

    /**
     * Посты относящиеся к форуму
     */
    @OneToMany
    public List <Post> posts;
    /**
     * Автор темы
     */
    @ManyToOne
    public User author;
    
    /**
     * Время создания темы
     */
    public Date createdAt = new Date();
    
	/**
	 * utility из ebean для поиска и выборки из базы данных
	 */
	public static Finder<Long, Topic> find = new Finder<Long, Topic>
		(Long.class, Topic.class);
	
	/**
	 * Организует темы в обратном порядке
	 */
	public static List<Topic> orderForSection(long id) {
		return find.where().eq("section_id", id).findList();
	}
	
	public void setTitle(String title) {this.title = title;}
	public String getTitle() {return title;}
	public long getId() {return id;}
	public User getAuthor() {return author;}

 }
