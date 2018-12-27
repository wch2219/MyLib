package com.example.baselibs.utils;


import com.example.baselibs.BuildConfig;

public class Contents {

    public static final int Code_200 =  200	;//请求成功	接口成功执行会返回该状态码
    public static final int Code_201 = 201;//新增成功	新增数据成功会返回该状态码
    public static final int Code_400 =400;//请求失败	请求出现错误, 该错误一般是由于传递的参数错误导致的
    public static final int Code_401 =401;//	未授权	访问的接口需要授权, 即需要登录令牌 Token
    public static final int Code_403 =403;//	禁止访问	一般是由于 CORS 跨域认证失败导致的
    public static final int Code_404 =404;//数据未找到	即请求的数据不存在
    public static final int Code_405 =405;//不支持请求的方法	接口只支持 POST 请求
    public static final int Code_415 =415;//	不支持请求的媒体类型	POST 请求只支持 application/x-www-form-urlencoded
    public static final int Code_500 =500;//后端服务异常	后端服务存在 BUG
    public static final String ImageUrl = "imageurl";
    public static final String EndAtBegin = "EndAtBegin";
    public static final String PublishPicturesCount = "PublishPicturesCount";
    public static final String Deleted = "Deleted";
    public static final String CustomerId = "CustomerId";
    public static final String LoginId = "loginId";
    public static final String FranchiseeId = "FranchiseeId";
    public static final String Watermark = "Watermark";
    public static final String PictureHandler = "PictureHandler";
    public static final String SmallThum = "?imageView2/1/w/600/h/400";//小缩略图
    private static final boolean DEBUG = BuildConfig.DEBUG;
    public static final String APP_ID = "wxd3b76a4070d1e98d";  //release
//    public static final String APP_ID = "wxa453d50368c692ef"; //Debug
    public static final String AppSecret = "15431961474850e0dc9c8313e5ce9d1316021bfbe1bd2";
    //管理，加盟商，用户
    public static final String Manager = "Manager";
    public static final String Franchisee = "Franchisee";
    public static final String Customer = "Customer";
    public static final String Role = "Role";//用户类型
    public static final String Token = "Token";


    public static final String PhoneNo = "PhoneNo";

    public static final String Password = "Password";
    public static final String OldPassword = "OldPassword";
    public static final String NewPassword = "NewPassword";
    public static final String Fields = "Fields";
    public static final String PageIndex = "PageIndex";
    public static final String PageSize = "PageSize";
    public static final String BeginAtbegin = "BeginAtbegin";
    public static final String BeginAtEnd = "BeginAtEnd";
    public static final String EndAtEnd = "EndAtEnd";
    public static final String ActivityId = "ActivityId";
    public static final String NECaptchaValidate = "NECaptchaValidate";
    public static final String PassWord = "PassWord";
    public static final String Code = "Code";

    public static final String HasTitle = "HasTitle";
    public static final String Id = "Id";
    public static final String Title = "Title";
    public static final String DescriptionTitle = "DescriptionTitle";
    public static final String Description = "Description";
    public static final String CategoryId = "CategoryId";
    public static final String Name = "Name";
    public static final int IntentCode = 1001;
    public static final String Result = "result";
    public static final String Position = "position";
    public static final String ClassResult = "classResult";
    public static final float TopHeight = 20;//沉浸式顶部距离
    public static final String PrevId = "PrevId";//PrevId
    public static final String Avatar = "Avatar";
    public static final String Nickname = "Nickname";
    public static final String BuglyAppIdDebug = "eeba935b2e";
    public static final String BuglyAppIdRelease = "eeba935b2e";
    public static final String BuglyAppId = DEBUG?BuglyAppIdDebug:BuglyAppIdRelease;


}