package ro.transport.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ro.transport.demo.domain.Color;
import ro.transport.demo.services.ColorService;

import java.util.List;

@RestController
@EnableJpaRepositories(basePackages = {"ro.transport.demo.repositories"})
public class ColorController {
    @Autowired
    private ColorService colorService;

    @GetMapping("/color/id/{id}")
    @ResponseBody
    public Color find(@PathVariable("id") Long id) {
        return colorService.find(id);
    }

    @GetMapping("/color/all")
    @ResponseBody
    public List<Color> findAll() {
        return colorService.findAll();
    }
}
