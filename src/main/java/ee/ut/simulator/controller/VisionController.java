package ee.ut.simulator.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


 
@Controller
public class VisionController {
 
 
    @RequestMapping("/vision")
    public String listUser(Map<String, Object> map) {

        return "vision";
    }
}