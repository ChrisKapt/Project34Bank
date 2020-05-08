//V1.1.5.5 Bank
//Wire method request is made and needs to be tested
//

#include <MFRC522.h>
#include <Keypad.h>
#include <SPI.h>
#include <Wire.h>

#define Slave_Address 9
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

//(RST, SS) pins
MFRC522 mfrc522 (10, 9);
String tagUID = "BB D7 17 0A";
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

String date;
String accountNR;
String BILL10;
String BILL20;
String BILL50;
String transNR;
int printReceipt = 0;
int readyToWithdraw = 0;

//keypad creeren
Keypad keypad = Keypad(makeKeymap(keymap), rowPins, colPins, numRows, numCols);


void setup() {
  Serial.begin(9600);
  Wire.begin();
  Wire.onRequest(requestEvent);
//Wire.onReceive(receiveEvent);
  SPI.begin();
  //Zorgt ervoor dat de mfrc522 gaat werken
  mfrc522.PCD_Init();
}

void loop() {
  String javaData = "";
  while(Serial.available() > 0){
    javaData += (char)Serial.read();
  }
  
  keyCheck();
  
  rfidCheck();
  
  if(decodingData(javaData)){
      readyToWithdraw = 1;
  }
}

void rfidCheck() {
      //print de tag
       if (mfrc522.PICC_IsNewCardPresent()&& mfrc522.PICC_ReadCardSerial()) {
      for (byte j = 0; j <mfrc522.uid.size; j++){
        //Serial.print(mfrc522.uid.uidByte[j] < 0x010 ? " 0" : " ");
        Serial.print(mfrc522.uid.uidByte[j], HEX);
      }
      mfrc522.PICC_HaltA();
  }
}


void keyCheck() {
    // Serial.println("Pass detected");
        key_pressed = keypad.getKey();
        //Als er een key ingedrukt wordt gaat het hierin en zet de code ""+key. 
        if(key_pressed){
          Serial.println(key_pressed);
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
  BILL10 = dataI.substring(29, 31);
  BILL20 = dataI.substring(31, 33);
  BILL50 = dataI.substring(33, 35);
  transNR = dataI.substring(35,43);
//  printReceipt = dataI.substring(dataLength - 1, dataLength);

  return true;
}

void requestEvent(){
   String myData [7];

   myData[0] = date;
   myData[1] = BILL10;
   myData[2] = BILL20;
   myData[3] = BILL50;
   myData[4] = transNR;
   myData[5] = accountNR;
//   myData[6] = printReceipt;

   Wire.beginTransmission(Slave_Address);
   Wire.write((byte*) myData, sizeof myData);
   Wire.endTransmission();

   delay(100);
}

//void receiveEvent(int howMany) {
//    if (howMany == 1) {
//        requestedData = (int)Wire.read();
//    } else if (howMany == 2) {
//        String progress;
//        progress = (char)Wire.read();
//        progress += (char)Wire.read();
//        Serial.print(progress);
//    } else if (howMany > 4) {
//        String billCount;
//        for (int i = 0; i < howMany; i++) {
//            billCount += (char)Wire.read();
//        }
//        Serial.print(billCount);
//    }
//}
