package translator.ws;

import org.apache.cxf.ws.discovery.WSDiscoveryClient;
import org.apache.cxf.ws.discovery.wsdl.ProbeMatchType;
import org.apache.cxf.ws.discovery.wsdl.ProbeType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import translator.service.TranslatedText;
import translator.service.TranslatorService;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.EndpointReference;
import javax.xml.ws.WebServiceFeature;
/**
 * Created by ismaro3 on 27/10/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Client.class)
public class TranslatorDiscoveryTest {

    //Service to be found
    private static final QName SERVICE_TYPE_NAME = new QName("http://service.translator/", "TranslatorService");

    //Class that allows us to use WS-Discovery
    private WSDiscoveryClient client;

    @Before
    public void before() {
        client = new WSDiscoveryClient();
        client.setDefaultProbeTimeout(1000);
    }


    @Test
    public void testWsDiscovery() {

        
        ProbeType pt = new ProbeType();
        List<ProbeMatchType> pmts = client.probe(pt).getProbeMatch();

        // Verifies that a service exists that offers the Service Type corresponding to TranslatorEndpoint.
        assertTrue(
                pmts.stream().
                        map(ProbeMatchType::getTypes).
                        flatMap(l->l.stream()).
                        anyMatch(i->i.equals(SERVICE_TYPE_NAME)));


        //Note: More than one EndpointReference for the same service type could be received.
        List<EndpointReference> ers = client.probe(SERVICE_TYPE_NAME);


        //Obtain object to comunicate with Translator Endpoint
        //We use the first EndpointReference received.
        TranslatorService hws = ers.get(0).getPort(TranslatorService.class, (WebServiceFeature[]) null);
        TranslatedText response = hws.translate("en",  "es", "The ones who always win know nothing about life");

        assertNotNull(response);
        assertThat(response, instanceOf(TranslatedText.class));
        assertThat(response.getTranslation(), is("Los que siempre ganan no saben nada acerca de la vida"));

    }

    @After
    public void after() throws IOException {
        client.close();
    }

}
