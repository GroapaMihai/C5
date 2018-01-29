package ro.transport.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;
import ro.transport.demo.domain.Delivery;
import ro.transport.demo.services.DeliveryService;

import java.util.List;

@RestController
@EnableJpaRepositories(basePackages = {"ro.transport.demo.repositories"})
public class DeliveryController {
    @Autowired
    private DeliveryService deliveryService;

    @CrossOrigin(allowedHeaders="*",allowCredentials="true")
    @PostMapping(value = "/delivery/add")
    @ResponseBody
    public Delivery add(@RequestBody Delivery jsonDelivery) {
        return deliveryService.add(jsonDelivery);
    }

    @GetMapping("/delivery/id/{id}")
    @ResponseBody
    public Delivery find(@PathVariable("id") Long id) {
        return deliveryService.find(id);
    }

    @GetMapping("/delivery/all")
    @ResponseBody
    public List<Delivery> findAll() {
        return deliveryService.findAll();
    }

    @GetMapping("/delivery/status/{statusName}")
    @ResponseBody
    public List<Delivery> findByStatusName(@PathVariable("statusName") String statusName) {
        return deliveryService.findByStatusName(statusName);
    }

    @CrossOrigin(allowedHeaders="*",allowCredentials="true")
    @PutMapping("/delivery/updateDelivery/id/{id}")
    @ResponseBody
    public Delivery update(@RequestBody Delivery jsonDelivery) {
        return deliveryService.update(jsonDelivery);
    }

    @CrossOrigin(allowedHeaders="*",allowCredentials="true")
    @DeleteMapping("/delivery/deleteDelivery/id/{id}")
    @ResponseBody
    public void delete(@PathVariable("id") Long id) {
        deliveryService.delete(id);
    }
}
