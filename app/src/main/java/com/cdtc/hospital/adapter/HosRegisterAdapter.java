package com.cdtc.hospital.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cdtc.hospital.R;
import com.cdtc.hospital.bean.HosRegisterBean;
import com.cdtc.hospital.local.dao.BaseLocalDao;
import com.cdtc.hospital.local.dao.DoctorLocalDao;
import com.cdtc.hospital.local.dao.HosRegisterLocalDao;
import com.cdtc.hospital.local.dao.impl.DoctorLocalDaoImpl;
import com.cdtc.hospital.entity.Doctor;
import com.cdtc.hospital.entity.HosRegister;
import com.cdtc.hospital.local.dao.impl.HosRegisterLocalDaoImpl;
import com.cdtc.hospital.task.HosRegisterTask;
import com.cdtc.hospital.view.HosRegisterDetailsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sweven on 2018/11/29.
 * Email:sweventears@Foxmail.com
 */
public class HosRegisterAdapter extends RecyclerView.Adapter<HosRegisterAdapter.HosRegisterViewHold> {

    private Activity activity;
    private List<HosRegisterBean> hosRegisterBeans;
    private LayoutInflater inflater;
    private OnSelectListener mOnSelectListener=null;

    public HosRegisterAdapter(Activity activity, List<HosRegister> hosRegisters) {
        this.activity = activity;

        hosRegisterBeans = new ArrayList<>();
        for (HosRegister hosRegister : hosRegisters) {
            HosRegisterBean bean = new HosRegisterBean();
            bean.setSelected(false);
            bean.setHosRegister(hosRegister);
            hosRegisterBeans.add(bean);
        }

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
    public void onBindViewHolder(@NonNull HosRegisterViewHold hold, int position) {
        iniData(hold, position);

    }

    /**
     * 初始化数据
     *
     * @param hold
     * @param position
     */
    private void iniData(@NonNull HosRegisterViewHold hold, int position) {
        HosRegister hosRegister = hosRegisterBeans.get(position).getHosRegister();
        boolean selected = hosRegisterBeans.get(position).isSelected();

        if (hosRegister == null || hosRegisterBeans == null || hosRegisterBeans.size() == 0) {
            hold.hosRegisterInfoView.removeAllViews();
            TextView view = new TextView(activity);
            view.setText("无数据");
            view.setTextSize(38);
            hold.hosRegisterInfoView.addView(view);
            view.setGravity(View.TEXT_ALIGNMENT_CENTER);
            return;
        }

        DoctorLocalDao doctorLocalDao = new DoctorLocalDaoImpl(activity, BaseLocalDao.QUERY);
        Doctor doctor = doctorLocalDao.queryDoctorById(hosRegister.getD_id());

        hold.hosR_id.setText(String.valueOf(hosRegister.getHosR_id()));
        hold.d_name.setText(doctor.getD_name());

        hold.d_keshi.setText(doctor.getD_keshi());

        Integer hosR_state = hosRegister.getHosR_state();
//        挂号状态0挂号1住院2出院3退号4退院
        if (hosR_state == 0) {
            hold.hosR_state.setText("挂号");
        } else if (hosR_state == 1) {
            hold.hosR_state.setText("住院");
        } else if (hosR_state == 2) {
            hold.hosR_state.setText("出院");
        } else if (hosR_state == 3) {
            hold.hosR_state.setText("退号");
        } else if (hosR_state == 4) {
            hold.hosR_state.setText("退院");
        }

        CheckBox checkBox = hold.checkBox;
        if (selected) {
            checkBox.setChecked(true);
            hosRegisterBeans.get(position).setSelected(true);
        } else {
            checkBox.setChecked(false);
            hosRegisterBeans.get(position).setSelected(false);
        }
    }

    /**
     * 插入新数据
     *
     * @param hosRegister 新数据
     */
    public void insertHosRegister(HosRegister hosRegister) {
        hosRegisterBeans.add(new HosRegisterBean(false, hosRegister));
        notifyItemInserted(hosRegisterBeans.size() - 1);
    }

    public void updateHosRegister(Integer d_id) {
        for (int i = 0; i < getItemCount(); i++) {
            if (!hosRegisterBeans.get(i).isSelected()) {
                hosRegisterBeans.get(i).getHosRegister().setD_id(d_id);
                notifyItemChanged(i);
                return;
            }
        }
    }

    /**
     * 全选
     */
    public void selectAll() {
        for (int i = 0; i < getItemCount(); i++) {
            if (!hosRegisterBeans.get(i).isSelected()) {
                hosRegisterBeans.get(i).setSelected(true);
                notifyItemChanged(i);
            }
        }
    }

    /**
     * 取消全选
     */
    public void cancelSelectAll() {
        for (int i = 0; i < getItemCount(); i++) {
            if (hosRegisterBeans.get(i).isSelected()) {
                hosRegisterBeans.get(i).setSelected(false);
                notifyItemChanged(i);
            }
        }
    }

    /**
     * 更新挂号状态：退号
     */
    public void updateState() {
        for (int i = 0; i < getItemCount(); i++) {
            HosRegisterBean hosRegisterBean = hosRegisterBeans.get(i);
            if (hosRegisterBean.isSelected()) {
                // 界面更新
                hosRegisterBeans.get(i).getHosRegister().setHosR_state(3);
                notifyItemChanged(i);

                // 数据库数据更新
                HosRegister hosRegister = hosRegisterBean.getHosRegister();
                HosRegisterTask task = new HosRegisterTask(activity, hosRegister.getHosR_id(), 3, HosRegisterTask.TASK_UPDATE_STATE);
                task.execute();
            }
        }
    }

    /**
     * @return 获取选中项的数量
     */
    public int getSelectedCount() {
        int count = 0;
        for (int i = 0; i < getItemCount(); i++) {
            HosRegisterBean hosRegisterBean = hosRegisterBeans.get(i);
            if (hosRegisterBean.isSelected()) {
                count++;
            }
        }
        return count;
    }

    /**
     * @return 获取单个选中项的HosR_id
     */
    public int getSelectedHosR_id() {
        for (int i = 0; i < getItemCount(); i++) {
            if (hosRegisterBeans.get(i).isSelected()) {
                return hosRegisterBeans.get(i).getHosRegister().getHosR_id();
            }
        }
        return 1001;
    }

    @Override
    public int getItemCount() {
        return hosRegisterBeans.size();
    }

    class HosRegisterViewHold extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout hosRegisterInfoView;
        private CheckBox checkBox;
        private TextView hosR_id;
        private TextView d_name;
        private TextView d_keshi;
        private TextView hosR_state;

        HosRegisterViewHold(@NonNull View itemView) {
            super(itemView);
            hosRegisterInfoView = itemView.findViewById(R.id.hos_register_info_view);
            checkBox = itemView.findViewById(R.id.select_checkbox);
            hosR_id = itemView.findViewById(R.id.hos_r_id);
            d_name = itemView.findViewById(R.id.d_name);
            d_keshi = itemView.findViewById(R.id.d_keshi);
            hosR_state = itemView.findViewById(R.id.hos_r_state);

            hosRegisterInfoView.setOnClickListener(this);

            checkBox.setOnCheckedChangeListener(
                    (compoundButton, b) -> {
                        if (mOnSelectListener != null) {
                            hosRegisterBeans.get(getAdapterPosition()).setSelected(b);
                            mOnSelectListener.onSelect();
                        }
                    });
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (hosRegisterBeans.get(position).getHosRegister() == null) {
                return;
            }
            Intent intent = new Intent(activity, HosRegisterDetailsActivity.class);
            intent.putExtra("hosR_id", hosRegisterBeans.get(position).getHosRegister().getHosR_id());
            activity.startActivity(intent);
        }

    }

    public interface OnSelectListener {
        void onSelect();
    }

    public void setOnSelectListener(OnSelectListener mOnSelectListener) {
        this.mOnSelectListener = mOnSelectListener;
    }


}
