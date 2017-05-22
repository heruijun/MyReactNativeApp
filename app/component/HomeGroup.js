'use strict';

import React, { Component, PropTypes } from 'react'
import {
  Text,
  View,
  Image,
  StyleSheet,
  Dimensions,
  Platform,
  TouchableHighlight,
  AlertIOS,
  TouchableNativeFeedback
} from 'react-native'
import px2dp from '../util'
import Button from './Button'
import Ionicons from 'react-native-vector-icons/Ionicons'
import FontAwesome from 'react-native-vector-icons/FontAwesome'

let {width, height} = Dimensions.get('window')
const itemHeight = px2dp(54)

const Font = {
  Ionicons,
  FontAwesome
}
class ItemButton extends Component {
  constructor(props){
    super(props)
  }
  render(){
    return (
      <Button onPress={this.props.onPress}>
        <View style={styles.button}>
          <Text style={{color: this.props.color || "#f00"}}>{this.props.name}</Text>
        </View>
      </Button>
    )
  }
}

export default class HomeGroup extends Component {
  constructor(props){
    super(props)
  }
  static propTypes = {
    name: PropTypes.string.isRequired,
    subName: PropTypes.string,
    color: PropTypes.string,
    first: PropTypes.bool,
    disable: PropTypes.bool,
    iconSize: PropTypes.number,
    font: PropTypes.string,
    onPress: PropTypes.func
  }
  _render(){
    let {iconSize, name, subName, color, first, disable, font} = this.props
    font = font||"Ionicons"
    const Icon = Font[font]
    return (
      <View style={styles.listItem}>
        <View style={[styles.listInfo, {borderTopWidth: !first?1:0}]}>
          <View style={{flex: 1, marginLeft: 16}}><Text style={{color: "#333", fontSize: 15}}>{name}</Text></View>
          <View style={styles.listInfoRight}>
            {subName?(<Text style={styles.subName}>{subName}</Text>):null}
            {disable?null:(<Font.Ionicons style={{marginLeft: 10}} name="ios-arrow-forward-outline" size={px2dp(18)} color="#bbb" />)}
          </View>
        </View>
      </View>
    )
  }
  render(){
    let { onPress, first, disable } = this.props
    onPress = onPress || (() => {})
    return disable?
      this._render():
      <Button onPress={onPress}>{this._render()}</Button>
  }
}
HomeGroup.Button = ItemButton
const styles = StyleSheet.create({
  listItem: {
    height: itemHeight,
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "center"
  },
  button:{
    height: itemHeight,
    backgroundColor: "#fff",
    justifyContent: "center",
    alignItems: "center"
  },
  subName: {
    color: "#333",
    paddingHorizontal: 10,
    paddingVertical: 5,
    borderRadius: 10,
    backgroundColor:"#f5f5f5",
    fontSize:15
  },
  listInfo: {
    height: itemHeight,
    flex: 1,
    paddingRight: 12,
    flexDirection: "row",
    alignItems: "center",
    justifyContent: "space-between",
    borderTopColor: "#eee"
  },
  listInfoRight: {
    flexDirection: "row",
    alignItems: "center"
  }
})
