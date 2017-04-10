package cn.kwim.mqttcilent.common;

/**
 * 全局单列变量，一些公用数据
 *
 * @author hmg
 */
public class Global {
    public static final String GL_NICKNAME = "gl_nickname";
    public static final String GL_LOGO = "gl_logo";
    public static final String GL_GENDER = "gl_gender";
    public static final String GL_UID = "gl_uid";


    public static Global instance;
    //当前用户信息
    private String userName;
    private String passWord;
    private String userId;
    private String logo;
    private String nickName;
    private String gender;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public static Global getInstance() {
        if (instance == null) {
            instance = new Global();
        }
        return instance;
    }

    private Global() {

    }


}
