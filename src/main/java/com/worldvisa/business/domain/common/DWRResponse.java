package com.worldvisa.business.domain.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.BeanConverter;

@DataTransferObject(converter = BeanConverter.class)
public class DWRResponse<T>
implements Serializable {
    private static final long serialVersionUID = -1061752280514994976L;
    private T                 mainObject;
    private List<WebMessage>  messages;
    private String            nextScreen;

    public DWRResponse() {
        // do nothing here
    }

    public DWRResponse(final T mainObject) {
        super();
        this.mainObject = mainObject;
    }

    public void addMessage(final WebMessage message) {
        if (null == this.messages) {
            this.messages = new ArrayList<WebMessage>();
        }
        this.messages.add(message);
    }

    public T getMainObject() {
        return this.mainObject;
    }

    public List<WebMessage> getMessages() {
        return this.messages;
    }

    public String getNextScreen() {
        return this.nextScreen;
    }

    public void setMainObject(final T mainObject) {
        this.mainObject = mainObject;
    }

    public void setMessages(final List<WebMessage> messages) {
        this.messages = messages;
    }

    public void setNextScreen(final String nextScreen) {
        this.nextScreen = nextScreen;
    }
}
