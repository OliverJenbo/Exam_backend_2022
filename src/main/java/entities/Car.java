package entities;


import dtos.CarDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Car")
@NamedQuery(name = "Car.deleteAllRows", query = "DELETE FROM Car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    private String name;
    private String brand;
    private String make;
    private int year;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Driver driver;
    @ManyToMany()
    private List<Race> raceList;
    public Car() {
    }

    public Car(int id, String name, String brand, String make, int year, Driver driver) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.make = make;
        this.year = year;
        this.driver = driver;
        this.raceList = new ArrayList<>();
    }

    public Car(String name, String brand, String make, int year, Driver driver) {
        this.name = name;
        this.brand = brand;
        this.make = make;
        this.year = year;
        this.driver = driver;
        this.raceList = new ArrayList<>();
    }

    public Car(String name, String brand, String make, int year) {
        this.name = name;
        this.brand = brand;
        this.make = make;
        this.year = year;
        this.driver = driver;
        this.raceList = new ArrayList<>();
    }

    public static List<Car> getEntities(List<CarDTO> carDTOS) {
        List<Car> attributes = new ArrayList<>();
        if (carDTOS != null) {
            carDTOS.forEach(carDTO -> attributes.add(new Car(carDTO.getName(), carDTO.getBrand(), carDTO.getMake(), carDTO.getYear(), carDTO.getDriver())));
        }
        return attributes;
    }

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

    public List<Race> getRaceList() {
        return raceList;
    }


    public void addRaceToCar(Race race) {
        if (race != null) {
            this.raceList.add(race);
            race.getCarList().add(this);
        }
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", make='" + make + '\'' +
                ", year=" + year +
                ", driver=" + driver +
                ", raceList=" + raceList +
                '}';
    }
}