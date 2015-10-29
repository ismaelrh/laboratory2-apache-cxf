package translator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import translator.domain.Language;
import translator.domain.LanguageSourceTarget;
import translator.domain.TranslatedText;
import translator.domain.Translator;
import translator.exception.TranslatorException;

import java.util.concurrent.Future;

import translator.infrastructure.*;

@Service
public class TranslatorServiceImpl implements TranslatorService {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());
    

    
    @Override
    //Returns a TranslatedText
	public TranslatedText translate(String langFrom, String langTo, String text) {

         Translator translator = new YandexTranslator();
        LanguageSourceTarget languageSourceTarget = new LanguageSourceTarget(Language.fromString(langFrom), Language.fromString(langTo));
        if (languageSourceTarget.sourceAndTargeAreEquals()) {
            throw new TranslatorException("The languages from and to must be differents.");
        }
        
        Future<String> translatorResult = translator.translate(languageSourceTarget, text);
        TranslatedText response = new TranslatedText();
        response.setFrom(languageSourceTarget.getSourceAsStr());
        response.setTo(languageSourceTarget.getTargetAsStr());
        
        response.setTranslation(getTranslation(translatorResult));
        return response;
    }

    
    private String getTranslation(Future<String> futureResult) {
        try {
            return futureResult.get();
        } catch (Throwable e) {
            LOG.error("Problems getting the translation", e);
            return "Error:" + e.getMessage();
        }
    }
}
