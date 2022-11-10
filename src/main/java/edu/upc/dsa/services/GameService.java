
package edu.upc.dsa.services;
import edu.upc.dsa.models.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import edu.upc.dsa.GameEngine;
import edu.upc.dsa.GameEngineImpl;
import edu.upc.dsa.models.Game;
import edu.upc.dsa.models.User;
import io.swagger.annotations.Api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Api(value = "/game", description = "Endpoint to Text Service")
@Path("game")
public class GameService {
    private GameEngine gm;
    public GameService() {
        this.gm = GameEngineImpl.getInstance();
        if (gm.sizeGames()==0){
            Game g1 = this.gm.addGame(new Game("1","Outer Wilds",8));
            Game g2 = this.gm.addGame(new Game("2","Destiny 2",3));
        }
        if (gm.sizeUsers()==0){
            this.gm.addUser(new User("12","Yelepo Girocompleto"));
            this.gm.addUser(new User("32","Churro Mototruco"));

        }
    }
    @GET
    @ApiOperation(value = "get all Users", notes = "See all users in the lsit")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class, responseContainer="List"),
    })
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {

        List<User> users = this.gm.getAllUsers();
        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users) {};
        return Response.status(201).entity(entity).build()  ;
    }
}
