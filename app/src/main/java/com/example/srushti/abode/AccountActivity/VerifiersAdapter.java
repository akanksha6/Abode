package com.example.srushti.abode.AccountActivity;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class VerifiersAdapter extends ArrayAdapter<users>{
    private List<users> landlordlist;
    public VerifiersAdapter(Context context, List<users> landlordlist) {

        super(context,0,landlordlist);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null)
        {
            convertView=((Activity)getContext()).getLayoutInflater().inflate(R.layout.assistantprofile,parent,false);
        }
        TextView t1=(TextView)convertView.findViewById(R.id.name);
        TextView t2=(TextView)convertView.findViewById(R.id.phone);
        TextView t3=(TextView)convertView.findViewById(R.id.Fee);
        TextView t5=(TextView)convertView.findViewById(R.id.id);
        CircleImageView mview =convertView.findViewById(R.id.setting_image);
        users ll=getItem(position);
        t1.setText(ll.getname());
        t2.setText(ll.getphone());
        t3.setText("Fee"+ll.getFee());
        t5.setText("ID : "+ll.getId());
        if(ll.getProImg()!=null)
        {
            Picasso.get().load(ll.getProImg()).into(mview);
        }
        return  convertView;
    }


}
