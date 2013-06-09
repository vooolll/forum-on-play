
package models;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class UserTest extends BaseModelTest {
	User user;
	
	@Before
	public void setUp() {
		user = new User();
		user.password = "baha";
		user.save();
	}
	
	
	@Test
	public void testVisitCount() {
		assertThat(user.visitCount).isEqualTo(0);
	}
	
	@Test
	public void testIsFirstVisit() {
		user.visitCount = 1;
		assertThat(user.isFirstVisit()).isEqualTo(true);
	}
	
	@Test
	public void testIsPasswordOk() {
		assertThat(User.wrongPass(null)).isEqualTo(true);
		assertThat(User.wrongPass("asd")).isEqualTo(true);
		assertThat(User.wrongPass("ASDASD")).isEqualTo(false);
		assertThat(User.isMatch("asd","asd")).isEqualTo(true);
		assertThat(User.isMatch("asd","asd1")).isEqualTo(false);
	}
	
	@Test
	public void testUpdate() {
		user.setPassword("izmail");
		user.update();
		assertThat(user.password).isEqualTo("izmail");
	}
}