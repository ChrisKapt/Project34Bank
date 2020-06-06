package GUI;

import java.io.IOException;

import Database.SelectApp;
import com.fazecast.jSerialComm.*;
import javax.swing.*;
import java.io.InputStream;
import java.rmi.ServerError;
import java.time.LocalTime;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommunicatieKeypad extends JPanel{
    SerialPort sp;
    String input = "";
    String invoer = "";
    String key = "";
    String[] keys = {"1","2","3","4","5","6","7","8","9","0"};


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

    //String die gestuurd moet worden is transNR+accountNR+BILL10+BILL20+BILL50+printReceipt+date
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
        key = getInput();
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

//        if(frameIncome.equalsIgnoreCase("introscreen")) {
//            if(key.equalsIgnoreCase("D")){
//                closePort();
//                 welcome = new WelcomeScreen();
//                welcome.setVisible(true);
//                frame.setVisible(false);
//            }
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
//            }

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

    public boolean rfidCheck(){
        if(input.length() > 10){
            return true;
        }
        return false;
    }

    public void rfidLog(JFrame frame) throws InterruptedException {
        SelectApp rfidSelect = new SelectApp();
        String rfid = getInput();
        String RFIDIn = "NK-GUCI-12345678";
//        System.out.println(rfid);
            System.err.println("Checking");
            System.out.println(rfid);
        System.err.println(RFIDIn);
            if (rfid.equals(RFIDIn)) {
                System.err.println("Correct pass");
                closePort();
                WelcomeScreen welcome = new WelcomeScreen(rfid);
                welcome.setVisible(true);
                frame.setVisible(false);
        }
        System.err.println("NOT FOUND");
        clearInput();
//        String[] rfids = rfidSelect.selectRFID();
//        for(int i=0; i<10 ; i++){
//            if(rfid.equals(rfids[i])){
//                System.out.println("goede RFID gevonden");
//                WelcomeScreen welcome = new WelcomeScreen(rfid);
//                welcome.setVisible(true);
//                frame.setVisible(false);
//                break;
//            }
//        }
    }

    public void inlog(JFrame frame, String user) throws InterruptedException {
        key = getInput();
        String currentUser = user;
        String password = invoer;
        if(isCijfer(key)) {
            invoer += input;
            clearInput();
        }
//        SelectApp pwSelect = new SelectApp();
//        String correctPass = pwSelect.selectPin(currentUser);
        String correctPass = "0001";
        System.out.println(invoer);
        if(key.equalsIgnoreCase("B")) {
//            if (password.equals(correctPass)) {
            if (password.equalsIgnoreCase(correctPass)) {
                closePort();
                HomeScreen home = new HomeScreen();
                home.setVisible(true);
                frame.setVisible(false);
            } else {
                System.err.println("False");
                invoer = "";
                return;
            }
        }
        clearInput();
            System.out.println(invoer);
        }

        public boolean isCijfer(String key){
            for (String keyInput :keys) {
                if(key.equalsIgnoreCase(keyInput)){
                    return true;
                }
            }
            return false;
        }


    public boolean haveInput(){
        if (input == "") {
            return false;
        }
        return true;
    }

    public void fastWithdrawal(String user) throws InterruptedException {
        key = getInput();
        String pinUser = user.substring(17,19);
        System.out.println(pinUser);
        if(key.equalsIgnoreCase("A")){
//            sendString("0001"+pinUser+"0001011"+datum);
            closePort();
        }else if(key.equalsIgnoreCase("B")){
//            sendString("0001"+pinUser+"0200011"+datum);
            closePort();
        }
        clearInput();
    }

    //fast
    //error
    //thanks
    //custom
    //alles wat ingetikt wordt bij pinnen en custom te zien is.  !!!!!!!!!!!!


    public void customWithdrawal(String user, int amount) throws InterruptedException {
        key = getInput();
        String pinUser = user.substring(17,19);
        System.out.println(pinUser);
        String keuze1;
        int BILL10;
        int BILL20;
        int BILL50;
        //Logica om keuzes te geven
        //bv 170 - 50 = 120 - 100 = 20 - 20  (3x50 en 1x20)
        //bv 20 - 50 = -30 dus gaat niet probeert die - 20 te doen
        //while(amount > 9){
        //if(amount> 50){
        //  BILL50++;
        //  amount -= 50;
        //}else if(amount> 20 && amount < 50){
        //  BILL20++;
        //  amount-= 20;
        //}else if(amount> 10 && amount <20){
        //  BILL10++;
        //  amount-= 10;
        //}
//        keuze1 = BILL10+BILL20+BILL50;
    }


}