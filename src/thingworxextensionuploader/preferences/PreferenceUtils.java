package thingworxextensionuploader.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

public class PreferenceUtils {
    private static final String PREF_KEY_SELECTED_PROJECT = "selected_project_name";
    private static final String PREF_KEY_URL = "url";
    private static final String PREF_KEY_APPKEY = "username";
    
    public static void storeSelectedProjectName(String projectName) {
        IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode("com.yourcompany.yourplugin");
        prefs.put(PREF_KEY_SELECTED_PROJECT, projectName);
    }

    public static String getStoredProjectName() {
        IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode("com.yourcompany.yourplugin");
        return prefs.get(PREF_KEY_SELECTED_PROJECT, null);
    }

    public static void storeURL(String url) {
        IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode("com.yourcompany.yourplugin");
        prefs.put(PREF_KEY_URL, url);
    }

    public static String getStoredURL() {
        IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode("com.yourcompany.yourplugin");
        return prefs.get(PREF_KEY_URL, "");
    }

    public static void storeAppKey(String username) {
        IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode("com.yourcompany.yourplugin");
        prefs.put(PREF_KEY_APPKEY, username);
    }

    public static String getStoredAppKey() {
        IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode("com.yourcompany.yourplugin");
        return prefs.get(PREF_KEY_APPKEY, "");
    }
}