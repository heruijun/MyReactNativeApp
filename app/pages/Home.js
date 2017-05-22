'use strict';

import React, { Component } from 'react'
import Icon from 'react-native-vector-icons/Ionicons';
import {
  Text,
  Dimensions,
  ScrollView,
  StyleSheet,
  Animated,
  View,
  Image
} from 'react-native'
import NavBar from '../component/NavBar'
import HomeGroup from '../component/HomeGroup'
import CodePush from "react-native-code-push"
import px2dp from '../util'

export default class HomePage extends Component {
  constructor(props) {
    super(props)
    this.groups = [
      [
        {name:"蘑菇分啊", subName:"173"},
      ],
      [
        {name:"账户余额", subName:"99笔：237803.58元"},
        {name:"水电煤欠款", subName:"455笔：305060.87元"},
        {name:"待收款(未来3天内)", subName:"1004.00元"},
        {name:"退房未结账", subName:"16个"},
      ],
      [
        {name:"待审核租约", subName:"2个"},
        {name:"要退房租约", subName:"2个"},
        {name:"需要租客登记", subName:"2个"},
      ],
      [
        {name:"空置房间(分散式)", subName:"6间：入住率73%"},
      ],
      [
        {name:"蘑菇宝(聚有财)需要买回", subName:"2笔"},
        {name:"蘑菇宝(拉卡拉)需要买回", subName:"5笔"},
        {name:"蘑菇宝(聚有财)租客还款逾期", subName:"8笔"},
        {name:"蘑菇宝(拉卡拉)租客还款逾期", subName:"3笔"},
      ]
    ]
  }

  componentDidMount() {
    CodePush.sync({
      installMode: CodePush.InstallMode.IMMEDIATE,
      updateDialog: true
    });
  }

  _renderHeader(){
    return (
      <View style={{paddingBottom: 25}}>
        <View style={{paddingBottom: 25, paddingLeft: 25}}>
          <View>
            <Text style={{fontSize: 16, color: "#fff"}}>{"累计欠款(元)"}</Text>
            <Text style={{fontSize: 30, color: "#fff", marginTop: 4}}>{"215212"}</Text>
          </View>
        </View>
        <View style={{flexDirection: "row"}}>
          <View style={[styles.count, {paddingRight: 16}]}>
            <View>
              <Text style={{fontSize: 16, color: "#fff"}}>{"昨日实收(元)"}</Text>
              <Text style={{fontSize: 22, color: "#fff", marginTop: 4}}>{"1200.6"}</Text>
            </View>
          </View>
          <View style={[styles.count, {borderLeftColor: "#f5f5f5", borderLeftWidth: 0.3, paddingLeft: 16}]}>
            <View>
              <Text style={{fontSize: 16, color: "#fff"}}>{"今日实收(元)"}</Text>
              <Text style={{fontSize: 22, color: "#fff", marginTop: 4}}>{"89100.5"}</Text>
            </View>
          </View>
        </View>
      </View>
    )
  }

  _renderHomeGroup(group){
    return group.map((item, i) => {
      if(i == 0){
        item.first = true
      }
      return (<HomeGroup key={i} {...item}/>)
    })
  }

  _renderHomeGroups(){
    return (
      <View style={{marginTop: -35}}>
        {this.groups.map((item, i) => {
          return (
            <View key={i} style={styles.homeGroup}>
              {this._renderHomeGroup(item)}
            </View>
          )
        })}
      </View>
    )
  }

  render(){
    return (
      <View>
        <NavBar title="工作台" />
        <ScrollView
          showsHorizontalScrollIndicator={false}>
          <View style={{marginBottom: px2dp(100)}}>
            <View style={styles.header}>
              {this._renderHeader()}
            </View>
            {this._renderHomeGroups()}
          </View>
        </ScrollView>
      </View>
    )
  }
}

const styles = StyleSheet.create({
  count: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    flexDirection: "row"
  },

  header: {
    backgroundColor: "#0398ff",
    paddingHorizontal: 16,
    paddingVertical: 16
  },

  homeGroup: {
    borderColor: '#ccc',
    borderWidth: 0.5,
    borderRadius: 6,
    backgroundColor: '#fff',
    marginTop: 8,
    marginLeft: 16,
    marginRight: 16
  }
})
