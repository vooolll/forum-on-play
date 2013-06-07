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
     * Код темы
     */
    @Id
    public Long id;

	
	/**
	 * Название
	 */
	@Required
	public String name;
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
    public Date cratedAt = new Date();
    
	/**
	 * utility из ebean для поиска и выборки из базы данных
	 */
	public static Finder<Long, Topic> find = new Finder<Long, Topic>
		(Long.class, Topic.class);
	
	/**
	 * Организует темы в обратном порядке
	 */
	public static List<Topic> orderByDateDesc() {
		List <Topic> myl = find.orderBy("crated_at desc").findList();
		for (Topic t: myl) {
			System.out.println(t.cratedAt);
		}
		return myl;
	}
}
