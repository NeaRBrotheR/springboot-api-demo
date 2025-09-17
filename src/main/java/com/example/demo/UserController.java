package com.example.demo;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository repo;

    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    // 查詢所有使用者
    @GetMapping
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    //查單一使用者
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }

    @GetMapping("/by-email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return repo.findByEmail(email);
    }

    @GetMapping("/by-name/{name}")
    public User getUserByName(@PathVariable String name) {
        return repo.findByName(name);
    }

    @GetMapping("/by-address/{address}")
    public List<User> getUserByaddress(@PathVariable String address) {
        return repo.findByaddress(address);
    }

    @GetMapping("/by-sex/{sex}")
    public List<User> getUserBySex(@PathVariable String sex) {
        return repo.findBySex(sex);
    }
}
