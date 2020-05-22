//package net.sqlitetutorial;

import java.io.IOException;

import com.fazecast.jSerialComm.SerialPort;
/**
 * Simple application that is part of an tutorial. 
 * The tutorial shows how to establish a serial connection between a Java and Arduino program.
 * @author Michael Schoeffler (www.mschoeffler.de)
 *
 */
public class ArduinoCom {
    public static void main(String[] args) throws IOException, InterruptedException {
        ArduinoCom com = new ArduinoCom();
        com.sendString("haii");
    }

    //opent de port naar arduino, stuurt een string, doet de port weer dicht.
    public void sendString(String s) throws IOException, InterruptedException {
        SerialPort sp = SerialPort.getCommPort("COM3"); // device name TODO: must be changed
        sp.setComPortParameters(9600, 8, 1, 0); // default connection settings for Arduino
        sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0); // block until bytes can be written

        //open port
        if (sp.openPort()) {
            System.out.println("Port is open :)");
        } else {
            System.out.println("Failed to open port :(");
            return;
        }

        //send string
        try{
            Thread.sleep(2000);
            s += "!"; //de arduino leest de string tot hij een ! tegenkomt
            sp.getOutputStream().write(s.getBytes());
            sp.getOutputStream().flush();
            System.out.println("Sent String: " + s);
            Thread.sleep(1000);
        } catch(Exception e){}

        //close port
        if (sp.closePort()) {
            System.out.println("Port is closed :)");
        } else {
            System.out.println("Failed to close port :(");
            return;
        }

    }
}