
package models;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class UserTest extends BaseModelTest {
	User user;
	
	@Before
	public void setUp() {
		user = new User();
	}
	
	
	@Test
	public void testVisitCount() {
		
		assertThat(user.visitCount).isEqualTo(0);
	}
	
	@Test
	public void assertIsFirstVisit() {
		user.visitCount = 1;
		assertThat(user.isFirstVisit()).isEqualTo(true);
	}
}