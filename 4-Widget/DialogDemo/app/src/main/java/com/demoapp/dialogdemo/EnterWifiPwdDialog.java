package com.demoapp.dialogdemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkSpecifier;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;

public class EnterWifiPwdDialog extends AlertDialog implements OnCheckedChangeListener {
    private String TAG = "EnterWifiPwdDialog";
    private Context mContext;
    private Window mWindow;
    private View mPwdArea;
    private TextView mTitleView;
    private TextView mPasswordView;
    private Button mButtonPositive;
    private Button mButtonNegative;
    private Handler handler;
    private WifiManager wifiManager;
    private BroadcastReceiver wifiScanReceiver;

    public EnterWifiPwdDialog(@NonNull Context context) {
        super(context);
//        applyFlags(this);
        mContext = context;
        mWindow = getWindow();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
        setContentView(R.layout.ifly_wifi_locked_dialog);

        final FrameLayout custom = mWindow.findViewById(R.id.custom);
        mPwdArea = getLayoutInflater().inflate(R.layout.ifly_wifi_password_area, null);
        custom.addView(mPwdArea, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mWindow.setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        initDialog();

        handler = new Handler();
        handler.postDelayed(() -> {
            if (mPasswordView != null && mPasswordView.requestFocus()) {
                Context context = getContext();
                if (context != null) {
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.showSoftInput(mPasswordView, InputMethodManager.SHOW_IMPLICIT);
                    }
                }
            }
        }, 200);
    }

    public static Dialog applyFlags(Dialog dialog) {
        final Window window = dialog.getWindow();

        window.setType(2017);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        return dialog;
    }

    public void initDialog() {
        setupTitle("WiFi名称");
        setupSecurityFields();
        setupButtons();

//        initWifiConfig();
        scanWifi();
    }

    public void setupTitle(CharSequence mTitle) {
        mTitleView = (TextView) mWindow.findViewById(R.id.dialog_title);
        mTitleView.setText(mTitle);
    }

    protected void setupSecurityFields() {
        mPasswordView = (TextView) mWindow.findViewById(R.id.password);
        setPasswordTextWatcher(mPasswordView);
        CheckBox mCheckBox = mWindow.findViewById(R.id.show_password);
        mCheckBox.setOnCheckedChangeListener(this);
    }

    public void setupButtons() {
        mButtonPositive = (Button) mWindow.findViewById(R.id.connect_button);
        mButtonPositive.setText("连接");
        mButtonPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "密码为: " + mPasswordView.getText(), Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        mButtonNegative = (Button) mWindow.findViewById(R.id.cancel_button);
        mButtonNegative.setText("取消");
        mButtonNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "点击取消", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int pos = mPasswordView.getSelectionEnd();
        mPasswordView.setInputType(InputType.TYPE_CLASS_TEXT
                | (isChecked ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                : InputType.TYPE_TEXT_VARIATION_PASSWORD));
        if (pos >= 0) {
            ((EditText) mPasswordView).setSelection(pos);
        }
    }

    public void scanWifi() {
        wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> scanResults = wifiManager.getScanResults();

        for (ScanResult scanResult : scanResults) {
            String SSID = scanResult.SSID;
            String BSSID = scanResult.BSSID;
            String capabilities = scanResult.capabilities;
            int level = scanResult.level;
            Log.d("WIFISCAN", "SSID: " + SSID + ", BSSID: " + BSSID);
        }
    }

    /**
     * @param networkSSID WiFi名称
     * @param networkPass WiFi密码
     */
    public void connectToWifi(String networkSSID, String networkPass) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        WifiNetworkSpecifier wifiNetworkSpecifier = new WifiNetworkSpecifier.Builder()
                .setSsid(networkSSID)
                .setWpa2Passphrase(networkPass)
                .build();
        //网络请求
        NetworkRequest request = new NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .removeCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_RESTRICTED)
                .addCapability(NetworkCapabilities.NET_CAPABILITY_TRUSTED)
                .setNetworkSpecifier(wifiNetworkSpecifier)
                .build();
        //网络回调处理
        ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                Log.d("WifiUtils", "连接成功");
            }

            @Override
            public void onUnavailable() {
                super.onUnavailable();
                Log.d("WifiUtils", "连接失败");
            }
        };

        //连接网络
        connectivityManager.requestNetwork(request, networkCallback);
    }

    private void setPasswordTextWatcher(TextView textView) {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, textView.getText().toString());
                if (isValidPsk(mPasswordView.getText().toString())) {
                    mButtonPositive.setTextAppearance(R.style.Ifly_DialogButtonPositiveEnable);
                } else {
                    mButtonPositive.setTextAppearance(R.style.Ifly_DialogButtonPositiveDisable);

                }
            }
        };
        textView.addTextChangedListener(textWatcher);
    }

    boolean isValidPsk(String password) {
        if (password.length() == 64 && password.matches("[0-9A-Fa-f]{64}")) {
            return true;
        } else if (password.length() >= 8 && password.length() <= 63) {
            return true;
        }
        return false;
    }
}
