/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sanandk.twitterapigatewaydb.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import twitter4j.JSONObject;

/**
 * REST Web Service
 *
 * @author SAnanda
 */
@Path("GetKey")
public class GetKeyResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GetKeyResource
     */
    public GetKeyResource() {
    }

    /**
     * Retrieves representation of an instance of co.sanandk.twitterapigatewaydb.service.GetKeyResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson(@QueryParam("AuthKey") String AuthKey,@QueryParam("username") String username, @QueryParam("password") String password) {
        //TODO return proper representation object
        int success=0;
        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
        
        String original = null;
        if(username!=null && password!=null && username.equals("admin") && password.equals("mulesoft"))
        {
            try {
                success=1;
                original = username+password+sd.format(Calendar.getInstance().getTime());
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(original.getBytes());
                byte[] digest = md.digest();
                StringBuffer sb = new StringBuffer();
                for (byte b : digest) {
                    sb.append(String.format("%02x", b & 0xff));
                }
                 original=sb.toString();
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(GetKeyResource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         JSONObject obj=new JSONObject();
            try
            {
            obj.put("success",success);
            if(success==0)
                obj.put("error","Invalid Login Credentials!");        
            else
                obj.put("key",original);        
            }
            catch(Exception e)
            {
            }

            return obj.toString();
        
    }

    /**
     * PUT method for updating or creating an instance of GetKeyResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
