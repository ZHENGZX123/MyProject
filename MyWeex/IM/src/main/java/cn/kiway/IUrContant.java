package cn.kiway;

public class IUrContant {
    /**
     * 比吧webview地址
     */
    public static final String BEEBA_WB_URL = "http://static.beeba.cn/thirdapp/contents/static/app/index" +
            ".html?appid=e9a64ef49283dd53&platformKey=kiway&requestToken=";
    /**
     * 基础地址
     */
   public static String BASE_URL = "http://www.yuertong.com/yjpts/";// 内网地址
   //public static String BASE_URL = "http://192.168.8.206:8180/yjpt/";// 内网地址
    /**
     * Beeba产生一个token
     */
    public static final String BEEBA_CREATE_TOKEN_URL = BASE_URL + "V1/Oauth/createToken";
    /**
     * 登录地址
     */
    public static final String LOGIN_URL = BASE_URL + "app/imLogin";
    /**
     * 注册地址
     */
    public static final String REGIGER_URL = BASE_URL + "app/regist";
    /**
     * 获取我的用户信息 get  修改 我的用户信息 post
     */
    public static final String GET_MY_INFO_URL = BASE_URL + "app/user";
    /**
     * 上传文件 post
     */
    public static final String UPLOAD_FILE_URL = BASE_URL + "course/file";
    /**
     * 获取学校班级列表 get
     */
    public static final String GET_CLASS_LIST_URL = BASE_URL + "app/school/{schoolId}/class";
    /**
     * 获取区域所有学校 get
     */
    public static final String GET_SCHOOL_LIST_URL = BASE_URL + "app/school";
    /**
     * 加入班级 post
     */
    public static final String JOINS_CLASS_URL = BASE_URL + "app/class/bind/";
    /**
     * 获取基础数据 get
     */
    public static final String GET_CUSE_BASE = BASE_URL + "app/base";
    /**
     * 获取我的班级列表 get
     */
    public static final String GET_MY_CLASS_LIST = BASE_URL + "app/class";
    /**
     * 获取班级学生与老师列表信息（get）与添加学生（post）
     */
    public static final String CALSS_URL = BASE_URL + "app/class/{classId}/student";
    /**
     * 解除与绑定盒子
     */
    public static final String BANG_DING_HE_ZI_URL = BASE_URL + "app/class/{classId}/box";
    /**
     * 退出班级
     */
    public static final String EXIT_CLASS_URL = BASE_URL + "app/class/unbind/";
    /**
     * 获取班级信息 get
     */
    public static final String GET_CLASS_INFO = BASE_URL + "app/class/{classId}/detail";

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
     * 创建亲子圈 post
     */
    public static final String CREATE_QINZIQUAN_URL = BASE_URL + "app/class/moments";
    /**
     * 删除亲自圈 detele
     */
    public static final String DELETE_CLASS_RING_URL = BASE_URL + "app/moments/{postId}";
    /**
     * 赞亲子圈 post
     */
    public static final String PRASE_CLASS_RING_URL = BASE_URL + "app/moments/{postId}/praise";
    /**
     * 获取亲子圈 get
     */
    public static final String SREACH_CLASS_RING_URL = BASE_URL + "app/class/{classId}/moments?minDate=";
    /**
     * 评论亲子圈 post
     */
    public static final String COMMENT_CLASS_RING_URL = BASE_URL + "app/moments/{postId}/reply";
    /**
     * 手机验证码 get
     */
    public static final String VALIDATE_URL = BASE_URL + "app/sms?phone=";
    /**
     * 获取宝贝详情 get
     */
    public static final String GET_MY_BABY_URL = BASE_URL + "app/student/";
    /**
     * 删除孩子 delete
     */
    public static final String DELETE_BABY_URL = BASE_URL + "app/class/{classId}/student/{studentId}";

    /**
     * 发送邮件 get
     */
    public static final String SENG_EMAIL_URL = BASE_URL + "app/mail?receiver={emial}&classId=";
    /**
     * 修改密码
     */
    public static final String CHANGE_PASSWORD_URL = BASE_URL + "app/user/password";
    /**
     * 服务协议
     */
    public static final String SERIVCE_AGREE_URL = BASE_URL + "static/app/serviceAgreement.html";
    /**
     * 检查更新
     */
    public static final String CHECK_VERSION_URL = BASE_URL + "app/version";
    /**
     * 下载地址
     */
    public static final String DOWNLOAD_APK_URL = BASE_URL + "static/app/Yjpty.apk";
    /**
     * 修改宝贝名字 put
     */
    public static final String EDIT_BABY_URL = BASE_URL + "app/student/";
    /**
     * 删除家长
     */
    public static final String DELTET_PARENT_URL = BASE_URL + "";
    /***
     * 网页登录
     */
    public static final String SCAN_URL = BASE_URL + "";
    /**
     * 预览新的内容
     */
    public static final String LETTER = BASE_URL + "#/letter/";
    /**
     * 退出登录
     */
    public static final String LOGOUT_URL = BASE_URL + "app/logout";
}

