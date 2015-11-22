package in.harkema.devices_android.storage;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import in.harkema.devices_android.R;

public class DeviceDetail extends AppCompatActivity {

    public static final String TAG = DeviceDetail.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final int id = getIntent().getIntExtra("id", 0);
        final Storage storage = Storage.getInstance(DeviceDetail.this);
        try {
            final Device device = storage.helper.getTeacherDao().queryForId(id);
            setTitle(device.name);
            ((TextView)findViewById(R.id.description)).setText(device.description);

            findViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        storage.helper.getTeacherDao().deleteById(id);
                        storage.meteor.remove("devices", device._id);
                        finish();
                    } catch (Exception e) {
                        Log.e(TAG, "Failed to remove", e);
                    }
                }
            });
        }
        catch (Exception e) {
            Log.e(TAG, "EXC", e);
        }
    }

}
