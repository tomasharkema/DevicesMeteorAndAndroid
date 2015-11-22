package in.harkema.devices_android;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;

import in.harkema.devices_android.storage.Device;
import in.harkema.devices_android.storage.DeviceDetail;
import in.harkema.devices_android.storage.Storage;

public class DevicesActivity extends ListActivity implements Storage.Callback {

    public static final String TAG = ListActivity.class.getClass().getSimpleName();

    public List<Device> devices;
    public List<String> data;
    public ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);

        Storage storage = Storage.getInstance(this);
        storage.setCallback(this);

        refresh();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Device device = devices.get(position);

        if (device == null) { return; }

        Intent intent = new Intent(this, DeviceDetail.class);
        intent.putExtra("id", device.id);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        Storage storage = Storage.getInstance(this);
        try {
            devices = storage.helper.getTeacherDao().queryForAll();

            data = new ArrayList<>();
            for (Device device: devices) {
                data.add(device.name);
            }

            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, data);

            setListAdapter(adapter);

            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.e(TAG, "EXC", e);
        }
    }

    @Override
    public void update() {
        refresh();
    }
}
