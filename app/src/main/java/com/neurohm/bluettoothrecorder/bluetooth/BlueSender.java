package com.neurohm.bluettoothrecorder.bluetooth;

import com.neurohm.bluettoothrecorder.bus.BusProvider;
import com.neurohm.bluettoothrecorder.bus.StateChangeEvent;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by w.maciejewski on 2014-11-07.
 */
public class BlueSender {
    private final BlueToothInfo blueToothInfo;
    private final OutputStream mmOutStream;
    public BlueSender(BlueToothInfo blueToothInfo){

        this.blueToothInfo = blueToothInfo;
        OutputStream tmpOut = null;
        try {

            tmpOut =  blueToothInfo.getSocket().getOutputStream();
        } catch (IOException e) {

        }


        mmOutStream = tmpOut;

    }

    public void write(byte[] buffer) {
        try {
            mmOutStream.write(buffer);

        } catch (IOException e) {
            connectionFailed();
        }

    }


    public void write(int out) {
        try {
            mmOutStream.write(out);


        } catch (IOException e) {
            connectionFailed();

        }
    }
    private void connectionFailed() {
        BusProvider.getInstance().post(new StateChangeEvent(blueToothInfo.getPosition(),BlueToothInfo.STATE_CONNECTION_FAILED));

    }
}
