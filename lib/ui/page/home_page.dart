import 'dart:math';

import 'package:drift_bottle/custom_widget/custom_drawer.dart';
import 'package:drift_bottle/ui/page/driftbottle_page.dart';
import 'package:drift_bottle/ui/page/login_page.dart';
import 'package:drift_bottle/utils/channel_utils.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class HomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  static const EventChannel eventChannel = const EventChannel('eventChannel');
  final _scaffoldKey = GlobalKey<ScaffoldState>();
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    eventChannel.receiveBroadcastStream().listen(onData);
  }

  //Stream监听回调
  void onData(event) {
    _connectionAlertDialog(event);
  }

  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
        length: 3,
        child: Scaffold(
          key: _scaffoldKey,
          appBar: AppBar(
            actions: <Widget>[
              IconButton(
                icon: Icon(Icons.search),
                onPressed: () {},
              )
            ],
            bottom: TabBar(
              unselectedLabelColor: Colors.black38,
              indicatorColor: Colors.black54,
              tabs: <Widget>[
                Tab(text: '漂流瓶', icon: Icon(Icons.opacity)),
                Tab(text: '好友', icon: Icon(Icons.account_circle)),
                Tab(text: '消息', icon: Icon(Icons.message))
              ],
            ),
          ),
          body: TabBarView(
            children: <Widget>[
              DriftBottlePage(),
              DriftBottlePage(),
              DriftBottlePage(),
            ],
          ),
          drawer: CustomDrawer(),
        ));
  }

  //Stream 对话框
   _connectionAlertDialog(content){
    return showDialog(
        context: context,
        barrierDismissible: false,
        builder: (BuildContext context) {
          return CupertinoAlertDialog(
            title: Text('警告'),
            content: Text(content),
            actions: <Widget>[
              CupertinoButton(
                  onPressed: () {
                    //注销登录
                    ChannelUtils.loginOut();
                    //返回登录界面
                    Navigator.of(context).pushAndRemoveUntil(
                        MaterialPageRoute(
                            builder: (BuildContext context) => LoginPage()),
                        (Route<dynamic> route) => false);
                  },
                  child: Text('确认')),
            ],
          );
        });
  }
}