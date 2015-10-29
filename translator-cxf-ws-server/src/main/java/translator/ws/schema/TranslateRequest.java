package translator.ws.schema;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Translate Request class
 * This request holds the source and destination language, and the text
 * to be translated.
 */
@XmlRootElement
public class TranslateRequest {
    private String langFrom;
    private String langTo;
    private String text;

    public String getLangFrom() {
        return langFrom;
    }

    public void setLangFrom(String langFrom) {
        this.langFrom = langFrom;
    }

    public String getLangTo() {
        return langTo;
    }

    public void setLangTo(String langTo) {
        this.langTo = langTo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
