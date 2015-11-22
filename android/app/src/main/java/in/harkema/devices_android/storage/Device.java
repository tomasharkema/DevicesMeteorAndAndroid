package in.harkema.devices_android.storage;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by tomas on 22-11-15.
 */
@DatabaseTable(tableName = "device")
public class Device {

    @DatabaseField(generatedId = true, columnName = "id")
    public int id;

    @DatabaseField(columnName = "_id")
    public String _id;

    @DatabaseField(columnName = "name")
    public String name;

    @DatabaseField(columnName = "type")
    public int type;

    @DatabaseField(columnName = "status")
    public int status;

    @DatabaseField(columnName = "description")
    public String description;
}
