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
import jdk.tools.jlink.internal.Platform;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Api(value = "/game", description = "Endpoint to Game Service")
@Path("game")
public class GameService {
    private GameEngine gm;
    public GameService() {
        this.gm = GameEngineImpl.getInstance();
        if (gm.sizeGames()==0){
            this.gm.createGame("Outer Wilds","10/10 best game",3);
            this.gm.createGame("Destiny 2","Aliens go BRRRRR",5);
            this.gm.createGame("Jak & Daxter", "Un man y su lagarto",4);

        }
        if (gm.sizeUsers()==0){
            this.gm.addUser("Yelepo");
            this.gm.addUser("Churro Mototruco");
        }
        //Arranca el programa de exploracion del espacio: Outer Wilds Ventures
        this.gm.startGame("Outer Wilds","Yelepo");
    }
    @POST
    @ApiOperation(value = "Create Game", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Game.class),
            @ApiResponse(code = 451, message = "You're not legally allowed to see this")

    })
    @Path("/game/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createGame(Game game) {
        Game g = this.gm.createGame(game.getIdGame(),game.getDesc(),game.getNumLvl());
        if (g==null){
            return Response.status(451).build();
        }
        return Response.status(201).entity(g).build();
    }

    @GET
    @ApiOperation(value = "get actual level", notes = "Get the actual lvl of a player")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "All good", response = Integer.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "Player or game does not exists"),
    })
    @Path("/player/{id}/actualLevel")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActualLevel(@PathParam("id") String id) {
        int lvl = this.gm.getActualLvl(id);
        if (lvl>=0){
            return Response.status(200).entity(lvl).build();
        }
        return Response.status(404).build();
    }

    @PUT
    @ApiOperation(value = "Start a game", notes = "Start a game?")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "All good", response = Game.class),
            @ApiResponse(code = 404, message = "Game does not exists"),
    })
    @Path("/player/startGame")
    public Response startGame(String idGame, String idUser) {
        Game g = this.gm.startGame(idGame,idUser);
        if(g!=null){
            return Response.status(201).entity(g).build();
        }
        return Response.status(404).build();
    }

    @GET
    @ApiOperation(value = "get actual points", notes = "Get the actual points of a player")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "All good", response = String.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "Player or game does not exists"),
    })
    @Path("/player/{id}/actualPoints")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActualPoints(@PathParam("id") String idUser) {
        String points = this.gm.getActualPoints(idUser);
        if(points!=null){
            return Response.status(200).entity(points).build();
        }
        return Response.status(404).build();
    }

    @PUT
    @ApiOperation(value = "Advance lvl", notes = "Make the user move 1 lvl")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Next lvl"),
            @ApiResponse(code = 202, message = "Game ended"),
            @ApiResponse(code = 404, message = "Player/game does not exist")
    })
    @Path("/player/nextLvl")
    public Response nextLvl(String idUser, int points, String date) {
        User u = this.gm.nextLvl(idUser,points,String.valueOf(java.time.LocalDate.now()));
        if(u==null){
            return Response.status(404).build();
        }
        if (u.getPlaying()==true){
            return Response.status(200).build();
        }
        return Response.status(202).build();
    }

    @PUT
    @ApiOperation(value = "End a match", notes = "End a match")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Match ended"),
            @ApiResponse(code = 404, message = "Player/match does not exist"),
    })
    @Path("/player/{id}/endMatch")
    @Produces(MediaType.APPLICATION_JSON)
    public Response endMatch(@PathParam("id") String id) {
        User u = this.gm.endMatch(id);
        if (u!=null){
            return Response.status(200).build();
        }
        return Response.status(404).build();
    }

    @POST
    @ApiOperation(value = "Sort players", notes = "Sort players in a game by points desc")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "All good", response = User.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "This game/user/match does not exist"),
    })
    @Path("/games/{id}/sortPlayers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sortPlayers(@PathParam("id") String idGame) {
        Game g = this.gm.getGame(idGame);
        List<User> users = this.gm.sortUsers(g);
        if (users==null){
            return Response.status(404).build();
        }
        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users){};
        return Response.status(200).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "Get player's mathc", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "All good", response = Match.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "This player does not exist")
    })
    @Path("/player/{id}/matches")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMatchesUser(@PathParam("id") String id) {
        try{
            List<Partida> partidas = this.manager.getPartidasPlayer(id);
            GenericEntity<List<Partida>> entity = new GenericEntity<List<Partida>>(partidas){};
            return Response.status(200).entity(entity).build();
        } catch (PlayerDoesNotExistException e) {
            return Response.status(404).build();
        }
    }
}