import 'dart:async';

import 'package:flutter/services.dart';
import 'constant.dart';

class FlutterBluetoothPlus {
  static const MethodChannel _channel =
      const MethodChannel(Constant.PLUGIN_NAME);

  // 获取应用的版本号
  static Future<String> getPlatformVersion() async {
    return _channel.invokeMethod(Constant.GET_PLATFORM_VERSION);
  }

  // 打开蓝牙
  static Future<bool> open() async {
    return await _channel.invokeMethod(Constant.OPEN);
  }

  // 关闭蓝牙
  static Future<bool> close() async {
    return await _channel.invokeMethod(Constant.CLOSE);
  }

  // 开始查找
  static Future<bool> startDiscovery() async {
    return _channel.invokeMethod(Constant.START_DISCOVERY);
  }

  // 取消查找
  static Future<bool> cancelDiscovery() async {
    return await _channel.invokeMethod(Constant.CANCEL_DISCOVERY);
  }

  // 开始配对
  static Future<bool> createBond(String address) async {
    return await _channel.invokeMethod(Constant.CREATE_BOND, address);
  }

  // 取消配对
  static Future<bool> removeBond(String address) async {
    return await _channel.invokeMethod(Constant.REMOVE_BOND, address);
  }

  // 获取已配对的蓝牙设备
  static Future<List<dynamic>> getBoundDevices() async {
    return await _channel.invokeListMethod(Constant.BOUND_DEVICES);
  }

  // 连接蓝牙
  static Future<bool> connect(String address) async {
    return await _channel.invokeMethod(Constant.CONNECT);
  }

  // 断开连接
  static Future<bool> disConnect() async {
    return await _channel.invokeMethod(Constant.DISCONNECT);
  }

  // 读数据
  static Future<String> read() async {
    return await _channel.invokeMethod(Constant.READ);
  }

  // 写数据
  static Future<bool> write(String data) async {
    return await _channel.invokeMethod(Constant.WRITE, data);
  }
}
