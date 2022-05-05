package com.example.temiproject;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity3 extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Robot robot = Robot.getInstance();

        robot.speak(TtsRequest.create( "넘어짐이 감지되었습니다.",true));

        mediaPlayer = MediaPlayer.create(this, R.raw.alarm);
        mediaPlayer.setLooping(true);
        mediaPlayer.start(); //음악 시작

        ImageView img =(ImageView)findViewById(R.id.imageView);

        Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        img.startAnimation(startAnimation);

        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop(); //음악 종료
                finish();
            }
        });


    }
*/
        private int counter = 60;
        private TextView timer_text;
        private final Handler handler = new Handler();

        //private MediaPlayer mediaPlayer;

        private Timer timer = new Timer();


        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main3);
            timer_text = findViewById(R.id.timer);

            ImageView img =(ImageView)findViewById(R.id.imageView);

            Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
            img.startAnimation(startAnimation);

            Robot robot = Robot.getInstance();
            robot.speak(TtsRequest.create( "넘어짐이 감지되었습니다.",true));

            //시간측정
            TimerTask tt = new TimerTask() {
                @Override
                public void run() {
                    Update();
                    counter--;
                    if (counter == 0) {
                        counter = 60;


                        robot.startTelepresence(robot.getAdminInfo().getName(), robot.getAdminInfo().getUserId());

                        StopTimer();
                        finish();
                    }
                }
            };

            //타이머 생성
            timer.schedule(tt, 0, 1000);

            // 알람음 재생
            this.mediaPlayer = MediaPlayer.create(this, R.raw.alarm);
            this.mediaPlayer.setLooping(true);
            this.mediaPlayer.start();

            findViewById(R.id.btnClose).setOnClickListener(mClickListener);
        }

        public void StopTimer () {
            timer.cancel();
        }


        //알람업데이트 시간
        protected void Update () {
            Runnable updater = new Runnable() {
                public void run() {
                    timer_text.setText(counter + "초");
                }
            };
            handler.post(updater);
        }

        @Override
        protected void onDestroy () {
            super.onDestroy();

            // MediaPlayer release
            if (this.mediaPlayer != null) {
                this.mediaPlayer.release();
                this.mediaPlayer = null;
            }
        }

        /* 알람 종료 */
        private void close () {
            if (this.mediaPlayer.isPlaying()) {
                this.mediaPlayer.stop();
                this.mediaPlayer.release();
                this.mediaPlayer = null;
            }

            finish();
        }

        View.OnClickListener mClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnClose:
                        // 알람 종료
                        close();
                        StopTimer();
                        finish();
                        break;
                }
            }
        };



}
