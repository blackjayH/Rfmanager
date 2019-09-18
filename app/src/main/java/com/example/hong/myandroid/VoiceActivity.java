package com.example.hong.myandroid;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

public class VoiceActivity extends AppCompatActivity {
    private ProgressBar mProgress;
    private ImageView mLeftVolume[], mRightVolume[];
    private SpeechRecognizer mRecognizer;
    private final int READY = 0, END = 1, FINISH = 2;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case READY:
                    mProgress.setVisibility(View.INVISIBLE);
                    findViewById(R.id.stt_ui).setVisibility(View.VISIBLE);
                    break;
                case END:
                    mProgress.setVisibility(View.VISIBLE);
                    findViewById(R.id.stt_ui).setVisibility(View.INVISIBLE);
                    sendEmptyMessageDelayed(FINISH, 5000);
                    break;
                case FINISH:
                    finish();
                    break;
            }
        }
    };

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** Called TedPermission **/
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(VoiceActivity.this, "권한 승인", Toast.LENGTH_SHORT).show();
                run();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(VoiceActivity.this, "권한 거부\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT)
                        .show();
            }
        };
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setRationaleTitle(R.string.rationale_title)
                .setRationaleMessage(R.string.rationale_message)
                .setDeniedTitle("권한 거부")
                .setDeniedMessage(
                        "권한 거부 설정시 사용에 제약을 받을 수 있습니다.\n\n[설정] > [권한]으로 on/off 할 수 있습니다.")
                .setGotoSettingButtonText("Setting")
                .setPermissions(Manifest.permission.RECORD_AUDIO, Manifest.permission.INTERNET)
                .check();


    }

    public void run() {
        setContentView(R.layout.stt_my_ui);
        mProgress = (ProgressBar) findViewById(R.id.progress);

        mLeftVolume = new ImageView[3];
        mRightVolume = new ImageView[3];
        for (int i = 0; i < 3; i++) {
            mLeftVolume[i] = (ImageView) findViewById(R.id.volume_left_1 + i);
            mRightVolume[i] = (ImageView) findViewById(R.id.volume_right_1 + i);
        }


        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getApplication().getPackageName());
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");

        mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mRecognizer.setRecognitionListener(listener);
        mRecognizer.startListening(i);
        Toast.makeText(VoiceActivity.this, "음성입력 해주세요", Toast.LENGTH_SHORT).show();
    }

    private void setVolumeImg(int step) {
        for (int i = 0; i < 3; i++) {
            if (i < step) {
                mLeftVolume[0].setVisibility(View.VISIBLE);
                mRightVolume[0].setVisibility(View.VISIBLE);
            } else {
                mLeftVolume[0].setVisibility(View.INVISIBLE);
                mRightVolume[0].setVisibility(View.INVISIBLE);
            }
        }
    }

    private RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onRmsChanged(float rmsdB) {
            int step = (int) (rmsdB / 7);
            setVolumeImg(step);
        }

        @Override
        public void onResults(Bundle results) {
            mHandler.removeMessages(END);

            Intent i = new Intent();
            i.putExtras(results);
            setResult(777, i);

            finish();
        }

        @Override
        public void onReadyForSpeech(Bundle params) {
            mHandler.sendEmptyMessage(READY);
        }

        @Override
        public void onEndOfSpeech() {
            mHandler.sendEmptyMessage(END);
        }

        @Override
        public void onError(int error) {
            Toast.makeText(getApplicationContext(), "음성 인식 오류 다시 인식!!", Toast.LENGTH_SHORT).show();
            setResult(error);
        }

        @Override
        public void onBeginningOfSpeech() {
            Toast.makeText(getApplicationContext(), "음성 인식 시작!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
        }
    };

    @Override
    public void finish() {
        if (mRecognizer != null) mRecognizer.stopListening();
        mHandler.removeMessages(READY);
        mHandler.removeMessages(END);
        mHandler.removeMessages(FINISH);
        super.finish();
    }
}
