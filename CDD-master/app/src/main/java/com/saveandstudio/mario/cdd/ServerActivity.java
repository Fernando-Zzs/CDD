package com.saveandstudio.mario.cdd;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.saveandstudio.mario.cdd.connect.AcceptThread;
import com.saveandstudio.mario.cdd.connect.ConnectThread;
import com.saveandstudio.mario.cdd.connect.Constant;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ServerActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 0;
    private List<BluetoothDevice> mDeviceList = new ArrayList<>();
    private List<BluetoothDevice> mBondedDeviceList = new ArrayList<>();

    private BlueToothController mController = new BlueToothController();
    private Handler mUIHandler = new MyHandler();

    private ListView mListView;
    private DeviceAdapter mAdapter;
    private Toast mToast;

    private AcceptThread mAcceptThread;
    private ConnectThread mConnectThread;

    private Button mBtn_listening;
    private Button mBtn_hello;
    private Button mBtn_start_game;
    private TextView mTv_wait_for_client;

    private boolean connected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        initUI();
        registerBluetoothReceiver();
        mController.turnOnBlueTooth(this, REQUEST_CODE);
        mBtn_listening = findViewById(R.id.btn_listening);
        mBtn_hello = findViewById(R.id.btn_hello);
        mBtn_start_game = findViewById(R.id.btn_start_game);
        mTv_wait_for_client = findViewById(R.id.tv_wait_for_client);

        setOnClickListener();
    }

    public void setOnClickListener() {
        OnClick onClick = new OnClick();
        mBtn_listening.setOnClickListener(onClick);
        mBtn_hello.setOnClickListener(onClick);
        mBtn_start_game.setOnClickListener(onClick);
    }

    private class OnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_listening:
                    if (mAcceptThread != null) {
                        mAcceptThread.cancel();
                    }
                    mAcceptThread = new AcceptThread(mController.getAdapter(), mUIHandler);
                    mAcceptThread.start();
                    break;
                case R.id.btn_hello:
                    say("Hello");
                    break;
                case R.id.btn_start_game:
                    if (connected) {
                        Intent game = new Intent(ServerActivity.this, GameActivity.class);
                        startActivity(game);
                        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                        say("start");
                    } else {
                        showToast("请与客户端连接后再开始游戏");
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void registerBluetoothReceiver() {
        IntentFilter filter = new IntentFilter();
        //开始查找
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        //结束查找
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        //查找设备
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        //设备扫描模式改变
        filter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        //绑定状态
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);

        registerReceiver(receiver, filter);
    }

    //注册广播监听搜索结果
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                //setProgressBarIndeterminateVisibility(true);
                //初始化数据列表
                mDeviceList.clear();
                mAdapter.notifyDataSetChanged();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //setProgressBarIndeterminateVisibility(false);
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //找到一个添加一个
                mDeviceList.add(device);
                mAdapter.notifyDataSetChanged();

            } else if (BluetoothAdapter.ACTION_SCAN_MODE_CHANGED.equals(action)) {  //此处作用待细查
                int scanMode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, 0);
                if (scanMode == BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
                    setProgressBarIndeterminateVisibility(true);
                } else {
                    setProgressBarIndeterminateVisibility(false);
                }

            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                BluetoothDevice remoteDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (remoteDevice == null) {
                    showToast("无设备");
                    return;
                }
                int status = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, 0);
                if (status == BluetoothDevice.BOND_BONDED) {
                    showToast("已绑定" + remoteDevice.getName());
                } else if (status == BluetoothDevice.BOND_BONDING) {
                    showToast("正在绑定" + remoteDevice.getName());
                } else if (status == BluetoothDevice.BOND_NONE) {
                    showToast("未绑定" + remoteDevice.getName());
                }
            }
        }
    };

    //初始化用户界面
    private void initUI() {
        mListView = findViewById(R.id.device_list);
        mAdapter = new DeviceAdapter(mDeviceList, this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(bondDeviceClick);
    }

    private void say(String word) {
        if (mAcceptThread != null) {
            try {
                mAcceptThread.sendData(word.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else if (mConnectThread != null) {
            try {
                mConnectThread.sendData(word.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

    }


    private AdapterView.OnItemClickListener bondDeviceClick = new AdapterView.OnItemClickListener() {
        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            BluetoothDevice device = mDeviceList.get(i);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                device.createBond();
            }
        }
    };
    private AdapterView.OnItemClickListener bondedDeviceClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            BluetoothDevice device = mBondedDeviceList.get(i);
            if (mConnectThread != null) {
                mConnectThread.cancel();
            }
            mConnectThread = new ConnectThread(device, mController.getAdapter(), mUIHandler);
            mConnectThread.start();
        }
    };

    private class MyHandler extends Handler {

        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (message.what) {
                case Constant.MSG_GOT_DATA:
                    showToast("data:" + String.valueOf(message.obj));
                    break;
                case Constant.MSG_ERROR:
                    showToast("error:" + String.valueOf(message.obj));
                    break;
                case Constant.MSG_CONNECTED_TO_SERVER:
                    // 客户端连接成功后显示
                    showToast("连接到服务端");
                    break;
                case Constant.MSG_GOT_A_CLINET:
                    // 服务器连接成功后显示
                    mTv_wait_for_client.setText("连接成功，现在您可以开始游戏");
                    connected = true;
                    showToast("找到服务端");
                    break;
            }
        }
    }

    //设置toast的标准格式
    public void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
            mToast.show();
        } else {
            mToast.setText(text);
            mToast.show();
        }

    }

    /**
     * 退出时注销广播、注销连接过程、注销等待连接的监听
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAcceptThread != null) {
            mAcceptThread.cancel();
        }
        if (mConnectThread != null) {
            mConnectThread.cancel();
        }

        unregisterReceiver(receiver);
    }
}