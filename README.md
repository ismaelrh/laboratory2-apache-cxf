# Laboratory2 Apache CXF
Yandex Translator SOAP Big Web-Service using Apache CXF and Spring, having the same funtionality that [Laboratory 2](https://github.com/UNIZAR-30246-WebEngineering/big-ws).
Also, it uses the following WS-* specifications:
* WS-Discovery

It includes a CXF Server and a Client to test that it's working and that it implements WS-Discovery specification.

##How to run the Server (translator-cxf-ws-server)

Enter to the directory and run the server with ```gradle run```. Then, open the page at [http://localhost:8080/api/](http://localhost:8080/services). There, you can view all the implemented SOAP services.

You can view the Service WSDL at [http://localhost:8080/api/translator?wsdl](http://localhost:8080/services/translator?wsdl). 

##How to test WS-Discovery (translator-cxf-ws-client)
NOTE: To test it, the server has to be running on the a machine on the same LAN.

In order to test WS-Discovery implementation, you have to run the test from the client project.
To do so, enter to the directory and run ```gradle check```. A test will execute. This test search in the local network for Translator services. Then, it uses the found service to make a translation of an example phrase. If the test passes, we can ensure that the server is working and that WS-Discovery is working too.
Before all this, the client downloads the .wsdl file from the server and generates the proper Java source code from it.

##How to run the Client (translator-cxf-ws-client)
NOTE: To test it, the server has to be running on the same machine.

Also, the client can be run in order to do a basic SOAP request to the endpoint. It is not a test.
Enter to the directory and execute ```gradle run```. This creates a client, connects it to the Endpoint and translates an example phrase.


##About WS-Discovery

###What is it?
According to [Apache CXF docs](http://cxf.apache.org/docs/ws-discovery.html),
WS-Discovery is a protocol to enable dynamic discovery of services available on the local network.
By default, WS-Discovery uses a UDP based multicast transport to announce new services and probe 
for existing services. However, it also supports a managed mode where a discovery proxy is used
to reduce the amount of multicast traffic required.

###How to Enable WS-Discovery
According to [Apache CXF docs](http://cxf.apache.org/docs/ws-discovery.html), to enable CXF to send "Hello" announcements when services and endpoint are started, 
the cxf-services-ws-discovery-service and cxf-services-ws-discovery-api jars need to be available on the classpath. 
The cxf-services-ws-discovery-service jar will register a ServerLifecyleListener that will automatically publish 
the "Hello" messages. It will also respond to any Probe requests that match the services it has published.


