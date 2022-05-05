package com.example.temiproject.temi;

import android.util.Log;

import com.robotemi.sdk.NlpResult;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;
import com.robotemi.sdk.activitystream.ActivityStreamPublishMessage;
import com.robotemi.sdk.listeners.OnBeWithMeStatusChangedListener;
import com.robotemi.sdk.listeners.OnConstraintBeWithStatusChangedListener;
import com.robotemi.sdk.listeners.OnDetectionStateChangedListener;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;
import com.robotemi.sdk.listeners.OnLocationsUpdatedListener;
import com.robotemi.sdk.listeners.OnRobotReadyListener;
import com.robotemi.sdk.listeners.OnUserInteractionChangedListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/*
  extends : 정의된 객체를 상속받았을 경우 해당 기능을 연장해서 사용한다는 뜻이다.

  implements : abstract 객체를 상속받고 그 내용들을 작성했다는 뜻이다.
              abstract 클래스의 경우 필수 기능의 메서드의 형태만 정의되어있기때문에
              상세 내용을 override를 통해서 반드시 정의해야하며 , 부모늬 클래스는 작성된 내용이 없기때문에
              super를 통하여 부모의 기능을 실핼 수 없다.
*/
public class RoboTemiListeners extends RoboTemi // extends는 Main2Activity 예제에서 사용한다.
        implements
       Robot.NlpListener,
       OnRobotReadyListener,
       Robot.ConversationViewAttachesListener,
       Robot.WakeupWordListener,
       Robot.ActivityStreamPublishListener,
       Robot.TtsListener,
       OnBeWithMeStatusChangedListener,
       OnGoToLocationStatusChangedListener,
       OnDetectionStateChangedListener,
        OnUserInteractionChangedListener {

   private Robot robot;


   public void init(){
       robot.addOnRobotReadyListener(this);
       robot.addNlpListener(this);
       robot.addOnBeWithMeStatusChangedListener(this);
       robot.addOnGoToLocationStatusChangedListener(this);
       robot.addConversationViewAttachesListenerListener(this);
       robot.addWakeupWordListener(this);
       robot.addTtsListener(this);
       robot.addOnDetectionStateChangedListener(this);

   }
   public void stop(){
       robot.removeOnRobotReadyListener(this);
       robot.removeNlpListener(this);
       robot.removeOnBeWithMeStatusChangedListener(this);
       robot.removeOnGoToLocationStatusChangedListener(this);
       robot.removeConversationViewAttachesListenerListener(this);
       robot.removeWakeupWordListener(this);
       robot.removeTtsListener(this);
       robot.removeDetectionStateChangedListener(this);
       robot.stopMovement();
   }



   @Override
   public void onPublish(@NotNull ActivityStreamPublishMessage activityStreamPublishMessage) {
       //Activity 스트림 publish가 끝났을 때 호출
       //최근 행동들의 기록

   }

   @Override
   public void onConversationAttaches(boolean b) {
       // 대화가 시작되었을 경우
   }

   @Override
   public void onNlpCompleted(@NotNull NlpResult nlpResult) {
       // 자연 언어 처리 완료 후(natural language processing)
       String str = nlpResult.toString();
       Log.i("NLP RESULT",str);
   }

   @Override
   public void onTtsStatusChanged(@NotNull TtsRequest ttsRequest) {
       //테미가 말하는 것의 상태 변화(Text to Speedh)
   }

   @Override
   public void onWakeupWord(@NotNull String s, int i) {
       //테미를 불러서 깨웠을때
       speak("네 부르셨나요?");//roboTemi class를 상속했기 때문에 사용이 가능하다.
   }

   @Override
   public void onBeWithMeStatusChanged(@NotNull String status) {
       switch (status) {

           case "search":
               speak("저기요, 우리 같이가요.");
               break;

           case "track":
               speak("따라가고 있어요.");
               break;
       }
   }


   @Override
   public void onDetectionStateChanged(int state) {
       // DETECTED, IDLE, LOST
       switch (state){
           case DETECTED:
               //대상 포착시 행동
               break;
           case IDLE:
               //대기상태
               break;
           case LOST:
               //대상을 잃어버림
               break;
       }
   }

   private String currentLocation = "";
   @Override
   public void onGoToLocationStatusChanged(String location, String status, int descriptionId, String description) {

       switch (status) {
           case "start":
               speak(location+" 위치로 이동중입니다.");
               currentLocation = location;
               if (location.equals("home base")){
               }else {
               }
               break;
           case "going":
               if ( location.contains("방역 구역") ){
                    speak("해당 구역에 방역작업을 진행 중입니다.");
               }else {
                    speak(location+" 위치로 이동중입니다.");
               }
               currentLocation = location;
               break;
           case "complete":
               speak(location + " 위치에 도착했습니다.");
               currentLocation = location;
               break;
       }
   }


   @Override
   public void onRobotReady(boolean b) {
       //로봇이 준비된 상태에 들어갔을때
   }


    @Override
    public void onUserInteraction(boolean b) {
        robot.stopMovement();
    }
}
