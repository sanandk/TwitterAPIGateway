/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sanandk.twitterapigatewaydb.service;

import co.sanandk.twitterapigatewaydb.Blacklistips;
import co.sanandk.twitterapigatewaydb.Config;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
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
@Path("co.sanandk.data.blacklistips")
public class BlacklistipsFacadeREST extends AbstractFacade<Blacklistips> {
   
    private EntityManager em;

    public BlacklistipsFacadeREST() {
        super(Blacklistips.class);
    }
    public void setEM(EntityManager em){
        this.em=em;
    }

    @GET
    @Path("create")
    public int create(@QueryParam("key") String key, @QueryParam("value") String value) {
        Blacklistips entity=new Blacklistips();
        String id=super.max();
        entity.setId(Integer.parseInt(id)+1);
        entity.setIp(key);
        entity.setTimestamp(value);
        super.create(entity);
        return Integer.parseInt(id);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Blacklistips entity) {
        super.edit(entity);
    }

    @GET
    @Path("delete/{id}")
    @Produces("text/plain")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }
    
    public int findByIP(String IP) {
        List list = em.createQuery("SELECT b.id FROM Blacklistips b WHERE b.ip= '" + IP+"'").getResultList();
        int ans=list.size();
        
        return ans;
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Blacklistips find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Blacklistips> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Blacklistips> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @GET
    @Path("max")
    @Produces("text/plain")
    public String maxREST() {
        return String.valueOf(super.max());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
   
}
