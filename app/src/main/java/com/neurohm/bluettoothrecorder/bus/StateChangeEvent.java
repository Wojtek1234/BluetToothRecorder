package com.neurohm.bluettoothrecorder.bus;

/**
 * Created by w.maciejewski on 2014-11-06.
 */
public class StateChangeEvent {


    private int state;
    private int position;


    public StateChangeEvent(int position,int state) {
       this.state=state;
        this.position=position;

    }

    public int getState() {
        return state;
    }

    public int getPosition() {
        return position;
    }

}
