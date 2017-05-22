'use strict';

import React, { Component } from 'react'
import {
  Text,
  Dimensions,
  StyleSheet,
  Animated,
  View,
  Image
} from 'react-native'
import NavBar from '../component/NavBar'
import ActionButton from '../component/ActionButton/ActionButton'
import Icon from 'react-native-vector-icons/Ionicons'
import CodePush from "react-native-code-push"
import Swiper from 'react-native-swiper'
import Grid from 'react-native-grid-component'

const colors = [
  '#F44336', '#E91E63', '#9C27B0', '#673AB7', '#3F51B5',
  '#2196F3', '#03A9F4', '#00BCD4', '#009688', '#4CAF50',
  '#8BC34A', '#CDDC39', '#FFEB3B', '#FFC107', '#FF9800',
  '#FF5722', '#795548', '#9E9E9E', '#607D8B',
];

function generateRandomColorsArray(length) {
  return Array.from(Array(length)).map(() => colors[Math.floor(Math.random() * colors.length)]);
}

export default class Room extends Component {
  constructor(props) {
    super(props)
    this.state = {
      data: generateRandomColorsArray(21),
    }
  }

  componentDidMount() {
    CodePush.sync({
      installMode: CodePush.InstallMode.IMMEDIATE,
      updateDialog: true
    });
  }

  _renderItem = (data, i) => <View style={[{backgroundColor: data}, styles.item]} key={i}/>

  render(){

    return (
      <View style={{flex:1}}>
        <NavBar title="分散式公寓" />
        {/*<View style={{height: 200, backgroundColor:'red'}}>
          <Swiper style={styles.wrapper} showsButtons={false}>
            <View style={styles.slide1}>
              <Text style={styles.text}>第一屏</Text>
            </View>
            <View style={styles.slide2}>
              <Text style={styles.text}>第二屏</Text>
            </View>
            <View style={styles.slide3}>
              <Text style={styles.text}>第三屏</Text>
            </View>
          </Swiper>
        </View>*/}
        <View style={{flex:1}}>
          <Grid
            style={styles.list}
            renderItem={this._renderItem}
            data={this.state.data}
            itemsPerRow={3}
            itemHasChanged={(d1, d2) => d1 !== d2}
            onEndReached={() =>
              this.setState({ data: [...this.state.data, ...generateRandomColorsArray(21)] })}
          />
        </View>
        {/*<ActionButton buttonColor="rgba(231,76,60,1)" position="right">
          <ActionButton.Item buttonColor='#9b59b6' title="分散式公寓" onPress={() => console.log("notes tapped!")}>
            <Icon name="md-create" style={styles.actionButtonIcon} />
          </ActionButton.Item>
          <ActionButton.Item buttonColor='#1abc9c' title="集中式公寓" onPress={() => {}}>
            <Icon name="md-done-all" style={styles.actionButtonIcon} />
          </ActionButton.Item>
        </ActionButton>*/}
      </View>
    )
  }
}

const styles = StyleSheet.create({
  actionButtonIcon: {
    fontSize: 20,
    height: 22,
    color: 'white',
  },
  slide1: {
    height: 200,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#000000',
  },
  slide2: {
    height: 200,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#000000',
  },
  slide3: {
    height: 200,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#000000',
  },
  text: {
    color: '#fff',
    fontSize: 30,
  },
  item: {
    flex: 1,
    height: 120,
    margin: 1
  },
  list: {
    flex: 1
  },
})
