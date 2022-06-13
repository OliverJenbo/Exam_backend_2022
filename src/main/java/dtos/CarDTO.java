package dtos;

import entities.Car;
import entities.Driver;
import java.util.ArrayList;
import java.util.List;

public class CarDTO {
/** CAR VARIABLES **/
    private int id;
    private String name;
    private String brand;
    private String make;
    private int year;
    private Driver driver;

/** DRIVERDTO CONSTRUCTOR**/
    public CarDTO(Car car) {
        this.id = car.getId();
        this.name = car.getName();
        this.brand = car.getBrand();
        this.make = car.getMake();
        this.year = car.getYear();
    }

/** Converts DTOs into Entities **/
    public static List<CarDTO> getDtos(List<Car> car) {
        List<CarDTO> carDTOS = new ArrayList();
        if (car != null) {
            car.forEach(c -> carDTOS.add(new CarDTO(c)));
        }
        return carDTOS;
    }

/** GETTERS AND SETTERS **/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public String getMake() {
        return make;
    }

    public int getYear() {
        return year;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    @Override
    public String toString() {
        return "CarDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", make='" + make + '\'' +
                ", year=" + year +
                '}';
    }
}
