package com.example.bluetoothdeviceslist;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ListView listView;
    private BluetoothAdapter btAdap;
    private ArrayList<String> mDeviceList = new ArrayList<String>();
    private TextView textElement;
    String countryList[] = {"India", "China", "australia", "Portugle", "America", "NewZealand"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btAdap = BluetoothAdapter.getDefaultAdapter();

        if (btAdap == null) {
            Log.i(TAG, "Bluetooth not supported");
        } else {

            setContentView(R.layout.activity_main);
            listView = (ListView) findViewById(R.id.list_view);
            btAdap.startDiscovery();

            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mReceiver, filter);

            textElement = (TextView) findViewById(R.id.text_view);
            textElement.setText("Created by Omkar");
        }
    }

        @Override
        protected void onDestroy() {
            unregisterReceiver(mReceiver);
            super.onDestroy();
        }

        private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent
                            .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    mDeviceList.add(device.getName() + "\n" + device.getAddress());
                    Log.i("BT", device.getName() + "\n" + device.getAddress());
                    listView.setAdapter(new ArrayAdapter<String>(context,
                            android.R.layout.simple_list_item_1, mDeviceList));
                }
            }
        };

    }