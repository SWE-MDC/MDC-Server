package org.osuswe.mdc;

import org.osuswe.mdc.model.User;
import org.osuswe.mdc.repositories.UserMapper;
import org.osuswe.mdc.services.MailService;
import org.osuswe.mdc.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class MdcApplication implements CommandLineRunner {

    @Autowired
    MailService mailService;

    public static void main(String[] args) {
        SpringApplication.run(MdcApplication.class, args);
    }

    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @Override
    public void run(String... args) throws Exception {
//        User user = new User();
//        user.setUsername("gengliang");
//        user.setPassword("hello");
//        userMapper.addUser(user);
//        User user = userMapper.getUser(1);
//        System.out.println(user);
    }
}
