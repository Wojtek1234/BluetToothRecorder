package com.neurohm.bluettoothrecorder.bluetooth;

import android.util.Log;

import com.neurohm.bluettoothrecorder.bus.BusProvider;
import com.neurohm.bluettoothrecorder.bus.StateChangeEvent;

import java.io.IOException;

/**
 * Created by w.maciejewski on 2014-11-06.
 */
public class BlueConnect extends Thread {

    private final BlueToothInfo blueToothInfo;

    public BlueConnect(BlueToothInfo blueToothInfo){
        this.blueToothInfo=blueToothInfo;
    }
    @Override
    public void run() {
        this.blueToothInfo.cancelDiscovery();
        connectSocket();
    }

    public void closeSocket(){
        try {

            this.blueToothInfo.closeSocket();

        } catch (IOException closeException) {

            Log.e("CONNECT", "unable to close() socket during connection failure", closeException);
        }
    }

    private void connectSocket(){

        try {

            blueToothInfo.connectSocket();

        } catch (IOException connectException) {

            connectionFailed();
            closeSocket();
            return;

        }

        this.obtainAndSendMessage();
    }


    private void connectionFailed() {
        BusProvider.getInstance().post(new StateChangeEvent(blueToothInfo.getPosition(),BlueToothInfo.STATE_CONNECTION_FAILED));

    }

    private void obtainAndSendMessage(){
        BusProvider.getInstance().post(new StateChangeEvent(blueToothInfo.getPosition(),BlueToothInfo.STATE_CONNECTING));
    }

}
