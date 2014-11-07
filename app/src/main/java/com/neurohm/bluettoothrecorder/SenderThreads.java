package com.neurohm.bluettoothrecorder;

import com.neurohm.bluettoothrecorder.bluetooth.BlueToothObject;

import java.util.List;

/**
 * Created by w.maciejewski on 2014-11-07.
 */
public class SenderThreads implements Runnable {

    private List<BlueToothObject> blueToothObjects;
    private int[] messegas;

    public SenderThreads(List<BlueToothObject> blueToothObjects,int[] massage){
        this.blueToothObjects=blueToothObjects;
        this.messegas=massage;
    }
    @Override
    public void run() {

        for(BlueToothObject blueToothObject:this.blueToothObjects){
            for(int mark:this.messegas){
                (new Thread(new Send(blueToothObject,mark))).start();
            }

        }

    }

    private class Send extends Thread{
        private BlueToothObject blueToothObject;
        private int messega;
        public Send(BlueToothObject blueToothObject,int message){
            this.blueToothObject=blueToothObject;
            this.messega=message;
        }

        @Override
        public void run() {
            blueToothObject.sendInfo(messega);
        }
    }
}
