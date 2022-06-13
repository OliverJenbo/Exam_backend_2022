package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.DriverFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/driver")
public class DriverResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final DriverFacade FACADE = DriverFacade.getDriverFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Path("/getDriversByRace/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getDriversByRace(@PathParam("id") int id) {
        return GSON.toJson(FACADE.getDriversByRace(id));
    }
}