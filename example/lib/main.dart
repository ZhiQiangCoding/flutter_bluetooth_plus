import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter_bluetooth_plus/flutter_bluetooth_plus.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      platformVersion = await FlutterBluetoothPlus.getPlatformVersion();
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
            children: [
              Text('Running on: $_platformVersion\n'),
              RaisedButton(
                child: Text("打开蓝牙"),
                onPressed: () async {
                  await FlutterBluetoothPlus.open();
                },
              ),
              RaisedButton(
                child: Text("关闭蓝牙"),
                onPressed: () async {
                  await FlutterBluetoothPlus.close();
                },
              ),
              RaisedButton(
                child: Text("配对蓝牙数据"),
                onPressed: () async {
                  await FlutterBluetoothPlus.getBoundDevices()
                      .then((value) => print("==" + value.toString()));
                },
              ),
            ],
          ),
        ),
      ),
    );
  }
}
