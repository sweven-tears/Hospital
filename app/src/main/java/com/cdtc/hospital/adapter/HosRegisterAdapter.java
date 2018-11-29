package com.cdtc.hospital.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdtc.hospital.network.entity.HosRegister;

import java.util.ArrayList;

/**
 * Created by Sweven on 2018/11/29.
 * Email:sweventears@Foxmail.com
 */
public class HosRegisterAdapter extends RecyclerView.Adapter<HosRegisterAdapter.RegisterViewHold> {

    private Context context;
    private ArrayList<HosRegister> hosRegisters;
    private LayoutInflater inflater;

    public HosRegisterAdapter(Context context, ArrayList<HosRegister> hosRegisters) {
        this.context = context;
        this.hosRegisters = hosRegisters;
        this.inflater= LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RegisterViewHold onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=inflater.inflate(null,viewGroup,false);
        return new RegisterViewHold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RegisterViewHold registerViewHold, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class RegisterViewHold extends RecyclerView.ViewHolder {
        public RegisterViewHold(@NonNull View itemView) {
            super(itemView);
        }
    }
}
