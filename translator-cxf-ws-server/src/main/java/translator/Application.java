package translator;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;
import translator.service.TranslatorServiceImpl;

import java.net.NetworkInterface;
import java.util.Enumeration;
import java.net.InetAddress;
import javax.servlet.ServletContext;

@SpringBootApplication
@ImportResource({ "classpath:META-INF/cxf/cxf.xml", "classpath:META-INF/cxf/cxf-servlet.xml" })
public class Application extends SpringBootServletInitializer 
    implements ApplicationListener<EmbeddedServletContainerInitializedEvent>{
        //ApplicationListene<...> is implemented in order to onApplicationEvent being called
        //when the server is up and running.
 
    @Autowired
    private ApplicationContext applicationContext;

    //Obtains the Servlet Context in order to retrieve the context path where it is published.
    @Autowired
    private ServletContext servletContext;

    //This is auto-filled when Endpoint bean is returned.
    @Autowired
    private Endpoint endpoint;
 
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
 
    // Replaces the need for web.xml
    @Bean
    public ServletRegistrationBean servletRegistrationBean(ApplicationContext context) {
        //Registers the Apache CXF Servlet on the /api/ path, so all request that go
        //to /api/* are redirected to this Servlet.
    	ServletRegistrationBean srb = new ServletRegistrationBean(new CXFServlet(), "/api/*");
    	srb.setLoadOnStartup(1);
    	return srb;
    }
 
    // Replaces cxf-servlet.xml
    @Bean
    public Endpoint translator() {
    	Bus bus = (Bus) applicationContext.getBean(Bus.DEFAULT_BUS_ID);
        EndpointImpl endpoint = new EndpointImpl(bus, new TranslatorServiceImpl());
        //NOTE: Endpoint is published once the server is up and running (See onApplicationEvent)
        return endpoint;
    }
     
  
    // Used when deploying to a standalone servlet container, i.e. tomcat
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
 

     @Override
     /*
      It listens on the EmbeddedServletContainerInitializedEvent. It gets called once the server is up and running,
      so we can obtain Port, Context and IP Addresses (The IPv4 of the first found interface).
      When it obtains those parameters, then it publishes the endpoint.
     */
    public void onApplicationEvent(final EmbeddedServletContainerInitializedEvent event) {
          

        int port = event.getEmbeddedServletContainer().getPort(); //Obtain port   
        String address = getServerAddress(); //Obtain address
        String contextPath = servletContext.getContextPath(); //Obtain contextPath of the webapplication

        //In case of error
        if(address==null){
           address = "localhost";
           System.err.println("Using localhost for WS-Discovery...");
          }

        /*Used to fix a problem with Apache CXF:
            As default, in its published WS-Discovery messages it only sends relative path (e.g: /translator),
            but we want it to publish the full URI. To do so, we have to write a property that indicates Apache CXF
            to try to publish with the given URI instead of the relative.
        */
        endpoint.getProperties().put("ws-discovery-published-url", "http://" + address + ":" + port + "/api/translator");

        //Publish endpoint
        endpoint.publish("/translator");

        System.out.println("Endpoint published at http://" + address + ":" + port + "/api/translator");

      }

      /*
        Returns the IPv4 address corresponding on the first found interface of the machine.
        If no IPv4 address has been found, it returns null.
      */
      public String getServerAddress(){
            String address = null;
            try{
                  Enumeration<NetworkInterface> allInterfaces = NetworkInterface.getNetworkInterfaces();

                  //Only first interface
                  NetworkInterface firstInterface = allInterfaces.nextElement();

                  //Obtain all addresses for the first interface
                  Enumeration<InetAddress> allAddresses = firstInterface.getInetAddresses();

                  //We search for the first address that is not an IPv6 address.
                  while(address == null && allAddresses.hasMoreElements()){
                    InetAddress currentAddress = allAddresses.nextElement();
                    String currentAddressText = currentAddress.getHostAddress();
                    if(!currentAddressText.contains(":")){
                        //Not an IPv6 address (IPv6 addresses contain colons))
                        address = currentAddressText;

                    }
                  }

              
            }
            catch(Exception ex){
                System.err.println("Error obtaining IP address for WS-Discovery.");
                ex.printStackTrace();
            }
            return address;

      }
}
