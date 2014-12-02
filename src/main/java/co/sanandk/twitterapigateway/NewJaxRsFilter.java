/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sanandk.twitterapigateway;

import co.sanandk.twitterapigatewaydb.Logs;
import co.sanandk.twitterapigatewaydb.service.BlacklistipsFacadeREST;
import co.sanandk.twitterapigatewaydb.service.ConfigFacadeREST;
import co.sanandk.twitterapigatewaydb.service.LogsFacadeREST;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import twitter4j.JSONArray;
import twitter4j.JSONObject;

/**
 *
 * @author SAnanda
 */
@Provider
public class NewJaxRsFilter implements ContainerRequestFilter,ContainerResponseFilter {
    @Context
    private ResourceInfo resourceInfo;
    
    @Context
    HttpServletRequest req;
 
    @Override
    public void filter(ContainerRequestContext crc) {
      Method theMethod = resourceInfo.getResourceMethod();
      Class c=theMethod.getDeclaringClass();
    System.out.println("TCmethod="+c.getSimpleName());
    }
    
     @Override
    public void filter(ContainerRequestContext requestContext,
            ContainerResponseContext responseContext) throws IOException {
        
        JSONObject obj=new JSONObject();
        try
        {
        obj.put("success",0);
        obj.put("error","API Limit Exceeded!");
        }
        catch(Exception e)
        {
        }
        
        JSONObject obj2=new JSONObject();
        try
        {
        obj.put("success",0);
        obj.put("error","IP Blacklisted!");
        }
        catch(Exception e)
        {
        }
      
        
        Method theMethod = resourceInfo.getResourceMethod();
        Class c=null;
        if(theMethod!=null)
        {
            c=theMethod.getDeclaringClass();
        if(responseContext.getEntity()!=null && !c.getPackage().getName().equals("co.sanandk.twitterapigatewaydb.service")){
                 EntityManagerFactory emf = Persistence.createEntityManagerFactory("dbService");
                EntityManager em = emf.createEntityManager();
                em.getTransaction().begin();
                BlacklistipsFacadeREST bf=new BlacklistipsFacadeREST();
                bf.setEM(em);
                if(bf.findByIP(req.getRemoteAddr())!=0)
                     responseContext.setEntity(obj.toString());

                LogsFacadeREST cf=new LogsFacadeREST();
                cf.setEM(em);
                
                Logs entity=new Logs();
                ConfigFacadeREST cg=new ConfigFacadeREST();
                cg.setEM(em);
                String limit_time=cg.findByKey("limit_time");
                String limit_req=cg.findByKey("limit_req");
                String no_req=cf.checklimit(limit_time, req.getRemoteAddr());
                System.out.println("req="+no_req);
                System.out.println("lreq="+limit_req);
                if(Integer.parseInt(no_req)>=Integer.parseInt(limit_req))
                {
                     responseContext.setEntity(obj.toString());
                }
                entity.setEndpointname(c.getSimpleName().replace("Resource", ""));
                entity.setIp(req.getRemoteAddr());
                entity.setTimestamp(System.currentTimeMillis()+"");
                String out=responseContext.getEntity().toString();
                String sp[]=out.split("\"error\":\""),spl[]=null;
                if(sp.length>1)
                {
                    spl=sp[1].split("\"");
                    entity.setSuccess(0);
                    entity.setError(spl[0]);
                }
                else
                {
                    entity.setSuccess(1);
                    entity.setError("");
                    
                }
                cf.create(entity);
                em.getTransaction().commit();
                em.close();
                emf.close();
        }
        }
           //System.out.println(",out="+c.getPackage().getName()+"="+c.getSimpleName()+","+responseContext.getEntity().toString());
           }
    
    
}
