package org.example.resources;

import com.google.gson.Gson;
import org.example.common.Util;
import org.example.domain.Holidays;
import org.example.services.HolidaysServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/Holidays")
public class HolidaysResources extends Util {
    private final Gson gson = new Gson();

    @GET
    @Produces("application/json")
    public Response getHolidays() throws Exception {
        String holidays;
        try {
            ArrayList<Holidays> holidaysArrayList = new HolidaysServiceImpl().getHolidays();
            holidays = gson.toJson(holidaysArrayList);
            if (holidaysArrayList.isEmpty())
                return Response.status(Response.Status.BAD_REQUEST).entity("Don't have any holidays").build();
            return Response.status(Response.Status.OK).entity(holidays).build();

        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getHoliday(@PathParam("id") String id) throws Exception {
        String holiday;

        try {
            ArrayList<Holidays> holidaysArrayList  = new HolidaysServiceImpl().getOneHoliday(id);
            holiday = gson.toJson(holidaysArrayList);
            if (holidaysArrayList.isEmpty())
                return Response.status(Response.Status.BAD_REQUEST).entity("holiday with id: " + id + " does not exist").build();
            return Response.status(Response.Status.OK).entity(holiday).build();

        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

    }
    @POST
    @Consumes("application/json")
    public Response setHoliday(String payload) throws Exception {
        Holidays holidays = gson.fromJson(payload, Holidays.class);
        try {
            new HolidaysServiceImpl().insertUpdateHoliday(holidays);
            return toJsonResponse(holidays);

        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("{id}")
    @Consumes("application/json")
    public Response deleteHoliday(@PathParam("id") String id) throws Exception {
        try {
            new HolidaysServiceImpl().deleteHoliday(id);
            return Response.status(Response.Status.OK).entity("Holiday removed successfully").build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
