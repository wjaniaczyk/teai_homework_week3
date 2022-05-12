package pl.bykowski.carshomeworkweek3.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bykowski.carshomeworkweek3.entity.Car;
import pl.bykowski.carshomeworkweek3.entity.CarRequestDTO;
import pl.bykowski.carshomeworkweek3.service.CarService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public ResponseEntity<CollectionModel<Car>> getAll(@RequestParam(required = false) String color){
        List<Car> all;
        if(color != null){
            all = carService.getAllByColor(color);
        } else {
            all = carService.getAll();
        }
        all.forEach(car -> car.add(linkTo(CarController.class).slash(car.getId()).withSelfRel()));
        Link link = linkTo(methodOn(CarController.class).getAll(color)).withSelfRel();
        CollectionModel<Car> result = CollectionModel.of(all, link);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Car> getById(@PathVariable long id){
        Car byId = carService.getById(id);
        byId.add(linkTo(CarController.class).slash(byId.getId()).withSelfRel());
        return new ResponseEntity<>(byId, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CarRequestDTO> addCar(@Valid @RequestBody CarRequestDTO car){
        boolean added = carService.addCar(car);
        if(added){
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarRequestDTO> updateCar(@PathVariable long id,
                                       @RequestBody CarRequestDTO car){
        boolean updated = carService.updateCar(id, car);
        if(updated){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CarRequestDTO> updatePartCar(@PathVariable long id,
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
