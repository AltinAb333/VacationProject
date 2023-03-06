package org.example.resources;

import com.google.gson.Gson;
import org.example.common.Util;
import org.example.domain.EmployeeBalance;
import org.example.services.EmployeeBalanceServiceImpl;


import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/employeeBalance")
public class EmployeeBalanceResources extends Util {
    private final Gson gson = new Gson();

    @GET
    @Produces("application/json")
    public Response getEmployeeBalances() throws Exception {
        String employees;
        try {
            ArrayList<EmployeeBalance> employeeBalanceArrayList = new EmployeeBalanceServiceImpl().getEmployeeBalances();
            employees = gson.toJson(employeeBalanceArrayList);
            if (employeeBalanceArrayList.isEmpty()){
                return Response.status(Response.Status.BAD_REQUEST).entity("Don't have any Employees").build();
            }
            return Response.status(Response.Status.OK).entity(employees).build();

        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getEmployeeBalance(@PathParam("id") String id) throws Exception {
        try {
            EmployeeBalance employeeBalance  = new EmployeeBalanceServiceImpl().getEmployeeBalanceByEmployeeId(id);
            if (employeeBalance == null){
                return Response.status(Response.Status.BAD_REQUEST).entity("Employee Balance with id: " + id + " does not exist").build();
            }
            return Response.status(Response.Status.OK).entity(gson.toJson(employeeBalance)).build();

        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

    }
    @POST
    @Consumes("application/json")
    public Response setEmployeeBalance(String payload) throws Exception {
        EmployeeBalance employeeBalance = gson.fromJson(payload, EmployeeBalance.class);
        try {
            new EmployeeBalanceServiceImpl().insertUpdateEmployeeBalance(employeeBalance);

        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return toJsonResponse(employeeBalance);
    }

    @DELETE
    @Path("{id}")
    @Consumes("application/json")
    public Response deleteEmployeeBalance(@PathParam("id") String id) throws Exception {
        try {
            new EmployeeBalanceServiceImpl().deleteEmployeeBalance(id);
            return Response.status(Response.Status.OK).entity("Employee removed successfully").build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
