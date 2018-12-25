package com.cdtc.hospital.network.dao;

import com.cdtc.hospital.entity.BeHospital;

import java.util.List;

public interface BeHospitalDao {

    /**
     * 分页+条件查询
     *
     * @param beHospital
     * @return
     */
    List<BeHospital> selectBeHospitalByCondition(BeHospital beHospital);

    /**
     * 计算behospital表的数据总数
     *
     * @param beHospital
     * @return
     */
    Integer queryBeHospitalCount(BeHospital beHospital);

    /**
     * 根据住院编号更新住院状态
     *
     * @param hosR_state
     * @param beH_id
     * @return
     */
    Integer updateHosR_StateById(Integer hosR_state, Integer beH_id);

    /**
     * 根据id查询出挂号id
     *
     * @param beH_id
     * @return
     */
    Integer queryHosR_idById(Integer beH_id);

    /**
     * 根据id查询出护理人员和床号
     *
     * @param beH_id
     * @return
     */
    BeHospital queryNurseBedById(Integer beH_id);

    /**
     * 通过id更新护理人员和床号
     *
     * @param beH_nursePeople
     * @param beH_patBed
     * @param beH_id
     * @return
     */
    Integer updateNurseBedById(String beH_nursePeople,
                               String beH_patBed, Integer beH_id);

    /**
     * 添加信息
     *
     * @param beHospital
     * @return
     */
    Integer addBeHospital(BeHospital beHospital);

    /**
     * 为缴纳押金页面查询信息
     *
     * @param beH_id
     * @return
     */
    BeHospital queryBeHospitalByIdForAntecedent(Integer beH_id);

    /**
     * 根据id修改缴纳押金
     *
     * @param beH_antecedent
     * @param beH_id
     * @return
     */
    Integer updateAntecedentById(Double beH_antecedent, Integer beH_id);
}
