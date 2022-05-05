#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>

#define FIREBASE_HOST "temiproject-7ca5d-default-rtdb.firebaseio.com"
#define FIREBASE_AUTH "o55ZOXc8g8l40Z5KWNG2rtOM1IbapbJKKuvLMUpN"
#define WIFI_SSID "O_O"
#define WIFI_PASSWORD "01055790971"


void setup() 
{ // put your setup code here, to run once: 
  Serial.begin(115200); 
  pinMode(D3, INPUT); 
  pinMode(D4, INPUT); 
  
  

   WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("connecting");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  Serial.println();
  Serial.print("connected: ");
  Serial.println(WiFi.localIP());
  
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);

}
  void loop() 
  { // put your main code here, to run repeatedly: 
    int button1State = digitalRead(D3);
    Serial.print(button1State); 
    delay(100);
     Firebase.setInt("button",digitalRead(D3));
  delay(100);
  
  int button2State = digitalRead(D4);
    Serial.print(button2State); 
    delay(100);
     Firebase.setInt("button2",digitalRead(D4));
  delay(100);
  
  }
