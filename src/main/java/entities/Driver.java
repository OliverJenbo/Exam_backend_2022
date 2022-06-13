package entities;


import dtos.DriverDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Driver")
@NamedQuery(name = "Driver.deleteAllRows", query = "DELETE FROM Driver")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    private String name;
    private int birthYear;
    private String gender;

    @OneToMany(mappedBy = "driver")
    private List<Car> cars;

    public Driver() {
    }

    public Driver(int id, String name, int birthYear, String gender) {
        this.id = id;
        this.name = name;
        this.birthYear = birthYear;
        this.gender = gender;
    }

    public Driver(String name, int birthYear, String gender) {
        this.name = name;
        this.birthYear = birthYear;
        this.gender = gender;
    }

    public static List<Driver> getEntities(List<DriverDTO> driverDTOS) {
        List<Driver> attributes = new ArrayList<>();
        if (driverDTOS != null) {
            driverDTOS.forEach(driverDTO -> attributes.add(new Driver(driverDTO.getName(), driverDTO.getBirthYear(), driverDTO.getGender())));
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

    public int getBirthYear() {
        return birthYear;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthYear=" + birthYear +
                ", gender='" + gender + '\'' +
                ", cars=" + cars +
                '}';
    }
}