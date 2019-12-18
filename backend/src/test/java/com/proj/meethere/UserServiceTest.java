package com.proj.meethere;

import com.proj.meethere.Service.UserService;
import com.proj.meethere.dao.UserRepository;
import com.proj.meethere.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Yiqing Tao
 * @date 2019-12-17 22:34
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void get_user_info_should_be_executed() {
        ]
    }
}
