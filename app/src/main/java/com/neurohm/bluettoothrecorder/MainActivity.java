package com.neurohm.bluettoothrecorder;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.neurohm.bluettoothrecorder.adapters.BlueToothArrayAdapter;
import com.neurohm.bluettoothrecorder.bluetooth.BlueToothObject;
import com.neurohm.bluettoothrecorder.bus.BusProvider;
import com.neurohm.bluettoothrecorder.bus.StateChangeEvent;
import com.squareup.otto.Subscribe;

import java.util.List;


public class MainActivity extends Activity {
    private static final int REQUEST_ENABLE_BT = 2;

    private  final int START_RECORDING = 1;
    private  final int STOP_RECORDING =3;

    private boolean recording=false;
    private ListView listView;
    private BlueToothArrayAdapter adapter;
    private BlueToothListHolder blueToothListHolder=BlueToothListHolder.getInstance(BluetoothAdapter.getDefaultAdapter());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startBTIntentEnable(BluetoothAdapter.getDefaultAdapter());
        initGUI();
    }

    private void initGUI() {

        listView=(ListView)findViewById(R.id.listView);
        List<BlueToothObject> list_blueTooth=blueToothListHolder.getList();
        adapter=new BlueToothArrayAdapter(getApplicationContext(),R.layout.list_single_item,list_blueTooth);
        BusProvider.getInstance().register(this);
        listView.setAdapter(adapter);

        }

    private void  startBTIntentEnable(BluetoothAdapter mBluetoothAdapter){

        if(!mBluetoothAdapter.isEnabled()){
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn,REQUEST_ENABLE_BT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_CANCELED){
            finish();
        }
    }


    @Override
    protected void onDestroy() {
        blueToothListHolder.setList(adapter.getObjectList());
        BusProvider.getInstance().unregister(this);
        super.onDestroy();
    }


    public void disconnect(View view) {
    }

    public void sendMarker(View view) {
    }

    public void startRecording(View view) {
        if(recording){

            Button tmp=(Button)findViewById(R.id.nagrywaj_but);
            Drawable draw=getResources().getDrawable(R.drawable.recorddisabled);
            tmp.setCompoundDrawablesWithIntrinsicBounds(draw,null,null,null);
            SenderThreads senderThreads=new SenderThreads(adapter.getObjectList(),new int[]{STOP_RECORDING});
            senderThreads.run();
            recording=false;

        }else{
            Button tmp=(Button)findViewById(R.id.nagrywaj_but);
            Drawable draw=getResources().getDrawable(R.drawable.recordnormal);
            tmp.setCompoundDrawablesWithIntrinsicBounds(draw,null,null,null);
            SenderThreads senderThreads=new SenderThreads(adapter.getObjectList(),new int[]{START_RECORDING,100});
            senderThreads.run();
            recording=true;
        }


    }

    public void connect(View view) {
      for(BlueToothObject blueToothObject:adapter.getObjectList()){
         blueToothObject.establishConnection();
      }
    }

    @Subscribe
    public void answerAvailable(StateChangeEvent event) {
        int position=event.getPosition();
        int state=event.getState();
        adapter.getObjectList().get(position).setState(state);
        NotifyThread notifyThread=new NotifyThread();
        notifyThread.run();

    }

    private class NotifyThread extends Thread {
        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }
}
