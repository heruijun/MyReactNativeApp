'use strict';

import React, { Component } from 'react'
import {
  Text,
  Dimensions,
  ScrollView,
  StyleSheet,
  Animated,
  View,
  Alert,
  Image
} from 'react-native'
import NavBar from '../component/NavBar'
import ScrollableTabView, {DefaultTabBar, } from 'react-native-scrollable-tab-view'
import CodePush from "react-native-code-push"
import px2dp from '../util'

export default class OperatingStatement extends Component {
  constructor(props) {
    super(props)
  }

  componentDidMount() {
    CodePush.sync({
      installMode: CodePush.InstallMode.IMMEDIATE,
      updateDialog: true
    });
  }

  _onChangeTab(page) {
    // Alert.alert('' + page.i)
  }

  render(){
    return (
      <View style={{flex:1}}>
        <NavBar title="营业报表" />
        <ScrollableTabView
          onChangeTab={this._onChangeTab.bind(this)}
          tabBarUnderlineStyle={{backgroundColor: "#0398ff",height: 3}}
          renderTabBar={() => <DefaultTabBar
            textStyle={{marginTop:5, fontWeight:"normal", fontSize: 15}}
            activeTextColor="#999999"
            fontWeight="normal"
            inactiveTextColor="#999999"/>}>
          <View tabLabel='应收日期'>
            <Text>aaa</Text>
          </View>
          <Text tabLabel='房源应收'>2</Text>
          <Text tabLabel='实收日期'>3</Text>
          <Text tabLabel='房源实收'>4</Text>
        </ScrollableTabView>
      </View>
    )
  }
}

const styles = StyleSheet.create({

})
