package com.nikolidakis.controllers;

import com.nikolidakis.models.User;
import com.nikolidakis.services.UserServices;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Data
public class UserController {

    @Autowired
    private UserServices services;

    @RequestMapping("/allusers")
    public List<User> getAllUsers(){
        return services.getAllUsers();
    }

}
