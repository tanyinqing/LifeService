package com.linyou.lifeservice.entity;

import java.io.Serializable;

/**
 * 用户
 * @author 志强
 *
 */
public class User implements Serializable{
	/**
	 * private String age;//年龄
	 private String createTime;//注册时间
	 private String email;//注册邮箱
	 private String headportrait;//头像
	 private String id;//用户id
	 private String nickName;//昵称
	 private String password;//密码
	 private String phoneNum;//手机号
	 private String sex;//性别
	 private String userName;//用户名
	 private String level;//用户等级
	 */
	private static final long serialVersionUID = 1L;
	private String age;//年龄
	private String regTime;//注册时间
	private String email;//注册邮箱
	private String portrait;//头像
	private String userId;//用户id
	private String userName;//昵称
	private String password;//密码
	private String photoMob;//手机号
	private String gender;//性别
	private String realName;//用户名
	private String logins;//用户等级

	@Override
	public String toString() {
		return "User{" +
				"age='" + age + '\'' +
				", regTime='" + regTime + '\'' +
				", email='" + email + '\'' +
				", portrait='" + portrait + '\'' +
				", userId='" + userId + '\'' +
				", userName='" + userName + '\'' +
				", password='" + password + '\'' +
				", photoMob='" + photoMob + '\'' +
				", gender='" + gender + '\'' +
				", realName='" + realName + '\'' +
				", logins='" + logins + '\'' +
				'}';
	}

	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getCreateTime() {
		return regTime;
	}
	public void setCreateTime(String Reg_time) {
		this.regTime = Reg_time;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHeadportrait() {
		return portrait;
		//return "/data/files/store_10/goods_67/small_201408150951074849.jpg";
	}
	public void setHeadportrait(String headportrait) {
		this.portrait = headportrait;
	}
	public String getId() {
		return userId;
	}
	public void setId(String id) {
		this.userId = id;
	}
	public String getNickName() {
		return userName;
	}
	public void setNickName(String nickName) {
		this.userName = nickName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNum() {
		return photoMob;
	}
	public void setPhoneNum(String phoneNum) {
		this.photoMob = phoneNum;
	}
	public String getSex() {
		return gender;
	}
	public void setSex(String sex) {
		this.gender = sex;
	}
	public String getUserName() {
		return realName;
	}
	public void setUserName(String userName) {
		this.realName = userName;
	}
	public String getLevel() {
		return logins;
	}
	public void setLevel(String level) {
		this.logins = level;
	}
	
}
