package models;


import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.fest.assertions.Assertions.*;



public class TopicTest extends BaseModelTest{
	Topic extProg;
	Topic someProg;
	Section s;
	
	@Before
	public void setUp() {
		s = new Section();
		s.name = "wa";
		s.save();
		extProg = new Topic();
		extProg.title = "Extrime programming";
		extProg.section = s;
		extProg.save();
		
	}

	public void makeSelect() {
		someProg = Topic.find.where().eq("title", "Extrime programming").findUnique();
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
		assertThat(extProg.title, is("Extrime programming"));
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
	
	@Test
	public void testOrderForSection() {
		Topic one = new Topic();
		one.setTitle("one");
		one.section = s;
		one.save();
		Topic two = new Topic();
		two.setTitle("two");
		two.section = s;
		two.save();
		List <Topic> orderedTopics = Topic.orderForSection(s.id);
		assertThat(orderedTopics).isNotNull();
		assertThat(orderedTopics.size()).isEqualTo(3);
		assertThat(orderedTopics.get(0).getTitle()).isEqualTo("two");
	}
	
}
