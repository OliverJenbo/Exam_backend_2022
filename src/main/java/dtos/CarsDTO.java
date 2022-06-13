package dtos;

import entities.Car;
import java.util.List;
import java.util.Objects;

public class CarsDTO {

    private List<CarDTO> cars;

    public CarsDTO(List<Car> cars) {
        this.cars = CarDTO.getDtos(cars);
    }

    public List<CarDTO> getCars() {
        return cars;
    }

    /** AUTO GENERATED EQUALS & HASHCODE, HELPS AVOIDING DOUBLE INJECTIONS INTO THE DATABASE**/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarsDTO carsDTO = (CarsDTO) o;
        return Objects.equals(cars, carsDTO.cars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cars);
    }
}
