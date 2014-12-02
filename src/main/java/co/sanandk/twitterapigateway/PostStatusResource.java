/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sanandk.twitterapigateway;

import static co.sanandk.twitterapigateway.Config.twitter;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;

/**
 * REST Web Service
 *
 * @author SAnanda
 */
@Path("postStatus")
public class PostStatusResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PostStatusResource
     */
    public PostStatusResource() {
    }

    /**
     * Retrieves representation of an instance of co.sanandk.twitterapigateway.PostStatusResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson(@QueryParam("AuthToken") String AuthToken, @QueryParam("AuthSecret") String AuthSecret, @QueryParam("statusText") String statusText) {
        //TODO return proper representation object
        int success=0;
        Status status;
        
        String str="Invalid Parameters";
        if(AuthToken!=null && AuthSecret!=null)
        {
            twitter.setOAuthAccessToken(new AccessToken(AuthToken, AuthSecret));
        if(statusText.length()<1)
            str="Invalid status text!";
        else
        {
        try {    
            
            status = twitter.updateStatus(statusText);
            success=1;
        } catch (TwitterException ex) {
            //Logger.getLogger(GetOauthTokenResource.class.getName()).log(Level.SEVERE, null, ex);
            str=ex.getMessage();
        }
        }
        
        }
        JSONObject obj=new JSONObject();
        try
        {
        obj.put("success",success);
        if(success==0)
            obj.put("error",str);
        }
        catch(Exception e)
        {
        }
        
        return obj.toString();
    }

    /**
     * PUT method for updating or creating an instance of PostStatusResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
