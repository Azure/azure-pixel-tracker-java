#Cortana Intelligence Marketing
# Pixel Tracker Java Service

## Pixel API
An interface and an abstract class are provided to base a Pixel API upon. The interface, **Pixel.java** first contains the request mapping for the path **/pixel**, using the Spring **@RequestMapping** annotation. It next sets the produced media type of the API to GIF. Lastly, it specifically requires GET requests, and will aceppt all headers. While these can be overridden, building future APIs upon this interface will help maintain backward comptabiltiy. The **@RequestParam** annotation is used to pass the query string from the request to the java class. 

### Pixel.java
```java
package com.microsoft.azure.server.pixeltracker.api;

import ...;

public interface Pixel {

    @RequestMapping(value = "/pixel",
            produces = MediaType.IMAGE_GIF_VALUE,
            method = RequestMethod.GET, headers = "Accept=*/*")
    byte[] pixel(@RequestParam Map<String, String> queryParameters);
}```

The abstract implementation, **AbstractPixel.java**, adds some basic functionality to the class. 
package com.microsoft.azure.server.pixeltracker.api;

### AbstractPixel.java
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

