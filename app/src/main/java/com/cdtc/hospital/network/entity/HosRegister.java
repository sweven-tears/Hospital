package com.cdtc.hospital.network.entity;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

/**
 * @author sweven
 * @version 1.0
 * 病例实体类
 */
public class HosRegister {
    /*
     * 挂号的编号，也就是主键
     */
    private Integer hosR_id;

    /*
     * 医生的名字
     */
    private String d_name;

    /*
     * 挂号的时间
     */
    private Date hosR_createTime;

    /*
     * 科室
     */
    private String d_keshi;

    /*
     * 挂号状态
     */
    private Integer hosR_state;

    /*
     * 病人姓名
     */
    private String hosR_name;

    /*
     * 病人身份证
     */
    private String hosR_idCard;

    /*
     * 病人社保卡
     */
    private String hosR_medical;

    /*
     * 挂号费
     */
    private Double hosR_regPrice;

    /*
     * 联系电话
     */
    private String hosR_phone;

    /*
     * 是否自费0自费1免费，默认0
     */
    private Integer hosR_selfPrice;

    /*
     * 性别0男1女，默认0
     */
    private Integer hosR_sex;

    /*
     * 年龄
     */
    private Integer hosR_age;

    /*
     * 职位
     */
    private String hosR_work;

    /*
     * 初复诊0出诊1复诊，默认0
     */
    private Integer hosR_lookDoctor;

    /*
     * 医生编号，外键关联医生表主键
     */
    private Integer d_id;

    private String hosR_remark;

    public Integer getHosR_id() {
        return hosR_id;
    }

    public void setHosR_id(Integer hosR_id) {
        this.hosR_id = hosR_id;
    }

    public String getD_name() {
        return d_name;
    }

    public void setD_name(String d_name) {
        this.d_name = d_name;
    }

    public Date getHosR_createTime() {
        return hosR_createTime;
    }

    public void setHosR_createTime(Date hosR_createTime) {
        this.hosR_createTime = hosR_createTime;
    }

    public String getD_keshi() {
        return d_keshi;
    }

    public void setD_keshi(String d_keshi) {
        this.d_keshi = d_keshi;
    }

    public Integer getHosR_state() {
        return hosR_state;
    }

    public void setHosR_state(Integer hosR_state) {
        this.hosR_state = hosR_state;
    }

    public String getHosR_name() {
        return hosR_name;
    }

    public void setHosR_name(String hosR_name) {
        this.hosR_name = hosR_name;
    }

    public String getHosR_idCard() {
        return hosR_idCard;
    }

    public void setHosR_idCard(String hosR_idCard) {
        this.hosR_idCard = hosR_idCard;
    }

    public String getHosR_medical() {
        return hosR_medical;
    }

    public void setHosR_medical(String hosR_medical) {
        this.hosR_medical = hosR_medical;
    }

    public Double getHosR_regPrice() {
        return hosR_regPrice;
    }

    public void setHosR_regPrice(Double hosR_regPrice) {
        this.hosR_regPrice = hosR_regPrice;
    }

    public String getHosR_phone() {
        return hosR_phone;
    }

    public void setHosR_phone(String hosR_phone) {
        this.hosR_phone = hosR_phone;
    }

    public Integer getHosR_selfPrice() {
        return hosR_selfPrice;
    }

    public void setHosR_selfPrice(Integer hosR_selfPrice) {
        this.hosR_selfPrice = hosR_selfPrice;
    }

    public Integer getHosR_sex() {
        return hosR_sex;
    }

    public void setHosR_sex(Integer hosR_sex) {
        this.hosR_sex = hosR_sex;
    }

    public Integer getHosR_age() {
        return hosR_age;
    }

    public void setHosR_age(Integer hosR_age) {
        this.hosR_age = hosR_age;
    }

    public String getHosR_work() {
        return hosR_work;
    }

    public void setHosR_work(String hosR_work) {
        this.hosR_work = hosR_work;
    }

    public Integer getHosR_lookDoctor() {
        return hosR_lookDoctor;
    }

    public void setHosR_lookDoctor(Integer hosR_lookDoctor) {
        this.hosR_lookDoctor = hosR_lookDoctor;
    }

    public Integer getD_id() {
        return d_id;
    }

    public void setD_id(Integer d_id) {
        this.d_id = d_id;
    }

    public String getHosR_remark() {
        return hosR_remark;
    }

    public void setHosR_remark(String hosR_remark) {
        this.hosR_remark = hosR_remark;
    }

    @Override
    public String toString() {
        return "HosRegister [hosR_id=" + hosR_id + ", d_name=" + d_name
                + ", hosR_createTime=" + hosR_createTime + ", d_keshi="
                + d_keshi + ", hosR_state=" + hosR_state + ", hosR_name="
                + hosR_name + ", hosR_idCard=" + hosR_idCard
                + ", hosR_medical=" + hosR_medical + ", hosR_regPrice="
                + hosR_regPrice + ", hosR_phone=" + hosR_phone
                + ", hosR_selfPrice=" + hosR_selfPrice + ", hosR_sex="
                + hosR_sex + ", hosR_ageInteger=" + hosR_age
                + ", hosR_work=" + hosR_work + ", hosR_lookDoctor="
                + hosR_lookDoctor + ", d_id=" + d_id + ", hosR_remark="
                + hosR_remark + "]";
    }

    public static String toJSonString(HosRegister hosRegister) {
        return JSONArray.fromObject(hosRegister).toString();
    }

    public static HosRegister toHosRegister(String jsonString) {

        JSONArray array = JSONArray.fromObject(jsonString);
        JSONObject object = JSONObject.fromObject(array);
        Object hosRegister = JSONObject.toBean(object, HosRegister.class);
        return (HosRegister) hosRegister;
    }

}
