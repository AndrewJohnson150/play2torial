package services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import configs.AppConfig;
import configs.TestDataConfig;

import jpa.User;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

@ContextConfiguration(classes = {AppConfig.class, TestDataConfig.class})
public class UserPersistenceServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Inject
    private UserPersistenceService userPersist;

    @Test
    public void emptyListTest() {
        final List<User> list = userPersist.fetchAllUsers();
        assertTrue("List should be empty", list.isEmpty());
    }

    @Test
    public void saveValidUserTest() {
        assertTrue("List should be empty", userPersist.fetchAllUsers().isEmpty());

        final User t = new User();
        t.setUsername("contents");
        assertNull("ID should not be set before persist is called", t.getId());
        userPersist.saveUser(t);
        assertNotNull("ID should be set after persist is called", t.getId());
        final List<User> list = userPersist.fetchAllUsers();
        assertTrue("List should have one element", list.size() == 1);
    }

    @Test
    public void saveBlankUserTest() {
        try {
            final User t = new User();
            userPersist.saveUser(t);
            fail("This should have failed since contents is blank");
        } catch (javax.validation.ConstraintViolationException ignored) {
        }
    }

    @Test
    public void saveNonBlankIdUserTest() {
        try {
            final User t = new User();
            t.setUsername("contents");
            t.setId(1);
            userPersist.saveUser(t);
            fail("This should have failed since id is not blank");
        } catch (PersistenceException ignored) {
        }
    }

    @Test
    public void saveExistingUserTest() {
        final User t = new User();
        t.setUsername("contents");
        userPersist.saveUser(t);
        assertNotNull("The ID should be set", t.getId());
        final List<User> list = userPersist.fetchAllUsers();
        assertTrue("List should have one element", list.size() == 1);

        // Attempt to save the same user again
        userPersist.saveUser(t);

        //the method should return the ONLY user with username as specified. If it returns null, it is because there are
        //0 or 2+ users with that username.
        assertNotNull("We shouldn't be able to resave the same item",userPersist.fetchUserByUsername(t.getUsername()));
    }
}