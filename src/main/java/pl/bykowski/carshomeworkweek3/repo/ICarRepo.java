package pl.bykowski.carshomeworkweek3.repo;

import pl.bykowski.carshomeworkweek3.entity.Car;

import java.util.List;
import java.util.Optional;

public interface ICarRepo {

    List<Car> findAll();

    List<Car> findByColor(String color);

    Optional<Car> findById(long id);

    boolean save(Car car);

    boolean delete(Car car);
}
