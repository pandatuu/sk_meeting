package com.example.facetime.conference.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.facetime.R;
import com.example.facetime.conference.fragment.BackgroundForJavaFragment;
import com.example.facetime.conference.fragment.ShareForJavaFragment;
import com.example.facetime.conference.listener.VideoChatControllerListener;
import com.example.facetime.util.BaseUiListener;
import com.example.facetime.util.CommonActivity;
import com.example.facetime.wxapi.WXEntryActivity;
import com.facebook.react.modules.core.PermissionListener;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;

import org.jetbrains.annotations.NotNull;
import org.jitsi.meet.sdk.JitsiMeetActivityDelegate;
import org.jitsi.meet.sdk.JitsiMeetActivityInterface;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions.Builder;
import org.jitsi.meet.sdk.JitsiMeetFragment;
import org.jitsi.meet.sdk.JitsiMeetView;
import org.jitsi.meet.sdk.JitsiMeetViewListener;
import org.jitsi.meet.sdk.R.id;
import org.jitsi.meet.sdk.R.layout;

import java.util.ArrayList;
import java.util.Map;

import javax.annotation.Nullable;

import kotlin.Unit;
import kotlin.coroutines.Continuation;


public class JitsiMeetActivitySon extends FragmentActivity implements ActivityCompat.OnRequestPermissionsResultCallback, JitsiMeetActivityInterface, JitsiMeetViewListener, VideoChatControllerListener, ShareForJavaFragment.SharetDialogSelect, BackgroundForJavaFragment.ClickBack {
    protected static final String TAG = JitsiMeetActivitySon.class.getSimpleName();
    public static final String ACTION_JITSI_MEET_CONFERENCE = "org.jitsi.meet.CONFERENCE";
    public static final String JITSI_MEET_CONFERENCE_OPTIONS = "JitsiMeetConferenceOptions";

    private Fragment backgroundFragment;
    private Fragment shareFragment;

    private static int leaveType = 2;

    Context context;

    final Handler cwjHandler = new Handler();
    View view;

    Boolean shareClickFlag = true;

    public JitsiMeetActivitySon() {
    }

    public void launch(Context context, JitsiMeetConferenceOptions options, String interviewId) {

        Intent intent = new Intent(context, JitsiMeetActivitySon.class);
        intent.setAction("org.jitsi.meet.CONFERENCE");
        intent.putExtra("JitsiMeetConferenceOptions", options);
        context.startActivity(intent);

        overridePendingTransition(
                R.anim.right_in,
                R.anim.left_out
        );
    }

//    public static void launch(Context context, String url) {
//        JitsiMeetConferenceOptions options = (new Builder()).setRoom(url).build();
//        launch(context, options);
//    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        this.setContentView(layout.activity_jitsi_meet);
        if (!this.extraInitialize()) {
            this.initialize();
        }


        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);


        view = CommonActivity.Companion.addMyChild(this);

        addContentView(view, params);


        LinearLayout layout = (LinearLayout) ((LinearLayout) view).getChildAt(0);
        LinearLayout inside = (LinearLayout) layout.getChildAt(0);

        LinearLayout image = (LinearLayout) inside.getChildAt(2);


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shareClickFlag) {
                    shareClickFlag = false;
                    System.out.println("xxxxxxxxxxxxxxxxxxxxx");

                    addListFragment();
                }

            }
        });

    }


    @SuppressLint("ResourceType")
    private void addListFragment() {
        int WRITE = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        );
        int READ = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
        );
        if (READ == PackageManager.PERMISSION_GRANTED && WRITE == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "hava this permission", Toast.LENGTH_SHORT)
                    .show();
            share();
        } else {
            Toast.makeText(this, "no this permission", Toast.LENGTH_SHORT).show();
            if (Build.VERSION.SDK_INT >= 23) {
                String[] mPermissionList = {
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.READ_LOGS,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.SET_DEBUG_APP,
                        Manifest.permission.SYSTEM_ALERT_WINDOW,
                        Manifest.permission.GET_ACCOUNTS,
                        Manifest.permission.WRITE_APN_SETTINGS
                };
                ActivityCompat.requestPermissions(this, mPermissionList, 123);
            }
        }
    }

    @SuppressLint("ResourceType")
    private void share() {
        FragmentTransaction mTransaction = getFragmentManager().beginTransaction();
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (backgroundFragment == null) {
            backgroundFragment =
                    BackgroundForJavaFragment.Companion.newInstance();

            mTransaction.add(R.id.jitsiFragment, backgroundFragment);
        }

        mTransaction.setCustomAnimations(R.anim.bottom_in_a, R.anim.bottom_in_a);

        shareFragment = ShareForJavaFragment.Companion.newInstance();
        mTransaction.add(R.id.jitsiFragment, shareFragment);

        mTransaction.commit();

        shareClickFlag = true;
    }


    @SuppressLint("ResourceType")
    private void closeAlertDialog() {

        FragmentTransaction mTransaction = getFragmentManager().beginTransaction();
        if (shareFragment != null) {
            mTransaction.setCustomAnimations(R.anim.bottom_out_a, R.anim.bottom_out_a);

            mTransaction.remove(shareFragment);
            shareFragment = null;
        }

        if (backgroundFragment != null) {
            mTransaction.setCustomAnimations(
                    R.anim.fade_in_out_a, R.anim.fade_in_out_a
            );
            mTransaction.remove(backgroundFragment);
            backgroundFragment = null;
        }
        mTransaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    public void finishVideo(int type) {
        leaveType = type;
        System.out.println("离开视频！！！！！！！！！！！！！！！！！");
        finishFlag = true;

        JitsiMeetActivitySon.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    leave();
                } catch (Exception e) {
                    System.out.println("被动离开视频时，报错了！！！！！");
                }

                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivityForResult(intent, 12);
                overridePendingTransition(
                        R.anim.right_in,
                        R.anim.left_out
                );


                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        });


    }

    protected JitsiMeetView getJitsiView() {

        JitsiMeetFragment fragment = (JitsiMeetFragment) this.getSupportFragmentManager().findFragmentById(id.jitsiFragment);
        return fragment.getJitsiView();
        //return new JitsiMeetView(this);
    }

    public void join(@Nullable String url) {
        JitsiMeetConferenceOptions options = (new Builder()).setRoom(url).build();
        this.join(options);
    }

    public void join(JitsiMeetConferenceOptions options) {
        this.getJitsiView().join(options);
    }

    public void leave() {
        System.out.println("离开视频！！！！！");

        try {
            this.getJitsiView().leave();
        } finally {

        }


    }

    @Nullable
    private JitsiMeetConferenceOptions getConferenceOptions(Intent intent) {
        String action = intent.getAction();
        if ("android.intent.action.VIEW".equals(action)) {
            Uri uri = intent.getData();
            if (uri != null) {
                return (new Builder()).setRoom(uri.toString()).build();
            }
        } else if ("org.jitsi.meet.CONFERENCE".equals(action)) {
            return (JitsiMeetConferenceOptions) intent.getParcelableExtra("JitsiMeetConferenceOptions");
        }

        return null;
    }

    protected boolean extraInitialize() {
        return false;
    }

    protected void initialize() {
        this.getJitsiView().setListener(this);
        this.join(this.getConferenceOptions(this.getIntent()));
    }

    public void onBackPressed() {
        JitsiMeetActivityDelegate.onBackPressed();
    }

    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        JitsiMeetConferenceOptions options;
        if ((options = this.getConferenceOptions(intent)) != null) {
            this.join(options);
        } else {
            JitsiMeetActivityDelegate.onNewIntent(intent);
        }
    }

    protected void onUserLeaveHint() {
        this.getJitsiView().enterPictureInPicture();
    }

    public void requestPermissions(String[] permissions, int requestCode, PermissionListener listener) {
        JitsiMeetActivityDelegate.requestPermissions(this, permissions, requestCode, listener);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        JitsiMeetActivityDelegate.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 123) {
            share();
        }
    }

    int count = 60;
    int min = 0;
    int second = 0;
    String minStr = "";
    String secStr = "";
    Boolean finishFlag = false;
    String showString = "";

    public void onConferenceJoined(Map<String, Object> data) {
        Log.d(TAG, "Conference joined: " + data);
        count = (int) getIntent().getLongExtra("time", 0) / 1000;


//        AlertDialog dialog = new AlertDialog.Builder(JitsiMeetActivitySon.this).setTitle("警告")
//                .setIcon(android.R.drawable.ic_dialog_info)
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        Log.d(TAG, "onClick");
//                        JitsiMeetActivitySon.this.finish();
//                    }
//                }).show();
//        Log.e(TAG, "AlertDialog");
//        dialog.setCanceledOnTouchOutside(false);

        if (count == 0) {

        } else {

            new Thread(new Runnable() {
                @Override
                public void run() {


                    //刚搞页延迟
                    Handler mainHandler = new Handler(Looper.getMainLooper());

                    LinearLayout layout = (LinearLayout) ((LinearLayout) view).getChildAt(0);
                    LinearLayout inside = (LinearLayout) layout.getChildAt(0);

                    TextView text = (TextView) inside.getChildAt(0);
                    while (true) {
                        try {
                            Thread.sleep(1000);

                            mainHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {


                                    min = count / 60;
                                    second = count % 60;


                                    if (min < 10) {
                                        minStr = "0" + min;
                                    } else {
                                        minStr = min + "";
                                    }

                                    if (second < 10) {
                                        secStr = "0" + second;
                                    } else {
                                        secStr = second + "";
                                    }
                                    showString = "已经视频：" + minStr + ":" + secStr;
                                    System.out.println("已经视频：" + minStr + ":" + secStr);
                                    // text.setText("已经视频："+minStr+":"+secStr);
                                    text.setText(showString);

                                }
                            }, 0);


                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                        if (count == 5) {
                            mainHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Toast toast = Toast.makeText(
                                            getApplicationContext(),
                                            "视频会议即将结束",
                                            Toast.LENGTH_SHORT
                                    );
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                }
                            }, 0);

                        }

                        if (count == 0) {


                            finishVideo(1);

                            break;
                        }
                        count = count - 1;

                        if (finishFlag) {
                            break;
                        }
                    }
                }
            }).start();

        }


    }

    public void onConferenceTerminated(Map<String, Object> data) {
        Log.d(TAG, "Conference terminated: " + data);
        System.out.println("onConferenceTerminated");

        if (leaveType != 2) {
            //thiscontext.sendMessageToHimToshutDownVideo(thisInterviewId);
            // this.finishVideo(1);
        }

        leaveType = 0;


        this.finishVideo(1);
    }

    public void onConferenceWillJoin(Map<String, Object> data) {
        Log.d(TAG, "Conference will join: " + data);
    }

    @Override
    public void closeVideo() {
        this.finishVideo(2);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            finishFlag = true;
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            startActivityForResult(intent, 12);
            overridePendingTransition(
                    R.anim.left_in,
                    R.anim.right_out
            );

            finish();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void clickAll() {
        closeAlertDialog();
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public Object getSelectedItem(int index, @NotNull Continuation<? super Unit> continuation) {

        Activity activity=this;
        SharedPreferences saveTool = PreferenceManager.getDefaultSharedPreferences(this);
        String addr = saveTool.getString("serviceAdd", getString(R.string.videoUrl));
        String id = saveTool.getString("MyRoomNum", "");
        switch (index) {
            case 0: {
                //Toast.makeText(this, "qq!!!!", Toast.LENGTH_LONG).show();
                //APP ID1110022340  APP KEYY1q9LJRhykp44N4j
                PlatformConfig.setQQZone("1110022340", "Y1q9LJRhykp44N4j");

                new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.QQ)//传入平台
                        .withText("this is chat App,welcome to try")
                        .withMedia(new UMWeb("https://www.baidu.com"))
                        .setCallback(new  UMShareListener() {
                            @Override
                            public void onStart(SHARE_MEDIA share_media) {
                                Toast.makeText(activity, "qq222222!!!!", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onResult(SHARE_MEDIA share_media) {
                                Toast.makeText(activity, "qq222222!!!!", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                                Toast.makeText(activity, "qq222222!!!!", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCancel(SHARE_MEDIA share_media) {
                                Toast.makeText(activity, "qq222222!!!!", Toast.LENGTH_LONG).show();
                            }
                        })
                .share();

                break;
            }
            case 1: {
                String APP_ID = "wxa734d668789e6b82";
                IWXAPI api = WXAPIFactory.createWXAPI(this, APP_ID, true);

                api.registerApp(APP_ID);
                //初始化一个 WXTextObject 对象，填写分享的文本内容
                WXTextObject textObj = new WXTextObject();
                textObj.text = "111111111111";

                //用 WXTextObject 对象初始化一个 WXMediaMessage 对象
                WXMediaMessage msg = new WXMediaMessage();
                msg.mediaObject = textObj;
                msg.description = "11111111111";

                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());  //transaction字段用与唯一标示一个请求
                req.message = msg;

//                WXEntryActivity wx = new WXEntryActivity();
//                api.handleIntent(getIntent(), wx);
                //调用api接口，发送数据到微信
                api.sendReq(req);

                break;
            }
            case 2: {
                Toast.makeText(this, "dingding", Toast.LENGTH_LONG).show();
                UMShareListener shareListener = new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        System.out.println("dingding成功");
                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        System.out.println("dingding成功");
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        Toast.makeText(context, "dingding失败----"+throwable.getMessage(), Toast.LENGTH_LONG).show();
                        System.out.println("dingding失败----"+throwable.getMessage());
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        System.out.println("dingding取消");
                    }
                };
                PlatformConfig.setDing("dingoamq8ymfaatukyfhgl");
                new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.DINGTALK)//传入平台
                        .withText("this is chat App,welcome to try")
                        .setCallback(shareListener)
                        .share();
                break;
            }
            default:
                closeAlertDialog();
                break;
        }
        return null;
    }
}
