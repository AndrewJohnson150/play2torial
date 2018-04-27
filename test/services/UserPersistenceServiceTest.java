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
import javax.validation.ConstraintViolationException;

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

        final User u = new User();
        u.setUsername("contents");
        assertNull("ID should not be set before persist is called", u.getId());
        userPersist.saveUser(u);
        assertNotNull("ID should be set after persist is called", u.getId());
        final List<User> list = userPersist.fetchAllUsers();
        assertTrue("List should have one element", list.size() == 1);
    }

    @Test
    public void saveBlankUserTest() {
        try {
            final User u = new User();
            userPersist.saveUser(u);
            fail("This should have failed since username is blank");
        } catch (ConstraintViolationException ignored) {
        }
    }

    @Test
    public void saveNonBlankIdUserTest() {
        try {
            final User u = new User();
            u.setUsername("contents");
            u.setId(1);
            userPersist.saveUser(u);
            fail("This should have failed since id is not blank");
        } catch (PersistenceException ignored) {
        }
    }

    @Test
    public void saveExistingUserTest() {
        final User u = new User();
        u.setUsername("contents");
        userPersist.saveUser(u);
        assertNotNull("The ID should be set", u.getId());
        final List<User> list = userPersist.fetchAllUsers();
        assertTrue("List should have one element", list.size() == 1);

        // Attempt to save the same user again
        userPersist.saveUser(u);

        //the method should return the ONLY user with username as specified. If it returns null, it is because there are
        //0 or 2+ users with that username.
        assertNotNull("We shouldn't be able to resave the same item",userPersist.fetchUserByUsername(u.getUsername()));
    }

    @Test
    public void saveUserWithShortUsername() {
        try {
            final User u = new User("aa");
            userPersist.saveUser(u);
            fail("We shouldn't be able to save a user with a username less than 3 characters.");
        } catch (ConstraintViolationException ignore) {}
    }

    @Test
    public void saveUserWithLongUsername() {
        try {
            final User u = new User("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            userPersist.saveUser(u);
            fail("We shouldn't be able to save a user with a username more than 20 characters.");
        } catch (ConstraintViolationException ignore) {}
    }

    @Test
    public void saveUsernameOfSpaces() {
        try {
            final User u = new User("   ");
            userPersist.saveUser(u);
            fail("We shouldn't be able to save a user with a username of only spaces");
        } catch (ConstraintViolationException ignore) {}

    }

    //Obviously nothing can't be added to the db. If this throws an exception then the test fails, though.
    @Test
    public void addNullUser() {
        try {
            userPersist.saveUser(null);
        } catch (Exception fail) {
            fail("Saving a null user causes an exception");
        }
    }

    @Test
    public void addMultipleUsers() {
        userPersist.saveUser(new User("Bob"));
        userPersist.saveUser(new User("Joe"));
        userPersist.saveUser(new User("Dan"));
    }

    @Test
    public void addUserWithSpecialCharacters() {
        userPersist.saveUser(new User("!@#$%^%"));
    }

    @Test
    public void testFetchAllUsers() {
        userPersist.saveUser(new User("Bob"));
        userPersist.saveUser(new User("Joe"));
        userPersist.saveUser(new User("Dan"));

        final List<User> list = userPersist.fetchAllUsers();

        assertTrue("fetch all users did not return proper number of users",list.size() == 3);
    }

    @Test
    public void testUniqueID() {
        User bob = new User("Bob");
        User joe = new User("Joe");
        User dan = new User("Dan");
        userPersist.saveUser(bob);
        userPersist.saveUser(joe);
        userPersist.saveUser(dan);

        assertTrue((bob.getId()!=joe.getId()) && (joe.getId()!=dan.getId()));
    }


}