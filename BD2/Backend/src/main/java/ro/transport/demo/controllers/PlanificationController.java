package ro.transport.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;
import ro.transport.demo.domain.Planification;
import ro.transport.demo.services.PlanificationService;

import java.util.List;

@RestController
@EnableJpaRepositories(basePackages = {"ro.transport.demo.repositories"})
public class PlanificationController {
    @Autowired
    private PlanificationService planificationService;

    @CrossOrigin(allowedHeaders="*",allowCredentials="true")
    @PostMapping(value = "/planification/add")
    @ResponseBody
    public Planification add(@RequestBody Planification jsonPlanification) {
        return planificationService.add(jsonPlanification);
    }

    @GetMapping("/planification/id/{id}")
    @ResponseBody
    public Planification find(@PathVariable("id") Long id) {
        return planificationService.find(id);
    }

    @GetMapping("/planification/all")
    @ResponseBody
    public List<Planification> findAll() {
        return planificationService.findAll();
    }

    @GetMapping("/planification/driver/{driverId}")
    @ResponseBody
    public List<Planification> findByDriver(@PathVariable("driverId") Long driverId) {
        return planificationService.findByDriverId(driverId);
    }

    @GetMapping("/planification/status/{statusName}")
    @ResponseBody
    public List<Planification> findByStatusName(@PathVariable("statusName") String statusName) {
        return planificationService.findByStatusName(statusName);
    }

    @GetMapping("/planification/driver/status/{driverId}/{statusName}")
    @ResponseBody
    public List<Planification> findByDriverAndStatusName(@PathVariable("driverId") Long driverId, @PathVariable("statusName") String statusName) {
        return planificationService.findByDriverIdAndStatusName(driverId, statusName);
    }

    @CrossOrigin(allowedHeaders="*",allowCredentials="true")
    @PostMapping(value = "/planification/deliver")
    @ResponseBody
    public Planification deliver(@RequestBody Planification jsonPlanification) {
        return planificationService.deliver(jsonPlanification);
    }
}
