package com.neurohm.bluettoothrecorder.bluetooth;

import com.neurohm.bluettoothrecorder.bus.BusProvider;
import com.neurohm.bluettoothrecorder.bus.MessagaComingEvent;
import com.neurohm.bluettoothrecorder.bus.StateChangeEvent;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by w.maciejewski on 2014-11-06.
 */
public class BlueConnected extends Thread {


    private final BlueToothInfo blueToothInfo;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private boolean doRecive=true, pierwszy=true;

    public BlueConnected(BlueToothInfo blueToothInfo) {
        this.blueToothInfo = blueToothInfo;

        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        try {
            tmpOut = blueToothInfo.getSocket().getOutputStream();
            tmpIn = blueToothInfo.getSocket().getInputStream();
        } catch (IOException e) {
            connectionFailed();
        }
        mmInStream = tmpIn;
        mmOutStream = tmpOut;

    }

    @Override
    public void run() {
        while (doRecive) {
            try {
                readOnebyte();
            } catch (IOException e) {
                connectionFailed();
                break;
            }
        }
    }

    private void readOnebyte() throws IOException {

        if (mmInStream.available() > 0) {
            if (pierwszy) doOnFirst();
            massageRecived(mmInStream.read());
        }
    }


    private void massageRecived(int msg) {
        BusProvider.getInstance().post(new MessagaComingEvent(msg, blueToothInfo.getPosition()));
    }

    public void cancel() {
        doRecive = false;
    }

    private void doOnFirst() {
        connectionEstablish();
        pierwszy = false;
    }


    private void connectionFailed() {
        BusProvider.getInstance().post(new StateChangeEvent(blueToothInfo.getPosition(), BlueToothInfo.STATE_CONNECTION_FAILED));
    }

    private void connectionEstablish() {
        BusProvider.getInstance().post(new StateChangeEvent(blueToothInfo.getPosition(), BlueToothInfo.STATE_CONNECTED));
    }
}
