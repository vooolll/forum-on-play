package models;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.fest.assertions.Assertions.*;


public class SectionTest extends BaseModelTest{
	Section music;
	
	@Before
	public void setUp() {
		music = new Section();
		music.name = "Music";
		music.description = "Section for chating about music";
		music.save();
	}
	
	@Test
	public void testCreate() {
		assertThat(music).isNotNull();
	}
	
	@Test
	public void testIdTextDescription() {
		assertThat(music.name, is("Music"));
		assertThat(music.description, is("Section for chating about music"));
	}
	
	@Test
	public void testSave() {
		assertThat(music.id).isNotNull();
		System.out.println("DEBUG: ID = " + music.id);
	}
	
	@Test
	public void testSelect() {
		music = Section.find.where().eq("name", "Music").findUnique();
		assertThat(music).isNotNull();
		assertThat(music.name, is("Music"));
	}
	
	@Test
	public void testDelete() {
		music = Section.find.where().eq("name", "Music").findUnique();
		System.out.println("DEBUG: " + music.id + " " + music.name);
		music.delete();
		Section deletedMusic = Section.find.where().eq("name", "Music").findUnique();
		assertThat(deletedMusic).isNull();
	}
	

}
