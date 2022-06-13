package entities;


import dtos.RaceDTO;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Race")
@NamedQuery(name = "Race.deleteAllRows", query = "DELETE FROM Race")
public class Race {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    private String name;
    private String date;
    private double time;
    private String location;

    @ManyToMany(mappedBy = "raceList")
    private List<Car> carList;

    public Race() {
    }

    public Race(int id, String name, String date, double time, String location) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.location = location;
        this.carList = new ArrayList<>();
    }

    public Race(String name, String date, double time, String location) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.location = location;
        this.carList = new ArrayList<>();
    }

    public static List<Race> getEntities(List<RaceDTO> raceDTOS) {
        List<Race> race = new ArrayList<>();
        if (raceDTOS != null) {
            raceDTOS.forEach(raceDTO -> race.add(new Race(raceDTO.getName(), raceDTO.getDate(), raceDTO.getTime(), raceDTO.getLocation())));
        }
        return race;
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

    public String getDate() {
        return date;
    }

    public double getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public List<Car> getCarList() {
        return carList;
    }


    public void addCarToRace(Car car) {
        if (car != null) {
            this.carList.add(car);
            car.getRaceList().add(this);
        }
    }

    @Override
    public String toString() {
        return "Race{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", location='" + location + '\'' +
                ", carList=" + carList +
                '}';
    }
}