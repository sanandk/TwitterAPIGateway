/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sanandk.twitterapigateway;

import static co.sanandk.twitterapigateway.Config.generateAuthorizationToken;
import static co.sanandk.twitterapigateway.Config.getAuthorizationToken;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import twitter4j.JSONObject;
import twitter4j.auth.RequestToken;

/**
 * REST Web Service
 *
 * @author SAnanda
 */
@Path("getAuthorizationURL")
public class GetAuthorizationURLResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GetAuthorizationURLResource
     */
    public GetAuthorizationURLResource() {
    }

    /**
     * Retrieves representation of an instance of co.sanandk.twitterapigateway.GetAuthorizationURLResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        int success=0;
        String str="Error";
        Config.init();
        RequestToken requestToken = generateAuthorizationToken();
        if(requestToken!=null)
        {
           str=requestToken.getAuthorizationURL();
           success=1;
        }
        JSONObject obj=new JSONObject();
        try
        {
        obj.put("success",success);
        if(success==1)
        {
            obj.put("url",str);
            obj.put("Token",requestToken.getToken());
            obj.put("TokenSecret",requestToken.getTokenSecret());
        }
        else
            obj.put("error",str);
        }
        catch(Exception e)
        {
        }
        
        return obj.toString();
    }

    /**
     * PUT method for updating or creating an instance of GetAuthorizationURLResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }
}
