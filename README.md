
# TwitterAPIGateway

TwitterAPIGateway is a simple API Management Gateway for RESTful APIs like Twitter. There are two different sets of RESTful services handled in this gateway. One is to proxy the Twitter API and the other is to manage and analyze the API calls.

##Features
  - Authentication-based services not only for proxied services (Twitter) but also for management gateway services. The authentication is based on the key whose lifetime is limited to a few hours after which it can be obtained with the username and password.
  - Logs of all the calls made to the proxied services (Twitter) are maintained and can be viewed
  - Analysis of the calls can be made such as number of calls for an endpoint, endpoint with maximum number of errors, number of API Limit errors occurred etc.
  - Whitelist/Backlist IPs in the management gateway for the proxied services.
  - Limit the number of calls per minute, for example 1000 API calls per 5 minutes


## Version
1.0.2


## Tech

The following technical dependencies are present in the current system.

* Twitter 4J - A Java Library for Twitter APIs
* Java Derby DB - Database to store the configuration, analysis and user data
* 

### Instructions

You need to set up the Derby Database by creating from the file in the folder backup_db.

For example, to create the database from a backup copy in c:\mybackups\sample, the connection URL should be:

```sh
jdbc:derby:sample;createFrom=c:\mybackups\sample
```

Import the project into Netbeans IDE and deploy the project. The location URL will be

```sh
http://localhost:8080/TwitterAPIGateway/webresources/
```

For example, the URL for Analytics query will be 
```sh
http://localhost:8080/TwitterAPIGateway/webresources/Analytics?query=getTopUsed&count=1&AuthKey=97da0f50466475e2e583170b8172bff1
```

### RAML for API Management gateway

---

```sh
#%RAML 0.8
title: APIGateway
baseUri: http://localhost:8080/TwitterAPIGateway/webresources
version: 1
traits:  
- secured:
      usage: Apply this to any method that needs to be secured
      description: Some requests like analysis, configuration etc. require administrative authentication.
      queryParameters: 
        AuthKey:
          description: Admin Access Token obtained using GetKey request
          type: string
          example: 97da0f50466475e2e583170b8172bff1
          required: true
/GetKey:
  get:
    description: Get the administrative AuthKey for analysis, configuration etc.
    queryParameters:
      username: Username of the administrator
      password: Password of the administrator
    responses:
      200:
        body:
          application/json:
            schema:
               { "success": "int",
                  "key": "string",
                }
            example:
              {
              "success":1,
              "key":"97da0f50466475e2e583170b8172bff1"
              }
    

/GetAllConfig:
  is: [ secured ]
  get:
    description: Get all the configurations being set for the API
    responses:
      200:
        body:
          application/json:
            schema:
               { "success": "int",
                  "configData": [
                     { 
                     "id" : "int",
                     "name" : "string",
                     "val": "string" 
                     }
                  ],
                }
            example:
            {
            "configData":
              [
                {"val":"5","name":"limit_req","id":1},
                {"val":"5","name":"limit_time","id":2}
              ],
            "success":1
            }

/SetConfig:
  is: [ secured ]
  get:
    description: Set configuration value for the API
    queryParameters:
      key: name of the setting
      val: value of the setting
    responses:
        200:
          body:
            application/json:
              schema:
               { "success": "int",
                  "error": "string"
                }
              example:
              {
                "success":1
              }

/AddWhitelistIP:
  is: [ secured ]
  get:
    description: Add IP to the whitelist
    queryParameters:
      ip: IP address
    responses:
        200:
          body:
            application/json:
              schema:
               { "success": "int",
                  "error": "string"
                }
              example:
              {
                "success":1
              }

/AddBlacklistIP:
  is: [ secured ]
  get:
    description: Add IP to the blacklist
    queryParameters:
      ip: IP address
    responses:
        200:
          body:
            application/json:
              schema:
               { "success": "int",
                  "error": "string"
                }
              example:
              {
                "success":1
              }

/GetAllWhitelistIPs:
  is: [ secured ]
  get:
    description: Get whitelist IPs
    queryParameters:
      ip: IP address
    responses:
        200:
          body:
            application/json:
              schema:
               { "success": "int",
                  "whitelistipsData": 
                    [
                      {
                        id: "int",
                        ip: "string",
                        timestamp: "string"
                      }
                    ]
                }
              example:
              {
                "success":1,
                "whitelistipsData":
                  [
                    {
                      "ip":"192.168.0.1",
                      "id":1,
                      "timestamp":"1417493869318"
                    }
                  ]
                }
              }

/GetAllBlacklistIPs:
  is: [ secured ]
  get:
    description: Get blacklist IPs
    queryParameters:
      ip: IP address
    responses:
        200:
          body:
            application/json:
              schema:
               { "success": "int",
                  "blacklistipsData": 
                    [
                      {
                        id: "int",
                        ip: "string",
                        timestamp: "string"
                      }
                    ]
                }
              example:
              {
                "success":1,
                "blacklistipsData":
                  [
                    {
                      "ip":"192.168.0.1",
                      "id":1,
                      "timestamp":"1417493869318"
                    }
                  ]
                }
              }

/RemoveWhitelistIPs:
  is: [ secured ]
  get:
    description: Remove IP from the whitelist
    queryParameters:
      id: ID of IP address in the whitelist
    responses:
        200:
          body:
            application/json:
              schema:
               { "success": "int",
                  "error": "string"
                }
              example:
              {
                "success":1
              }

/RemoveBlacklistIPs:
  is: [ secured ]
  get:
    description: Remove IP from the blacklist
    queryParameters:
      id: ID of IP address in the blacklist
    responses:
        200:
          body:
            application/json:
              schema:
               { "success": "int",
                  "error": "string"
                }
              example:
              {
                "success":1
              }

/GetAllLogs:
  is: [ secured ]
  get:
    description: Get all logs of the calls to the API endpoints excluding administrative calls
    responses:
        200:
          body:
            application/json:
              schema:
               { "success": "int",
                  "logsData": 
                    [
                      {
                        "id": "int",
                        "endpointname": "string",
                        "ip": "string",
                        "success": "int",
                        "error": "string",
                        "timestamp": "string"
                      }
                    ]
                }
              example:
              {
                "logsData":
                  [
                    {
                      "success":1,
                      "endpointname":"GetAuthorizationURL",
                      "ip":"0:0:0:0:0:0:0:1",
                      "id":1,
                      "error":"",
                      "timestamp":"1416891394225"
                    }
                  ],
                "success":1
              }

/Analytics:
  is: [ secured ]
  get:
    description: Analysis of the calls to the API endpoints
    queryParameters:
      query: Query to analyse the calls made to the endpoints (Allowed queries: getTopUsed,getMostErrors)
      count: Limit the number of results
    responses:
        200:
          body:
            application/json:
              schema:
               { 
                 "success": "int",
                  "error": "string",
                  "result": 
                    [ "object" ]
                }
              example:
              {
                "success":1
                "result":
                  [
                    ["GetAuthorizationURL",14]
                  ]
              }


```
##Proxied Services (Twitter)
The following are the few API proxied services provided. Below are some examples which are self-explanatory

```
Request:
 http://localhost:8080/TwitterAPIGateway/webresources/getAuthorizationURL
Response:
{"success":1,"TokenSecret":"mWGEMQjLvg9IYkcjxQiwxnR0I3bSwxxx","Token":"vISzbbpH2xxrS0aRWicbEeUuAc6xXxxx","url":"https://api.twitter.com/oauth/authorize?oauth_token=vISzbbpH2xxrS0aRWicbEeUuAc6xXf6X"}

Other Requests:
http://localhost:8080/TwitterAPIGateway/webresources/getLatestTweets?AuthToken=383587206-1JPLJOmvkxcP1fK98JGOCkVtBncODrBPAWTyxxx&AuthSecret=CsBCLkLsqt8mU4ecGKdWITaheUHMTPKt8lPc9HqKpxxx

http://localhost:8080/TwitterAPIGateway/webresources/getFriendsIDs?AuthToken=383587206-1JPLJOmvkxcP1fK98JGOCkVtBncODrBPAWTyxxx&AuthSecret=CsBCLkLsqt8mU4ecGKdWITaheUHMTPKt8lPc9HqKpxxx

http://localhost:8080/TwitterAPIGateway/webresources/sendMessage?AuthToken=383587206-1JPLJOmvkxcP1fK98JGOCkVtBncODrBPAWTyxxx&AuthSecret=CsBCLkLsqt8mU4ecGKdWITaheUHMTPKt8lPc9HqKpxxx&screenName=anandk&statusText=Mulesoft%20Rocks!

http://localhost:8080/TwitterAPIGateway/webresources/getMessages?AuthToken=383587206-1JPLJOmvkxcP1fK98JGOCkVtBncODrBPAWTyxxx&AuthSecret=CsBCLkLsqt8mU4ecGKdWITaheUHMTPKt8lPc9HqKpxxx

http://localhost:8080/TwitterAPIGateway/webresources/getTrends?AuthToken=383587206-1JPLJOmvkxcP1fK98JGOCkVtBncODrBPAWTyxxx&AuthSecret=CsBCLkLsqt8mU4ecGKdWITaheUHMTPKt8lPc9HqKpxxx

http://localhost:8080/TwitterAPIGateway/webresources/postStatus?AuthToken=383587206-1JPLJOmvkxcP1fK98JGOCkVtBncODrBPAWTyxxx&AuthSecret=CsBCLkLsqt8mU4ecGKdWITaheUHMTPKt8lPc9HqKpxxx&statusText=Mulesoft%20Rocks!

http://localhost:8080/TwitterAPIGateway/webresources/postStatus?AuthToken=383587206-1JPLJOmvkxcP1fK98JGOCkVtBncODrBPAWTyxxx&AuthSecret=CsBCLkLsqt8mU4ecGKdWITaheUHMTPKt8lPc9HqKpxxx&queryText=Mulesoft%20Rocks!
```

### This is just the basic implementation of the API Management Gateway

A lot more features like end-to-end testing, validation through RAML upload etc. are not yet implemented due to time constraints. I will keep updating and tweaking whenever I have time.
