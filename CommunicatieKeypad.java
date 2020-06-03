package GUI;

import java.io.IOException;

import Database.SelectApp;
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
    String invoer = "";


    public CommunicatieKeypad(String com ){
        clearInput();
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
            e.printStackTrace();
        }

    }


    public String getInput() throws InterruptedException {
//        is niet nodig maar weerhoud wel van krijgen van 0
        while(input.isEmpty()){
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
        String frameIncome = frame.getTitle();
        Balance balance;
        WelcomeScreen welcome;
        Withdrawal_Options options;
//      FastWithdraw fast;
        CashWithdrawl cashWithdrawl;
        HomeScreen home;
        String key = getInput();
        System.out.println(frameIncome);

        System.out.println(key);

        //cashWithdrawal
        if (frameIncome.equalsIgnoreCase("cashWithdrawal")) {
            //        if(key.equalsIgnoreCase("A")){
//            fast = new Fastwithdraw();
//            fast.setVisible(true);
//            frame.setVisible(false);
//        }

            if (key.equalsIgnoreCase("B")) {
                closePort();
                balance = new Balance();
                 balance.setVisible(true);
                frame.setVisible(false);
//                frame.setVisible(false);
            }

            if (key.equalsIgnoreCase("C")) {
                closePort();
                options = new Withdrawal_Options();
                options.setVisible(true);
                frame.setVisible(false);
//            frame.setVisible(false);
            }
            if(key.equalsIgnoreCase("*")){
                closePort();
                home = new HomeScreen();
                home.setVisible(true);
                frame.setVisible(false);
            }
            if(key.equalsIgnoreCase("#")){
                closePort();
                home = new HomeScreen();
                home.setVisible(true);
                frame.setVisible(false);
            }
        }

        //Homescreen
        if (frameIncome.equalsIgnoreCase("homescreen")) {
            if (key.equalsIgnoreCase("A")) {
                closePort();
                cashWithdrawl = new CashWithdrawl();
                cashWithdrawl.setVisible(true);
                frame.setVisible(false);
            }
            if(key.equalsIgnoreCase("B")){
                closePort();
                balance = new Balance();
                balance.setVisible(true);
                frame.setVisible(false);
            }
            if(key.equalsIgnoreCase("C")){
//            fast = new Fastwithdraw();
//            fast.setVisible(true);
//            frame.setVisible(false);
            }
            if(key.equalsIgnoreCase("#")){
                closePort();
                home = new HomeScreen();
                home.setVisible(true);
                frame.setVisible(false);
            }
        }

        //Balance
        if(frameIncome.equalsIgnoreCase("balance")){
            if(key.equalsIgnoreCase("*")){
                closePort();
                cashWithdrawl = new CashWithdrawl();
                cashWithdrawl.setVisible(true);
                frame.setVisible(false);
            }
            if(key.equalsIgnoreCase("#")){
                closePort();
                home = new HomeScreen();
                home.setVisible(true);
                frame.setVisible(false);
            }
        }

        if(frameIncome.equalsIgnoreCase("options")){
            if(key.equalsIgnoreCase("*")){
                closePort();
                cashWithdrawl = new CashWithdrawl();
                cashWithdrawl.setVisible(true);
                frame.setVisible(false);
            }
            if(key.equalsIgnoreCase("#")){
                closePort();
                home = new HomeScreen();
                home.setVisible(true);
                frame.setVisible(false);
            }
        }

        if(frameIncome.equalsIgnoreCase("introscreen")) {
            if(key.equalsIgnoreCase("D")){
                closePort();
                 welcome = new WelcomeScreen();
                welcome.setVisible(true);
                frame.setVisible(false);
            }
//            if (input.length() == 16) {
                //check met database
                //if(true){
                //welcome = new WelcomeScreen();
                //welcome.setVisible(true);
                //frame.setVisible(false);
                //}
                //else{
                //error = new ErrorScreen();
                //error.setVisible(true);
                //frame.setVisible(false);
//                }
//            }
            }

//        if(frameIncome.equalsIgnoreCase("welcomescreen")){
//            if(key.equalsIgnoreCase("D")){
//                closePort();
//                home = new HomeScreen();
//                home.setVisible(true);
//                frame.setVisible(false);
//            }

            // is het een key checken (asl rfid nogmaals door gestuurd wordt doet hij hier niks mee.
            // check of het mogelijk (cijfers en geen letters)
            // buffer += input
            // laat zien dat er een nieuw cijfer bijgekomen is.
            // als B ingedrukt wordt dan ga je checken met database
            // komt pincode overeen {
            // home = new Homescreen();
            // closePort();
            // home.setVisible(true);
            // frame.setVisible(false);
            //}else{
            // Laten weten dat deze niet correct is
            // if(kansen >= 3){
            // pasBlokkeren.
            //}
//            if(!key.equalsIgnoreCase("A")||!key.equalsIgnoreCase("B")||!key.equalsIgnoreCase("C")||!key.equalsIgnoreCase("D")){
//                invoer += key;
//                //Laat zien op gui dat er een cijfer bijkomt.
//            }
//            if(key.equalsIgnoreCase("B")){
//                //Check bij database
//                if(invoer == pincode){
//                    home = new HomeScreen();
//                    home.setVisible(true);
//                    frame.setVisible(false);
//                    JPasswordField
//                }
//            }
//        }

        clearInput();
    }

//    public void inlog(JFrame frame, String user) throws InterruptedException {
//       String currentUser = user;
//       String password = invoer;
//        SelectApp pwSelect = new SelectApp();
////        String correctPass = pwSelect.selectPin(user);
//        String correctPass = "0001";
//            System.out.println(invoer);
//            if (password.equals(correctPass)) {
//                closePort();
//                HomeScreen home = new HomeScreen();
//                home.setVisible(true);
//                frame.setVisible(false);
//            } else {
//                System.err.println("False");
//                invoer = "";
//            invoer += input;
//            clearInput();
//            System.out.println(invoer);
//        }

        //        HomeScreen home;
//        String key = getInput();
//        if(!key.equalsIgnoreCase("A")||!key.equalsIgnoreCase("B")||!key.equalsIgnoreCase("C")||!key.equalsIgnoreCase("D")){
//            invoer = invoer+ key;
//            //Laat zien op gui dat er een cijfer bijkomt.
//        }
//        if(key.equalsIgnoreCase("B")){
//            //Check bij database
//            if(invoer == pincode){
//                closePort();
//                home = new HomeScreen();
//                home.setVisible(true);
//                frame.setVisible(false);
//                invoer = 0;
//            }else{
//                System.err.println("ERROR");
//                invoer = 0;
//            }
//        }
//        clearInput();
//        System.out.println(invoer);
//    }

    public boolean haveInput(){
        if (input == "") {
            return false;
        }
        return true;
    }

}