package com.neurohm.bluettoothrecorder.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by w.maciejewski on 2014-11-06.
 */
public class BlueToothObject {
    private BlueToothInfo blueTootInfo;
    private final static UUID MY_UUID = UUID.fromString("04c6093b-0000-1000-8000-00805f9b34fb");

    private BlueSender senderBlue;
    private CommunicationControler communicationControler;
    private boolean checked=true;







    private int state=0;
    public BlueToothObject(BluetoothDevice mmDevice, int pos) {
        blueTootInfo = new BlueToothInfo();
        blueTootInfo.setPosition(pos);
        blueTootInfo.setDevice(mmDevice);
        blueTootInfo.setSocket(assigneSocket(mmDevice));
        blueTootInfo.setAdapter(BluetoothAdapter.getDefaultAdapter());

    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDeviceName(){
        return blueTootInfo.getDevice().getName();
    }


    private BluetoothSocket assigneSocket(BluetoothDevice mmDevice) {
        try {
            return mmDevice.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public synchronized void establishConnection() {
        communicationControler = new CommunicationControler(blueTootInfo);
        communicationControler.connect();
        communicationControler.connected();
        senderBlue = communicationControler.getSenderBlue();
    }


    public synchronized void closeConnection() {
        communicationControler.closeConnection();
    }


    public void sendInfo(int msg) {
        senderBlue.write(msg);
    }


    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }




}
