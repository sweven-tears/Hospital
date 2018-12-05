package com.cdtc.hospital.local.dao;

import com.cdtc.hospital.entity.Doctor;

import java.util.List;

public interface DoctorLocalDao {
	
	/**
	 * 查询所有的医生的信息
	 * @return
	 */
	public List<Doctor> queryDoctors();
	
	/**
	 * 查询出科室列表
	 * @return
	 */
	public List<String> queryKeshiList();

	public List<String> queryNameByKeshi(String d_keshi);
	
	/**
	 * 根据科室查询医生信息列表 
	 * @date 2018年11月19日
	 * @time 下午8:19:52
	 * @param d_keshi
	 * @return
	 */
	public List<Doctor> queryDoctorByKeshi(String d_keshi);

	/**
	 * 根据名字查询医生信息列表
	 * @date 2018年11月19日
	 * @time 下午8:19:52
	 * @param d_name
	 * @return
	 */
	public List<Doctor> queryDoctorByName(String d_name);
	
	/**
	 * 根据ID查询出名字
	 * @param d_id
	 * @return
	 */
	public String queryNameById(Integer d_id);

	public Doctor queryDoctorById(Integer d_id);

	public long insertDoctor(Doctor doctor);
}
