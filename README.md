##Android接入RN的混编基础工程，可以直接导入，免去搭建过程中踩坑

##参考文章：http://www.jianshu.com/p/845cbddeb37a


1. $ npm init
2. $ npm install --save react
3. $ npm install --save react-native
4. $ curl -o .flowconfig https://raw.githubusercontent.com/facebook/react-native/master/.flowconfig
5. $ adb reverse tcp:8081 tcp:8081
6. $ 启动npm start
7. $ Dev Settings设置192.168.50.50:8081
8. $ npm run android-release 生成android热更新包


##常用命令
1. 查看RN版本号react-native --version
2. 版本更新参考https://facebook.github.io/react-native/docs/upgrading.html
3. 全局安装npm install -g react-native-git-upgrade
4. 安装最新的RN版本react-native-git-upgrade
