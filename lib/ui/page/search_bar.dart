//搜索
import 'package:drift_bottle/ui/page/contacts_details_page.dart';
import 'package:flutter/material.dart';

class SearchBar extends SearchDelegate<String> {

  // 点击清楚的方法
  @override
  List<Widget> buildActions(BuildContext context) {
    return [
      IconButton(
        icon: Icon(Icons.clear),
        // 点击把文本空的内容清空
        onPressed: () {
          query = "";
        },
      )
    ];
  }

  // 点击箭头返回
  @override
  Widget buildLeading(BuildContext context) {
    return IconButton(
      icon: AnimatedIcon(
        // 使用动画效果返回
        icon: AnimatedIcons.menu_arrow, progress: transitionAnimation,
      ),
      // 点击的时候关闭页面（上下文）
      onPressed: () {
        close(context, null);
      },
    );
  }

  // 点击搜索出现结果
  @override
  Widget buildResults(BuildContext context) {
    if(query.isNotEmpty){
      return ContactsDetailsPage(emid: query);
    }
    return Text("");
  }


  @override
  Widget buildSuggestions(BuildContext context) {
    // 模拟的假数据
/*    const searchList = [
      "lao-老王",
      "lao-老张",
      "xiao-小王",
      "xiao-小张"
    ];

    const recentSuggest = [
      "马云-1",
      "马化腾-2"
    ];

    // 定义变量 并进行判断
    final suggestionList = query.isEmpty
        ? recentSuggest
        :searchList.where((input) => input.startsWith(query)).toList();*/
    return Container();/*ListView.builder(
        itemCount: suggestionList.length,
        itemBuilder: (context,index){
          return  ListTile(
              title: RichText(
                  text: TextSpan(
                    // 获取搜索框内输入的字符串，设置它的颜色并让让加粗
                      text: suggestionList[index].substring(0, query.length),
                      style: TextStyle(
                          color: Colors.black, fontWeight: FontWeight.bold),
                      children: [
                        TextSpan(
                          //获取剩下的字符串，并让它变成灰色
                            text: suggestionList[index].substring(query.length),
                            style: TextStyle(color: Colors.grey))
                      ]
                  )
              )
          );
        }
    );*/
  }

}
