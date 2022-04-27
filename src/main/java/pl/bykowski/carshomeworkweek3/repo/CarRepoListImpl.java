package pl.bykowski.carshomeworkweek3.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.bykowski.carshomeworkweek3.entity.Car;
import pl.bykowski.carshomeworkweek3.entity.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CarRepoListImpl implements ICarRepo{

    List<Car> cars;

    @Autowired
    public CarRepoListImpl() {
        this.cars = new ArrayList<>();
        cars.add(new Car(1L, "Honda", "Civic", Color.BLACK));
        cars.add(new Car(2L, "Mazda", "6", Color.WHITE));
    }

    @Override
    public List<Car> findAll() {
        return cars;
    }

    @Override
    public List<Car> findByColor(String color) {
        return cars.stream()
                .filter(c -> c.getColor().toString().equalsIgnoreCase(color))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Car> findById(long id) {
        return cars.stream()
                .filter(c -> c.getId() == id)
                .findFirst();
    }

    @Override
    public boolean save(Car car) {
        return cars.add(car);
    }

    @Override
    public boolean delete(Car car) {
        return cars.remove(car);
    }
}
