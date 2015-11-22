package in.harkema.devices_android.storage;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.File;

/**
 * Created by tomas on 22-11-15.
 */

public class DatabaseConfigUtil extends OrmLiteConfigUtil {
    private static final Class<?>[] classes = new Class[] {
            Device.class,
    };
    public static void main(String[] args) throws Exception {
        writeConfigFile(new File("app/src/main/res/raw/ormlite_config.txt"), classes);
    }
}