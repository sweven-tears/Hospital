package com.cdtc.hospital.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cdtc.hospital.R;
import com.cdtc.hospital.local.dao.BaseLocalDao;
import com.cdtc.hospital.local.dao.DoctorLocalDao;
import com.cdtc.hospital.local.dao.impl.DoctorLocalDaoImpl;
import com.cdtc.hospital.network.entity.Doctor;
import com.cdtc.hospital.network.entity.HosRegister;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Sweven on 2018/11/29.
 * Email:sweventears@Foxmail.com
 */
public class HosRegisterAdapter extends RecyclerView.Adapter<HosRegisterAdapter.HosRegisterViewHold> {

    private Activity activity;
    private List<HosRegister> hosRegisters;
    private LayoutInflater inflater;

    public HosRegisterAdapter(Activity activity, List<HosRegister> hosRegisters) {
        this.activity = activity;
        this.hosRegisters = hosRegisters;
        this.inflater = LayoutInflater.from(activity);
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
        if (hosRegister==null){
            hosRegisterViewHold.hosRegisterInfoView.removeAllViews();
            TextView view=new TextView(activity);
            view.setText("无数据");
            view.setTextSize(38);
            hosRegisterViewHold.hosRegisterInfoView.addView(view);
            view.setGravity(View.TEXT_ALIGNMENT_CENTER);
            return;
        }

        DoctorLocalDao doctorLocalDao=new DoctorLocalDaoImpl(activity,BaseLocalDao.QUERY_DATABASE);
        Doctor doctor=doctorLocalDao.queryDoctorById(hosRegister.getD_id());

        hosRegisterViewHold.hosR_id.setText(String.valueOf(hosRegister.getHosR_id()));
        hosRegisterViewHold.d_name.setText(doctor.getD_name());

        Date date=hosRegister.getHosR_createTime();
        hosRegisterViewHold.hosR_createTime.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));

        hosRegisterViewHold.d_keshi.setText(doctor.getD_keshi());

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

    public void insertHosRegister(HosRegister hosRegister){
        hosRegisters.add(hosRegister);
        notifyItemInserted(hosRegisters.size()-1);
    }

    @Override
    public int getItemCount() {
        return hosRegisters.size();
    }

    class HosRegisterViewHold extends RecyclerView.ViewHolder {

        private LinearLayout hosRegisterInfoView;
        private TextView hosR_id;
        private TextView d_name;
        private TextView hosR_createTime;
        private TextView d_keshi;
        private TextView hosR_state;

        HosRegisterViewHold(@NonNull View itemView) {
            super(itemView);
            hosRegisterInfoView=itemView.findViewById(R.id.hos_register_info_view);
            hosR_id = itemView.findViewById(R.id.hos_r_id);
            d_name = itemView.findViewById(R.id.d_name);
            hosR_createTime = itemView.findViewById(R.id.hos_r_create_time);
            d_keshi = itemView.findViewById(R.id.d_keshi);
            hosR_state = itemView.findViewById(R.id.hos_r_state);
        }
    }
}
