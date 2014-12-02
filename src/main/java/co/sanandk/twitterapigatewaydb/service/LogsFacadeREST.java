/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sanandk.twitterapigatewaydb.service;
import co.sanandk.twitterapigatewaydb.Config;
import co.sanandk.twitterapigatewaydb.Logs;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 *
 * @author SAnanda
 */
@Stateless
@Path("co.sanandk.twitterapigatewaydb.logs")
public class LogsFacadeREST extends AbstractFacade<Logs> {
  
    private EntityManager em;

    public LogsFacadeREST() {
        super(Logs.class);
    }
    
    public void setEM(EntityManager em){
        this.em=em;
    }

    //@GET
    //@Path("checklimit")
    public String checklimit(String set, String ip) { 
        long limit=Long.parseLong(set)*60000;
        Long tm=System.currentTimeMillis()-limit;
        System.out.println("t="+tm);
        Integer ans = em.createQuery("SELECT l.id FROM Logs l WHERE l.ip = '" + ip + "' AND l.timestamp>"+tm).getResultList().size();
        if(ans!=null)
            return ans.toString();        
        else
            return "0";
    }

    
     //@GET
    //@Path("create")
    public void create(Logs entity) {
        
        String id=super.max();
        entity.setId(Long.parseLong(id)+1);
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Long id, Logs entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Logs find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Logs> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Logs> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
