package com.example.drift_bottle.listener;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.util.NetUtils;
import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.EventChannel;

public class MyConnectionListener implements EMConnectionListener {

    private FlutterActivity Activity;
    private EventChannel.EventSink Sink;

    public MyConnectionListener(FlutterActivity activity, EventChannel.EventSink sink){
        Activity = activity;
        Sink = sink;
    }

    @Override
    public void onConnected() {

    }

    @Override
    public void onDisconnected(final int error) {

        Activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if(error == EMError.USER_REMOVED){
                    Sink.success("user_removed");
                    // 显示帐号已经被移除
                }else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                    //显示帐号在其他设备登录
                    Sink.success("user_login_another_device");
                } else {
                    if (NetUtils.hasNetwork(Activity)){
                       Sink.success("disconnected_to_service");
                        //连接不到聊天服务器
                    }
                    else{
                        System.out.println("no_net");
                        Sink.success("no_net");
                        //当前网络不可用，请检查网络设置
                    }

                }
            }
        });
    }
}
