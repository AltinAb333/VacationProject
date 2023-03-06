package org.example.resources;

import com.google.gson.Gson;
import org.example.common.Util;
import org.example.domain.Employee;
import org.example.services.EmployeeServiceImpl;


import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/Employee")

public class EmployeeResources extends Util {
    private final Gson gson = new Gson();

    @GET
    @Produces("application/json")
    public Response getEmployees() throws Exception {
        String employees;
        try {
            ArrayList<Employee> employeeArrayList = new EmployeeServiceImpl().getEmployees();
            employees = gson.toJson(employeeArrayList);
            if (employeeArrayList.isEmpty()){
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
    public Response getEmployee(@PathParam("id") String id) throws Exception {
        String employee;

        try {
            ArrayList<Employee> employeeArrayList  = new EmployeeServiceImpl().getOneEmployee(id);
            employee = gson.toJson(employeeArrayList);
            if (employeeArrayList.isEmpty())
                return Response.status(Response.Status.BAD_REQUEST).entity("Employee with id: " + id + " does not exist").build();
            return Response.status(Response.Status.OK).entity(employee).build();

        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

    }
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response setEmployee(String payload) throws Exception {
        try {
            Employee employee = gson.fromJson(payload, Employee.class);
            new EmployeeServiceImpl().insertUpdateEmployee(employee);
            return toJsonResponse(employee);

        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("{id}")
    @Consumes("application/json")
    public Response deleteEmployee(@PathParam("id") String id) throws Exception {
        try {
            new EmployeeServiceImpl().deleteEmployee(id);
            return Response.status(Response.Status.OK).entity("Employee removed successfully").build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
