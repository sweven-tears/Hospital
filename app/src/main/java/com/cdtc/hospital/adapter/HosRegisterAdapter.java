package com.cdtc.hospital.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cdtc.hospital.R;
import com.cdtc.hospital.network.entity.HosRegister;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Sweven on 2018/11/29.
 * Email:sweventears@Foxmail.com
 */
public class HosRegisterAdapter extends RecyclerView.Adapter<HosRegisterAdapter.HosRegisterViewHold> {

    private Context context;
    private List<HosRegister> hosRegisters;
    private LayoutInflater inflater;

    public HosRegisterAdapter(Context context, List<HosRegister> hosRegisters) {
        this.context = context;
        this.hosRegisters = hosRegisters;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public HosRegisterViewHold onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.hos_register_item, viewGroup, false);
        return new HosRegisterViewHold(view);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onBindViewHolder(@NonNull HosRegisterViewHold hosRegisterViewHold, int position) {
        HosRegister hosRegister=hosRegisters.get(position);
        hosRegisterViewHold.hosR_id.setText(String.valueOf(hosRegister.getHosR_id()));
        hosRegisterViewHold.d_name.setText(hosRegister.getD_name());

        Date date=hosRegister.getHosR_createTime();
        hosRegisterViewHold.hosR_createTime.setText(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date));

        hosRegisterViewHold.d_keshi.setText(hosRegister.getD_keshi());

        Integer hosR_state=hosRegister.getHosR_state();
//        挂号状态0挂号1住院2出院3退号4退院
        if (hosR_state==0) {
            hosRegisterViewHold.hosR_state.setText("挂号");
        }else if (hosR_state==1){
            hosRegisterViewHold.hosR_state.setText("住院");
        }else if (hosR_state==2){
            hosRegisterViewHold.hosR_state.setText("出院");
        }else if (hosR_state==3){
            hosRegisterViewHold.hosR_state.setText("退号");
        }else if (hosR_state==4){
            hosRegisterViewHold.hosR_state.setText("退院");
        }
    }

    @Override
    public int getItemCount() {
        return hosRegisters.size();
    }

    class HosRegisterViewHold extends RecyclerView.ViewHolder {

        private TextView hosR_id;
        private TextView d_name;
        private TextView hosR_createTime;
        private TextView d_keshi;
        private TextView hosR_state;

        HosRegisterViewHold(@NonNull View itemView) {
            super(itemView);
            hosR_id = itemView.findViewById(R.id.hos_r_id);
            d_name = itemView.findViewById(R.id.d_name);
            hosR_createTime = itemView.findViewById(R.id.hos_r_create_time);
            d_keshi = itemView.findViewById(R.id.d_keshi);
            hosR_state = itemView.findViewById(R.id.hos_r_state);
        }
    }
}
