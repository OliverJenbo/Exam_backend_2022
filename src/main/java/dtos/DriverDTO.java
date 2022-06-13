package dtos;

import entities.Driver;

import java.util.ArrayList;
import java.util.List;

public class DriverDTO {

/** DRIVER VARIABLES **/
    private int id;
    private String name;
    private int birthYear;
    private String gender;

/** DRIVERDTO CONSTRUCTOR**/
    public DriverDTO(Driver driver) {
        this.id = driver.getId();
        this.name = driver.getName();
        this.birthYear = driver.getBirthYear();
        this.gender = driver.getGender();
    }

/** Converts DTOs into Entities **/
    public static List<DriverDTO> getDtos(List<Driver> driver) {
        List<DriverDTO> driverDTOS = new ArrayList();
        if (driver != null) {
            driver.forEach(d -> driverDTOS.add(new DriverDTO(d)));
        }
        return driverDTOS;
    }


/** Getters & Setters**/
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

    public int getBirthYear() {
        return birthYear;
    }

    public String getGender() {
        return gender;
    }


    @Override
    public String toString() {
        return "DriverDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthYear=" + birthYear +
                ", gender='" + gender + '\'' +
                '}';
    }
}
