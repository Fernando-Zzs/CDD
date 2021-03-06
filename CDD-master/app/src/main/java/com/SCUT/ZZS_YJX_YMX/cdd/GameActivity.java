package com.SCUT.ZZS_YJX_YMX.cdd;

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
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.SCUT.ZZS_YJX_YMX.cdd.Components.GameSystem;
import com.SCUT.ZZS_YJX_YMX.cdd.Basic.Global;
import com.SCUT.ZZS_YJX_YMX.cdd.Basic.Physics;
import com.SCUT.ZZS_YJX_YMX.cdd.Basic.Renderer;
import com.SCUT.ZZS_YJX_YMX.cdd.Scenes.Scene;
import com.SCUT.ZZS_YJX_YMX.cdd.Connect.AcceptThread;
import com.SCUT.ZZS_YJX_YMX.cdd.Connect.ClickThread;
import com.SCUT.ZZS_YJX_YMX.cdd.Connect.ConnectThread;
import com.SCUT.ZZS_YJX_YMX.cdd.Connect.Constant;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class GameActivity extends AppCompatActivity {

    public static GameActivity gameActivity;
    private Toast newGame_hint;
    private boolean exit = false;
    private static long lastClickTime = System.currentTimeMillis();
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
    private Button mBtn_bound_devices;
    private Button mBtn_encode;
    private Button mBtn_hide_devices;
    private Button mBtn_start_game;
    private Button mBtn_start_auto_play;
    private Button mBtn_cancel_auto_play;
    private ListView mLv_device_list;

    // 0?????????1?????????2?????????
    public static int state = 0;
    private Handler clickHandler = new ClickHandler();
    private ClickThread clickThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        hideNavigationBar();

        Button mainMenu = findViewById(R.id.main_menu);
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                exit();
            }
        });
        initUI();
        registerBluetoothReceiver();
        mController.turnOnBlueTooth(this, REQUEST_CODE);
        mBtn_listening = findViewById(R.id.btn_listening);
        mBtn_bound_devices = findViewById(R.id.btn_bound_devices);
        mBtn_hello = findViewById(R.id.btn_hello);
        mBtn_encode = findViewById(R.id.btn_encode);
        mBtn_hide_devices = findViewById(R.id.btn_hide_devices);
        mLv_device_list = findViewById(R.id.device_list);
        mBtn_start_game = findViewById(R.id.btn_start_game);
        mBtn_start_auto_play = findViewById(R.id.btn_start_auto_play);
        mBtn_cancel_auto_play = findViewById(R.id.btn_cancel_auto_play);

        if (Global.isServer) {
            mBtn_bound_devices.setVisibility(View.GONE);
        } else {
            mBtn_listening.setVisibility(View.GONE);
            mBtn_encode.setVisibility(View.GONE);
            mBtn_start_game.setVisibility(View.GONE);
        }

        setOnClickListener();

        if (Global.player_id == 0) {
            // ????????????
            if (mAcceptThread != null) {
                mAcceptThread.cancel();
            }
            mAcceptThread = new AcceptThread(mController.getAdapter(), mUIHandler);
            mAcceptThread.start();
            showToast("????????????????????????");
        }

        if (Global.player_id == 1) {
            mBondedDeviceList = mController.getBondedDeviceList();
            mAdapter.refresh(mBondedDeviceList);
            mListView.setOnItemClickListener(bondedDeviceClick);

            BluetoothDevice device = mBondedDeviceList.get(0);
            if (mConnectThread != null) {
                mConnectThread.cancel();
            }
            mConnectThread = new ConnectThread(device, mController.getAdapter(), mUIHandler);
            mConnectThread.start();
        }

        clickThread = new ClickThread(clickHandler);
        clickThread.start();
    }

    public void setOnClickListener() {
        OnClick onClick = new OnClick();
        mBtn_listening.setOnClickListener(onClick);
        mBtn_bound_devices.setOnClickListener(onClick);
        mBtn_hello.setOnClickListener(onClick);
        mBtn_encode.setOnClickListener(onClick);
        mBtn_hide_devices.setOnClickListener(onClick);
        mBtn_start_game.setOnClickListener(onClick);
        mBtn_start_auto_play.setOnClickListener(onClick);
        mBtn_cancel_auto_play.setOnClickListener(onClick);
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

                    showToast("????????????????????????");
                    break;
                case R.id.btn_bound_devices:
                    mBondedDeviceList = mController.getBondedDeviceList();
                    mAdapter.refresh(mBondedDeviceList);
                    mListView.setOnItemClickListener(bondedDeviceClick);

                    mBtn_bound_devices.setVisibility(View.GONE);
                    mBtn_hide_devices.setVisibility(View.VISIBLE);
                    mLv_device_list.setVisibility(View.VISIBLE);
                    break;
                case R.id.btn_hello:
                    say("Hello");
                    break;
                case R.id.btn_encode:
                    say(Global.encodedString);
                    break;
                case R.id.btn_hide_devices:
                    mBtn_bound_devices.setVisibility(View.VISIBLE);
                    mBtn_hide_devices.setVisibility(View.GONE);
                    mLv_device_list.setVisibility(View.GONE);
                case R.id.btn_start_game:
                    break;
                case R.id.btn_start_auto_play:
                    mBtn_start_auto_play.setVisibility(View.GONE);
                    mBtn_cancel_auto_play.setVisibility(View.VISIBLE);
                    // ????????????
                    Global.proxy = true;
                    break;
                case R.id.btn_cancel_auto_play:
                    mBtn_start_auto_play.setVisibility(View.VISIBLE);
                    mBtn_cancel_auto_play.setVisibility(View.GONE);
                    // ????????????
                    Global.proxy = false;
                    break;
                default:
                    break;
            }
        }
    }

    private void registerBluetoothReceiver() {
        IntentFilter filter = new IntentFilter();
        //????????????
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        //????????????
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        //????????????
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        //????????????????????????
        filter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        //????????????
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);

        registerReceiver(receiver, filter);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                //setProgressBarIndeterminateVisibility(true);
                //?????????????????????
                mDeviceList.clear();
                mAdapter.notifyDataSetChanged();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //setProgressBarIndeterminateVisibility(false);
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //????????????????????????
                mDeviceList.add(device);
                mAdapter.notifyDataSetChanged();

            } else if (BluetoothAdapter.ACTION_SCAN_MODE_CHANGED.equals(action)) {  //?????????????????????
                int scanMode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, 0);
                if (scanMode == BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
                    setProgressBarIndeterminateVisibility(true);
                } else {
                    setProgressBarIndeterminateVisibility(false);
                }

            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                BluetoothDevice remoteDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (remoteDevice == null) {
                    showToast("?????????");
                    return;
                }
                int status = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, 0);
                if (status == BluetoothDevice.BOND_BONDED) {
                    showToast("?????????" + remoteDevice.getName());
                } else if (status == BluetoothDevice.BOND_BONDING) {
                    showToast("????????????" + remoteDevice.getName());
                } else if (status == BluetoothDevice.BOND_NONE) {
                    showToast("?????????" + remoteDevice.getName());
                }
            }
        }
    };

    //?????????????????????
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
                    Log.d(TAG, String.valueOf(message.obj));
                    if (Global.player_id == 1) {
                        Global.client_get_data_count++;

                        if (Global.client_get_data_count == 1) {
                            Global.seed = Integer.parseInt(String.valueOf(message.obj));
                            showToast("??????????????????");
                        } else {
                            // ????????????????????????????????? ?????? ??????
                            Global.SendCard = String.valueOf(message.obj);
                            Global.isSend = true;
                        }
                    } else {
                        Global.server_get_data_count++;
                        // ??????????????????????????? ?????? ??????
                        Global.SendCard = String.valueOf(message.obj);
                        Global.isSend = true;
                    }
                    // ????????????say(String)
                    break;
                case Constant.MSG_ERROR:
                    showToast("error:" + String.valueOf(message.obj));
                    break;
                case Constant.MSG_CONNECTED_TO_SERVER:
                    showToast("??????????????????");
                    break;
                case Constant.MSG_GOT_A_CLINET:
                    say(Global.seed + "");
                    break;
            }
        }
    }

    //??????toast???????????????
    private void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
            mToast.show();
        } else {
            mToast.setText(text);
            mToast.show();
        }

    }

    /**
     * ????????????????????????????????????????????????????????????????????????
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

    @Override
    protected void onResume() {
        super.onResume();
        hideNavigationBar();
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
        exit();
    }


    public void exit() {
        synchronized (Renderer.renderersList) {
            Renderer.clear = true;
            if (Renderer.renderersList != null) {
                Renderer.renderersList.clear();
            }
        }
        Scene.getInstance().clear = true;
        Physics.Clear();
        synchronized (GameSystem.getInstance()) {
            GameSystem.getInstance().remove();
        }
    }

    private void hideNavigationBar() {
        if (Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    private class ClickHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    // ???
                    Log.d(TAG, "handleMessage: guo");
                    say("-1");
                    break;
                case 2:
                    // ??????
                    Log.d(TAG, "handleMessage: chu");
                    say(Global.outCards);
                    break;
                default:
                    break;
            }
        }
    }

}
