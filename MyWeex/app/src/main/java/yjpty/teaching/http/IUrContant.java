package yjpty.teaching.http;

public class IUrContant {
    /**
     * 基础地址
     */
   public static String BASE_URL = "http://www.yuertong.com/yjpts/";// 内网地址
 /**
  * 获取我的班级列表 get
  */
 public static final String GET_MY_CLASS_LIST = BASE_URL + "app/class";
    /**
     * 获取全部课程 get
     */
    public static final String GET_ALL_SESSION = BASE_URL + "app/course?classId=";
    /**
     * 获取我的课程
     */
    public static final String GET_MY_SESSION = BASE_URL + "app/course/my_section?classId=";
    /**
     * 获取某个课程的里面课时数据 get
     */
    public static final String GET_ONE_SESSION = BASE_URL + "app/course/{courseId}/section?classId=";
    /**
     * 获取某个课时的数据 get
     */
    public static final String GET_ONE_COURSE = BASE_URL + "app/course/section/{sectionId}?classId=";
    /**
     * 获取基础数据 get
     */
    public static final String GET_CUSE_BASE = BASE_URL + "app/base";
    /**
     * 解除与绑定盒子
     */
    public static final String BANG_DING_HE_ZI_URL = BASE_URL + "app/class/{classId}/box";
}

