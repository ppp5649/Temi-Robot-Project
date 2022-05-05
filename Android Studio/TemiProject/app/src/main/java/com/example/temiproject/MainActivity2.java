package com.example.temiproject;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Robot robot = Robot.getInstance();

        robot.speak(TtsRequest.create( "가스 누출이 감지되었습니다. 주방으로 이동합니다.",true));


        mediaPlayer = MediaPlayer.create(this, R.raw.alarm);
        mediaPlayer.setLooping(true);
        mediaPlayer.start(); //음악 시작

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable()  {
            public void run() {
                // 시간 지난 후 실행할 코딩
                robot.goTo("주방");
            }
        }, 2000); // 0.5초후



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
}