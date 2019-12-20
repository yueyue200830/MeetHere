package com.proj.meethere.repositoryTest;

import com.mysql.cj.exceptions.AssertionFailedException;
import com.proj.meethere.dao.UserRepository;
import com.proj.meethere.entity.User;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Yiqing Tao
 * @date 2019-12-17 21:28
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;

    private User user;
    @Before
    public void init() {
        user = new User("Jack","passwordIsSecret",1,"mockUserPhoto");
    }

    @After
    public void cleanUp() {
        this.testEntityManager.clear();
    }

    @Test
    public void select_specific_user_should_be_exact_the_same_as_init() {
        this.testEntityManager.persist(user);
        List<User> users = userRepository.selectSpecificUser(user.getId());
        System.out.println("here");
        Assert.assertEquals(1,users.size());
        User onlyUser = users.get(0);
        Assert.assertEquals("Jack",onlyUser.getUserName());
        Assert.assertEquals("passwordIsSecret", onlyUser.getUserKey());
        System.out.println("clear");
        Assert.assertEquals(new Integer(1),onlyUser.getUserType());
        Assert.assertEquals("mockUserPhoto", onlyUser.getUserPhoto());
    }

    @Test
    public void delete_user_should_return_null() {
        this.testEntityManager.persist(user);
        int result = userRepository.deleteSpecificUser(user.getId());
        Assert.assertEquals(1,result);
        List<User> users = userRepository.selectSpecificUser(1);
        Assert.assertEquals(0,users.size());
    }

    @Test
    public void select_all_users_should_be_exact_the_same() {
        User user2 = new User("test2","passwd",0,"fakePhoto");
        this.testEntityManager.persist(user);
        this.testEntityManager.persist(user2);
        List<User> users = userRepository.selectAllUserInfo();
        Assert.assertEquals(2,users.size());
        User firstUser = users.get(0);
        User secondUser = users.get(1);
        Assert.assertEquals("Jack",firstUser.getUserName());
        Assert.assertEquals("passwordIsSecret",firstUser.getUserKey());
        Assert.assertEquals(new Integer(1),firstUser.getUserType());
        Assert.assertEquals("mockUserPhoto",firstUser.getUserPhoto());
        Assert.assertEquals("test2", secondUser.getUserName());
        Assert.assertEquals("passwd",secondUser.getUserKey());
        Assert.assertEquals(new Integer(0), secondUser.getUserType());
        Assert.assertEquals("fakePhoto", secondUser.getUserPhoto());
    }




}
