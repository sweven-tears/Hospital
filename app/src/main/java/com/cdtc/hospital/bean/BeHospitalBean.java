package com.cdtc.hospital.bean;

import com.cdtc.hospital.entity.BeHospital;

/**
 * Created by Sweven on 2019/1/17.
 * Email:sweventears@Foxmail.com
 */
public class BeHospitalBean {

    private boolean selected;
    private BeHospital beHospital;

    public BeHospitalBean() {
    }

    public BeHospitalBean(boolean selected, BeHospital beHospital) {
        this.selected = selected;
        this.beHospital = beHospital;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public BeHospital getBeHospital() {
        return beHospital;
    }

    public void setBeHospital(BeHospital beHospital) {
        this.beHospital = beHospital;
    }
}
