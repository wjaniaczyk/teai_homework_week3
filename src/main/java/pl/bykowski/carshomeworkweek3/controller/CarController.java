package pl.bykowski.carshomeworkweek3.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bykowski.carshomeworkweek3.entity.Car;
import pl.bykowski.carshomeworkweek3.entity.CarRequestDTO;
import pl.bykowski.carshomeworkweek3.service.CarService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cars")
public class CarController {

    private CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping(produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Car>> getAll(@RequestParam(required = false) String color){
        List<Car> all = carService.getAll(color);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Car> getById(@PathVariable long id){
        Car byId = carService.getById(id);
        return new ResponseEntity<>(byId, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addCar(@RequestBody CarRequestDTO car){
        boolean added = carService.addCar(car);
        if(added){
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCar(@PathVariable long id,
                                       @RequestBody CarRequestDTO car){
        boolean updated = carService.updateCar(id, car);
        if(updated){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updatePartCar(@PathVariable long id,
                                           @RequestBody Map<String, Object> updates){
        carService.updatePartCar(id, updates);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}", produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Car> deleteById(@PathVariable long id){
        Car byId = carService.getById(id);
        boolean deleted = carService.deleteCar(id);
        if(deleted){
            return new ResponseEntity<>(byId, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
