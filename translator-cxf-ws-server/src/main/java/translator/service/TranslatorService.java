package translator.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

import translator.domain.TranslatedText;

@WebService
public interface TranslatorService {

	@WebMethod
	TranslatedText translate(String langFrom, String langTo, String text);
}
