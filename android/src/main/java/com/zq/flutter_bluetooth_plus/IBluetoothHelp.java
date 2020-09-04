package com.zq.flutter_bluetooth_plus;

import android.bluetooth.BluetoothDevice;

import java.util.List;
import java.util.Set;

/**
 * ================================================
 * 作    者：程志强
 * 邮    箱：zhiqiang8838@163.com
 * 版    本：1.0.0
 * 创建日期：2020/09/03  16:00
 * 包    名：com.zq.flutter_bluetooth_plus
 * 描    述：
 * 修订历史：
 * ================================================
 */
public interface IBluetoothHelp {
    /**
     * 打开蓝牙
     *
     * @return true 打开成功 false 打开失败
     */
    boolean open();

    /**
     * 关闭蓝牙
     *
     * @return true 关闭成功 false 关闭失败
     */
    boolean close();

    /**
     * 开始查找
     *
     * @return true 成功  false 失败
     */
    boolean startDiscovery();

    /**
     * 取消查找
     *
     * @return true 成功  false 失败
     */
    boolean cancelDiscovery();

    /**
     * 开始配对
     *
     * @param address 蓝牙地址
     * @return true 成功  false 失败
     */
    boolean createBond(String address);

    /**
     * 取消配对
     *
     * @param address 蓝牙地址
     * @return true 成功  false 失败
     */
    boolean removeBond(String address);

    /**
     * 获取已配对的蓝牙设备
     *
     * @return 已配对蓝牙设备
     */
    BluetoothDevice[] boundDevices();

    /**
     * 连接蓝牙
     *
     * @param address 蓝牙地址
     * @return true 连接成功 false连接失败
     */
    boolean connect(String address);

    /**
     * 断开连接
     *
     * @return true 断开连接成功 false断开连接失败
     */
    boolean disConnect();

    /**
     * 读数据
     *
     * @return 读取到的内容
     */
    String read();
    /**
     * 写数据
     *
     * @param data 写入的内容
     * @return true 成功 false 失败
     */
    boolean write(String data);
}
