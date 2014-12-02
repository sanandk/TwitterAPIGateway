/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sanandk.twitterapigatewaydb.service;
import co.sanandk.twitterapigatewaydb.Config;
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
@Path("co.sanandk.twitterapigatewaydb.config")
public class ConfigFacadeREST extends AbstractFacade<Config> {
 //   @PersistenceContext(unitName = "dbService")
    private EntityManager em;
    
    public ConfigFacadeREST() {
        super(Config.class);
    }

    public void setEM(EntityManager em)
    {
        this.em=em;
    }
    
    @GET
    @Path("create")
    public int create(@QueryParam("key") String key, @QueryParam("value") String value) {
        Config entity=new Config();
        String id=super.max();
        entity.setId(Integer.parseInt(id)+1);
        entity.setName(key);
        entity.setVal(value);
        super.create(entity);
        return Integer.parseInt(id);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Config entity) {
        super.edit(entity);
    }

    @GET
    @Path("delete/{id}")
    @Produces("text/plain")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Config find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    
    @GET
    @Path("findByKey")
    public String findByKey(@PathParam("key") String key) {
        Object ans = em.createQuery("SELECT c.val FROM Config c WHERE c.name = '" + key+"'").getSingleResult();
        if(ans!=null)
            return ans.toString();        
        else
            return "0";
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Config> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Config> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
