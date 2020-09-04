package com.zq.flutter_bluetooth_plus;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;

/**
 * ================================================
 * 作    者：程志强
 * 邮    箱：zhiqiang8838@163.com
 * 版    本：1.0.0
 * 创建日期：2020/09/03  16:01
 * 包    名：com.zq.flutter_bluetooth_plus
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class BluetoothHelpImpl implements IBluetoothHelp {
    private static final String TAG = "===" + BluetoothHelpImpl.class.getSimpleName() + "::";
    /**
     * 蓝牙适配器
     */
    private final BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

    /**
     * 蓝牙模拟成串口的服务就使用了一个标准的UUID
     */
    private static final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    /**
     * socket对象
     */
    private BluetoothSocket mSocket;

    @Override
    public boolean open() {
        return adapter.enable();
    }

    @Override
    public boolean close() {
        return adapter.disable();
    }

    @Override
    public boolean startDiscovery() {
        return adapter.startDiscovery();
    }

    @Override
    public boolean cancelDiscovery() {
        return adapter.cancelDiscovery();
    }

    @Override
    public boolean createBond(String address) {
        try {
            BluetoothDevice device = getDevice(address);
            if (device == null) {
                return false;
            }
            return device.createBond();
        } catch (Exception e) {
            Log.e(TAG, "蓝牙【" + address + "】配对失败", e);
            return false;
        }
    }

    @Override
    public boolean removeBond(String address) {
        try {
            BluetoothDevice device = getDevice(address);
            if (device == null) {
                return false;
            }
            Method method = device.getClass().getMethod("removeBond");
            method.setAccessible(true);
            return (boolean) method.invoke(device);
        } catch (Exception e) {
            Log.e(TAG, "蓝牙【" + address + "】取消配对失败", e);
            return false;
        }
    }

    @Override
    public BluetoothDevice[] boundDevices() {
        Set<BluetoothDevice> devices = adapter.getBondedDevices();
        BluetoothDevice[] b = new BluetoothDevice[devices.size()];
        return devices.toArray(b);
    }


    @Override
    public boolean connect(String address) {
        BluetoothDevice device = getDevice(address);
        if (device == null) {
            return false;
        }
        try {
            mSocket = device.createRfcommSocketToServiceRecord(SPP_UUID);
            if (!mSocket.isConnected()) {
                mSocket.connect();
            }
            return true;
        } catch (IOException e) {
            Log.e(TAG, "蓝牙【" + address + "】连接失败", e);
            if (null != mSocket) {
                try {
                    mSocket.close();
                } catch (IOException ex) {
                    Log.e(TAG, "蓝牙【" + address + "】断开连接失败", ex);
                }
            }
            return false;
        }
    }

    @Override
    public boolean disConnect() {
        if (null == mSocket) {
            return false;
        }
        try {
            mSocket.close();
            return true;
        } catch (IOException ex) {
            Log.e(TAG, "蓝牙断开连接失败", ex);
            return false;
        }
    }

    @Override
    public String read() {
        if (null == mSocket) {
            Log.e(TAG, "socket为空");
            return "";
        }
        byte[] b = new byte[1024];
        try (InputStream is = mSocket.getInputStream()) {
            StringBuilder sb = new StringBuilder();
            int len;
            while ((len = is.read(b)) != -1) {
                sb.append(new String(b, 0, len));
            }
            return sb.toString();
        } catch (IOException e) {
            Log.e(TAG, "读数据失败!", e);
            return "";
        }
    }

    @Override
    public boolean write(String data) {
        if (null == mSocket) {
            Log.e(TAG, "socket为空！");
            return false;
        }
        if (data == null) {
            Log.e(TAG, "data为空！");
            return false;
        }
        try (OutputStream os = mSocket.getOutputStream()) {
            os.write(data.getBytes());
            os.flush();
            return true;
        } catch (IOException e) {
            Log.e(TAG, "写数据失败!", e);
            return false;
        }
    }


    /**
     * 获取蓝牙设备
     *
     * @param address 蓝牙地址
     * @return BluetoothDevice
     */
    private BluetoothDevice getDevice(String address) {
        if (!adapter.enable()) {
            Log.e(TAG, "蓝牙【" + address + "】未开启");
            return null;
        }
        BluetoothDevice device = adapter.getRemoteDevice(address);
        if (null == device) {
            Log.e(TAG, "蓝牙【" + address + "】对应的蓝牙设备不存在");
            return null;
        }
        return device;
    }
}
