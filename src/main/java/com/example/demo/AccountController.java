package com.example.demo;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountRepository repo;

    public AccountController(AccountRepository repo) {
        this.repo = repo;
    }

    // 查所有帳號
    @GetMapping
    public List<Account> getAllAccounts() {
        return repo.findAll();
    }

    //查單一使用者
    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }

    //查暱稱
    @GetMapping("/by-nickname/{nickname}")
    public Account getAccountByNickname(@PathVariable String nickname) {
        return repo.findByNickname(nickname);  // 找不到會回傳 null
    }

    // 建立新帳號
    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return repo.save(account);
    }

    //JSON 陣列，新增多筆
    @PostMapping("/batch")
    public List<Account> createAccounts(@RequestBody List<Account> accounts) {
        return repo.saveAll(accounts);
    }

    //透過id移除使用者
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        repo.deleteById(id);
    }

}
