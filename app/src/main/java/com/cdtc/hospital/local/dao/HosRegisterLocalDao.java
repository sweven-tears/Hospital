package com.cdtc.hospital.local.dao;

import com.cdtc.hospital.entity.HosRegister;

import java.util.List;

public interface HosRegisterLocalDao {

    public List<HosRegister> queryHosRegisters();

    /**
     * 条件查询
     *
     * @param hosR_id
     * @return
     */
    public List<HosRegister> queryByCondition(Integer hosR_id);

    /**
     * 根据id查询出挂号信息
     *
     * @param hosR_id
     * @return
     */
    public HosRegister queryHosRegisterByHosR_id(Integer hosR_id);

    /**
     * 添加挂号信息
     *
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
     *
     * @param hosR_id
     * @return
     */
    public Integer deleteHosRegisterById(Integer hosR_id);

}
