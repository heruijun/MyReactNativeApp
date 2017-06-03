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
  RefreshControl,
  TouchableWithoutFeedback,
  Image
} from 'react-native'
import NavBar from '../../component/NavBar'
import ScrollableTabView, {DefaultTabBar, } from 'react-native-scrollable-tab-view'
import CodePush from "react-native-code-push"
import px2dp from '../../util'

class Row extends React.Component {
  _onClick = () => {
    this.props.onClick(this.props.data);
  }

  render() {
    return (
     <TouchableWithoutFeedback onPress={this._onClick} >
        <View style={styles.row}>
          <Text style={styles.text}>
            {this.props.data.text + ' (' + this.props.data.clicks + ' clicks)'}
          </Text>
        </View>
      </TouchableWithoutFeedback>
    );
  }
}

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

  static title = '<RefreshControl>';
  static description = 'Adds pull-to-refresh support to a scrollview.';

  state = {
    isRefreshing: false,
    loaded: 0,
    rowData: Array.from(new Array(20)).map(
      (val, i) => ({text: 'Initial row ' + i, clicks: 0})),
  }

  _onClick = (row) => {
    row.clicks++;
    this.setState({
      rowData: this.state.rowData,
    });
  }

  _onRefresh = () => {
    this.setState({isRefreshing: true});
    setTimeout(() => {
      // prepend 10 items
      const rowData = Array.from(new Array(10))
      .map((val, i) => ({
        text: 'Loaded row ' + (+this.state.loaded + i),
        clicks: 0,
      }))
      .concat(this.state.rowData);

      this.setState({
        loaded: this.state.loaded + 10,
        isRefreshing: false,
        rowData: rowData,
      });
    }, 2000);
  }

  _onChangeTab(page) {
    // Alert.alert('' + page.i)
  }

  render(){
    const rows = this.state.rowData.map((row, ii) => {
      return <Row key={ii} data={row} onClick={this._onClick}/>;
    })

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

            <ScrollView tabLabel='应收日期'
              style={styles.scrollview}
              refreshControl={
                <RefreshControl
                  refreshing={this.state.isRefreshing}
                  onRefresh={this._onRefresh}
                  tintColor="#ff0000"
                  title="Loading..."
                  titleColor="#00ff00"
                  colors={['#ff0000', '#00ff00', '#0000ff']}
                />
              }>
              {rows}
            </ScrollView>
          <Text tabLabel='房源应收'>2</Text>
          <Text tabLabel='实收日期'>3</Text>
          <Text tabLabel='房源实收'>4</Text>
        </ScrollableTabView>
      </View>
    )
  }
}

const styles = StyleSheet.create({
  row: {
    borderColor: 'grey',
    borderWidth: 1,
    padding: 20,
    backgroundColor: '#3a5795',
    margin: 5,
  },
  text: {
    alignSelf: 'center',
    color: '#fff',
  },
  scrollview: {
    flex: 1,
  },
})
