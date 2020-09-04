package com.zq.flutter_bluetooth_plus;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * 蓝牙插件
 *
 * @author zhiqi
 * @date 2020/09/03  16:09
 */
public class FlutterBluetoothPlusPlugin implements FlutterPlugin, MethodCallHandler {

    /**
     * Flutter and native Android 通信桥梁
     */
    private MethodChannel channel;

    private IBluetoothHelp iBluetoothHelp;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), Constant.PLUGIN_NAME);
        channel.setMethodCallHandler(this);
        iBluetoothHelp = new BluetoothHelpImpl();
    }

    // This static function is optional and equivalent to onAttachedToEngine. It supports the old
    // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
    // plugin registration via this function while apps migrate to use the new Android APIs
    // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
    //
    // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
    // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
    // depending on the user's project. onAttachedToEngine or registerWith must both be defined
    // in the same class.
    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), Constant.PLUGIN_NAME);
        channel.setMethodCallHandler(new FlutterBluetoothPlusPlugin());
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        Object arguments = call.arguments;
        switch (call.method) {
            case Constant.GET_PLATFORM_VERSION:
                result.success("Android " + android.os.Build.VERSION.RELEASE);
                break;
            case Constant.OPEN:
                result.success(iBluetoothHelp.open());
                break;
            case Constant.CLOSE:
                result.success(iBluetoothHelp.close());
                break;
            case Constant.START_DISCOVERY:
                result.success(iBluetoothHelp.startDiscovery());
                break;
            case Constant.CANCEL_DISCOVERY:
                result.success(iBluetoothHelp.cancelDiscovery());
                break;
            case Constant.CREATE_BOND:
                if (null == arguments) {
                    result.error("1", "蓝牙地址不能为空", null);
                    return;
                }
                result.success(iBluetoothHelp.createBond(arguments.toString()));
                break;
            case Constant.REMOVE_BOND:
                if (null == arguments) {
                    result.error("1", "蓝牙地址不能为空", null);
                    return;
                }
                result.success(iBluetoothHelp.removeBond(arguments.toString()));
                break;
            case Constant.BOUND_DEVICES:
                result.success(iBluetoothHelp.boundDevices());
                break;
            case Constant.CONNECT:
                if (null == arguments) {
                    result.error("1", "蓝牙地址不能为空", null);
                    return;
                }
                result.success(iBluetoothHelp.connect(arguments.toString()));
                break;
            case Constant.DISCONNECT:
                result.success(iBluetoothHelp.disConnect());
                break;
            case Constant.READ:
                result.success(iBluetoothHelp.read());
                break;
            case Constant.WRITE:
                if (null == arguments) {
                    result.error("1", "蓝牙地址不能为空", null);
                    return;
                }
                result.success(iBluetoothHelp.write(arguments.toString()));
                break;

            default:
                result.notImplemented();
                break;
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }
}
