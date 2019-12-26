package com.proj.meethere.unitTest.serviceTest;

import com.proj.meethere.dao.UserRepository;
import com.proj.meethere.service.UserInfoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

/**
 * @Author Tresaresa
 * @Date 2019/12/26 11:13
 */
@SpringBootTest
public class UserInfoServiceTest {
    
    @Autowired
    private UserInfoService userInfoService;
    
    @MockBean
    private UserRepository userRepository;
    
    @BeforeAll
    void init() {
        
    }
    
    @Test
    void should_return_1_when_user_name_exist() {

    }

    @Test
    void should_return_0_when_user_name_not_exist() {

    }

    @Test
    void should_return_1_when_user_name_exist_and_is_not_mine() {

    }

    @Test
    void should_return_0_when_user_name_not_exist_and_is_not_mine() {

    }
    
    @Test
    void should_return_1_when_update_success() {
        
    }

    @Test
    void should_return_0_when_update_failed() {

    }
    
    @Test
    void should_return_1_when_insert_success() {

    }

    @Test
    void should_return_0_when_insert_failed() {

    }
    
    @Test
    void should_return_id_when_login_success() {
        
    }
    
    @Test
    void should_return_minus_1_when_login_failed() {
        
    }
    
    @Test
    void should_return_1_when_update_photo_success() {
        
    }

    @Test
    void should_return_0_when_update_photo_failed() {

    }
    
    @Test
    void should_return_blob_when_photo_exist() {
        
    }
    
    @Test
    void should_return_null_when_photo_not_exist() {
        
    }
}
