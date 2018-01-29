package ro.transport.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ro.transport.demo.domain.Priority;
import ro.transport.demo.services.PriorityService;

import java.util.List;

@RestController
@EnableJpaRepositories(basePackages = {"ro.transport.demo.repositories"})
public class PriorityController {
    @Autowired
    private PriorityService priorityService;

    @GetMapping("/priority/id/{id}")
    @ResponseBody
    public Priority find(@PathVariable("id") Long id) {
        return priorityService.find(id);
    }

    @GetMapping("/priority/all")
    @ResponseBody
    public List<Priority> findAll() {
        return priorityService.findAll();
    }
}
