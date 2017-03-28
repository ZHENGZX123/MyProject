package cn.kwim.mqttcilent.common.views.sort;

import java.io.Serializable;

public class SortModel implements Serializable{

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 1L;
	private String name;   //显示
	private String id; 
	private String isNewRecord; 
	private String photo; 
	private String firstWord; 
	private String groupId; 
	private String isMy; 
	private String admin; 
	private String roleNames; 
	private String loginFlag; 
	private String sortLetters;  //显示数据拼音的首字母
	
	
	@Override
	public String toString() {
		return "SortModel [name=" + name + ", sortLetters=" + sortLetters + ", id=" + id + ", isNewRecord="
				+ isNewRecord + ", photo=" + photo + ", firstWord=" + firstWord + ", groupId=" + groupId + ", isMy="
				+ isMy + ", admin=" + admin + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIsNewRecord() {
		return isNewRecord;
	}
	public void setIsNewRecord(String isNewRecord) {
		this.isNewRecord = isNewRecord;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getFirstWord() {
		return firstWord;
	}
	public void setFirstWord(String firstWord) {
		this.firstWord = firstWord;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getIsMy() {
		return isMy;
	}
	public void setIsMy(String isMy) {
		this.isMy = isMy;
	}
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
	public String getRoleNames() {
		return roleNames;
	}
	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}
	public String getLoginFlag() {
		return loginFlag;
	}
	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}
}
