/// @date 2020/09/04  14:47
/// @author 程志强
/// @des 常量

class Constant {
  /// 插件名称
  static const String PLUGIN_NAME = "flutter_bluetooth_plus";

  /// 获取系统版本号
  static const String GET_PLATFORM_VERSION = "getPlatformVersion";

  /// 打开蓝牙
  static const String OPEN = "open";

  /// 关闭蓝牙
  static const String CLOSE = "close";

  /// 开始查找
  static const String START_DISCOVERY = "startDiscovery";

  /// 取消查找
  static const String CANCEL_DISCOVERY = "cancelDiscovery";

  /// 开始配对
  static const String CREATE_BOND = "createBond";

  /// 取消配对
  static const String REMOVE_BOND = "removeBond";

  /// 获取已配对的蓝牙设备
  static const String BOUND_DEVICES = "boundDevices";

  /// 连接蓝牙
  static const String CONNECT = "connect";

  /// 断开连接
  static const String DISCONNECT = "disConnect";

  /// 读数据
  static const String READ = "read";

  /// 写数据
  static const String WRITE = "write";
}
