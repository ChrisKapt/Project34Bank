package GUI;

import java.io.IOException;
import com.fazecast.jSerialComm.*;
import javax.swing.*;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommunicatieKeypad extends JPanel{
    SerialPort sp;
    String input = "";

    public CommunicatieKeypad(String com ){
        sp = SerialPort.getCommPort(com);
        sp.setComPortParameters(9600, 8, 1, 0); // default connection settings for Arduino
//        sp.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0); // block until bytes can be written
        //port wordt gelijk na aanmaken (en als de port er is opengezet)
        if (sp.openPort()) {
            System.out.println("Port is open :)");
        } else {
            //Port is er niet dus zegt kan niet open en return;
            System.out.println("Failed to open port :(");
            return;
        }

        sp.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(SerialPortEvent serialPortEvent) {
                if(serialPortEvent.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
                    return;



                //    MANIER 2:
                //Andere manier dat ook werkt ligt eraan wat we willen gebruiken zijn beide even snel (de scanner is wel accurater (op basis van de keren die ik getest hebt))
                byte[] newData = new byte[sp.bytesAvailable()];
                sp.readBytes(newData,newData.length);
                input += new String(newData);
//                System.out.println(input);
            }
        });
    }

    public void sendString(String s) throws IOException, InterruptedException {

        //send string
        try {
            Thread.sleep(2000);
//        s += time;
            s += "!"; //de arduino leest de string tot hij een ! tegenkomt
            System.out.println(s.length());
            sp.getOutputStream().write(s.getBytes());
            sp.getOutputStream().flush();
            System.out.println("Sent String: " + s);
            Thread.sleep(1000);
        } catch (Exception e) {
        }

    }


    public String getInput() throws InterruptedException {
//        is niet nodig maar weerhoud wel van krijgen van 0
        while(input == ""){
            Thread.sleep(1000);
//            System.out.println("Empty");
//            if(haveInput())
//                break;
        }
        return input;
    }

    //Om de port te sluiten nadat je alle informatie die je wilde gehad hebt zodat deze niet onnodig open blijft staan of input kan veranderen (miss nodig denk niet perse essentieel)
    public void closePort(){
        if(sp.isOpen()){
            if(sp.closePort()){
                System.out.println("Closed");
            }else{
                System.out.println("Still open");
            }
        }else{
            System.out.println("Port was already closed");
        }
    }

    public void clearInput(){
        input = "";
    }

    public boolean portOpen(){
        if(sp.isOpen()){
            return true;
        }
        return false;
    }

    //Maak klasse aan in klas
    //if(naam.haveInput){   Checkt of er input mocht dit niet het geval zijn gaat de klok ongestoord door
    //try{
    //com.hotkeyKeypad(frame);
    //}Catch(InterruptedException e){
    //e.printStackTrace();
    //}

    public void hotkeyKeypad(JFrame frame) throws InterruptedException {
        String frameIncome = frame.getTitle();;
        Balance balance;
        Withdrawal_Options options;
//      FastWithdraw fast;
        CashWithdrawl cashWithdrawl;
        HomeScreen home;
        String key = getInput();
        System.out.println(frameIncome);

        System.out.println(key);
        if (frameIncome.equalsIgnoreCase("cashWithdrawal")) {
            //        if(key.equalsIgnoreCase("A")){
//            fast = new Fastwithdraw();
//            fast.setVisible(true);
//            frame.setVisible(false);
//        }

            if (key.equalsIgnoreCase("B")) {
                balance = new Balance();
                balance.setVisible(true);
//                frame.setVisible(false);
            }

            if (key.equalsIgnoreCase("C")) {
                options = new Withdrawal_Options();
                options.setVisible(true);
//            frame.setVisible(false);
            }
        }
        if (frameIncome.equalsIgnoreCase("homescreen")) {
            if (key.equalsIgnoreCase("C")) {
                cashWithdrawl = new CashWithdrawl();
                cashWithdrawl.setVisible(true);
            }
            if(key.equalsIgnoreCase("D")){
                balance = new Balance();
                balance.setVisible(true);
            }
        }
            if (key.equalsIgnoreCase("#")) {
                home = new HomeScreen();
                home.setVisible(true);
//                frame.setVisible(false);
//            System.out.println("homeScreen");
            }
            frame.setVisible(false);
//

        clearInput();
    }

    public boolean haveInput(){
        if (input == "") {
            return false;
        }
        return true;
    }

}