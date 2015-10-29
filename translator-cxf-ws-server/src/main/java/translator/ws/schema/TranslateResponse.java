package translator.ws.schema;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Translate Response class
 * This holds a resultCode, errorMessage if proceeds and the translated text.
 */
@XmlRootElement
public class TranslateResponse {
    private String resultCode ;
    private String errorMsg;
    private String translation;


    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

  

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TranslateResponse{");
        sb.append("resultCode=").append(resultCode);
        sb.append(", errorMsg='").append(errorMsg).append('\'');
        sb.append(", translation='").append(translation).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
