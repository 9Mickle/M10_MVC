# M10_MVC

Main task
 
Please, complete the following task.

The total mark is 7:  5 points for regular homework and 2 points for the extra mile.

Transform project from Spring Introduction module into a web application, configure dispatcher servlet. (0.5 point)
Implement annotation-based controllers that will delegate to BookingFacade methods. For methods, that accept Entity, just send the list of parameters from the client and assemble the entity in the controller, no need for automatic conversion of request payload to java object. (0.5 point)
For methods, that should return a single entity or entity list result (e.g. getUsersByName), implement simple thymeleaf templates for displaying results. No sophisticated markup required, just the fact that you know how to implement the chain:
ModelAndView à Resolver à ThymeleafTemplate à Html page in the browser. (1 point)

      4. For the following facade method:

List getBookedTickets(User user, int pageSize, int pageNum);

Implement alternative controller, which will be mapped on header value "accept=application/pdf" and return PDF version of booked tickets list. (0.5 point)

      5. Implement batch creation of ticket bookings from XML file. Source file example:

<tickets>  

                <ticket user="..." event="..." category="..." place="..."/>  

                <ticket user="..." event="..." category="..." place="..."/>  

                <ticket user="..." event="..." category="..." place="..."/>  

</tickets>   

Add a method public void preloadTickets() to facade that will load this file from some predefined place (or from a location specified in parameter), unmarshal ticket objects using Spring OXM capabilities and update the storage. The whole batch should be performed in a single transaction, using programmatic transaction management. (1 point)

     6. Implement custom HandlerExceptionResolver, which in case of controller exception just send a simple text response to the client with a brief description of the error. (0.5 point) 

     7. Unit tests, logging, javadocs. (0.5 point)

     8. Implement integration tests for Booking service controllers using the MockMVC framework. (0.5 point)
Extra mile (Optional)
 
 
Please, complete the following task.

2 points

Update your application using spring boot instead of the current implementation.
Modify the method implemented in point 5. Make facade method accept some input stream, MVC controller for this method should handle multipart file upload and pass the file byte stream to the facade method.
