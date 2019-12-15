package com.my.accessingmysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/demo")
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);

        userRepository.save(user);

        return "Saved";
    }

    @GetMapping(path = "/show")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping(path = "/update")
    public @ResponseBody String updateUser(@RequestParam String name, @RequestParam String email) {
        String response = "Not updated";
        Iterable<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            if (user.getName().equals(name)) {
                user.setEmail(email);
                userRepository.save(user);
                response = "Updated";
            }
        }
        return response;
    }

    @DeleteMapping(path = "/delete")
    public @ResponseBody String deleteUser(@RequestParam String name) {
        String response = "Not deleted";
        Iterable<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            if (user.getName().equals(name)) {
                Integer id = user.getId();
                userRepository.deleteById(id);
                response = "Deleted";
                break;
            }
        }
        return response;
    }
}
