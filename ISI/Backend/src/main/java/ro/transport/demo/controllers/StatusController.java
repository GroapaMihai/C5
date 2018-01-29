package ro.transport.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ro.transport.demo.domain.Status;
import ro.transport.demo.services.StatusService;

import java.util.List;

@RestController
@EnableJpaRepositories(basePackages = {"ro.transport.demo.repositories"})
public class StatusController {
    @Autowired
    private StatusService statusService;

    @GetMapping("/status/id/{id}")
    @ResponseBody
    public Status find(@PathVariable("id") Long id) {
        return statusService.find(id);
    }

    @GetMapping("/status/all")
    @ResponseBody
    public List<Status> findAll() {
        return statusService.findAll();
    }
}
