package dtos;

import entities.Driver;

import java.util.List;
import java.util.Objects;

public class DriversDTO {

    private List<DriverDTO> drivers;

    public DriversDTO(List<Driver> drivers) {
        this.drivers = DriverDTO.getDtos(drivers);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DriversDTO that = (DriversDTO) o;
        return Objects.equals(drivers, that.drivers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(drivers);
    }

    @Override
    public String toString() {
        return "DriversDTO{" +
                "drivers=" + drivers +
                '}';
    }
}
