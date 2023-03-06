package org.example.resources;

import com.google.gson.Gson;
import org.example.common.Util;
import org.example.domain.Department;
import org.example.services.DepartmentServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/Department")
public class DepartmentResources extends Util {
    private final Gson gson = new Gson();

    @GET
    @Produces("application/json")
    public Response getDepartments() throws Exception {
        String departments;
        try {
            ArrayList<Department> departmentArrayList = new DepartmentServiceImpl().getDepartment();
            departments = gson.toJson(departmentArrayList);
            if (departmentArrayList.isEmpty())
                return Response.status(Response.Status.BAD_REQUEST).entity("Don't have any Departments").build();
            return Response.status(Response.Status.OK).entity(departments).build();

        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getDepartment(@PathParam("id") String id) throws Exception {
        String department;

        try {
            ArrayList<Department> departmentArrayList  = new DepartmentServiceImpl().getOneDepartment(id);
            department = gson.toJson(departmentArrayList);
            if (departmentArrayList.isEmpty())
                return Response.status(Response.Status.BAD_REQUEST).entity("Department with id: " + id + " does not exist").build();
            return Response.status(Response.Status.OK).entity(department).build();

        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

    }
    @POST
    @Consumes("application/json")
    public Response setDepartment(String payload) throws Exception {
        Department department = gson.fromJson(payload, Department.class);
        try {
            new DepartmentServiceImpl().insertUpdateDepartment(department);
                return toJsonResponse(department);
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("{id}")
    @Consumes("application/json")
    public Response deleteDepartment(@PathParam("id") String id) throws Exception {
        try {
            new DepartmentServiceImpl().deleteDepartment(id);
            return Response.status(Response.Status.OK).entity("Department removed successfully").build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
