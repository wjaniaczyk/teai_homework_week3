package pl.bykowski.carshomeworkweek3.service;

import org.springframework.stereotype.Service;
import pl.bykowski.carshomeworkweek3.entity.Car;
import pl.bykowski.carshomeworkweek3.entity.CarRequestDTO;
import pl.bykowski.carshomeworkweek3.entity.Color;
import pl.bykowski.carshomeworkweek3.exception.CarNotFoundException;
import pl.bykowski.carshomeworkweek3.repo.ICarRepo;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {

    private ICarRepo carRepo;

    public CarService(ICarRepo carRepo) {
        this.carRepo = carRepo;
    }

    public List<Car> getAll() {
       return carRepo.findAll().stream()
               .sorted(Comparator.comparing(Car::getId))
               .collect(Collectors.toList());
    }

    public List<Car> getAllByColor(String color) {
        return carRepo.findByColor(color).stream()
                .sorted(Comparator.comparing(Car::getId))
                .collect(Collectors.toList());
    }

    public Car getById(long id) {
        return carRepo.findById(id).orElseThrow(() -> new CarNotFoundException(id));
    }

    public boolean addCar(CarRequestDTO carRequestDTO) {
        Car car = carRequestDtoToCar(carRequestDTO);
        car.setId(createId());
        return carRepo.save(car);
    }

    private Long createId() {
        List<Car> all = carRepo.findAll();
        all.sort(Comparator.comparing(Car::getId, Comparator.reverseOrder()));
        Optional<Car> first = all.stream().findFirst();
        return first.map(value -> value.getId() + 1L).orElse(1L);
    }

    public boolean updateCar(long id, CarRequestDTO carRequestDTO) {
        Car carExists = getById(id);
        carRepo.delete(carExists);
        Car car = carRequestDtoToCar(carRequestDTO);
        car.setId(id);
        return carRepo.save(car);
    }

    public void updatePartCar(long id, Map<String, Object> updates) {
        Car car = getById(id);
        if (updates.containsKey("mark")) {
            car.setMark((String) updates.get("mark"));
        }
        if (updates.containsKey("model")) {
            car.setModel((String) updates.get("model"));
        }
        if (updates.containsKey("color")) {
            car.setColor(Color.valueOf(updates.get("color").toString().toUpperCase()));
        }
    }

    public boolean deleteCar(long id) {
        Car car = getById(id);
        return carRepo.delete(car);
    }

    private Car carRequestDtoToCar(CarRequestDTO carRequestDTO){
        Car car = new Car();
        car.setColor(Color.valueOf(carRequestDTO.getColor().toUpperCase()));
        car.setMark(carRequestDTO.getMark());
        car.setModel(carRequestDTO.getModel());
        return car;
    }
}
