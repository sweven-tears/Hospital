package com.cdtc.hospital.network.dao;

import com.cdtc.hospital.entity.Doctor;

import java.util.List;

public interface DoctorDao {

    /**
     * 查询所有的医生的信息
     *
     * @return
     */
    List<Doctor> queryDoctors();


    /*---------------------------------------以下为从未使用过的方法--------------------------------------------*/

    /**
     * 查询出科室列表
     *
     * @return
     */
    List<String> queryKeshiList();

    /**
     * 根据科室查询医生信息列表
     *
     * @param d_keshi
     * @return
     * @date 2018年11月19日
     * @time 下午8:19:52
     */
    List<Doctor> queryDoctorByKeshi(String d_keshi);

    /**
     * 根据ID查询出名字
     *
     * @param d_id
     * @return
     */
    String queryNameById(Integer d_id);
}
