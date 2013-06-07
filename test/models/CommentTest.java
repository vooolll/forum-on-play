package models;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.*;

public class CommentTest extends BaseModelTest{
	Comment comment;
	@Before
	public void setUp() {
		comment = new Comment();
		comment.text = "some comment text";
		comment.save();
	}
	
	
	@Test
	public void testCreate() {
		assertThat(comment).isNotNull();
	}
	
	
	@Test
	public void testSave() {
		assertThat(comment.id).isNotNull();
	}
	
	
	@Test
	public void testDelete() {
		comment.delete();
		Comment anotherComment = Comment.find.where().eq("text", "some comment text").findUnique();
		assertThat(anotherComment).isNull();
	}
}
