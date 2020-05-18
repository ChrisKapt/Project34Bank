// Dispensor(slave) arduino 
// Code version 2.5 
// Bon Printer werkt naar toebehoren (is uitgebreid naar wensen)
// Wire werkt naar toebehoren alle variabelen worden correct door gestuurd
// Stepper werkt door middel van een timer. (dit moet nog verbeterd worden)

//    Bonprinter aansluiting
// Rood: 5V - 9V
// Groen: 5
// Blauw: 6
// Geel: GND
// Zwart: GND

//    Motor aansluiten (zie motoren aanmaken)
//    name(In1,In2,In3,In4)
//    Vcc = 5V, GND = GND

#include "Adafruit_Thermal.h"
#include "SoftwareSerial.h"
#include <EEPROM.h>
#include <Wire.h>

#define TX_PIN 3
#define RX_PIN 2
#define address 9

//Aanmaken van Printer
SoftwareSerial mySerial(RX_PIN, TX_PIN);
Adafruit_Thermal printer(&mySerial);


int wantsReceipt;
String bankAccount;
String dateTime;
int BILL10;
int BILL20;
int BILL50;
String transNR;
bool dir = false;

//char groote voor de strings om via I2C te ontvangen
char datum[11];
char account[3];
char transactie [5];

void setup() {
  // put your setup code here, to run once:
  mySerial.begin(9600);
  printer.begin();
  Wire.begin(10);
  Serial.begin(115200);
  Wire.onReceive(receiveEvent);
}

void loop() {
  // put your main code here, to run repeatedly:
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

  void start(){
    digitalWrite(pins[0], HIGH);
    digitalWrite(pins[1], HIGH);
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

        //Als wantReceipt is een 1 dan gaat hij in printReceipt true
        if (wantsReceipt == 1) {
            printReceipt(true);   
        }

        BILL10Motor.start();
        BILL20Motor.start();
        BILL50Motor.start();

        //Berekend op basis van aantal biljetten hoelang de dispensers moet draaien
        BILL10Motor.dispense(BILL10);
        BILL20Motor.dispense(BILL20);
        BILL50Motor.dispense(BILL50);

        //Zolang de amount nog niet voldaan is blijft die stappen zetten
        while (BILL10Motor.isDispensing() || BILL20Motor.isDispensing() || BILL50Motor.isDispensing()) {
            BILL10Motor.takeStep();
            BILL20Motor.takeStep();   // let the motors dispense
            BILL50Motor.takeStep();
        }

        BILL10Motor.sleep();
        BILL20Motor.sleep();
        BILL50Motor.sleep();

        //Zet weer naar 0
        BILL10 = 0;
        BILL20 = 0;
        BILL50 = 0;

    delay(10);
}

void printReceipt(boolean success) {

    printer.justify('C');        // center
    printer.boldOn();           // Bold
    printer.setSize('L');       // Large

    printer.println("9UCCI");  // bank name on top

    printer.setSize('S');                 // small
    printer.println("01031 Rotterdam");    // 010 is Rotterdam,3 is c en 1 is group 1
//    printer.println("Wijnhaven 107-109");   //Plaats van de bank momenteel Wijnhaven als plaats aangenomen
//    printer.println("3011 WN Rotterdam");   //Postcode van de bank zodat de klant kan zien waar gepint is

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
    printer.println("- - - - - - - - - - - - - - - -");   
    printer.boldOff();

    printer.feed(1);      // \n

    printer.feed(3);

    printer.setDefault(); // Restore printer to defaults

    transNR = "";
    bankAccount = "";
    wantsReceipt = 0;
    dateTime = "";
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
    while(0 < Wire.available()){
      //Strings die apart als characters worden gestuurd worden opgevangen en in een array gezet.
      for(int i= 0; i< 11; i++){
        datum[i] = Wire.read();
      }
      dateTime =  String (datum); // De hele array van ingekomen input wordt op de variabele plek in deze arduino gezet om gebruikt te worden
      for(int i=0; i< 3; i++){
        account[i] = Wire.read();
      }
      bankAccount = String (account);
      BILL10 = Wire.read();
      BILL20 = Wire.read();
      BILL50 = Wire.read();
      for(int i=0; i<5; i++){
        transactie[i] = Wire.read();
      }
      transNR = String (transactie);
      wantsReceipt=Wire.read();
    }

      //Als het lezen klaaar is gaan de bank geld uitgeven en bon printer als hierom gevraagt wordt.
     dispenseMoney();
}
