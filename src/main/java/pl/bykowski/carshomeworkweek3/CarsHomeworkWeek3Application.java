package pl.bykowski.carshomeworkweek3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class CarsHomeworkWeek3Application {

    public static void main(String[] args) {
        SpringApplication.run(CarsHomeworkWeek3Application.class, args);
    }

}
