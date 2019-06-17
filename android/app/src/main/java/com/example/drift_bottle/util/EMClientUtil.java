package com.example.drift_bottle.util;

import android.app.Activity;
import android.util.Log;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import io.flutter.plugin.common.MethodChannel;

public class EMClientUtil {

   public static void register(String account,String password, Activity activity, MethodChannel.Result result){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(account,password);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            result.success("注册成功啦");
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            result.success("注册失败，账号已存在");
                        }
                    });
                }
            }
        }).start();

    }

    public static void login(String loginName,String password,Activity activity,MethodChannel.Result result){
       new Thread(new Runnable() {
           @Override
           public void run() {
                EMClient.getInstance().login(loginName, password, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                EMClient.getInstance().groupManager().loadAllGroups();
                                EMClient.getInstance().chatManager().loadAllConversations();
                                Log.d("main", "登录聊天服务器成功！");
                                System.out.println("===================成功");
                                result.success("登录成功");
                            }
                        });
                    }

                    @Override
                    public void onError(int code, String error) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("==============="+code+error);

                                switch (code) {
                                    // 网络异常 2
                                    case EMError.NETWORK_ERROR:
                                        result.success("IM 网络错误");
                                        break;
                                    // 无效的用户名 101
                                    case EMError.INVALID_USER_NAME:
                                        result.success("IM无效的用户名");
                                        break;
                                    // 无效的密码 102
                                    case EMError.INVALID_PASSWORD:
                                        result.success("IM无效的密码");
                                        break;
                                    // 用户认证失败，用户名或密码错误 202
                                    case EMError.USER_AUTHENTICATION_FAILED:
                                        result.success("IM用户认证失败，用户名或密码错误");
                                        break;
                                    // 用户不存在 204
                                    case EMError.USER_NOT_FOUND:
                                        result.success("IM用户不存在");
                                        break;
                                    // 无法访问到服务器 300
                                    case EMError.SERVER_NOT_REACHABLE:
                                        result.success("IM无法访问到服务器 ");
                                        break;
                                    // 等待服务器响应超时 301
                                    case EMError.SERVER_TIMEOUT:
                                        result.success("IM等待服务器响应超时 ");
                                        break;
                                    // 服务器繁忙 302
                                    case EMError.SERVER_BUSY:
                                        result.success("IM服务器繁忙");
                                        break;
                                    // 未知 Server 异常 303 一般断网会出现这个错误
                                    case EMError.SERVER_UNKNOWN_ERROR:
                                        result.success("IM未知的服务器异常");
                                        break;
                                    default:
                                        result.success("IM ml_sign_in_failed");
                                        break;
                                }
                            }
                        });
                    }

                    @Override
                    public void onProgress(int progress, String status) {
                         System.out.println("正在登录");
                    }
                });
           }
       }).start();
    }


    public static void autoLogin(Activity activity,MethodChannel.Result result){
       //判断是否登录
       if(EMClient.getInstance().isLoggedInBefore()){  //已经登陆

           //获取登录账号环信id
           EMClient.getInstance().getCurrentUser();

           //根据id查询账号信息保存全局

           //跳转主页
           result.success("ok");

       }else { //未登录过
           //跳转登录
           result.success("not_ok");
       }
    }
}
