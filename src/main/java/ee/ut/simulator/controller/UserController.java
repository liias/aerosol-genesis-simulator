package ee.ut.simulator.controller;

import java.util.Map;

import ee.ut.simulator.domain.User;
import ee.ut.simulator.service.UserService;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
 
@Controller
public class UserController {
 
    @Autowired
    private UserService userService;
 
    @RequestMapping("/index")
    public String listUser(Map<String, Object> map) {
 
        map.put("user", new User());
        map.put("userList", userService.listUsers());
 
        return "user";
    }
 
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user")
    User user, BindingResult result) {
 
        userService.addUser(user);
 
        return "redirect:/index";
    }
 
    @RequestMapping("/delete/{userId}")
    public String deleteUser(@PathVariable("userId")
    long userId) {
 
        userService.removeUser(userId);
 
        return "redirect:/index";
    }
}
