package models;


import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.fest.assertions.Assertions.*;



public class TopicTest extends BaseModelTest{
	Topic extProg;
	Topic someProg;
	
	@Before
	public void setUp() {
		extProg = new Topic();
		extProg.name = "Extrime programming";
		extProg.save();
		
	}

	public void makeSelect() {
		someProg = Topic.find.where().eq("name", "Extrime programming").findUnique();
	}
	
	@Test
	public void testCreate() {
		assertThat(extProg).isNotNull();
	}
	
	@Test
	public void testTextIdSection() {
		Section programming = new Section();
		extProg.section = programming;
		assertThat(extProg.section).isNotNull();
		assertThat(extProg.name, is("Extrime programming"));
	}
	
	@Test
	public void testSave() {
		assertThat(extProg.id).isNotNull();
	}
	
	@Test
	public void testSelect() {
		makeSelect();
		assertThat(someProg).isNotNull();
	}
	
	@Test
	public void testDelete() {
		extProg.delete();
		makeSelect();
		assertThat(someProg).isNull();
	}
	
	
}
