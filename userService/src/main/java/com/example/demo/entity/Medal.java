package com.example.demo.entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Date;

@ApiModel("用户登录")
public class Medal {
    @ApiModelProperty("勋章id")
    private Integer id;

    @ApiModelProperty("勋章名称")
    private String name;

    @ApiModelProperty("经验值")
    private Integer experience;

    @ApiModelProperty("勋章描述")
    private String describe;

    @ApiModelProperty("勋章图片")
    private String url;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        return "medal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                "experience=" + experience +
                ", describe='" + describe + '\'' +
                ", url='" + url + '\'' +
                "deleted=" + deleted +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                '}';
    }

}
