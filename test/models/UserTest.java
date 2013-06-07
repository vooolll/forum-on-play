
package models;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import java.util.Date;

import org.junit.Test;

public class UserTest extends BaseModelTest {
    private static final String TEST_EMAIL = "valera@lastochka-os.ru";
    private static final String TEST_PASSWORD = "secret";
    private static final String TEST_FULLNAME = "Валерий";


    @Test
    public void createRecord() {
        if(User.authorize(TEST_EMAIL, TEST_PASSWORD) != null) {
            return;
        }

        User user = new User();
        user.fullName = TEST_FULLNAME;
        user.email = TEST_EMAIL;
        user.password = TEST_PASSWORD;
        user.role = User.Role.ADMIN;
        user.save();

    }


    @Test
    public void loginUpdateStatistics() {
        User user = User.authorize(TEST_EMAIL, TEST_PASSWORD);
        assertNotNull(user);
        assertEquals(TEST_FULLNAME, user.fullName);

        // Запоминаем старые значения
        Date oldLastLogin = user.lastLogin;
        int oldLoginsCount = user.loginsCount;


        // Обновление записи
        System.err.println("!!!!! >>>>>" + user.id);
        user.lastLogin = new Date();
        user.loginsCount++;
        user.update(user.id);

        // Проверяем обновление
        user = User.authorize(TEST_EMAIL, TEST_PASSWORD);
        assertNotSame(user.lastLogin, oldLastLogin);
        assertNotSame(user.loginsCount, oldLoginsCount);

    }
    public void retriveRecord() {
        //assertNotNull(bob);
        //
    }

}