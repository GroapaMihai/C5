package ro.transport.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ro.transport.demo.domain.AccountType;
import ro.transport.demo.services.AccountTypeService;

import java.util.List;

@RestController
@EnableJpaRepositories(basePackages = {"ro.transport.demo.repositories"})
public class AccountTypeController {
    @Autowired
    private AccountTypeService accountTypeService;

    @GetMapping("/accountType/id/{id}")
    @ResponseBody
    public AccountType find(@PathVariable("id") Long id) {
        return accountTypeService.find(id);
    }

    @GetMapping("/accountType/all")
    @ResponseBody
    public List<AccountType> findAll() {
        return accountTypeService.findAll();
    }
}
