package com.nikolidakis.services;

import com.nikolidakis.exceptions.AuthenticateException;
import com.nikolidakis.repository.UserRepository;
import com.nikolidakis.requests.AuthenticateUserRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserServicesImplTest {

    @Autowired
    private UserRepository dao;
    @Autowired
    private UserServices service;


    @Autowired
    private TestEntityManager entityManager;


    @Test
    public void getToken_Success() throws AuthenticateException {
        String username = "tasos kopanos";
        String pass = "1234";
        AuthenticateUserRequest request = new AuthenticateUserRequest(username, pass);
        String token = service.getToken(request);

        System.out.println(token);

    }


}