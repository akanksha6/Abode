package com.example.srushti.abode.AccountActivity;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends ArrayAdapter<users>{
   // private Activity context;
    private List<users> landlordlist;

    public MyAdapter(Context context, List<users> landlordlist) {

        super(context,0,landlordlist);
       // this.context=context;
       // this.landlordlist=landlordlist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null)
        {
            convertView=((Activity)getContext()).getLayoutInflater().inflate(R.layout.users,parent,false);
        }
       // LayoutInflater inflater=context.getLayoutInflater();
       // View listViewItem=inflater.inflate(R.layout.users,null,true);

        TextView t1=(TextView)convertView.findViewById(R.id.user_single_name);
        TextView t2=(TextView)convertView.findViewById(R.id.Address);
        //TextView t3=convertView.findViewById(R.id.Phone);
        //TextView t4=convertView.findViewById(R.id.Rent);

        users ll=getItem(position);

        t1.setText(ll.getname());
        t2.setText(ll.getaddress());
        //t3.setText(ll.getphone());
        //t4.setText(ll.getrent());
        return  convertView;
    }


}
