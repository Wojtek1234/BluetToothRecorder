package com.neurohm.bluettoothrecorder;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import com.neurohm.bluettoothrecorder.bluetooth.BlueToothObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by w.maciejewski on 2014-11-07.
 */
public class BlueToothListHolder {
    private static BlueToothListHolder ourInstance = null;

    public static BlueToothListHolder getInstance(BluetoothAdapter adapter) {
        if (ourInstance == null) {
            ourInstance = new BlueToothListHolder(adapter);
            return ourInstance;
        } else return ourInstance;

    }


    private List<BlueToothObject> list;


    private BlueToothListHolder(BluetoothAdapter bluetoothAdapter) {
        this.list = makeListOfDevice(bluetoothAdapter);
    }


    public void setList(List<BlueToothObject> list) {
        this.list = list;
    }

    private List<BlueToothObject> makeListOfDevice(BluetoothAdapter mBluetoothAdapter) {
        if (mBluetoothAdapter != null) {

            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

            if (pairedDevices.size() > 0) {

                return getBTObjects(pairedDevices);

            } else return null;

        } else return null;
    }

    private List<BlueToothObject> getBTObjects(Set<BluetoothDevice> pairedDevices) {

        ArrayList<BlueToothObject> ldeviceList = new ArrayList<BlueToothObject>();
        int pom = 0;
        for (BluetoothDevice device : pairedDevices) {
            ldeviceList.add(new BlueToothObject(device, pom));
            pom++;
        }
        return ldeviceList;
    }

    public List<BlueToothObject> getList() {
        return list;
    }

}
