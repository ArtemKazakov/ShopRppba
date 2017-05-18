package by.bsuir.shop.dao.connectionpool;

import java.util.ResourceBundle;


public class DBResourceManager {
    private static final String BUNDLE_NAME = "mydb";

    private static DBResourceManager instance;

    private ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME);

    public static DBResourceManager getInstance(){
        if(instance == null){
            instance = new DBResourceManager();
        }
        return instance;
    }

    public String getValue(String key){
        return bundle.getString(key);
    }
}
