/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sanandk.twitterapigateway;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author SAnanda
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        
        resources.add(co.sanandk.twitterapigateway.GetAuthorizationURLResource.class);
        resources.add(co.sanandk.twitterapigateway.GetFriendsIDsResource.class);
        resources.add(co.sanandk.twitterapigateway.GetLatestTweetsResource.class);
        resources.add(co.sanandk.twitterapigateway.GetMessagesResource.class);
        resources.add(co.sanandk.twitterapigateway.GetOauthTokenResource.class);
        resources.add(co.sanandk.twitterapigateway.GetTrendsResource.class);
        resources.add(co.sanandk.twitterapigateway.NewJaxRsFilter.class);
        resources.add(co.sanandk.twitterapigateway.PostStatusResource.class);
        resources.add(co.sanandk.twitterapigateway.SearchTweetsResource.class);
        resources.add(co.sanandk.twitterapigateway.SendMessageResource.class);
        resources.add(co.sanandk.twitterapigatewaydb.service.AddBlacklistIPResource.class);
        resources.add(co.sanandk.twitterapigatewaydb.service.AddWhitelistIPResource.class);
        resources.add(co.sanandk.twitterapigatewaydb.service.AnalyticsResource.class);
        resources.add(co.sanandk.twitterapigatewaydb.service.BlacklistipsFacadeREST.class);
        resources.add(co.sanandk.twitterapigatewaydb.service.ConfigFacadeREST.class);
        resources.add(co.sanandk.twitterapigatewaydb.service.GetAllBlacklistIPsResource.class);
        resources.add(co.sanandk.twitterapigatewaydb.service.GetAllConfigResource.class);
        resources.add(co.sanandk.twitterapigatewaydb.service.GetAllLogsResource.class);
        resources.add(co.sanandk.twitterapigatewaydb.service.GetAllWhitelistIPsResource.class);
        resources.add(co.sanandk.twitterapigatewaydb.service.GetKeyResource.class);
        resources.add(co.sanandk.twitterapigatewaydb.service.LogsFacadeREST.class);
        resources.add(co.sanandk.twitterapigatewaydb.service.RemoveBlacklistIPsResource.class);
        resources.add(co.sanandk.twitterapigatewaydb.service.RemoveWhitelistIPsResource.class);
        resources.add(co.sanandk.twitterapigatewaydb.service.SetConfigResource.class);
        resources.add(co.sanandk.twitterapigatewaydb.service.WhitelistipsFacadeREST.class);
    }
    
}
