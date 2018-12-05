package com.cdtc.hospital.bean;

import com.cdtc.hospital.entity.HosRegister;

/**
 * Created by Sweven on 2018/12/4.
 * Email:sweventears@Foxmail.com
 */
public class HosRegisterBean extends HosRegister {
    private boolean selected;
    private HosRegister hosRegister;

    public HosRegisterBean(boolean selected, HosRegister hosRegister) {
        this.selected = selected;
        this.hosRegister = hosRegister;
    }

    public HosRegisterBean() {
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public HosRegister getHosRegister() {
        return hosRegister;
    }

    public void setHosRegister(HosRegister hosRegister) {
        this.hosRegister = hosRegister;
    }
}
