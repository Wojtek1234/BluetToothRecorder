package com.neurohm.bluettoothrecorder.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;

/**
 * Created by w.maciejewski on 2014-11-06.
 */
public class BlueToothInfo {
    public static final int STATE_NONE = 0;
    public static final int STATE_DISCONNECTED = 2;
    public static final int STATE_CONNECTING = 3;
    public static final int STATE_CONNECTED = 1;
    public static final int STATE_CONNECTION_FAILED = 2;


    private BluetoothDevice device;
    private BluetoothSocket socket;
    private BluetoothAdapter adapter;
    private int position;

    public BlueToothInfo(){

    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public BluetoothSocket getSocket() {
        return socket;
    }

    public void setSocket(BluetoothSocket socket) {
        this.socket = socket;
    }

    public BluetoothAdapter getAdapter() {

        return adapter;
    }

    public void cancelDiscovery() {
        adapter.cancelDiscovery();
    }



    public void setAdapter(BluetoothAdapter adapter) {
        this.adapter = adapter;
    }

    public BluetoothDevice getDevice() {

        return device;
    }

    public void setDevice(BluetoothDevice device) {
        this.device = device;
    }


    public void closeSocket() throws IOException {
        this.socket.close();
    }

    public void connectSocket() throws IOException {

        if (!this.socket.isConnected()) {
            this.socket.connect();
        }
    }


}
