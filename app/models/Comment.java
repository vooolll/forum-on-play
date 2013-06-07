package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import java.util.Date;

/**
 * Модель представляет собой сущность коментария на форуме
 *
 * @author Валерий
 *
 */
@Entity
public class Comment extends Model {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Id коментария
     */
    @Id
    public Long id;

    /**
     * Текст коментария
     */
    @Required
    @Column(columnDefinition = "TEXT")
    public String text;


    /**
     * Пост к которому относятся коментарии
     */
    @ManyToOne
    public Post post;

    /**
     * Автор коментария
     */
    @ManyToOne
    public User author;

    /**
     * Дата и время создния коментария
     */
    @Column(nullable = false)
    public Date createdAt = new Date();

    public static Finder<Long, Comment> find = new Finder<Long, Comment>(Long.class, Comment.class);


}
