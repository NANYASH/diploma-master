package resource;
import java.util.Locale;
import java.util.ResourceBundle;



public enum ResourceManager {
    


    INSTANCE;

    private ResourceBundle resourceBundle;

    private final String RESOURCE_NAME = "resources.text";

    private ResourceManager() {
        resourceBundle = ResourceBundle.getBundle(RESOURCE_NAME, Locale.getDefault());
    }

    public void changeResource(Locale locale) {
        resourceBundle = ResourceBundle.getBundle(RESOURCE_NAME, locale);
    }

    public String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}