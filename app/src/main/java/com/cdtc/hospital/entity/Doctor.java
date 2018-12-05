package com.cdtc.hospital.entity;

import java.sql.Date;

/**
 * 
 * @author sweven
 * @version 1.0
 * 医生的对象
 */
public class Doctor {
	
	/*
	 * 主键
	 */
	private Integer d_id;
	
	/*
	 * 姓名
	 */
	private String d_name;
	
	/*
	 * 身份证
	 */
	private String d_idCard;
	
	/*
	 * 手机号
	 */
	private String d_phone;
	
	/*
	 * 座机号
	 */
	private String d_telPhone;
	
	/*
	 * 性别
	 */
	private Integer d_sex;
	
	/*
	 * 生日
	 */
	private Date d_birthday;
	
	/*
	 * 年龄
	 */
	private Integer d_age;
	
	/*
	 * 邮箱
	 */
	private String d_email;
	
	/*
	 * 所属科室
	 */
	private String d_keshi;
	
	/*
	 * 学历
	 */
	private String d_xueli;
	
	/*
	 * 描述
	 */
	private String d_descr;
	
	/*
	 * 入院时间
	 */
	private Date d_inTime;
	
	/*
	 * 状态
	 */
	private Integer d_state;
	
	public Doctor(){
		
	}

	public Doctor(Integer d_id, String d_name, String d_idCard, String d_phone,
			String d_telPhone, Integer d_sex, Date d_birthday, Integer d_age,
			String d_email, String d_keshi, String d_xueli, String d_descr,
			Date d_inTime, Integer d_state) {
		super();
		this.d_id = d_id;
		this.d_name = d_name;
		this.d_idCard = d_idCard;
		this.d_phone = d_phone;
		this.d_telPhone = d_telPhone;
		this.d_sex = d_sex;
		this.d_birthday = d_birthday;
		this.d_age = d_age;
		this.d_email = d_email;
		this.d_keshi = d_keshi;
		this.d_xueli = d_xueli;
		this.d_descr = d_descr;
		this.d_inTime = d_inTime;
		this.d_state = d_state;
	}

	public Integer getD_id() {
		return d_id;
	}

	public void setD_id(Integer d_id) {
		this.d_id = d_id;
	}

	public String getD_name() {
		return d_name;
	}

	public void setD_name(String d_name) {
		this.d_name = d_name;
	}

	public String getD_idCard() {
		return d_idCard;
	}

	public void setD_idCard(String d_idCard) {
		this.d_idCard = d_idCard;
	}

	public String getD_phone() {
		return d_phone;
	}

	public void setD_phone(String d_phone) {
		this.d_phone = d_phone;
	}

	public String getD_telPhone() {
		return d_telPhone;
	}

	public void setD_telPhone(String d_telPhone) {
		this.d_telPhone = d_telPhone;
	}

	public Integer getD_sex() {
		return d_sex;
	}

	public void setD_sex(Integer d_sex) {
		this.d_sex = d_sex;
	}

	public Date getD_birthday() {
		return d_birthday;
	}

	public void setD_birthday(Date d_birthday) {
		this.d_birthday = d_birthday;
	}

	public Integer getD_age() {
		return d_age;
	}

	public void setD_age(Integer d_age) {
		this.d_age = d_age;
	}

	public String getD_email() {
		return d_email;
	}

	public void setD_email(String d_email) {
		this.d_email = d_email;
	}

	public String getD_keshi() {
		return d_keshi;
	}

	public void setD_keshi(String d_keshi) {
		this.d_keshi = d_keshi;
	}

	public String getD_xueli() {
		return d_xueli;
	}

	public void setD_xueli(String d_xueli) {
		this.d_xueli = d_xueli;
	}

	public String getD_descr() {
		return d_descr;
	}

	public void setD_descr(String d_descr) {
		this.d_descr = d_descr;
	}

	public Date getD_inTime() {
		return d_inTime;
	}

	public void setD_inTime(Date d_inTime) {
		this.d_inTime = d_inTime;
	}

	public Integer getD_state() {
		return d_state;
	}

	public void setD_state(Integer d_state) {
		this.d_state = d_state;
	}

	@Override
	public String toString() {
		return "Doctor [d_id=" + d_id + ", d_name=" + d_name + ", d_idCard="
				+ d_idCard + ", d_phone=" + d_phone + ", d_telPhone="
				+ d_telPhone + ", d_sex=" + d_sex + ", d_birthday="
				+ d_birthday + ", d_age=" + d_age + ", d_email=" + d_email
				+ ", d_keshi=" + d_keshi + ", d_xueli=" + d_xueli
				+ ", d_descr=" + d_descr + ", d_inTime=" + d_inTime
				+ ", d_state=" + d_state + "]";
	}
	
	
}
