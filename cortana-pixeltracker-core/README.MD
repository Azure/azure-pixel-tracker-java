#Cortana Intelligence Marketing
# Pixel Tracker Java Libraries

## Pixel API
### Pixel.java
An interface and an abstract class are provided to base a Pixel API upon. The interface, **Pixel.java** first contains the request mapping for the path **/pixel**, using the Spring **@RequestMapping** annotation. It next sets the produced media type of the API to GIF. Lastly, it specifically requires GET requests, and will aceppt all headers. While these can be overridden, building future APIs upon this interface will help maintain backward comptabiltiy. The **@RequestParam** annotation is used to pass the query string from the request to the java class. 

```java
package com.microsoft.azure.server.pixeltracker.api;
import ...;

public interface Pixel {
    @RequestMapping(value = "/pixel",
            produces = MediaType.IMAGE_GIF_VALUE,
            method = RequestMethod.GET, headers = "Accept=*/*")
    byte[] pixel(@RequestParam Map<String, String> queryParameters);
}```

### AbstractPixel.java
The abstract implementation, **AbstractPixel.java**, adds some basic functionality to the class. 

```java
package com.microsoft.azure.server.pixeltracker.api;
import ...;

public class AbstractPixel implements Pixel {
    private static Logger logger = LogManager.getLogger();
    private final Handler handlers;

    public AbstractPixel(Handler handlers) throws Exception {...}

    @Override
    public byte[] pixel(Map<String, String> queryParameters) {...}
}
```

First, it creates the base constructor, which performs a fast fail check on the handlers input.

```java
    public AbstractPixel(Handler handlers) throws Exception {
        if (handlers == null) throw new Exception("Handler not expected to be null. Check your Spring Config!");
        this.handlers = handlers;
    }
```    
 Second, it declares the functionality of the pixel operation that was stubbed in the interface. This establishings a default logic for the API that could be overridden if desired. Log4j2 is used to record any unexpected errors. 

```java
    @Override
    public byte[] pixel(Map<String, String> queryParameters) {
        try {
            handlers.handle(new RequestImpl(queryParameters));
        } catch (Exception e) {
            logger.error(e);
        }
        return new byte[]{0};
    }
```

##Requst Model
**Request.java**
A common object needs to be based from handler to handler that will contain the change of states. The **Request.java** interface provides the framework for this model. 
```java
package com.microsoft.azure.server.pixeltracker.api.model;
import ...;

public interface Request {
    JSONObject getJson();
    boolean isSuccess();
    Request setSuccess(boolean success);
    String getQueryString();
    void put(String key, String value);
    byte[] getBytes(String encoding) throws UnsupportedEncodingException;
}
```

The **RequestImpl.java** abstract class provides the default implmentation for the methods declared in **Request** interface. 
```java
package com.microsoft.azure.server.pixeltracker.api.model;
import ...;

public class RequestImpl implements Request {
    private static Logger logger = LogManager.getLogger();
    private final JSONObject json = new JSONObject();
    private boolean success = true;
    private final Map<String, String> queryParameters;

    public RequestImpl(Map<String, String> queryParameters) {...}

    @Override
    public boolean isSuccess() {...}

    @Override
    public Request setSuccess(boolean success) {...}

    @Override
    public String getQueryString() {...}

    @Override
    public void put(String key, String value) {...}

    @Override
    public byte[] getBytes(String encoding) throws UnsupportedEncodingException {...}

    @Override
    public JSONObject getJson() {...}
}
```


##Handlers
###Handler.java
**Handler.java** is an interface for chaining together transformations. Each handler will implement a *handle*, *strategy* and *setNextOperation* method.

```java
package com.microsoft.azure.server.pixeltracker.api.handlers;
import ...;

public interface Handler {
    Future<Boolean> handle(Request request) throws Exception;
    void strategy(Request request) throws Exception;
    Handler setNextOperation(Handler childHandler);    
}
```

###AbstractHandlerjava
**AbstractHandler.java** is an abstract class that makes implemnting the interface simpler. 

```java
package com.microsoft.azure.server.pixeltracker.api.handlers.impl;
import ...;

public abstract class AbstractHandler implements Handler {
    private Handler childHandler = null;

    @Override
    public Handler setNextOperation(Handler childHandler) {...}

    @Override
    public Future<Boolean> handle(Request request) throws Exception {...}

    private Handler setChildHandler(Handler childHandler) {...}

    public Future<Boolean> chainHandler(Request request) throws Exception {...}
}
```

*handle* will be called on each API request. The abstract class implments a common flow of *strategy*, then *chainHandler* that should be followed on each request. *Strategy* is not implemented in the abstract class, and must be written for each implementation. 

```java
    @Override
    public Future<Boolean> handle(Request request) throws Exception {
        strategy(request);
        return chainHandler(request);
    }
```
*chainHandler* determines the order in which transformations are performed. The default implementation is to synchronously call the next handler. This default behavior can be overridden if desired.

```java
    public Future<Boolean> chainHandler(Request request) throws Exception {
        return childHandler == null
                ? new AsyncResult<>(request.isSuccess())
                : this.childHandler.handle(request);
    }
```

###JsonQueryStringHandler.java
The **JsonQueryStringHandler.java** handler processes the query string from the web request and converts it into a JSON. 

```java
package com.microsoft.azure.server.pixeltracker.api.handlers.impl;
import ...;

public class JsonQueryStringHandler extends AbstractHandler {
    private static Logger logger = LogManager.getLogger();
    private final String PARAMDELIM = "&";

    @Override
    public void strategy(Request request) throws Exception {...}

    private boolean isNotEmpty(String queryString) {...}

    private Stream<String> getParamsFrom(String queryString) {...}

    private void putIntoOutput(String param, Request request) {...}
}
```

The *strategy* first retrieves the query string from the request object. It then checks to see if the there were entries in the query string. Next, it places each parameter into the output json, stored in the request object. 

```java
   @Override
    public void strategy(Request request) throws Exception {
        logger.trace("Json Query String Strategy");
        String queryString = request.getQueryString();
        if (isNotEmpty(queryString))
            getParamsFrom(queryString)
                    .forEach(param -> putIntoOutput(param, request));
        else request.setSuccess(false);
    }
```

###EventHubSendHandler.java

The **EventHubSendHandler.java** takes the JSON stored in request, and sends it to Event Hub for storage and processing. Spring is used to wire the Event Hub configurations. The configurations will be declared as beans in the main class of our service.

```java
package com.microsoft.azure.server.pixeltracker.api.handlers.impl;
import ...;

public class EventHubSendHandler extends AbstractHandler {
    private static Logger logger = LogManager.getLogger();

    @Autowired
    private EventHubAutoConfiguration ehClient;
    private final String ENCODING = "UTF-8";

    public EventHubSendHandler(EventHubAutoConfiguration ehClient) {...}

    @Override
    public void strategy(Request request) throws Exception {...}

    private CompletableFuture<Void> sendWithEventHubClient(Request request) throws UnsupportedEncodingException {...}

    private EventData eventDataFrom(Request request) throws UnsupportedEncodingException {...}
}
```

The *constructor* takes an EventHubAutoConfiguration object as an input. This is used to access a singleton event hub client. 

```java
    public EventHubSendHandler(EventHubAutoConfiguration ehClient) {
        assert ehClient != null;
        this.ehClient = ehClient;
    }
```

The *strategy* checks to see that the previous handlers were succesful, and then sends the JSON to event hub. 
```java
    @Override
    public void strategy(Request request) throws Exception {
        logger.trace("Event hub Send Strategy");
        if (request.isSuccess()) sendWithEventHubClient(request);
    }
```
##Event Hub Configuration
###EventHubTemplate.java

The **EventHubTemplate.java** class is used to help wire the configurations for Event Hub using Spring. It provides getters that are used by other classes to retrieve the stored credentials. 
```java
package com.microsoft.azure.eventhubs.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
        prefix = "azure.eventhub"
)
public class EventHubTemplate {

    private String serviceBusNamespaceName;
    private String eventHubName;
    private String sharedAccessSignatureKeyName;
    private String sharedAccessSignatureKey;

    public EventHubTemplate(String eventHubName,
                            String serviceBusNamespaceName,
                            String sharedAccessSignatureKeyName,
                            String sharedAccessSignatureKey) throws Exception {...}

    ...
}
```

The *constructor* provides a fast fail in case the parameters for event hub have been configured incorrectly. 

```java
    public EventHubTemplate(String eventHubName,
                            String serviceBusNamespaceName,
                            String sharedAccessSignatureKeyName,
                            String sharedAccessSignatureKey) throws Exception {
        assert !(eventHubName == null || eventHubName.isEmpty()
                || serviceBusNamespaceName == null || serviceBusNamespaceName.isEmpty()
                || sharedAccessSignatureKeyName == null || sharedAccessSignatureKeyName.isEmpty()
                || sharedAccessSignatureKey == null || sharedAccessSignatureKey.isEmpty())
                : "Event Hub Parameters not properly set. Check Configuration";

        this.eventHubName = eventHubName;
        this.serviceBusNamespaceName = serviceBusNamespaceName;
        this.sharedAccessSignatureKeyName = sharedAccessSignatureKeyName;
        this.sharedAccessSignatureKey = sharedAccessSignatureKey;
    }
```

###EventHubAutoConfiguration.java

```java
package com.microsoft.azure.eventhubs.spring;

import ...;

@Configuration
@ConditionalOnClass({com.microsoft.azure.eventhubs.EventHubClient.class})
@Conditional({EventHubAutoConfiguration.EventHubPropertyCondition.class})
@EnableConfigurationProperties({EventHubTemplate.class})
public class EventHubAutoConfiguration {
    private static Logger logger = LogManager.getLogger();
    private final EventHubTemplate eventHubTemplate;
    private EventHubClient ehClient;

    public EventHubAutoConfiguration(EventHubTemplate eventHubTemplate) {...}

    @Bean
    @ConditionalOnMissingBean({EventHubClient.class})
    public EventHubClient eventHubClient() {...}

    static class EventHubPropertyCondition extends AnyNestedCondition {...}
}
```

The *constructor* takes an EventHubTemplate and should be wired using Spring. 

```java
    public EventHubAutoConfiguration(EventHubTemplate eventHubTemplate) {
        this.eventHubTemplate = eventHubTemplate;
    }
```

The *eventHubClient* method acts as a getter method. It will first check to see if an Event Hub Client has already been intialized. If not it will create one. Then it will provide thread safe access to the event hub client.

```java
    public EventHubClient eventHubClient() {
        if (ehClient == null) {
            ConnectionStringBuilder connStr = new ConnectionStringBuilder(
                    eventHubTemplate.getServiceBusNamespaceName(),
                    eventHubTemplate.getEventHubName(),
                    eventHubTemplate.getSharedAccessSignatureKeyName(),
                    eventHubTemplate.getSharedAccessSignatureKey());
            try {
                ehClient = EventHubClient.createFromConnectionStringSync(connStr.toString());
            } catch (ServiceBusException | IOException e) {
                logger.error(e);
            }
        }
        return ehClient;
    }
```