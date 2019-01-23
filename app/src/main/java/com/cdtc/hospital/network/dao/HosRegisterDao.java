package com.cdtc.hospital.network.dao;

import com.cdtc.hospital.entity.HosRegister;

import java.util.List;

public interface HosRegisterDao {


    /**
     * @return hosRegisterList
     */
    List<HosRegister> queryHosRegisters();

    /**
     * 添加挂号信息
     *
     * @param hosRegister 挂号信息
     * @return
     */
    Integer addHosRegister(HosRegister hosRegister);

    /**
     * 更新挂号信息
     *
     * @param hosRegister
     * @return
     */
    Integer updateHosRegisterById(HosRegister hosRegister);

    /**
     * 根据id删除数据
     *
     * @param hosR_id
     * @return
     */
    Integer deleteHosRegisterById(Integer hosR_id);

    Integer updateStateById(Integer hosR_id, Integer hosR_state);

    Integer getLastHosRId();

    /**
     * 条件查询
     *
     * @param hosR_id 主键，病历号
     * @param d_name  医生名
     * @param d_keshi 科室
     * @return 查询数据集合
     */
    List<HosRegister> selectByCondition(Integer hosR_id, String d_name, String d_keshi);

    /**
     * 条件查询出总记录数
     *
     * @param hosR_id
     * @param d_name
     * @param d_keshi
     * @return
     */
    Integer getHosRegisterCount(Integer hosR_id, String d_name,
                                String d_keshi);

    /**
     * 根据id查询出挂号信息
     *
     * @param hosR_id
     * @return
     */
    HosRegister queryHosRegisterByHosR_id(Integer hosR_id);

}
