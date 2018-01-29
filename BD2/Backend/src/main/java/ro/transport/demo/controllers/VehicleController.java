package ro.transport.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;
import ro.transport.demo.domain.Vehicle;
import ro.transport.demo.services.VehicleService;

import java.util.List;

@RestController
@EnableJpaRepositories(basePackages = {"ro.transport.demo.repositories"})
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @CrossOrigin(allowedHeaders="*",allowCredentials="true")
    @PostMapping(value = "/vehicle/add")
    @ResponseBody
    public Vehicle add(@RequestBody Vehicle jsonVehicle) {
        return vehicleService.add(jsonVehicle);
    }

    @GetMapping("/vehicle/id/{id}")
    @ResponseBody
    public Vehicle find(@PathVariable("id") Long id) {
        return vehicleService.find(id);
    }

    @GetMapping("/vehicle/all")
    @ResponseBody
    public List<Vehicle> findAll() {
        return vehicleService.findAll();
    }

    @GetMapping("/vehicle/status/{statusName}")
    @ResponseBody
    public List<Vehicle> findByStatusName(@PathVariable("statusName") String statusName) {
        return vehicleService.findByStatusName(statusName);
    }

    @CrossOrigin(allowedHeaders="*",allowCredentials="true")
    @PutMapping("/vehicle/update/id/{id}")
    @ResponseBody
    public Vehicle update(@RequestBody Vehicle jsonVehicle) {
        return vehicleService.update(jsonVehicle);
    }

    @CrossOrigin(allowedHeaders="*",allowCredentials="true")
    @DeleteMapping("/vehicle/delete/id/{id}")
    @ResponseBody
    public void delete(@PathVariable("id") Long id) {
        vehicleService.delete(id);
    }
}
