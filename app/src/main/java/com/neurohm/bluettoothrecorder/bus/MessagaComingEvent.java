package com.neurohm.bluettoothrecorder.bus;

/**
 * Created by w.maciejewski on 2014-11-07.
 */
public class MessagaComingEvent {

    private int message;
    private int position;


    public MessagaComingEvent(int state, int position) {
        this.message=state;
        this.position=position;

    }

    public int getState() {
        return message;
    }

    public int getPosition() {
        return position;
    }
}
