'use strict';

import React, { Component } from 'react'
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
import CodePush from "react-native-code-push"
import px2dp from '../util'

export default class RNComponent extends Component {
  constructor(props) {
    super(props)
  }

  componentDidMount() {
    CodePush.sync({
      installMode: CodePush.InstallMode.IMMEDIATE,
      updateDialog: true
    });
  }

  render(){
    return (
      <View>
        <NavBar title="RN组件" />
        <ScrollView
          showsHorizontalScrollIndicator={false}>
        </ScrollView>
      </View>
    )
  }
}

const styles = StyleSheet.create({

})
