//V4 Bank

//                          Info:
//Wire method werkt naar toebehoren en gaat he pas doen als alle info vanuit java gekomen is.
//Als er een pas op de RFID scanner blijft liggen blijft die niet de UID spammen.
//Als een key ingedrukt is stuurt hij deze gelijk door.
//De volgorde van de string is bekend bij de arduino en zodra de string is verdeeld zal dit zorgen dat de Wire aangaat voor 1 keer.


#include <MFRC522.h>
#include <Keypad.h>
#include <SPI.h>
#include <Wire.h>

// Wiring Keypad
//  123A look
//  456B
//  789C
//  *0#D
//  
//      Left to Right
//  A3       1
//  A2       2
//  A1       3
//  A0       4
//  5        5
//  4        6
//  3        7
//  2        8
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
MFRC522::MIFARE_Key authKey;
String tagUID;

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

String date = "01-03-2020 ";
String accountNR = "24 ";
byte BILL10 = 0;
byte BILL20 = 0;
byte BILL50 = 1;
String transNR = "1023 ";
int printReceipt = 3;

//Lengte van chars die worden gestuurd via I2C
char datum[16];
char account[3];
char transactie [5];

int ibanBlock = 1;
byte readBackBlock[16];


//keypad creeren
Keypad keypad = Keypad(makeKeymap(keymap), rowPins, colPins, numRows, numCols);


void setup() {
  Serial.begin(9600);
  Wire.begin();
  SPI.begin();
  //Zorgt ervoor dat de mfrc522 gaat werken
  mfrc522.PCD_Init();
  for(byte index = 0; index<6;index++){
    authKey.keyByte[index] = 0xFF;
  }
  while(!Serial){
    
  }
}

void loop() {
  //String voor alle data uit de java communicatie
  String javaData = "";
  if(Serial.available() > 0){
    javaData = Serial.readStringUntil('!');
    //javaData = Serial.read();   OUDE Code
  }
//
//  Serial.println(javaData);
  keyCheck();

    if(mfrc522.PICC_IsNewCardPresent() && mfrc522.PICC_ReadCardSerial()){
     readRFID(ibanBlock, readBackBlock);
     for(int j = 0; j< 16; j++){
      Serial.write(readBackBlock[j]);
    }
    Serial.println("");
    //Deze zorgt ervoor dat als er een pas te zien is en die boven de RFID reader gehouden blijft worden niet gaat spammen met UID
    mfrc522.PICC_HaltA();
    mfrc522.PCD_StopCrypto1();
    }

  //Als alle informatie weg gezet is wat nodig is voor andere arduino zal deze dit sturen (decodingdata geeft true terug als dit gedaan is anders false
  if(decodingData(javaData)){
      requestEvent();
   }
}

String rfidCheck() {
      //print de tagM
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
        key_pressed = keypad.getKey();
        
        if(key_pressed){
         Serial.write(key_pressed);
//         BILL10 = key_pressed;
//         requestEvent();
        }
}

//Check of er genoeg data is gestuurd om de andere arduino te laten werken
bool decodingData(String dataI) {
  if (dataI == NULL) {
    return false;
  }
  int dataLength = dataI.length();
  if (dataLength != 28) {
    return false;
  }

  //Alle data apart zetten op de variabele plekken
//  date = dataI.substring(0, 15);
//  accountNR = dataI.substring(15, 29);
//  BILL10 = dataI.substring(29, 31).toInt();
//  BILL20 = dataI.substring(31, 33).toInt();
//  BILL50 = dataI.substring(33, 35).toInt();
//  transNR = dataI.substring(35,43);
//  printReceipt = dataI.substring(dataLength - 1, dataLength).toInt();

  transNR = dataI.substring(0,4);
  accountNR= dataI.substring(4,6);
  BILL10 = dataI.substring(6,8).toInt();
  BILL20 = dataI.substring(8,10).toInt();
  BILL50 = dataI.substring(10,12).toInt();
  printReceipt = dataI.substring(12,13).toInt();
  date = dataI.substring(13,28);
  return true;
}


int readRFID(int blockNumber, byte arrayAddress[]) 
{
  int largestModulo4Number=blockNumber/4*4;
  int trailerBlock=largestModulo4Number+3;//determine trailer block for the sector

  //authentication of the desired block for access
  byte status = mfrc522.PCD_Authenticate(MFRC522::PICC_CMD_MF_AUTH_KEY_A, trailerBlock, &authKey, &(mfrc522.uid));

  if (status != MFRC522::STATUS_OK) {
         Serial.print("PCD_Authenticate() failed (read): ");
         Serial.println(mfrc522.GetStatusCodeName(status));
         return 3;//return "3" as error message
  }

//reading a block
byte buffersize = 18;//we need to define a variable with the read buffer size, since the MIFARE_Read method below needs a pointer to the variable that contains the size... 
status = mfrc522.MIFARE_Read(blockNumber, arrayAddress, &buffersize);//&buffersize is a pointer to the buffersize variable; MIFARE_Read requires a pointer instead of just a number
  if (status != MFRC522::STATUS_OK) {
          Serial.print("MIFARE_read() failed: ");
          Serial.println(mfrc522.GetStatusCodeName(status));
          return 4;//return "4" as error message
  }
//  Serial.println("block was read");
}

void requestEvent(){
  //Zet alles van de string in een charArray eerder geintialiseerd op deze straks via I2C te sturen
  date.toCharArray(datum, 16);
  accountNR.toCharArray(account, 3);
  transNR.toCharArray(transactie, 5);

  Wire.beginTransmission(10);
  //Stuurt lengte van de string de chars achter elkaar dit wordt in de andere arduino precies hetzelfde gedaan alleen vangt die het dan op
  for(int i = 0; i < 16; i++){
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
  Wire.write(printReceipt);
  Wire.endTransmission();
}
