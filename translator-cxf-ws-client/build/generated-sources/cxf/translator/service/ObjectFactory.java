
package translator.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the translator.service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Translate_QNAME = new QName("http://service.translator/", "translate");
    private final static QName _TranslateResponse_QNAME = new QName("http://service.translator/", "translateResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: translator.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Translate }
     * 
     */
    public Translate createTranslate() {
        return new Translate();
    }

    /**
     * Create an instance of {@link TranslateResponse }
     * 
     */
    public TranslateResponse createTranslateResponse() {
        return new TranslateResponse();
    }

    /**
     * Create an instance of {@link TranslatedText }
     * 
     */
    public TranslatedText createTranslatedText() {
        return new TranslatedText();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Translate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.translator/", name = "translate")
    public JAXBElement<Translate> createTranslate(Translate value) {
        return new JAXBElement<Translate>(_Translate_QNAME, Translate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TranslateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.translator/", name = "translateResponse")
    public JAXBElement<TranslateResponse> createTranslateResponse(TranslateResponse value) {
        return new JAXBElement<TranslateResponse>(_TranslateResponse_QNAME, TranslateResponse.class, null, value);
    }

}
