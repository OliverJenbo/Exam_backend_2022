package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.CarDTO;
import dtos.DriverDTO;
import dtos.RaceDTO;
import facades.CarFacade;
import facades.RaceFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/race")
public class RaceResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final RaceFacade FACADE = RaceFacade.getRaceFacade(EMF);
    private static final CarFacade CAR_FACADE = CarFacade.getCarFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }

    @Path("/create")
    @RolesAllowed("Admin")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String createRace(String race) {

        RaceDTO raceDTO = GSON.fromJson(race, RaceDTO.class);
        RaceDTO newRaceDTO = FACADE.createRace(raceDTO);
        return GSON.toJson(newRaceDTO);
    }

    @Path("/all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getRaces() {
        List<RaceDTO> raceDTOS = FACADE.getAllRaces();
        return GSON.toJson(raceDTOS);
    }

    @Path("getDriversById/{id}")
    @GET
    @Produces("application/json")
    public String getDriversById(@PathParam("id") int id) {
        List<DriverDTO> driverDTO = FACADE.getDriversByRace(id);
        return GSON.toJson(driverDTO);
    }
}