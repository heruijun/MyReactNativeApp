import './launcher'

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
import NavBar from './app/component/NavBar'

import Home from './app/pages/Home'
import Room from './app/pages/Room'
import Manager from './app/pages/Manager'
import Mine from './app/pages/Mine'
import OperatingStatement from './app/pages/operatingStatement/OperatingStatement'
import RNComponent from './app/pages/RNComponent'

AppRegistry.registerComponent('rntest', () => Home)
AppRegistry.registerComponent('rn2test', () => Room)
AppRegistry.registerComponent('rn3test', () => Manager)
AppRegistry.registerComponent('rn4test', () => Mine)
AppRegistry.registerComponent('operatingStatement', () => OperatingStatement)
AppRegistry.registerComponent('rnComponent', () => RNComponent)
