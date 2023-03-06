package org.example.resources;

import com.google.gson.Gson;
import org.example.common.Util;
import org.example.domain.Vacation;
import org.example.services.VacationServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/vacation")
public class VacationResources extends Util {
    private final Gson gson = new Gson();

    @GET
    @Produces("application/json")
    public Response getVacation() throws Exception {
        String vacation;
        try {
            ArrayList<Vacation> vacationList = new VacationServiceImpl().getVacation();
            vacation = gson.toJson(vacationList);
            if (vacationList.isEmpty())
                return Response.status(Response.Status.BAD_REQUEST).entity("Don't have any Vacation").build();
            return Response.status(Response.Status.OK).entity(vacation).build();

        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getVacation(@PathParam("id") String id) throws Exception {

        try {
            Vacation vacation = new VacationServiceImpl().getOneVacation(id);

            if (vacation == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("vacation with id: " + id + " does not exist").build();
            }
            return toJsonResponse(vacation);

        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

    }

    @POST
    @Consumes("application/json")
    public Response insertVacation(String payload) throws Exception {
        Vacation vacation = gson.fromJson(payload, Vacation.class);

        try {
            Vacation.type.valueOf(vacation.getType());
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid type is provided").build();
        }
        try {
            new VacationServiceImpl().insertUpdateVacation(vacation);
            return Response.status(Response.Status.CREATED).entity("Vacation update successfully").build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("{id}")
    @Consumes("application/json")
    public Response deleteVacation(@PathParam("id") String id) throws Exception {
        try {
            new VacationServiceImpl().deleteVacation(id);
            return Response.status(Response.Status.OK).entity("Vacation removed successfully").build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

}
