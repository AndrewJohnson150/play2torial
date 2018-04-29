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
        assertTrue("Username must be at least 3 characters", u.getUsername().length()>=3);
        assertNull("ID should not be set before persist is called", u.getId());
        userPersist.saveUser(u);
        assertNotNull("ID should be set after persist is called", u.getId());
        final List<User> list = userPersist.fetchAllUsers();
        assertTrue("List should have one element", list.size() == 1);
        assertEquals("Username does not match what was stored.", u.getUsername(),list.get(0).getUsername());
    }

    @Test
    public void saveBlankUserTest() {
        final User u = new User();
        assertFalse("This should have failed since username is blank",userPersist.saveUser(u));
    }

    @Test
    public void saveNonBlankIdUserTest() {
        final User u = new User();
        u.setUsername("contents");
        u.setId(1);
        assertFalse("This should have failed since id is not blank",userPersist.saveUser(u));
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
        assertFalse("We shouldn't be able to resave the same user twice",userPersist.saveUser(u));
    }

    @Test
    public void saveUserWithShortUsername() {
        final User u = new User("aa");
        assertFalse("We shouldn't be able to save a user with a username less than 3 characters.",userPersist.saveUser(u));
    }

    @Test
    public void saveUserWithLongUsername() {
        final User u = new User("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        assertFalse("We shouldn't be able to save a user with a username more than 20 characters.",userPersist.saveUser(u));
    }

    @Test
    public void saveUsernameOfSpaces() {
        final User u = new User("   ");
        assertFalse("We shouldn't be able to save a user with a username of only spaces",userPersist.saveUser(u));
    }

    //Obviously nothing can't be added to the db. If this throws an exception then the test fails, though.
    @Test
    public void saveNullUser() {
        assertFalse("Saving a null user causes an exception",userPersist.saveUser(null));
    }

    @Test
    public void addMultipleUsers() {
        assertTrue("Failed to add user",userPersist.saveUser(new User("Bob")));
        assertTrue("Failed to add user",userPersist.saveUser(new User("Joe")));
        assertTrue("Failed to add user",userPersist.saveUser(new User("Dan")));
        assertTrue("not all users stored",userPersist.fetchAllUsers().length() == 3);

    }

    @Test
    public void saveUserWithSpecialCharacters() {
        userPersist.saveUser(new User("!@#$%^%"));
    }

    @Test
    public void fetchUsers() {
        userPersist.saveUser(new User("Bob"));
        userPersist.saveUser(new User("Joe"));
        userPersist.saveUser(new User("Dan"));

        final List<User> list = userPersist.fetchAllUsers();

        assertTrue("fetch all users did not return proper number of users",list.size() == 3);
    }

    @Test
    public void fetchUsersWhenThereAreNone() {
        final List<User> list = userPersist.fetchAllUsers();
        assertTrue("fetch all users did not return proper number of users",list.size() == 0);
    }

    @Test
    public void fetchUsersWhenThereIsOne() {
        userPersist.saveUser(new User("Dan"));

        final List<User> list = userPersist.fetchAllUsers();
        assertTrue("fetch all users did not return proper number of users",list.size() == 1);
        assertTrue("User FetchAllUsers did not return the proper user in the list",list.get(0).getUsername().Equals("Dan"))
    }

    //fetchUserByID
    //many of the same tests essentially
    //storing 0,1,more, weird things as well

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