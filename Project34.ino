//V1 Bank

#include <MFRC522.h>
#include <Keypad.h>
#include <SPI.h>

MFRC522 mfrc522 (10, 9);
String tagUID = "";
boolean RFIDMode = true;
boolean NormalMode = true;
char key_pressed = 0;

const byte numRows = 4;
const byte numCols = 4;

char keymap[numRows][numCols]= 
{
{'1', '2', '3', 'A'}, 
{'4', '5', '6', 'B'}, 
{'7', '8', '9', 'C'},
{'*', '0', '#', 'D'}
};

byte rowPins[numRows] = { 9, 8, 7, 6 };
byte colPins[numCols] = { 5, 4, 3, 2 };

String pass ="3541";
String code ="";

Keypad keypad = Keypad(makeKeymap(key_pressed), rowPins, colPins, numRows, numCols);

void setup() {
  SPI.begin();
  mfrc522.PCD_Init();
}

void loop() {
  if(NormalMode == false){
  }

  else if (NormalMode == true){
    if(RFIDMode ==true){
    if ( ! mfrc522.PICC_IsNewCardPresent()) {
      return;
    }
      if(! mfrc522.PICC_ReadCardSerial()){
        return;
      }
      Serial.print("UID tag :");
      String tag = "";
      byte letter;
      for (byte j = 0; j <mfrc522.uid.size; j++){
        Serial.print(mfrc522.uid.uidByte[j] < 0x010 ? " 0" : " ");
        Serial.print(mfrc522.uid.uidByte[j], HEX);
        tag.concat(String(mfrc522.uid.uidByte[j] < 0x010 ? " 0" : " "));
        tag.concat(String(mfrc522.uid.uidByte[j], HEX));
      }
      Serial.println();
      Serial.print("Message: ");
      tag.toUpperCase();
      if(tag.substring(1) == tagUID){
        RFIDMode == false;
      }
      else{
        
      }
    }
      if (RFIDMode == false){
        if(key_pressed){
          code += key_pressed;
        }
        if(code.length() == pass.length()){
          if(code == pass){
            code = "";
            RFIDMode = true;
          }
          else{
            code= "";
            RFIDMode = true;
          }
        }
      }
    }
   }
