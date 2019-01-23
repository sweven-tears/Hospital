package com.cdtc.hospital.entity;

import java.sql.Date;

/**
 * 
 * @author sweven
 * @version 1.0
 * account class
 * map MySQL table: behospital
 * quote table:hosregister-hosR_name
 * quote table:
 *
 */
public class BeHospital {
	/*
	 * 住院编号
	 */
	private Integer beH_id;
	
	/*
	 * 护理人
	 */
	private String beH_nursePeople;
	
	/*
	 * 病床编号
	 */
	private String beH_patBed;
	
	/*
	 * 交纳押金，double
	 */
	private double beH_antecedent;
	
	/*
	 * 病情描述
	 */
	private String beH_illness;
	
	/*
	 * 结算状态，0未结算1已结算默认0
	 */
	private Integer beH_closePrice;
	
	/*
	 * 信息状态0正常1删除默认0
	 */
	private Integer beH_state;
	
	/*
	 * 外键关联挂号表主键
	 */
	private Integer hosR_id;
	
	/*
	 * 生成时间(入院时间)
	 */
	private Date beH_createTime;
	
	
	/**
	 * 对应hosRegiter表的hosR_name
	 */
	private String hosR_name;
	
	/**
	 * 对应hosRegister表的hosR_phone
	 */
	private String hosR_phone;
	
	/**
	 * 对应hosRegister表的hosR_idCard
	 */
	private String hosR_idCard;
	
	/**
	 * 对应hosRegister表的hosR_state
	 */
	private Integer hosR_state;
	
	/**
	 * 对应doctor表的d_name
	 */
	private String d_name;
	
	/**
	 * 对应doctor表的d_keshi
	 */
	private String d_keshi;

	public Integer getBeH_id() {
		return beH_id;
	}

	public void setBeH_id(Integer beH_id) {
		this.beH_id = beH_id;
	}

	public String getBeH_nursePeople() {
		return beH_nursePeople;
	}

	public void setBeH_nursePeople(String beH_nursePeople) {
		this.beH_nursePeople = beH_nursePeople;
	}

	public String getBeH_patBed() {
		return beH_patBed;
	}

	public void setBeH_patBed(String beH_patBed) {
		this.beH_patBed = beH_patBed;
	}

	public double getBeH_antecedent() {
		return beH_antecedent;
	}

	public void setBeH_antecedent(double beH_antecedent) {
		this.beH_antecedent = beH_antecedent;
	}

	public String getBeH_illness() {
		return beH_illness;
	}

	public void setBeH_illness(String beH_illness) {
		this.beH_illness = beH_illness;
	}

	public Integer getBeH_closePrice() {
		return beH_closePrice;
	}

	public void setBeH_closePrice(Integer beH_closePrice) {
		this.beH_closePrice = beH_closePrice;
	}

	public Integer getBeH_state() {
		return beH_state;
	}

	public void setBeH_state(Integer beH_state) {
		this.beH_state = beH_state;
	}

	public Integer getHosR_id() {
		return hosR_id;
	}

	public void setHosR_id(Integer hosR_id) {
		this.hosR_id = hosR_id;
	}

	public Date getBeH_createTime() {
		return beH_createTime;
	}

	public void setBeH_createTime(Date beH_createTime) {
		this.beH_createTime = beH_createTime;
	}

	public String getHosR_name() {
		return hosR_name;
	}

	public void setHosR_name(String hosR_name) {
		this.hosR_name = hosR_name;
	}

	public String getHosR_phone() {
		return hosR_phone;
	}

	public void setHosR_phone(String hosR_phone) {
		this.hosR_phone = hosR_phone;
	}

	public String getHosR_idCard() {
		return hosR_idCard;
	}

	public void setHosR_idCard(String hosR_idCard) {
		this.hosR_idCard = hosR_idCard;
	}

	public Integer getHosR_state() {
		return hosR_state;
	}

	public void setHosR_state(Integer hosR_state) {
		this.hosR_state = hosR_state;
	}

	public String getD_name() {
		return d_name;
	}

	public void setD_name(String d_name) {
		this.d_name = d_name;
	}

	public String getD_keshi() {
		return d_keshi;
	}

	public void setD_keshi(String d_keshi) {
		this.d_keshi = d_keshi;
	}

	@Override
	public String toString() {
		return "BeHospital [beH_id=" + beH_id + ", beH_nursePeople="
				+ beH_nursePeople + ", beH_patBed=" + beH_patBed
				+ ", beH_antecedent=" + beH_antecedent + ", beH_illness="
				+ beH_illness + ", beH_closePrice=" + beH_closePrice
				+ ", beH_state=" + beH_state + ", hosR_id=" + hosR_id
				+ ", beH_createTime=" + beH_createTime + ", hosR_name="
				+ hosR_name + ", hosR_phone=" + hosR_phone + ", hosR_idCard="
				+ hosR_idCard + ", hosR_state=" + hosR_state + ", d_name="
				+ d_name + ", d_keshi=" + d_keshi + "]";
	}

}
