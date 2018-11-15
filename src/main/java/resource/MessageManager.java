package resource;

import java.util.Locale;
import java.util.ResourceBundle;


public enum  MessageManager {
    INSTANCE;

    private ResourceBundle messageManager;

    private final String RESOURCE_NAME = "resources.messages";

    private MessageManager() {
        messageManager = ResourceBundle.getBundle(RESOURCE_NAME, Locale.getDefault());
    }

    public void changeResource(Locale locale) {
        messageManager = ResourceBundle.getBundle(RESOURCE_NAME, locale);
    }

    public String getProperty(String key) {
        return messageManager.getString(key);
    }
}
