/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sanandk.twitterapigatewaydb.service;

import co.sanandk.twitterapigatewaydb.Blacklistips;
import co.sanandk.twitterapigatewaydb.Whitelistips;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import twitter4j.JSONArray;
import twitter4j.JSONObject;

/**
 * REST Web Service
 *
 * @author SAnanda
 */
@Path("GetAllWhitelistIPs")
public class GetAllWhitelistIPsResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GetAllWhitelistIPsResource
     */
    public GetAllWhitelistIPsResource() {
    }

    /**
     * Retrieves representation of an instance of co.sanandk.twitterapigatewaydb.service.GetAllWhitelistIPsResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson(@QueryParam("AuthKey") String AuthKey) {
        //TODO return proper representation object
        int success=0;
        String original = "adminmulesoft";
        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
        if(AuthKey!=null)
        {
        try {
                original = original+sd.format(Calendar.getInstance().getTime());
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
        else
            AuthKey="";
        if(!AuthKey.equals(original))
        {
            JSONObject obj=new JSONObject();
        try
        {
        obj.put("success",success);
        if(AuthKey.equals(""))
            obj.put("error","Missing AuthKey!");        
        else
            obj.put("error","Invalid AuthKey!");        
        }
        catch(Exception e)
        {
        }
        
        return obj.toString();
        }
         EntityManagerFactory emf = Persistence.createEntityManagerFactory("dbService");
    EntityManager em = emf.createEntityManager();
    WhitelistipsFacadeREST cf=new WhitelistipsFacadeREST();
    cf.setEM(em);
    em.getTransaction().begin();

        List<Whitelistips> list = cf.findAll();
    
    em.getTransaction().commit();
    em.close();
    emf.close();
        success=1;
        
        JSONObject obj=new JSONObject();
        try
        {
        obj.put("success",success);
        if(success==0)
            obj.put("error","Error");        
        else
        {
            JSONArray arr=new JSONArray(list);
            obj.put("blacklistipsData", arr);
        }
        }
        catch(Exception e)
        {
        }
        
        return obj.toString();
    }


    /**
     * PUT method for updating or creating an instance of GetAllWhitelistIPsResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
