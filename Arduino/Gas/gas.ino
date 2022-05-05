th//
// Copyright 2015 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

// FirebaseDemo_ESP8266 is a sample that demo the different functions
// of the FirebaseArduino API.

#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>

#define FIREBASE_HOST "temiproject-7ca5d-default-rtdb.firebaseio.com"
#define FIREBASE_AUTH "o55ZOXc8g8l40Z5KWNG2rtOM1IbapbJKKuvLMUpN"
#define WIFI_SSID "yemin"
#define WIFI_PASSWORD "01096535250"

int GasPin = A0;
void setup() {
  pinMode(GasPin ,INPUT);
  Serial.begin(115200);

  // connect to wifi.
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

int n = 0;

void loop() {
  Serial.println(analogRead(GasPin));   // 가스센서로부터 아날로그 데이터를 받아와 시리얼 모니터로 출력함
  Firebase.setInt("gas",analogRead(GasPin));
  delay(1000);
}
