package com.cdtc.hospital.network.dao;

import com.cdtc.hospital.network.entity.HosRegister;

import java.util.List;

public interface HosRegisterDao {

	/**
	 * 分页+条件查询
	 * 
	 * @param hosR_id
	 * @param d_name
	 * @param d_keshi
	 * @param currentPageNo
	 * @param pageSize
	 * @return
	 */
	public List<Object> selectByPageCondition(Integer hosR_id,
                                              String d_name, String d_keshi, Integer currentPageNo,
                                              Integer pageSize);

	/**
	 * 条件查询出总记录数
	 * 
	 * @param hosR_id
	 * @param d_name
	 * @param d_keshi
	 * @return
	 */
	public Integer getHosregisterCount(Integer hosR_id, String d_name,
                                       String d_keshi);

	/**
	 * 根据id查询出挂号信息
	 * 
	 * @param hosR_id
	 * @return
	 */
	public HosRegister queryHosRegisterByHosR_id(Integer hosR_id);

	/**
	 * 添加挂号信息
	 * @param hosRegister
	 * @return
	 */
	public Integer addHosRegister(HosRegister hosRegister);

	/**
	 * 更新挂号信息
	 * 
	 * @param hosRegister
	 * @return
	 */
	public Integer updateHosRegisterById(HosRegister hosRegister);
	
	/**
	 * 根据id删除数据
	 * @param hosR_id
	 * @return
	 */
	public Integer deleteHosRegisterById(Integer hosR_id);

}
