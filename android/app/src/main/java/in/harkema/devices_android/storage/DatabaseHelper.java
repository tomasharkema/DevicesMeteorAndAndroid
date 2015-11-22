package in.harkema.devices_android.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import in.harkema.devices_android.R;

/**
 * Created by tomas on 22-11-15.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "device.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Device, Integer> deviceDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Device.class);
        } catch (Exception e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to create datbases", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
        try {

            TableUtils.dropTable(connectionSource, Device.class, true);
            onCreate(sqliteDatabase, connectionSource);

        } catch (Exception e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to upgrade database from version " + oldVer + " to new "
                    + newVer, e);
        }
    }

    public Dao<Device, Integer> getTeacherDao() throws SQLException {
        if (deviceDao == null) {
            deviceDao = getDao(Device.class);
        }
        return deviceDao;
    }
}
