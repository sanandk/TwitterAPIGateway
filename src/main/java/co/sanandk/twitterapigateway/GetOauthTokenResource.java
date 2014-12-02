/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sanandk.twitterapigateway;


import static co.sanandk.twitterapigateway.Config.generateAuthorizationToken;
import static co.sanandk.twitterapigateway.Config.getAuthorizationToken;
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
import twitter4j.auth.RequestToken;

/**
 * REST Web Service
 *
 * @author SAnanda
 */
@Path("getOauthToken")
public class GetOauthTokenResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GetOauthTokenResource
     */
    public GetOauthTokenResource() {
    }

    /**
     * Retrieves representation of an instance of co.sanandk.twitterapigateway.GetOauthTokenResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson(@QueryParam("Token") String Token, @QueryParam("TokenSecret") String TokenSecret, @QueryParam("verifier") String verifier, @QueryParam("pin") String pin) {
        //TODO return proper representation object
        Config.init();
        
        int success=0;
        String str="Error";
        RequestToken requestToken = new RequestToken(Token,TokenSecret);
        
         //twitter.setOAuthAccessToken(new AccessToken(Token, TokenSecret));
          Status status;
        try {
            twitter.getOAuthAccessToken(requestToken, verifier);
            //status = twitter.updateStatus("latestStatus");
            success=1;
      //      System.out.println("Successfully updated the status to [" + status.getText() + "].");
        } catch (TwitterException ex) {
            Logger.getLogger(GetOauthTokenResource.class.getName()).log(Level.SEVERE, null, ex);
            str=ex.getMessage();
        }
        
            
        JSONObject obj=new JSONObject();
        try
        {
        obj.put("success",success);
        if(success==1)
        {
            obj.put("AuthToken",twitter.getOAuthAccessToken().getToken());
            obj.put("AuthSecret",twitter.getOAuthAccessToken().getTokenSecret());
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
     * PUT method for updating or creating an instance of GetOauthTokenResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }
}
