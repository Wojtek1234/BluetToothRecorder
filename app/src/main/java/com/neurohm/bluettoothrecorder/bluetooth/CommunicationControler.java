package com.neurohm.bluettoothrecorder.bluetooth;

import com.neurohm.bluettoothrecorder.bus.BusProvider;
import com.neurohm.bluettoothrecorder.bus.StateChangeEvent;

/**
 * Created by w.maciejewski on 2014-11-06.
 */
public class CommunicationControler  {


    private final BlueToothInfo blueTootInfo;
    private BlueConnect blueConnect;
    private BlueConnected blueConnected;
    private BlueSender senderBlue;

    public CommunicationControler(BlueToothInfo blueToothInfo) {
        this.blueTootInfo=blueToothInfo;
    }

    public synchronized void connect() {

        cancelConnectedBlue();

        this.blueConnect = new BlueConnect(blueTootInfo);
        this.blueConnect.start();
    }

    private void cancelConnectedBlue() {
        if (this.blueConnected != null) {
            this.blueConnected.cancel();
            this.blueConnected = null;}
    }


    public BlueSender getSenderBlue() {
        return senderBlue;
    }

    public synchronized void connected() {
        cancelConnectedBlue();
        this.blueConnected = new BlueConnected(blueTootInfo);
        this.blueConnected.start();
        this.senderBlue =new BlueSender(blueTootInfo);
    }


    public void closeConnection() {
        stop();
        this.senderBlue=null;
        disconnected();
    }

    public synchronized void stop() {

        if (this.blueConnected != null) {blueConnected.cancel(); this.blueConnected = null;}
        if (this.blueConnect != null){
            this.blueConnect.closeSocket();
            this.blueConnect = null;
        }

    }
    private void disconnected() {
        BusProvider.getInstance().post(new StateChangeEvent(blueTootInfo.getPosition(), BlueToothInfo.STATE_DISCONNECTED));
    }
}
