#include "Adafruit_Thermal.h"
#include "SoftwareSerial.h"
#include EEPROM.h

#define TX_PIN 5
#define RX_PIN 3
#define CONNECTED_SLAVES 4

SoftwareSerial mySerial(RX_PIN, TX_PIN);
Adafruit_Therminal printer(&mySerial);

int address:
int wantsReceipt;
String bankAccount ="";
String dataTime = "";
int BILL10;
int BILL20;
int BILL50;
String transNum ="";

class Stepper{
  uint8_t pins[4], stepCount;
  uint16_t minMicroDelay = 1100, stepsPerRevolution = 4096, stepsToTake;
  uint32_t timer;

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
      if(micros() > timer){
        if(stepsToTake>0){
          stepsToTake--;
          timer = micros() +minMicroDelay;
          Switch(stepCount){
            case 0:
               digitalWrite(pins[1],LOW);
               break;
           case 1:
               digitalWrite(pins[3],HIGH);
               break;
           case 2:
               digitalWrite(pins[0],LOW);
               break;
           case 3:
               digitalWrite(pins[2],HIGH);
               break;
           case 4:
               digitalWrite(pins[3],LOW);
               break;
           case 5:
               digitalWrite(pins[1],HIGH);
               break;
           case 6:
               digitalWrite(pins[2],LOW);
               break;
            case 7:
               digitalWrite(pins[0],HIGH);
               break;
          }
          if(stepCount < 7){
            stepCount++;
          }else{
            stepCount = 0;
          }
        }
      }
    }
}

void setup() {
  // put your setup code here, to run once:

}

void loop() {
  // put your main code here, to run repeatedly:

}

void dispenseMoney() {
    if ((int)EEPROM.read(10) >= bill10 && (int)EEPROM.read(20) >= bill20 && (int)EEPROM.read(50) >= bill50) {  // check if we have enough bills

        if (wantsReceipt) {
            printReceipt(true);   
        }

        EEPROM.write(10, (int)EEPROM.read(10) - bill10);
        EEPROM.write(20, (int)EEPROM.read(20) - bill20);
        EEPROM.write(50, (int)EEPROM.read(50) - bill50);

        bill10Motor.dispense(bill10);
        bill20Motor.dispense(bill20);
        bill50Motor.dispense(bill50);

        bill10Motor.start();
        bill20Motor.start();
        bill50Motor.start();

        while (bill10Motor.isDispensing() || bill20Motor.isDispensing() || bill50Motor.isDispensing()) {
            bill10Motor.takeStep();
            bill20Motor.takeStep();   // let the motors dispense
            bill50Motor.takeStep();
        }

        bill10Motor.sleep();
        bill20Motor.sleep();
        bill50Motor.sleep();

        Wire.beginTransmission(address);
        Wire.write("su");         // success!
        Wire.endTransmission();
    } else {
        Wire.beginTransmission(address);
        Wire.write("fa");         // Fail...
        Wire.endTransmission();
      printReceipt(false);        // print a receipt with: TRANSACTION FAILED
    }
    
    delay(10);
    sendBillCount();
}

void sendBillCount() {  // creates a compact string with the count of every bill, and sends it to every client
    String billCount = (String)(int)EEPROM.read(10) + ":" + (String)(int)EEPROM.read(20) + ":" + (String)(int)EEPROM.read(50);
    for (int address = 8; address < (8 + CONNECTED_SLAVES); address++) {
        Wire.beginTransmission(address);
        Wire.write(billCount.c_str());
        Wire.endTransmission();     // send
    }
}

void printReceipt(boolean success) {

    printer.justify('C');        // center
    printer.boldOn();         // bold
    printer.setSize('L');       // Large

    printer.println("Poseidon Banking");  // bank name on top
    printer.println("________________");  // seperating line

    printer.justify('L');       // left
    printer.setSize('S');       // small

    printer.println(addSpaces(dateTime.substring(0, 5), dateTime.substring(5, 15)));  // time    date

    printer.boldOff();          

    printer.println(("- - - - - - - - - - - - - - - -"));   // seperating line

    printer.println(addSpaces("Account :", "XXXX XXXX XXXX " + bankAccount.substring(12, 14) ));  // iban
    printer.println(addSpaces("Amount :", String(10 * bill10 + 20 * bill20 + 50 * bill50) +  " EUR"));  // amount
    printer.println(addSpaces("Transaction# :", transNum));   // transaction ID
    printer.println(addSpaces("ATM# :", (String)address));    // ATM number, which is the I2C address

    printer.println("- - - - - - - - - - - - - - - -");   // line

    printer.feed(1);      // \n
    printer.justify('C');   // center
    printer.boldOn();     // bold
  
  if (success){
      printer.println("Have a nice day!");
  } else{
    printer.println("TRANSACTION FAILED");      // receipt for the failed transaction
    printer.println("PLEASE CONTACT LOCAL BANK");
  }

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
