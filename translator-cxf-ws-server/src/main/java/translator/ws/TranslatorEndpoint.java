/**
 * Translator Endpoint
 */
package translator.ws;

import javax.jws.WebService;

import translator.exception.TranslatorException;
import translator.ws.schema.*;
import translator.service.*;
import translator.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@WebService
@Component
/** It implements the SOAP Endpoint*/
public class TranslatorEndpoint {

@Autowired
TranslatorService translatorService;

    /** Translator service, that receives a TranslateRequest request with
    a text to be ranslated and returns a TranslateResponse response with the
    translated text*/
    public TranslateResponse translator(TranslateRequest request) {

    	TranslateResponse response = new TranslateResponse();

		try {
			
			TranslatedText translatedText = translatorService.translate(request.getLangFrom(), request.getLangTo(),
					request.getText());
			
			response.setResultCode("ok");
			response.setTranslation(translatedText.getTranslation());
		} catch (TranslatorException e) {
			
			response.setResultCode("error");
			response.setErrorMsg(e.getMessage());
		}
		
		return response;
		
    }

}
// END SNIPPET: service
