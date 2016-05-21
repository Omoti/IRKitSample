package example.irkitsample.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import example.irkitsample.R;
import example.irkitsample.irkit.IRKitDevice;
import example.irkitsample.irkit.IRKitDeviceController;
import example.irkitsample.irkit.IRKitDeviceManager;
import example.irkitsample.irkit.IRKitMessage;

public class MainActivity extends AppCompatActivity implements IRKitDeviceManager.IRKitDeviceListener, IRKitDeviceController.IRKitDeviceControllerCallback {
    private static final String TAG = MainActivity.class.getSimpleName();

    private IRKitDeviceManager mManager;
    private IRKitDeviceController mController;
    private RequestQueue mRequestQueue;
    private IRKitMessage mRecentMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRequestQueue = Volley.newRequestQueue(this);

        mManager = new IRKitDeviceManager(this.getApplicationContext());
        mManager.registerDeviceListener(this);

        findViewById(R.id.bt_recent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mController != null) {
                    mController.requestRecentMessage();
                }
            }
        });

        findViewById(R.id.bt_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mController != null) {
                    mController.sendMessage(mRecentMessage);
                }
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();

        //検索開始
        if (mManager != null) {
            mManager.startDiscovery();
        }
    }

    @Override
    protected void onStop(){
        super.onStop();

        //検索中止
        if (mManager != null) {
            mManager.stopDiscovery();
        }


    }

    @Override
    public void onFoundDevice(IRKitDevice device) {
        Toast.makeText(this, device.getName() + ',' + device.getIp(), Toast.LENGTH_SHORT).show();

        mController = new IRKitDeviceController(device, mRequestQueue);
        mController.setCallback(this);

        if (mManager != null) {
            mManager.stopDiscovery();
        }
    }

    @Override
    public void onReceiveRecentMessage(IRKitMessage message){
        ((TextView)findViewById(R.id.text_message)).setText(message.toString());

        mRecentMessage = message;
    }
}
