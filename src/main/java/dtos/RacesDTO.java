package dtos;

import entities.Race;

import java.util.List;
import java.util.Objects;

public class RacesDTO {

    private List<RaceDTO> races;

    public RacesDTO(List<Race> races) {
        this.races = RaceDTO.getDtos(races);
    }

    public void setRaces(List<RaceDTO> races) {
        this.races = races;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RacesDTO racesDTO = (RacesDTO) o;
        return Objects.equals(races, racesDTO.races);
    }

    @Override
    public int hashCode() {
        return Objects.hash(races);
    }

    @Override
    public String toString() {
        return "RacesDTO{" +
                "races=" + races +
                '}';
    }
}
