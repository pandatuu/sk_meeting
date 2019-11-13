package com.example.facetime.conference.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.facetime.R;
import com.example.facetime.conference.listener.VideoChatControllerListener;
import com.example.facetime.util.CommonActivity;
import com.facebook.react.modules.core.PermissionListener;

import org.jitsi.meet.sdk.JitsiMeetActivityDelegate;
import org.jitsi.meet.sdk.JitsiMeetActivityInterface;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions.Builder;
import org.jitsi.meet.sdk.JitsiMeetFragment;
import org.jitsi.meet.sdk.JitsiMeetView;
import org.jitsi.meet.sdk.JitsiMeetViewListener;
import org.jitsi.meet.sdk.R.id;
import org.jitsi.meet.sdk.R.layout;

import java.util.Map;

import javax.annotation.Nullable;


public class JitsiMeetActivitySon extends FragmentActivity implements JitsiMeetActivityInterface, JitsiMeetViewListener, VideoChatControllerListener {
    protected static final String TAG = JitsiMeetActivitySon.class.getSimpleName();
    public static final String ACTION_JITSI_MEET_CONFERENCE = "org.jitsi.meet.CONFERENCE";
    public static final String JITSI_MEET_CONFERENCE_OPTIONS = "JitsiMeetConferenceOptions";


    private static int leaveType = 2;

    Context context;

    final Handler cwjHandler = new Handler();
    View view;

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


        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(400, 200);


         view = CommonActivity.Companion.addMyChild(this);

        addContentView(view, params);


    }

    public void finishVideo(int type) {
        leaveType = type;
        System.out.println("离开视频！！！！！！！！！！！！！！！！！");
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
    }

    int count = 0;
    int min = 0;
    int second = 0;
    String minStr="";
    String secStr="";

    int max=600;
    public void onConferenceJoined(Map<String, Object> data) {
        Log.d(TAG, "Conference joined: " + data);


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


        new Thread(new Runnable() {
            @Override
            public void run() {



                //刚搞页延迟
                Handler mainHandler = new Handler(Looper.getMainLooper());

                LinearLayout layout=(LinearLayout)  ((LinearLayout) view).getChildAt(0);

                TextView text=(TextView)layout.getChildAt(0);
                while (true) {
                    try {
                        Thread.sleep(1000);

                        mainHandler.postDelayed( new Runnable() {
                            @Override
                            public void run() {


                                min=count/60;
                                second=count%60;


                                if(min<10){
                                    minStr="0"+min;
                                }else{
                                    minStr=min+"";
                                }

                                if(second<10){
                                    secStr="0"+second;
                                }else{
                                    secStr=second+"";
                                }

                                text.setText("已经视频："+minStr+":"+secStr);

                            }
                        },0);


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    if(count>max-5){
                        mainHandler.postDelayed( new Runnable() {
                            @Override
                            public void run() {
                                Toast toast = Toast.makeText(
                                        getApplicationContext(),
                                        "视频会议即将结束",
                                        Toast.LENGTH_SHORT
                                );
                                toast. setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        },0);

                    }

                    if (count > max-1) {


                        finishVideo(1);

                        break;
                    }
                    count = count + 1;
                }
            }
        }).start();


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


}
