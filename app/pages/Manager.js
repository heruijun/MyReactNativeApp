'use strict';

import React, { Component } from 'react'
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  Image,
  ScrollView,
  NativeModules,
  TouchableNativeFeedback
} from 'react-native'
import CodePush from "react-native-code-push"
import NavBar from '../component/NavBar'
import LocalImg from '../images'

const AUTH_BTNS = [
  {
    'auth': 'A0001',
    'name': '租约管理',
    'data': LocalImg.manager_icon1
  },
  {
    'auth': 'A0002',
    'name': '退房管理',
    'data': LocalImg.manager_icon2
  },
  {
    'auth': 'A0003',
    'name':'应收实收列表',
    'data': LocalImg.manager_icon3
  },
  {
    'auth': 'A0004',
    'name': '应收实收列表',
    'data': LocalImg.manager_icon4
  },
  {
    'auth': 'A0005',
    'name': '金融管理',
    'data': LocalImg.manager_icon5
  },
  {
    'auth': 'A0006',
    'name': '报修管理',
    'data': LocalImg.manager_icon6
  }
]

export default class Manager extends Component {
  constructor(props) {
    super(props)
    this.state = {
      authCodes: []
    }
  }

  componentDidMount() {
    CodePush.sync({
      installMode: CodePush.InstallMode.IMMEDIATE,
      updateDialog: true
    });
    NativeModules.AuthControlNativeModule.getAuthCodes('manager', (response) => {
      this.setState({
        authCodes: response
      })
    });
  }

  _renderItem(code) {
    if(code === AUTH_BTNS[0].auth && this.state.authCodes.includes(code)) {
      return (
        <TouchableNativeFeedback
          onPress={this._onPressButton}
          background={TouchableNativeFeedback.SelectableBackground()}>
          <View style={[styles.sbu_flex, styles.sbu_borderBottom, styles.sbu_borderRight]}>
            <View style={[styles.sub_con_flex]}>
              <Image style={[styles.sbu_icon_img]} source={{uri:AUTH_BTNS[0].data}}></Image>
            </View>
            <View style={[styles.sub_con_flex, styles.sub_text]}>
              <Text style={[styles.font16]}>{ AUTH_BTNS[0].name }</Text>
            </View>
          </View>
        </TouchableNativeFeedback>
      )
    } else if(code === AUTH_BTNS[1].auth && this.state.authCodes.includes(code)) {
      return (
        <TouchableNativeFeedback
          onPress={this._onPressButton}
          background={TouchableNativeFeedback.SelectableBackground()}>
          <View style={[styles.sbu_flex, styles.sbu_borderBottom, styles.sbu_borderRight]}>
            <View style={[styles.sub_con_flex]}>
              <Image style={[styles.sbu_icon_img]} source={{uri:AUTH_BTNS[1].data}}></Image>
            </View>
            <View style={[styles.sub_con_flex, styles.sub_text]}>
              <Text style={[styles.font16]}>{ AUTH_BTNS[1].name }</Text>
            </View>
          </View>
        </TouchableNativeFeedback>
      )
    } else if(code === AUTH_BTNS[2].auth && this.state.authCodes.includes(code)) {
      return (
        <TouchableNativeFeedback
          onPress={this._onPressButton}
          background={TouchableNativeFeedback.SelectableBackground()}>
          <View style={[styles.sbu_flex, styles.sbu_borderBottom]}>
            <View style={[styles.sub_con_flex]}>
              <Image style={[styles.sbu_icon_img]} source={{uri:AUTH_BTNS[2].data}}></Image>
            </View>
            <View style={[styles.sub_con_flex, styles.sub_text]}>
              <Text style={[styles.font16]}>{ AUTH_BTNS[2].name }</Text>
              <Text style={[styles.font16]}>(租期中)</Text>
            </View>
          </View>
        </TouchableNativeFeedback>
      )
    } else if(code === AUTH_BTNS[3].auth && this.state.authCodes.includes(code)) {
      return (
        <TouchableNativeFeedback
          onPress={this._onPressButton}
          background={TouchableNativeFeedback.SelectableBackground()}>
          <View style={[styles.sbu_flex, styles.sbu_borderBottom, styles.sbu_borderRight]}>
            <View style={[styles.sub_con_flex]}>
              <Image style={[styles.sbu_icon_img]} source={{uri:AUTH_BTNS[3].data}}></Image>
            </View>
            <View style={[styles.sub_con_flex, styles.sub_text]}>
              <Text style={[styles.font16]}>{ AUTH_BTNS[3].name }</Text>
              <Text style={[styles.font16]}>(已退房)</Text>
            </View>
          </View>
        </TouchableNativeFeedback>
      )
    } else if(code === AUTH_BTNS[4].auth && this.state.authCodes.includes(code)) {
      return (
        <TouchableNativeFeedback
          onPress={this._onPressButton}
          background={TouchableNativeFeedback.SelectableBackground()}>
          <View style={[styles.sbu_flex, styles.sbu_borderBottom, styles.sbu_borderRight]}>
            <View style={[styles.sub_con_flex]}>
              <Image style={[styles.sbu_icon_img]} source={{uri:AUTH_BTNS[4].data}}></Image>
            </View>
            <View style={[styles.sub_con_flex, styles.sub_text]}>
              <Text style={[styles.font16]}>{ AUTH_BTNS[4].name }</Text>
            </View>
          </View>
        </TouchableNativeFeedback>
      )
    } else if(code === AUTH_BTNS[5].auth && this.state.authCodes.includes(code)) {
      return (
        <TouchableNativeFeedback
          onPress={this._onPressButton}
          background={TouchableNativeFeedback.SelectableBackground()}>
          <View style={[styles.sbu_flex, styles.sbu_borderBottom]}>
            <View style={[styles.sub_con_flex]}>
              <Image style={[styles.sbu_icon_img]} source={{uri:AUTH_BTNS[5].data}}></Image>
            </View>
            <View style={[styles.sub_con_flex, styles.sub_text]}>
              <Text style={[styles.font16]}>{ AUTH_BTNS[5].name }</Text>
            </View>
          </View>
        </TouchableNativeFeedback>
      )
    }
  }

  _onPressButton() {
    // NativeModules.ToastNativeModule.callNativeMethod('你好');
    setTimeout(() => {
      NativeModules.IntentNativeModule.intent('com.example.activitytest.ACTION_START', (response) => {
        console.log(response)
      });
    }, 250)
  }

  render(){
    return (
      <View style={styles.container}>
        <NavBar title="管理" />
        <View style={[styles.sbu_view]}>
          { this._renderItem('A0001') }
          { this._renderItem('A0002') }
          { this._renderItem('A0003') }
        </View>
        <View style={[styles.sbu_view]}>
          { this._renderItem('A0004') }
          { this._renderItem('A0005') }
          { this._renderItem('A0006') }
        </View>
      </View>
    )
  }
}

var styles = StyleSheet.create({
  container:{
  	flex:1,
	},
	//slider
	wrapper: {
  	height:100,
	},
	slide: {
  	height:100,
  	resizeMode: Image.resizeMode.contain,
	},
	//sbu
	sbu_view:{
		height:110,
		flexDirection:'row'
	},
	sbu_flex:{
		flex:1,
	},
	sbu_borderRight:{
		borderColor:'#d9d9d9',
		borderRightWidth: 0.35,
	},
	sbu_icon_img:{
		height:32,
		width:32,
    marginTop:20,
		resizeMode:Image.resizeMode.contain,
	},
	sub_con_flex:{
		flex:1,
		justifyContent: 'center',
		alignItems: 'center',
	},
	sub_text:{
		justifyContent:'center',
	},
	font16:{
		fontSize:12,
		color:'#8e9299',
		fontWeight:'300',
	},
	sbu_borderBottom:{
		borderBottomWidth:0.35,
		borderBottomColor:'#d9d9d9',
	},
	img_view:{
		height:62,
		marginLeft:5,
		marginRight:5,
		flexDirection: 'row',
    marginBottom:10,
    backgroundColor:'#fff',
	},
	img_flex:{
		flex:1,
		borderWidth:1,
		borderColor:'#ccc',
	},
	img_wh: {
		height:59,
    borderRightWidth:0,
		resizeMode:Image.resizeMode.contain,
	}
});
