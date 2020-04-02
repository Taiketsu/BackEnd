package com.nullteam6.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nullteam6.models.User;
import com.nullteam6.models.UserTemplate;
import com.nullteam6.service.UserDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDAO dao;

    @RequestMapping(value="{userName}", method = RequestMethod.GET)
    public @ResponseBody User getUser(@PathVariable String userName) {
        return dao.findByUserName(userName);
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody User registerUser(@RequestBody String payload) {
        // TODO: Figure out error handling for yeeting errors
        ObjectMapper mapper = new ObjectMapper();
        UserTemplate userTemplate = null;

        try {
            userTemplate = mapper.readValue(payload, UserTemplate.class);
        } catch(JsonProcessingException e) {
            e.getStackTrace();
        }
        
        if (userTemplate != null) {
            User u = dao.registerUser(userTemplate);
            return u;
        } else {
            return null;
        }
    }
}