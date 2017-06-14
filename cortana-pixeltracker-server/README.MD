#Cortana Intelligence Marketing
# Pixel Tracker Java Service

## Pixel API
An interface and an abstract class are provided to base a Pixel API upon. The interface first contains the request mapping for the path **/pixel**. It next sets the produced media type of the API to GIF. Lastly, it specifically requires GET requests, and will aceppt all headers. While these can be overridden, building future APIs upon this interface will help maintain backward comptabiltiy. 

```
 @RequestMapping(value = "/pixel",

            produces = MediaType.IMAGE_GIF_VALUE,

            method = RequestMethod.GET, headers = "Accept=*/*")

    byte[] pixel(@RequestParam Map<String, String> queryParameters);
```
