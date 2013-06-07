package models;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class PostTest extends BaseModelTest{
	Post post;
	@Before
	public void setUp() {
		post = new Post();
		post.text = "hello world";
		post.save();
	}
	
	@Test
	public void testCreate() {
		assertThat(post).isNotNull();
	}
	
	
	@Test
	public void testSave() {
		assertThat(post.id).isNotNull();
	}
	
	@Test
	public void testSelect() {
		Post somePost = Post.find.where().eq("text", "hello world").findUnique();
		assertThat(somePost).isNotNull();
	}
	
	@Test
	public void testDelete() {
		post.delete();
		Post somePost = Post.find.where().eq("text", "hello world").findUnique();
		assertThat(somePost).isNull();
	}
	 
	@Test
	public void testUpdate() {
		post.setText("hello kitty");
		post.update();
		Post somePost = Post.find.where().eq("text", "hello kitty").findUnique();
		assertThat(somePost).isNotNull();
	}
}
