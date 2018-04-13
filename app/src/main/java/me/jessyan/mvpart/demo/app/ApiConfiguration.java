package me.jessyan.mvpart.demo.app;

/**
 * Created by czw on 2017/11/30.
 */
public class ApiConfiguration {

    // 云端众联app 请求url验签key
    public static final String SECURITYKEY = "85950c3da48e217cfd38df167aaa2f78";

    public static final String WX_APPID = "wxa9e215ef4c2332bc"; //微信APPID

    public static final String WX_SECRET = "11a7669ee8d22704f88d6f073c10cb52"; //微信secret

    public static final String QQ_APPID = "1106706541"; //QQ APPID

    public static final String QQ_SECRET = "Fna43fPjKpqPRQy2"; //QQ secret

    // 编译运行模式
    public static final class RunnModel {
        //debug模式，会开启测试数据
        public static final boolean DEBUG = false;
    }

    public static final class Domain {
        /*域名 http://192.168.1.46:8888/interface/user/login/v1*/
        /*http://wx.woyes.com*/
        public static final String BASE_HOST = RunnModel.DEBUG ? "http://192.168.1.46:8888" : "http://wx.woyes.com"; //"http://192.168.1.46:8888" : "http://wx.woyes.com";

        public static final String V1 = "/v1"; //接口版本号

        public static final String BASE_URL = BASE_HOST + "/interface/";

        public static final String H5_URL = RunnModel.DEBUG ? "http://192.168.1.243/" : "http://rc.woyes.com/"; //"http://192.168.1.243/" : "http://rc.woyes.com/";

        public static final String PROJECTARRANGETALKLIST = BASE_URL + "project/projectArrangeTalkList" + V1;

        public static final String APPLYPROJECTOFFLINE = BASE_URL + "project/applyProjectOffline" + V1;

        public static final String SENDPROJECTBYUSER = BASE_URL + "project/sendProjectByUser" + V1;

        public static final String SENDMYADVISE = BASE_URL + "common/sendMyAdvise" + V1;

        public static final String MYARRANGETALKLIST = BASE_URL + "project/myArrangeTalkList" + V1;

        public static final String GETALLARRANGETALKRECORD = BASE_URL + "project/getAllArrangeTalkRecord" + V1;

        public static final String DELETEPROJECT = BASE_URL + "project/deleteProject" + V1;//项目删除

        public static final String MYPROJECTLIST = BASE_URL + "project/myProjectList" + V1;

        public static final String SEARCHALLINVESTDIRECTION = BASE_URL + "project/searchAllInvestDirection" + V1;

        public static final String QUERYPROGRESS = BASE_URL + "user/queryProgress" + V1;

        public static final String GETUSERINFO = BASE_URL + "user/getUserInfo" + V1;//获取用户资料

        public static final String LOGIN = BASE_URL + "user/login" + V1; //登录

        public static final String REGISTER = BASE_URL + "user/registerUser" + V1; //登录

        public static final String SENDCODE = BASE_URL + "common/getMsgCodeByType" + V1; //获取验证码 type: 0 注册 ，1修改登录密码，2修改支付密码 3 验证码登录 4实名认证 5绑定银行卡

        public static final String UPLOADFILE = BASE_URL + "common/uploadFiles" + V1; //文件上传接口

        public static final String CHECKCODE = BASE_URL + "common/checkMsgCodeById" + V1; //校验验证码

        public static final String CHECKUSEREXIST = BASE_URL + "user/checkUserExist" + V1; //查询用户是否存在

        public static final String LOGINBYCODE = BASE_URL + "user/registerOrLoginByCode" + V1; //用户验证码注册/登录接口

        public static final String JOBCERTIFICATION = BASE_URL + "user/jobCertification" + V1; //职位认证

        public static final String NAMECERTIFICATION = BASE_URL + "user/nameCertification" + V1; //实名认证

        public static final String ASSETCERTIFICATION = BASE_URL + "user/assetCertification" + V1; //资产认证

        public static final String UPDATEUSER = BASE_URL + "user/updateUser" + V1; //修改用户资料

        public static final String SETPASSWORD = BASE_URL + "user/setPassword" + V1; //修改/初始化登录密码

        public static final String APPVERSION = BASE_URL + "common/getAppVersion" + V1; //获取最新版本App

        public static final String PAYORDER = BASE_URL + "order/payOrder" + V1; //订单支付

        public static final String GETPROJECTLIST = BASE_URL + "project/getProjectList" + V1; //项目列表

        public static final String GETNEWSLIST = BASE_URL + "news/getNewsList" + V1; //云空间信息列表

        public static final String CHANGETOPSTATUS = BASE_URL + "news/changeTopStatus" + V1; //云空间修改置顶状态

        public static final String GETFILTERCONDITION = BASE_URL + "project/getAllSearchCondition" + V1; //项目列表

        public static final String GETPICTURELIST = BASE_URL + "common/getPictureList" + V1; //获取模块内容接口（轮播、广告）

        public static final String GETPROJECTICON = BASE_URL + "project/getProjectIconList" + V1; //首页专场

        public static final String GETPROJECTDETAIL = BASE_URL + "project/getProjectDetail" + V1; //项目详情

        public static final String UPDATEUSERADDRESS = BASE_URL + "user/updateUserAddress" + V1; //更新用户位置接口

        public static final String ARRANGETALK = BASE_URL + "project/arrangeTalkInit" + V1; //申请约谈

        public static final String GETNEARBYUSER = BASE_URL + "user/getNearbyUser" + V1; //获取附近的人接口

        public static final String SEARCHFIELD = BASE_URL + "project/searchAllTradeInfo" + V1; //首页专场

        public static final String GETACTIVITYLIST = BASE_URL + "activity/getActivityList/v1"; //首页专场

        public static final String GETMYACTIVITY = BASE_URL + "activity/queryMyActivity/v1"; //首页专场

        public static final String GETACTIVITYDETAIL = BASE_URL + "activity/getActivityDetail/v1"; //

        public static final String APPLYSPONSOR = BASE_URL + "activity/createActivitySponsors/v1"; //申请赞助

        public static final String ACTIVITYSIGNUP = BASE_URL + "activity/activitySignUp/v1"; //
        //   http://192.168.1.243/h5/view/activity/activeReviewUI?id=d4e25898-99ac-4063-849c-6eb869e99c6e
        public static final String ACTIVITYREVIEW = H5_URL + "h5/view/activity/activeReviewUI?terminalType=androidClient&terminalType=&id="; //活动回顾

        public static final String GETMEMBERLIST = BASE_URL + "order/getMemberList" + V1; //获取会员列表

        public static final String CREATEMEMBERORDER = BASE_URL + "order/createMemberOrder" + V1; //创建会员订单

        public static final String QUERYMYINVITER = BASE_URL + "user/queryMyInviter" + V1; //查询我的推荐人

        public static final String GETMYCONTACTS = BASE_URL + "user/getMyContacts" + V1; //获取会员列表信息

        public static final String ADDPROJECTSUMMARY = BASE_URL + "project/addProjectSummary" + V1;

        public static final String GETSENDMSGRECORDBYUSERID = BASE_URL + "common/getSendMsgRecordByUserId" + V1;

        public static final String UPDATEMSGREADSTATUS = BASE_URL + "common/updateMsgReadStatus" + V1;

        public static final String GETCARDINFO = "http://api.woyes.com/cardbin/getCardInfo";

        public static final String BINDCARD = BASE_URL + "user/toBindCard" + V1;

        public static final String BANKCARDLIST = BASE_URL + "user/bindCardList" + V1;

        public static final String UNBINDCARD = BASE_URL + "user/deleteBindCard" + V1;

        public static final String UPDATEPROJECTBYUSER = BASE_URL + "project/updateProjectByUser" + V1;

        public static final String QUERYUSERCOLLECTION = BASE_URL + "user/queryUserCollection" + V1; //我的收藏列表 收藏类型 0:项目 1:活动

        public static final String OPERATEUSERCOLLECTION = BASE_URL + "user/operateUserCollection/" + V1; //添加/取消收藏

        public static final String GETNOTICEMSGLIST = BASE_URL + "common/getNoticeMsgList" + V1; //获取俱乐部、首页公告信息列表

        public static final String GETUNREAD = BASE_URL + "common/getUnReadNum" + V1; //获取唯独列表

        public static final String GETHOTREADLIST = BASE_URL + "project/hotSearchList" + V1; //获取热门搜索

        public static final String REMOVEWECHAT = BASE_URL + "user/removeWechat" + V1; //解除微信端绑定

        public static final String QUERYSHARECONTENT = BASE_URL + "common/queryShareContent" + V1; //解除微信端绑定

        public static final String CREATEMEMBERDIFFERENCEORDER = BASE_URL + "order/createMemberDifferenceOrder" + V1; //创建vip升级订单

        public static final String GETMEMBERDIFFERENCEPRICE = BASE_URL + "order/getMemberDifferencePrice" + V1; //查询vip升级订单信息
    }


    // 请求的方法名称方法
//    public static final class Method {
//        //--------------------------首页 Start------------------------
//        public static final String INDEX_MENU = "getPageByType";//  获取页面布局
//
//    }

}
