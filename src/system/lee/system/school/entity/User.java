package lee.system.school.entity;

public class User {
	/**
	 * id
	 */
	public String id;
	/**
	 * 用户id
	 */
	public String userId;
	/**
	 * 登录名
	 */
	public String loginName;

	/**
	 * 用户名称
	 */
	public String userName;
	/**
	 * 密码
	 */
	public String passWord;
	/**
	 * 性别
	 */
	public String sex;
	/**
	 * 身份证
	 */
	public String CID;
	/**
	 * 是否毕业
	 */
	public String isGraduate;
	
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCID() {
		return CID;
	}
	public void setCID(String cID) {
		CID = cID;
	}
	public String getIsGraduate() {
		return isGraduate;
	}
	public void setIsGraduate(String isGraduate) {
		this.isGraduate = isGraduate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
}
