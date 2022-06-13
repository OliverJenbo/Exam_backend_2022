package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.CarDTO;
import dtos.RaceDTO;
import facades.CarFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/car")
public class CarResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final CarFacade FACADE = CarFacade.getCarFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Path("/getCarsByRace/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getCarsByRace(@PathParam("id") int id) {
        return GSON.toJson(FACADE.getCarsByRace(id));
    }

    @Path("/all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getCars() {
        List<CarDTO> carsDTO = FACADE.getAllCars();
        return GSON.toJson(carsDTO);
    }

    @Path("/connectCarToRace/{carId}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String connectCarToRace(@PathParam("carId") int carId, String race) {
        Integer raceId = GSON.fromJson(race, Integer.class);
        CarDTO connectedCar = FACADE.connectToRace(carId, raceId);
        return GSON.toJson(connectedCar);
    }

    @Path("/delete/{id}")
    @RolesAllowed("Admin")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String deleteCar(@PathParam("id") int id) {
        CarDTO newCarDTO = FACADE.deleteCar(id);
        return GSON.toJson(newCarDTO);
    }

}