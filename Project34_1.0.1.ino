//V1.0.11Bank

#include <MFRC522.h>
#include <Keypad.h>
#include <SPI.h>

//(RST, SS) pins
MFRC522 mfrc522 (10, 9);
String tagUID = "BB D7 17 0A";
//RFIDMode zorgt ervoor dat alles pas in werking gaat als de pas aanwezig is zo niet dan checkt die steeds of dat er een pas is.
boolean RFIDMode = false;
char key_pressed = 0;

//Define de Rows & Cols
const byte numRows = 4;
const byte numCols = 4;

//Hoe de rows en cols eruit ziet
char keymap[numRows][numCols]=   
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
String pass ="3541";
String code ="";

//keypad creeren
Keypad keypad = Keypad(makeKeymap(keymap), rowPins, colPins, numRows, numCols);


void setup() {
  Serial.begin(9600);
  SPI.begin();
  //Zorgt ervoor dat de mfrc522 gaat werken
  mfrc522.PCD_Init();
}

void loop() {
  //Check of er een pass aanwezig is
    if(RFIDMode ==false){
    if ( ! mfrc522.PICC_IsNewCardPresent()) {
      return;
    }
      if(! mfrc522.PICC_ReadCardSerial()){
        return;
      }
      //Stuurt de RFID UID kaart
      Serial.print("UID tag :");
      //Tag cijfer
      String tag = "";
      byte letter;
      //print de tag
      for (byte j = 0; j <mfrc522.uid.size; j++){
        String RFID;
        Serial.print(mfrc522.uid.uidByte[j] < 0x010 ? " 0" : " ");
        Serial.print(mfrc522.uid.uidByte[j], HEX);
        RFID =String(mfrc522.uid.uidByte[j], HEX);
        RFID.toUpperCase();
        tag+= RFID;
      }
      Serial.println();
      RFIDMode = true;
  }
      if (RFIDMode == true){
        // Serial.println("Pass detected");
        key_pressed = keypad.getKey();
        //Als er een key ingedrukt wordt gaat het hierin en zet de code ""+key. 
        if(key_pressed){
          Serial.println(key_pressed);
          code += key_pressed;
        }
        //Als de code lengte hetzelfde is als de pincode dan gaat hij hierin
        if(code.length() == pass.length()){
          //Als de pass en code gelijk zijn gaat hij hierin en zet code leeg
          if(code == pass){
            code = "";
            RFIDMode = false;
          }
          //gooit alleen de code leeg
          else{
            code= "";
            RFIDMode = true;
          }
        }
      }
}
   //}
