package in.harkema.devices_android.storage;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;

import java.util.List;

import im.delight.android.ddp.Meteor;
import im.delight.android.ddp.MeteorCallback;

/**
 * Created by tomas on 22-11-15.
 */
public class Storage implements MeteorCallback {
    public final String TAG = this.getClass().getSimpleName();

    public static Storage instance;
    private Context context;
    public DatabaseHelper helper;
    public Gson gson = new Gson();


    public static Storage getInstance(Context context) {
        if (instance == null) {
            instance = new Storage(context);
        }
        instance.context = context;
        return instance;
    }

    public static final String host = "devices.harkema.in";

    public Meteor meteor;
    private Callback callback;

    public Storage(Context context) {
        meteor = new Meteor(context, "ws://" + host + "/websocket");
        meteor.setCallback(this);
        meteor.reconnect();

        helper = new DatabaseHelper(context);
    }

    public interface Callback {
        void update();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void onConnect(boolean b) {
        Log.i(TAG, "CONNECT");
    }

    @Override
    public void onDisconnect(int i, String s) {
        Log.i(TAG, "DISCONNECT");

        Runnable r = new Runnable() {
            @Override
            public void run() {
                meteor.reconnect();
            }
        };

        new Handler().postDelayed(r, 3000);
    }

    @Override
    public void onDataAdded(String s, String _id, String fields) {
        Log.i(TAG, "ADDED " + s + " " + _id + " " + fields);
        try {
            Dao<Device, Integer> teacherDao = helper.getTeacherDao();

            Device device = gson.fromJson(fields, Device.class);
            device._id = _id;

            List<Device> devices = teacherDao.queryForEq("_id", _id);
            if (!devices.isEmpty()) {
                device.id = devices.get(0).id;
            }

            helper.getTeacherDao().createOrUpdate(device);

        } catch (Exception e) {
            Log.e(TAG, "EXC", e);
        }

        if (callback != null) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.update();
                        }
                    });
                }
            };

            new Handler().postDelayed(r, 1000);
        }
    }

    @Override
    public void onDataChanged(String s, String s1, String s2, String s3) {
        Log.i(TAG, "ADDED " + s + " " + s1 + " " + s2 + " " + s3);
    }

    @Override
    public void onDataRemoved(String s, String s1) {
        Log.i(TAG, "ADDED " + s + " " + s1);
    }

    @Override
    public void onException(Exception e) {
        Log.e(TAG, "EXCEPTION", e);
    }
}
