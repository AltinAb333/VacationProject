package org.example.resources;

import com.google.gson.Gson;
import org.example.common.Util;
import org.example.domain.Vacation;
import org.example.domain.VacationRequest;
import org.example.services.VacationRequestServiceImpl;
import org.example.services.VacationServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/vacationRequest")
public class VacationRequestResources extends Util {
    private final Gson gson = new Gson();

    @GET
    @Produces("application/json")
    public Response getVacationRequest() throws Exception {
        String vacationRequest;
        try {
            ArrayList<VacationRequest> vacationRequestList = new VacationRequestServiceImpl().getVacationRequests();
            vacationRequest = gson.toJson(vacationRequestList);
            if (vacationRequestList.isEmpty())
                return Response.status(Response.Status.BAD_REQUEST).entity("Don't have any Vacation Request").build();
            return Response.status(Response.Status.OK).entity(vacationRequest).build();

        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getVacationRequest(@PathParam("id") String id) throws Exception {
        String vacationRequest;

        try {
            VacationRequest vacationRequestsList = new VacationRequestServiceImpl().getOneVacationRequest(id);
            vacationRequest = gson.toJson(vacationRequestsList);
            if (vacationRequestsList == null)
                return Response.status(Response.Status.BAD_REQUEST).entity("request with id: " + id + " does not exist").build();
            return Response.status(Response.Status.OK).entity(vacationRequest).build();

        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response setVacationRequest(String payload) throws Exception {
        validatePayload(payload);
        VacationRequest vacationRequest = gson.fromJson(payload, VacationRequest.class);

        try {
            VacationRequest.type.valueOf(vacationRequest.getType());
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid type is provided").build();
        }

        try {
            new VacationRequestServiceImpl().insertUpdateVacationRequest(vacationRequest);
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

        return toJsonResponse(vacationRequest);
    }

    private void validatePayload(String payload) {
        if (payload == null || payload.equals("")) {
            throw new BadRequestException("Invalid JSON provided.");
        }
    }

    @DELETE
    @Path("{id}")
    @Consumes("application/json")
    public Response deleteVacationRequest(@PathParam("id") String id) throws Exception {
        try {
            new VacationRequestServiceImpl().deleteVacationRequest(id, null);
            return Response.status(Response.Status.OK).entity("Vacation request removed successfully").build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/{id}/approve")
    @Consumes("application/json")
    @Produces("application/json")
    public Response approveRequest(@PathParam("id") String id) throws Exception {
        try {
            new VacationRequestServiceImpl().approveStatus(id);
            Vacation vacation = new VacationServiceImpl().getOneVacation(id);
            return Response.status(Response.Status.OK).entity(vacation).build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
