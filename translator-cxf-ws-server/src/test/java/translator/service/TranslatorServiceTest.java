package translator.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import translator.Application;
import translator.domain.TranslatedText;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes={Application.class})
/** Tests the translation service */
public class TranslatorServiceTest {

    @Autowired
    TranslatorServiceImpl translatorService;

    @Test
    public void translateTest() throws Exception {

        TranslatedText translatedText = translatorService.translate("en", "es", "The ones who always win know nothing about life");
        assertEquals("Los que siempre ganan no saben nada acerca de la vida",translatedText.getTranslation());

    }

}
