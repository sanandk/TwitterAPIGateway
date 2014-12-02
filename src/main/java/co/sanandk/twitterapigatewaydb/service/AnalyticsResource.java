/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sanandk.twitterapigatewaydb.service;

import co.sanandk.twitterapigatewaydb.Logs;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
@Path("Analytics")
public class AnalyticsResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AnalyticsResource
     */
    public AnalyticsResource() {
    }

    /**
     * Retrieves representation of an instance of co.sanandk.twitterapigatewaydb.service.AnalyticsResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson(@QueryParam("query") String query, @QueryParam("count") String count,@QueryParam("AuthKey") String AuthKey) {
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
    if(query.equals("getTopUsed"))
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("dbService");
        EntityManager em = emf.createEntityManager();
           List<Logs> temp;
           ArrayList<Logs> list=new ArrayList<Logs>();
           temp = em.createQuery("SELECT l.endpointname, count(l.endpointname) as cnt FROM Logs l GROUP BY l.endpointname ORDER BY cnt DESC").getResultList();
           int i=0;
           if(count==null)
               count=temp.size()+"";
           for(Object l: temp)
           {
               if(i < Integer.parseInt(count))
                list.add(temp.get(i));
               i++;
           }
           em.close();
           emf.close();
             //list = em.createQuery("SELECT endpointname, count(*) AS counter FROM Logs GROUP BY endpointname ORDER BY counter DESC LIMIT "+count).getResultList();
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
                obj.put("result", arr);
            }
            }
            catch(Exception e)
            {
            }

            return obj.toString();
    } 
    else if(query.equals("getMostErrors"))
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("dbService");
        EntityManager em = emf.createEntityManager();

           List<Logs> temp;
           ArrayList<Logs> list=new ArrayList<Logs>();
           temp = em.createQuery("SELECT l.endpointname,  count(l.endpointname) as cnt FROM Logs l WHERE l.success=0 AND l.error!=\"API Limit Exceeded!\" GROUP BY l.endpointname ORDER BY cnt DESC").getResultList();        
           int i=0;
           if(count==null)
               count=temp.size()+"";
           for(Object l: temp)
           {
               if(i < Integer.parseInt(count) && !l.toString().contains("API Limit Exceeded"))
                list.add(temp.get(i));
               i++;
           }
           em.close();
           emf.close();
             //list = em.createQuery("SELECT endpointname, count(*) AS counter FROM Logs GROUP BY endpointname ORDER BY counter DESC LIMIT "+count).getResultList();
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
                obj.put("result", arr);
            }
            }
            catch(Exception e)
            {
            }

            return obj.toString();
    }
        
              JSONObject obj=new JSONObject();
            try
            {
            obj.put("success",success);
            if(success==0)
                obj.put("error","Invalid query!");        
            }
            catch(Exception e)
            {
            }

            return obj.toString();
        
    }

    /**
     * PUT method for updating or creating an instance of AnalyticsResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
