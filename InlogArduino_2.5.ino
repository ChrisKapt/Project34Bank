//V1.1.5.5 Bank
//Wire method request is made and needs to be tested
//

#include <MFRC522.h>
#include <Keypad.h>
#include <SPI.h>
#include <Wire.h>

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

int address;
String date = "01-03-2020 ";
String accountNR = "24 ";
int BILL10 = 2;
int BILL20 = 1;
int BILL50 = 3;
String transNR = "1023 ";
int printReceipt = 1;
int readyToWithdraw = 0;

char datum[11];
char account[3];
char transactie [5];

//keypad creeren
Keypad keypad = Keypad(makeKeymap(keymap), rowPins, colPins, numRows, numCols);


void setup() {
  Serial.begin(9600);
  Wire.begin(9);
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
  
  if (mfrc522.PICC_IsNewCardPresent()&& mfrc522.PICC_ReadCardSerial()) {
    tagUID= rfidCheck();
    Serial.println(tagUID);
  }
  
  if(decodingData(javaData)){
      readyToWithdraw = 1;
      requestEvent();
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
  BILL10 = dataI.substring(29, 31).toInt();
  BILL20 = dataI.substring(31, 33).toInt();
  BILL50 = dataI.substring(33, 35).toInt();
  transNR = dataI.substring(35,43);
//  printReceipt = dataI.substring(dataLength - 1, dataLength);

  return true;
}

void requestEvent(){
  date.toCharArray(datum, 11);
  accountNR.toCharArray(account, 3);
  transNR.toCharArray(transactie, 5);

  Wire.beginTransmission(10);
  for(int i = 0; i < 11; i++){
    Wire.write(datum[i]);
  }
  for(int i = 0; i < 3; i++){
    Wire.write(account[i]);
  }
  Wire.write(BILL10);
  Wire.write(BILL20);
  Wire.write(BILL50);
  for(int i = 0; i < 5; i++){
    Wire.write(transactie[i]);
  }
  Wire.endTransmission();
  
}
