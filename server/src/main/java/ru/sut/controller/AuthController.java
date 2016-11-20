package ru.sut.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.sut.repository.UserRepository;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    private final UserRepository userRepository;

    @Autowired
    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "getUserId", method = RequestMethod.POST)
    public int getUserId(@RequestParam String login, @RequestParam String password){
        return userRepository.findByLoginAndPassword(login, password).getId();
    }

    @RequestMapping(value = "getUserGroup", method = RequestMethod.POST)
    public String getUserGroup(@RequestParam String id){
        return userRepository.findById(Integer.parseInt(id)).getGroup().getGrp();
    }
}
