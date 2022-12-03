package com.example.demo.entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Date;

@ApiModel("用户登录")
public class Login {

    @ApiModelProperty("登录id")
    private Integer id;

    @ApiModelProperty("登录密码")
    private String password;

    @ApiModelProperty("登录邮箱")
    private String email;

    @ApiModelProperty("登录状态")
    private Integer status;

    @ApiModelProperty("逻辑删除")
    private Integer deleted;

    @ApiModelProperty("创建时间")
    private Date create_time;

    @ApiModelProperty("修改时间")
    private Date update_time;


    public Integer getID() {
        return id;
    }

    public void setID(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreat_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    @Override
    public String toString() {
        return "login{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                "status=" + status +
                "deleted=" + deleted +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                '}';
    }
}
