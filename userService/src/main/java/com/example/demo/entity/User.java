package com.example.demo.entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Date;

@ApiModel("用户信息")
public class User {
    @ApiModelProperty("用户id")
    private Integer id;

    @ApiModelProperty("用户邮箱")
    private String email;

    @ApiModelProperty("头像")
    private String url;

    @ApiModelProperty("用户名")
    private String name;

    @ApiModelProperty("等级")
    private String grade;

    @ApiModelProperty("经验")
    private String experience;

    @ApiModelProperty("电话")
    private String tele;

    @ApiModelProperty("出生日期")
    private String birthday;

    @ApiModelProperty("岗位")
    private String post;

    @ApiModelProperty("职业")
    private String profession;

    @ApiModelProperty("个性签名")
    private String signature;

    @ApiModelProperty("性别")
    private String gender;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("访客数量")
    private Integer visits;

    @ApiModelProperty("状态")
    private Integer status;

    public Integer getID() {
        return id;
    }

    public void setID(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getVisits() {
        return visits;
    }

    public void setVisits(Integer visits) {
        this.visits = visits;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "user{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", grade='" + grade + '\'' +
                ", experience='" + experience + '\'' +
                ", tele='" + tele + '\'' +
                ", birthday='" + birthday + '\'' +
                ", post='" + post + '\'' +
                ", profession='" + profession + '\'' +
                ", signature='" + signature + '\'' +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                "visits=" + visits +
                "status=" + status +
                '}';
    }
}
