package translator.web.ws;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import translator.Application;
import translator.ws.TranslatorEndpoint;
import translator.ws.schema.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes={Application.class})
@WebIntegrationTest(randomPort = true)
public class TranslatorEnpointTest {

	@Autowired
	TranslatorEndpoint transl;
	

	@Test
	/** Tests the TranslatorEndpointImpl class */
	public void testSendAndReceive() {

		TranslateRequest request = new TranslateRequest();
		request.setLangFrom("en");
		request.setLangTo("es");
		request.setText("The ones who always win know nothing about life");

		TranslateResponse response = transl.translator(request);

		assertNotNull(response);
		assertThat(response, instanceOf(TranslateResponse.class));
		assertThat(response.getTranslation(), is("Los que siempre ganan no saben nada acerca de la vida"));
	}	
}
