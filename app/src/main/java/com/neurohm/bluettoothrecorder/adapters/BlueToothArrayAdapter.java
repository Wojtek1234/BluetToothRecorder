package com.neurohm.bluettoothrecorder.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.neurohm.bluettoothrecorder.R;
import com.neurohm.bluettoothrecorder.bluetooth.BlueToothObject;

import java.util.List;

/**
 * Created by w.maciejewski on 2014-11-06.
 */
public class BlueToothArrayAdapter  extends ArrayAdapter<BlueToothObject>{

    private final LayoutInflater aboutInflater;



    private List<BlueToothObject> objectList;
    private Drawable[] list_drawable;


    public BlueToothArrayAdapter(Context context, int resource, List<BlueToothObject> objects) {
        super(context, resource, objects);
        objectList=objects;
        aboutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setUpDrawable(context);



    }

    private void setUpDrawable(Context context){
        list_drawable=new Drawable[4];
        list_drawable[0]=context.getResources().getDrawable(R.drawable.neutral);
        list_drawable[1]=context.getResources().getDrawable(R.drawable.good);
        list_drawable[2]=context.getResources().getDrawable(R.drawable.bad);
        list_drawable[3]=context.getResources().getDrawable(R.drawable.middle);
    }




    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view=convertView;
        ViewHolder viewHolder;
        if(convertView==null){
            view= aboutInflater.inflate(R.layout.list_single_item,null);
            viewHolder=new ViewHolder();
            viewHolder.textView=(TextView)view.findViewById(R.id.device_name_text);
            viewHolder.checkBox=(CheckBox)view.findViewById(R.id.check_box);
            viewHolder.imageView=(ImageView)view.findViewById(R.id.device_status_icon);
            view.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        final BlueToothObject blueToothObject= objectList.get(position);
        TextView tv = viewHolder.textView;
        tv.setText(blueToothObject.getDeviceName());

        CheckBox checkBox=(CheckBox)viewHolder.checkBox;
        checkBox.setChecked(blueToothObject.isChecked());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                blueToothObject.setChecked(isChecked);
            }
        });

        ImageView iv= (ImageView)viewHolder.imageView;
        iv.setImageDrawable(list_drawable[blueToothObject.getState()]);

        return view;
    }

    class ViewHolder{
        TextView textView;
        ImageView imageView;
        CheckBox checkBox;
    }

    public List<BlueToothObject> getObjectList() {
        return objectList;
    }


}
