package translator.ws;

import java.io.IOException;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.EndpointReference;
import javax.xml.ws.WebServiceFeature;

import org.apache.cxf.ws.discovery.WSDiscoveryClient;
import translator.service.TranslatedText;
import translator.service.TranslatorService;

public class Client {
    private static final QName SERVICE_TYPE_NAME = new QName("http://service.translator/", "TranslatorService");
 	
	public static void main(String[] args) throws IOException {
		WSDiscoveryClient client = new WSDiscoveryClient();
        List<EndpointReference> ers = client.probe(SERVICE_TYPE_NAME);
        TranslatorService hws = ers.get(0).getPort(TranslatorService.class, (WebServiceFeature[]) null);
		TranslatedText response = hws.translate("en", "es", "The ones who always win know nothing about life");
		System.out.println(response.getTranslation());
		client.close();
	}

}
