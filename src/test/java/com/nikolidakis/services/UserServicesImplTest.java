//package com.nikolidakis.services;
//
//import com.nikolidakis.models.User;
//import com.nikolidakis.repository.UserRepository;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//public class UserServicesImplTest {
//
//    private UserRepository dao;
//    private UserServices service;
//
//    @Before
//    public void init(){
//        service = new UserServicesImpl();
//    }
//
//    @Test
//    public void getUsers(){
//        List<User> users = service.getAllUsers();
//        System.out.println(users);
//
//    }
//
//
//    @Test
//    public void getUserByIdOrEmail(){
//        String email = "nikolidakis.dimitris@gmail.com";
//        System.out.println(service.getUserByIdOrEmail(email));
//
//    }
//
//
//}