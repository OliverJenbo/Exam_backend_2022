package rest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.User;
import errorhandling.API_Exception;
import facades.UserFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("signup")
public class UserResource {

    private static final EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    UserFacade FACADE = UserFacade.getUserFacade(emf);
    User user = new User();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signup(String jsonString) throws API_Exception {
        String username;
        String password;
        try {
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            username = json.get("username").getAsString();
            user.setUserName(username);
            password = json.get("password").getAsString();
            user.setUserPass(password);
            FACADE.CreateNewUser(user);
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied", 400, e);
        }
        return null;
    }
}