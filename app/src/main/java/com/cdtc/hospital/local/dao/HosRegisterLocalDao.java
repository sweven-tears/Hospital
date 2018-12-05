package com.cdtc.hospital.local.dao;

import com.cdtc.hospital.entity.HosRegister;

import java.util.List;

public interface HosRegisterLocalDao {

    List<HosRegister> queryHosRegisters();

    /**
     * 条件查询
     *
     * @param hosR_id
     * @return
     */
    List<HosRegister> queryByCondition(Integer hosR_id);

    /**
     * 根据id查询出挂号信息
     *
     * @param hosR_id
     * @return
     */
    HosRegister queryHosRegisterByHosR_id(Integer hosR_id);

    /**
     * 添加挂号信息
     *
     * @param hosRegister
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

    Integer updateStateById(Integer hosR_id,Integer hosR_state);

}
