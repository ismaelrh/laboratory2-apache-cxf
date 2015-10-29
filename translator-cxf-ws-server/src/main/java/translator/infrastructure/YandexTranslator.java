package translator.infrastructure;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import translator.exception.TranslatorException;

import java.util.Properties;

@Component("yandexTranslator")
public class YandexTranslator extends TranslatorImpl {

	private ObjectMapper om = new ObjectMapper(); 

	private String API_KEY;
	
    @Override
    protected HttpRequestBase getHttpRequest(String from, String to, String text, String encodedText) {
        Resource resource = new ClassPathResource("/application.properties");
        try {
            Properties props = PropertiesLoaderUtils.loadProperties(resource);
            API_KEY = props.getProperty("yandex.api_key");
        }
        catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
        }

        HttpGet httpGet = new HttpGet("https://translate.yandex.net/api/v1.5/tr.json/translate?key="+API_KEY+"&lang="+from+"-"+to+"&text="+encodedText);
        return httpGet;
    }

    @Override
	protected String getTranslationFrom(String responseAsStr) {
    	try {
			return (String) om.readValue(responseAsStr, YandexResponse.class).text[0];
		} catch (Exception e) {
			throw new TranslatorException("Failled processing "+responseAsStr, e);
		}
    }

}

class YandexResponse {
	public String code;
	public String lang;
	public Object[] text;
}