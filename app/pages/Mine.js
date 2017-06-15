'use strict';

import React, { Component } from 'react'
import {
  Text,
  View,
  Image,
  StyleSheet,
  ScrollView,
  Dimensions,
  Platform,
  AlertIOS,
  RefreshControl,
  NativeModules
} from 'react-native'
import LocalImg from '../images'
import NavBar from '../component/NavBar'
import Item from '../component/Item'
import px2dp from '../util'
import Icon from 'react-native-vector-icons/Ionicons'
import ImagePicker from 'react-native-image-picker'
import Touch from '../util/Touch'

var options = {
  title: '设置头像',
  storageOptions: {
    skipBackup: true,
    path: 'images'
  }
};

export default class Mine extends Component {
  constructor(props) {
    super(props)
    this.state = {
      userInfo: [],
      avatarSource: LocalImg.avatar
    }
  }

  componentDidMount() {
    this.fetchUserInfo().done()
  }

  async fetchUserInfo() {
    try {
      let response = await fetch('http://192.168.77.120:8080/app/user_info')
      let responseJson = await response.json()
      let userInfo = responseJson.data
      this.setState({ userInfo })
    } catch(error) {
      console.error(error);
    }
  }

  goUserInfo(){

  }

  rightPress(){

  }

  _uploadAvatar() {
    ImagePicker.showImagePicker(options, (response) => {
      console.log('Response = ', response);

      if (response.didCancel) {
        console.log('User cancelled image picker');
      }
      else if (response.error) {
        console.log('ImagePicker Error: ', response.error);
      }
      else if (response.customButton) {
        console.log('User tapped custom button: ', response.customButton);
      }
      else {
        let source = { uri: response.uri };

        // You can also display the image using data:
        // let source = { uri: 'data:image/jpeg;base64,' + response.data };

        this.setState({
          avatarSource: source
        });
      }
    });
  }

  _renderListItem(){
    return this.state.userInfo.map((item, i) => {
      if(i == 0 || i == 1){
        item.first = true
      }
      return (<Item key={i} {...item}/>)
    })
  }

  render(){
    return (
      <View>
        <NavBar
          title="我的"
          rightIcon="ios-settings-outline"
          rightPress={this.rightPress.bind(this)}
        />
        <Touch onPress={this.goUserInfo.bind(this)}>
          <View style={styles.userHead}>
            <View style={{flex: 1,flexDirection: "row"}}>
              <Touch onPress={this._uploadAvatar.bind(this)}>
                <Image source={this.state.avatarSource} style={{width: px2dp(64), height: px2dp(64), borderRadius: px2dp(30)}}/>
              </Touch>
              <View style={{flex: 1, marginLeft: 10, paddingVertical: 5}}>
                <Text style={{fontSize: px2dp(18)}}>玉博</Text>
                <View style={{marginTop: px2dp(10), flexDirection: "row"}}>
                  <Text style={{fontSize: 13, paddingLeft: 5}}>135****6884</Text>
                </View>
              </View>
            </View>
            <Icon name="ios-arrow-forward-outline" size={px2dp(22)} color="#bbb" />
          </View>
        </Touch>
        <View>
          {this._renderListItem()}
        </View>
      </View>
    )
  }
}

const styles = StyleSheet.create({
  scrollView: {
    marginBottom: px2dp(46),
    backgroundColor: "#0398ff"
  },
  userHead: {
    justifyContent: "space-between",
    alignItems: "center",
    flexDirection: "row",
    paddingHorizontal: 16,
    paddingVertical: 20,
    backgroundColor: "#ffffff"
  }
})
