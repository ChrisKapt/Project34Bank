// Dispensor(slave) arduino 
// Code version 1.5.1 
// Bon Printer werkt naar toebehoren
// Wire staat erin (moet nog wel getest worden)
// Stepper werkt door middel van een timer 

//    Bonprinter aansluiting
// Rood: 5V - 9V
// Groen: 5
// Blauw: 6
// Zwart: GND
//

//    Motor aansluiten (zie motoren aanmaken)

#include "Adafruit_Thermal.h"
#include "SoftwareSerial.h"
#include <EEPROM.h>
#include <Wire.h>

#define TX_PIN 3
#define RX_PIN 2
#define CONNECTED_SLAVES 4

SoftwareSerial mySerial(RX_PIN, TX_PIN);
Adafruit_Thermal printer(&mySerial);

int address;
int wantsReceipt;
String bankAccount ="";
String dateTime = "";
int BILL10;
int BILL20;
int BILL50;
String transNR ="";
bool dir = false;
byte wireArray[7];

void setup() {
  // put your setup code here, to run once:
  mySerial.begin(9600);
  printer.begin();
  Serial.begin(115200);

  Wire.begin(9);
  Wire.onReceive(receiveEvent);
}

void loop() {
  // put your main code here, to run repeatedly:
  if(Serial.available()){
    String request = "";
    while(Serial.available()> 0){
     request += (char)Serial.read();
    }
  }
    receiveEvent();

    bankAccount = "";
    dateTime = "";
    transNR  = "";
    dispenseMoney();
  }

class Stepper{
  uint8_t pins[4];
  int duur, _step = 2;

  public:
    Stepper(uint8_t pinA, uint8_t pinB, uint8_t pinC, uint8_t pinD){
      pins[0] = pinA;
      pins[1] = pinB;
      pins[2] = pinC;
      pins[3] = pinD;

      for(int pin : pins){
        pinMode(pin, OUTPUT);
      }
    }

    void takeStep(){
    while(millis()< duur){
 switch(_step){ 
   case 0: 
     digitalWrite(pins[0], LOW);  
     digitalWrite(pins[1], LOW); 
     digitalWrite(pins[2], LOW); 
     digitalWrite(pins[3], HIGH); 
   break;  
   case 1: 
     digitalWrite(pins[0], LOW);  
     digitalWrite(pins[1], LOW); 
     digitalWrite(pins[2], HIGH); 
     digitalWrite(pins[3], HIGH); 
   break;  
   case 2: 
     digitalWrite(pins[0], LOW);  
     digitalWrite(pins[1], LOW); 
     digitalWrite(pins[2], HIGH); 
     digitalWrite(pins[3], LOW); 
   break;  
   case 3: 
     digitalWrite(pins[0], LOW);  
     digitalWrite(pins[1], HIGH); 
     digitalWrite(pins[2], HIGH); 
     digitalWrite(pins[3], LOW); 
   break;  
   case 4: 
     digitalWrite(pins[0], LOW);  
     digitalWrite(pins[1], HIGH); 
     digitalWrite(pins[2], LOW); 
     digitalWrite(pins[3], LOW); 
   break;  
   case 5: 
     digitalWrite(pins[0], HIGH);  
     digitalWrite(pins[1], HIGH); 
     digitalWrite(pins[2], LOW); 
     digitalWrite(pins[3], LOW); 
   break;  
     case 6: 
     digitalWrite(pins[0], HIGH);  
     digitalWrite(pins[1], LOW); 
     digitalWrite(pins[2], LOW); 
     digitalWrite(pins[3], LOW); 
   break;  
   case 7: 
     digitalWrite(pins[0], HIGH);  
     digitalWrite(pins[1], LOW); 
     digitalWrite(pins[2], LOW); 
     digitalWrite(pins[3], HIGH); 
   break;  
   default: 
     digitalWrite(pins[0], LOW);  
     digitalWrite(pins[1], LOW); 
     digitalWrite(pins[2], LOW); 
     digitalWrite(pins[3], LOW); 
   break;  
 } 
 if(dir){ 
   _step++; 
 }else{ 
   _step--; 
 } 
 if(_step>7){ 
   _step=0; 
 } 
 if(_step<0){ 
   _step=7; 
 } 
 delay(1); 
}
  }
  
      void sleep(){
        for(int pin : pins){
          digitalWrite(pin,LOW);
        }
      }


      void dispense(int amount){
        duur = amount * 4350;
      }

      boolean isDispensing(){
        return (bool)(millis()!=duur);
      }
};

//Aanmaken van de steppers (dispensors)
Stepper BILL50Motor(10,11,12,13);
Stepper BILL20Motor(6,7,8,9);
Stepper BILL10Motor(A0,A1,A2,A3);

void dispenseMoney() {
        if (wantsReceipt) {
            printReceipt(true);   
        }

        BILL10Motor.dispense(BILL10);
        BILL20Motor.dispense(BILL20);
        BILL50Motor.dispense(3);

        while (BILL10Motor.isDispensing() || BILL20Motor.isDispensing() || BILL50Motor.isDispensing()) {
            BILL10Motor.takeStep();
            BILL20Motor.takeStep();   // let the motors dispense
            BILL50Motor.takeStep();
        }

        BILL10Motor.sleep();
        BILL20Motor.sleep();
        BILL50Motor.sleep();

        Wire.beginTransmission(address);
        Wire.write("su");         // success!
        Wire.endTransmission();

    delay(10);
}

void printReceipt(boolean success) {

    printer.justify('C');        // center
    printer.boldOn();           // Bold
    printer.setSize('L');       // Large

    printer.println("9UCCI");  // bank name on top

    printer.setSize('S');
    printer.println("01031 Rotterdam");    // 010 is Rotterdam,3 is c en 1 is group 1
    printer.println("Wijnhaven 107-109");   //Plaats van de bank momenteel Wijnhaven als plaats aangenomen
    printer.println("3011 WN Rotterdam");   //Postcode van de bank zodat de klant kan zien waar gepint is

    printer.println("                           ");
    if (success){
      printer.println("Thanks for using the ATM");     //Vriendelijk gedag zeggen en bedanken voor het gebruik
      printer.println("Have a nice day!");
    }else if(!success){
     printer.println("TRANSACTION FAILED");      // receipt for the failed transaction
     printer.println("PLEASE CONTACT LOCAL BANK");
     printer.println("010-......");  //Telefoon nummer hier (in overleg met PO welke telefoon nummer hier moet komen_
    }
    
    printer.justify('L');       // left
    printer.setSize('M');       // Medium
    printer.println("____________________________");  // seperating line


    //printer.println(addSpaces(dateTime.substring(0, 5), dateTime.substring(5, 15)));  // time    date
       
    printer.println("- - - - - - - - - - - - - - - -");   // seperating line
    printer.boldOff();    

    printer.println(addSpaces("Account : ", "XXXX XXXX XXXX " +bankAccount));  // iban
    printer.println(addSpaces("Amount : ", String(10*BILL10 + 20* BILL20 + 50*BILL50)+ " EUR"));  // amount
    printer.println(addSpaces("Transaction# : ", transNR));   // transaction ID

    printer.boldOn();
    printer.println("- - - - - - - - - - - - - - - -");   // line
    printer.boldOff();

    printer.feed(1);      // \n
//    printer.justify('C');   // center
//    printer.boldOn();     // bold

    printer.feed(3);

    printer.setDefault(); // Restore printer to defaults
}

String addSpaces(String lStr, String rStr) {    // adds spaces in between two string
    int len = (lStr + rStr).length();       // so we can print words on the left side and right side of the receipt
                          // e.g. |ACCOUNT:  <-(spaces)->  XXXX XXXX XXXX 35|
                          
    if (len > 31) {                 // receipt length is only 32 (normal) characters
        return lStr + rStr;
    }

    String spaces;
    for (int i = len; i < 32; i++) {
        spaces += ' ';
    }

    return lStr + spaces + rStr;
}

void receiveEvent(){
    byte index = 0;
    Wire.requestFrom(2,3);
    while (Wire.available() > 0 && index < 7){
      wireArray[index] = Wire.read();
      index++;
    }

    dateTime = wireArray[0];
    BILL10 = (int)wireArray[1];
    BILL20 = (int)wireArray[2];
    BILL50 = (int)wireArray[3];
    transNR = wireArray[4];
    bankAccount = wireArray[5];
    wantsReceipt = (int)wireArray[6];
}
