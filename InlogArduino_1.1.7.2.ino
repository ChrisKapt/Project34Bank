//V1.1.5.5 Bank
//Wire method request is made and needs to be tested
//

#include <MFRC522.h>
#include <Keypad.h>
#include <SPI.h>
#include <Wire.h>

#define CONNECTED_SLAVES 4
// Wiring Keypad
//  123A look
//  456B
//  789C
//  *0#D
//                 Left to Right
//  White 1: A3       1
//  Red   1: A2       2
//  White 2: A1       3
//  Blue   : A0       4
//  Red   2: 5        5
//  Orange : 4        6
//  Green  : 3        7
//  White 3: 2        8
//

//   Wiring RFID Reader
//
//  3.3V  : 3.3V
//  RST   : 9
//  GND   : GND
//  MISO  : 12
//  MOSI  : 11
//  SCK   : 13
//  SDA   : 10
//

#define SS_pin 10
#define RST_pin 9

//(RST, SS) pins
MFRC522 mfrc522 (SS_pin, RST_pin);
String tagUID;
//RFIDMode zorgt ervoor dat alles pas in werking gaat als de pas aanwezig is zo niet dan checkt die steeds of dat er een pas is.
char key_pressed = 0;

//Define de Rows & Cols
const byte numRows = 4;
const byte numCols = 4;

int requestedData;

//Hoe de rows en cols eruit ziet
char keymap[numRows][numCols] =
{
  {'1', '2', '3', 'A'},
  {'4', '5', '6', 'B'},
  {'7', '8', '9', 'C'},
  {'*', '0', '#', 'D'}
};

//Waar de Rows en Cols zijn aangesloten
byte rowPins[numRows] = { A3, A2, A1, A0 };
byte colPins[numCols] = { 5, 4, 3, 2 };

//Pass is de code van de pass en code is de keys die erin gezet worden
String pass = "3541";
String code = "";

int address;
String date = "01-03-2020";
String accountNR = "24";
int BILL10 = 2;
int BILL20 = 1;
int BILL50 = 3;
String transNR = "1023";
int printReceipt = 1;
int readyToWithdraw = 0;

//keypad creeren
Keypad keypad = Keypad(makeKeymap(keymap), rowPins, colPins, numRows, numCols);


void setup() {
  Serial.begin(9600);
  Wire.begin(9);
//  Wire.onRequest(requestEvent);
//  Wire.onReceive(receiveEvent);
  SPI.begin();
  //Zorgt ervoor dat de mfrc522 gaat werken
  mfrc522.PCD_Init();
}

void loop() {
  String javaData = "";
  keyCheck();
  while(Serial.available() > 0){
    javaData += (char)Serial.read();
  }
  requestEvent();
  
  if (mfrc522.PICC_IsNewCardPresent()&& mfrc522.PICC_ReadCardSerial()) {
    tagUID= rfidCheck();
    Serial.println(tagUID);
  }

//  requestEvent();
  
  if(decodingData(javaData)){
      readyToWithdraw = 1;
  }
}

String rfidCheck() {
      //print de tag
      String UID;
      for (byte j = 0; j <mfrc522.uid.size; j++){
        //Serial.print(mfrc522.uid.uidByte[j] < 0x010 ? " 0" : " ");
         String Ubyte = String(mfrc522.uid.uidByte[j], HEX);
      //  Serial.print(mfrc522.uid.uidByte[j], HEX);
      UID += Ubyte;
      }
      return UID;
}


void keyCheck() {
    // Serial.println("Pass detected");
        key_pressed = keypad.getKey();
        
        if(key_pressed){
          //Serial.println(key_pressed);
          BILL10 = key_pressed;
        }
        
}

bool decodingData(String dataI) {
  if (dataI == NULL) {
    return false;
  }
  int dataLength = dataI.length();
  if (dataLength != 15) {
    return false;
  }

 // for (int i = 0; i < dataLength; i++) {
//    if (!(isDigit(dataI.charAt(i)))) {
//      return false;
 //   }
 // }

  date = dataI.substring(0, 15);
  accountNR = dataI.substring(15, 29);
  BILL10 = dataI.substring(29, 31).toInt();
  BILL20 = dataI.substring(31, 33).toInt();
  BILL50 = dataI.substring(33, 35).toInt();
  transNR = dataI.substring(35,43);
//  printReceipt = dataI.substring(dataLength - 1, dataLength);

  return true;
}

void requestEvent(){
  String dataS[7];
 // dataS += date+accountNR+BILL10+BILL20+BILL50+transNR+printReceipt;
//
//  dataS[0] = date;
//  dataS[1] = accountNR;
//  dataS[2] = BILL10;
//  dataS[3] = BILL20;
//  dataS[4] = BILL50;
//  dataS[5] = transNR;
//  dataS[6] = printReceipt;

  Wire.beginTransmission(9);
//  Wire.write((char*)dataS, sizeof dataS);
  for(int a = 0; a<7; a++){
    Wire.write(dataS[a].c_str(), sizeof dataS);
//    Serial.println(dataS);
  }
  Wire.endTransmission();
//
Wire.beginTransmission(9);
Wire.write(date.c_str());
Wire.write(accountNR.c_str());
Wire.write((byte*)BILL10);
Wire.write((byte*)BILL20);
Wire.write((byte*)BILL50);
Wire.write(transNR.c_str());
Wire.write(printReceipt);
Wire.endTransmission();
  
//   Wire.beginTransmission(9);
//   Wire.write(dataS.c_str());
//   Wire.endTransmission();

//  Wire.beginTransmission(9);
//  Wire.write(BILL10);
//  Wire.endTransmission();
   delay(500);
 }
