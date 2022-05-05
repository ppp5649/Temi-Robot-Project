
#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>

#define FIREBASE_HOST "temiproject-7ca5d-default-rtdb.firebaseio.com"
#define FIREBASE_AUTH "o55ZOXc8g8l40Z5KWNG2rtOM1IbapbJKKuvLMUpN"
#define WIFI_SSID "iPhone"
#define WIFI_PASSWORD "01055037461"

#include <Adafruit_MPU6050.h>
#include <Adafruit_Sensor.h>
#include <Wire.h>

double angleAcX, angleAcY;
const double RADIAN_TO_DEGREE = 180 / 3.14159;
double Xstr1, Xstr2, Xstr3, Ystr1,Ystr2, Ystr3; 
Adafruit_MPU6050 mpu;

void setup() {
  // put your setup code here, to run once:
  
Serial.begin(9600);
  while (!Serial)
    delay(10); // will pause Zero, Leonardo, etc until serial console opens

  Serial.println("Adafruit MPU6050 test!");

  // Try to initialize!
  if (!mpu.begin()) {
    Serial.println("Failed to find MPU6050 chip");
    while (1) {
      delay(10);
    }
  }
  Serial.println("MPU6050 Found!");

  mpu.setAccelerometerRange(MPU6050_RANGE_8_G);
  Serial.print("Accelerometer range set to: ");
  switch (mpu.getAccelerometerRange()) {
  case MPU6050_RANGE_2_G:
    Serial.println("+-2G");
    break;
  case MPU6050_RANGE_4_G:
    Serial.println("+-4G");
    break;
  case MPU6050_RANGE_8_G:
    Serial.println("+-8G");
    break;
  case MPU6050_RANGE_16_G:
    Serial.println("+-16G");
    break;
  }
  mpu.setGyroRange(MPU6050_RANGE_500_DEG);
  Serial.print("Gyro range set to: ");
  switch (mpu.getGyroRange()) {
  case MPU6050_RANGE_250_DEG:
    Serial.println("+- 250 deg/s");
    break;
  case MPU6050_RANGE_500_DEG:
    Serial.println("+- 500 deg/s");
    break;
  case MPU6050_RANGE_1000_DEG:
    Serial.println("+- 1000 deg/s");
    break;
  case MPU6050_RANGE_2000_DEG:
    Serial.println("+- 2000 deg/s");
    break;
  }

  mpu.setFilterBandwidth(MPU6050_BAND_21_HZ);
  Serial.print("Filter bandwidth set to: ");
  switch (mpu.getFilterBandwidth()) {
  case MPU6050_BAND_260_HZ:
    Serial.println("260 Hz");
    break;
  case MPU6050_BAND_184_HZ:
    Serial.println("184 Hz");
    break;
  case MPU6050_BAND_94_HZ:
    Serial.println("94 Hz");
    break;
  case MPU6050_BAND_44_HZ:
    Serial.println("44 Hz");
    break;
  case MPU6050_BAND_21_HZ:
    Serial.println("21 Hz");
    break;
  case MPU6050_BAND_10_HZ:
    Serial.println("10 Hz");
    break;
  case MPU6050_BAND_5_HZ:
    Serial.println("5 Hz");
    break;
  }

  Serial.println("");
  delay(100);
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

void loop() {
  // put your main code here, to run repeatedly:
   /* Get new sensor events with the readings */
  sensors_event_t a, g, temp;
  mpu.getEvent(&a, &g, &temp);

  
double a_x = a.acceleration.x;
double a_y = a.acceleration.y;
double a_z = a.acceleration.z;

angleAcX = atan2(a_x, a_z);
angleAcX *= RADIAN_TO_DEGREE;
angleAcY = atan2(a_y, a_z);
angleAcY *= RADIAN_TO_DEGREE;
Xstr1 = angleAcX;
Ystr1 = angleAcY;

Serial.print("Angle X: ");
Serial.print(Xstr1);
Serial.print(", Y: ");
Serial.println(Ystr1);

delay(50);
sensors_event_t a_2, g_2, temp_2;
mpu.getEvent(&a_2, &g_2, &temp_2);

double a_x2 = a_2.acceleration.x;
double a_y2 = a_2.acceleration.y;
double a_z2 = a_2.acceleration.z;

angleAcX = atan2(a_x2, a_z2);
angleAcX *= RADIAN_TO_DEGREE;
angleAcY = atan2(a_y2, a_z2);
angleAcY *= RADIAN_TO_DEGREE;
Xstr2 = angleAcX;
Ystr2 = angleAcY;
Serial.print("Angle X: ");
Serial.print(Xstr2);
Serial.print(", Y: ");
Serial.println(Ystr2);

Xstr3 = abs(Xstr1 - Xstr2);
Ystr3 = abs(Ystr1 - Ystr2);
Serial.print("Angle X: ");
Serial.print(Xstr3);
Serial.print(", Y: ");
Serial.println(Ystr3);

double g_x=g_2.gyro.x;
double g_y=g_2.gyro.y;
double g_z=g_2.gyro.z;

double gy_x, gy_y, gy_z;

gy_x = g_x * RADIAN_TO_DEGREE * 131;
gy_y = g_y * RADIAN_TO_DEGREE * 131;
gy_z = g_z * RADIAN_TO_DEGREE * 131;
Serial.print("gyro X: ");
Serial.print(gy_x);
Serial.print(", Y: ");
Serial.print(gy_y);
Serial.print(", Z: ");
Serial.println(gy_z);

delay(50);
sensors_event_t a_3, g_3, temp_3;
mpu.getEvent(&a_3, &g_3, &temp_3);
int n=0;

a_x = a_3.acceleration.x;
a_y = a_3.acceleration.y;
a_z = a_3.acceleration.z;

angleAcX = atan2(a_x, a_z);
angleAcX *= RADIAN_TO_DEGREE;
angleAcY = atan2(a_y, a_z);
angleAcY *= RADIAN_TO_DEGREE;
Serial.print("Angle X: ");
Serial.print(Xstr1);
Serial.print(", Y: ");
Serial.println(Ystr1);

Xstr1 = angleAcX;
Ystr1 = angleAcY;

if(abs(Xstr3) >= 25 || abs(Ystr3) >= 25){
  if (abs(gy_x) >= 10000 || abs(gy_y) >= 10000 || abs(gy_z) >= 10000){
    if(abs(Xstr1) >= 70 || abs(Ystr1) >= 70) n=1;
  }
}
 Serial.print("slipped :");
 Serial.println(n);
 
  Firebase.setFloat("slipped", n);
  // handle error
  if (Firebase.failed()) {
      Serial.print("setting /number failed:");
      Serial.println(Firebase.error());  
      return;
  }
 if(n==1) delay(1000);
  
  delay(100);
}
