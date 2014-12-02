/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sanandk.twitterapigateway;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.BasicAuthorization;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author SAnanda
 */
public  class Config {
    private static RequestToken requestToken;
    public static  Twitter twitter;
    public static void init(){
      ConfigurationBuilder cb = new ConfigurationBuilder();
           cb.setDebugEnabled(true)
      .setOAuthConsumerKey("dtbXX6HWgngJ52tZd4nJ5eohY")
      .setOAuthConsumerSecret("xU8TGKvGoYT2D4S6mlINgYMzKZcXNg2gs3q82N9kG6jB6JqT9s");
      
        //the following is set without accesstoken- desktop client
          TwitterFactory tf = new TwitterFactory(cb.build());
          twitter=
            tf.getInstance();
             
          
    }
    public static RequestToken generateAuthorizationToken()
    {
        try {
                // get request token.
                // this will throw IllegalStateException if access token is already available
                // this is oob, desktop client version
                requestToken = twitter.getOAuthRequestToken(); 
            }   
            catch(TwitterException e)
            {
                
            }
        return requestToken;
    }
    public static RequestToken getAuthorizationToken()
    {
        return requestToken;
            
    }
}
