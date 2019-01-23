package com.cdtc.hospital.entity;

public class User {

    private String u_loginName;
    private String u_passWord;
    private String u_trueName;
    private String u_email;
    private Integer u_state;
    private Integer r_id;

    public String getU_loginName() {
        return u_loginName;
    }

    public void setU_loginName(String u_loginName) {
        this.u_loginName = u_loginName;
    }

    public String getU_passWord() {
        return u_passWord;
    }

    public void setU_passWord(String u_passWord) {
        this.u_passWord = u_passWord;
    }

    public String getU_trueName() {
        return u_trueName;
    }

    public void setU_trueName(String u_trueName) {
        this.u_trueName = u_trueName;
    }

    public String getU_email() {
        return u_email;
    }

    public void setU_email(String u_email) {
        this.u_email = u_email;
    }

    public Integer getU_state() {
        return u_state;
    }

    public void setU_state(Integer u_state) {
        this.u_state = u_state;
    }

    public Integer getR_id() {
        return r_id;
    }

    public void setR_id(Integer r_id) {
        this.r_id = r_id;
    }

    @Override
    public String toString() {
        return "TableUser [u_loginName=" + u_loginName + ", u_passWord="
                + u_passWord + ", u_trueName=" + u_trueName + ", u_email="
                + u_email + ", u_state=" + u_state + ", r_id=" + r_id + "]";
    }

}
