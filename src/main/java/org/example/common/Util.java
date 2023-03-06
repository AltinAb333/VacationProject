package org.example.common;

import com.google.gson.Gson;

import javax.ws.rs.core.Response;

public class Util {
    protected Response toJsonResponse(Object obj){
        return Response.ok().entity(new Gson().toJson(obj)).build();
    }
}
