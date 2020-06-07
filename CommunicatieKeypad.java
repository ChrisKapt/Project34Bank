package GUI;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Database.SelectApp;
import com.fazecast.jSerialComm.*;
import javax.swing.*;

public class CommunicatieKeypad extends JPanel{
    SerialPort sp;
    String input = "";
    String invoer = "";
    String key = "";
    String[] keys = {"1","2","3","4","5","6","7","8","9","0"};
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mmdd-MM-yyyy");
    LocalDateTime ltd;
    String datum;
    SelectApp dtb;
//    System.out.println(time);


    public CommunicatieKeypad(String com ){
        clearInput();
        dtb = new SelectApp();
        sp = SerialPort.getCommPort(com);
        sp.setComPortParameters(9600, 8, 1, 0); // default connection settings for Arduino
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

                byte[] newData = new byte[sp.bytesAvailable()];
                sp.readBytes(newData,newData.length);
                input += new String(newData);
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

    public void hotkeyKeypad(JFrame frame, String user) throws InterruptedException {
        String frameIncome = frame.getTitle();
        Balance balance;
//        WelcomeScreen welcome;
//        IntroScreen begin;
        TankQScreen thanks;
        CustomWithdrawal custom;
        FastWithdrawal fast;
        CashWithdrawl cashWithdrawl;
        HomeScreen home;
        key = getInput();
        System.out.println(frameIncome);

        System.out.println(key);

        //cashWithdrawal
        if (frameIncome.equalsIgnoreCase("cashWithdrawal")) {
            if(key.equalsIgnoreCase("A")){
                closePort();
            fast = new FastWithdrawal(user);
            fast.setVisible(true);
            frame.setVisible(false);
        }

            if (key.equalsIgnoreCase("B")) {
                closePort();
                balance = new Balance(user);
                 balance.setVisible(true);
                frame.setVisible(false);
//                frame.setVisible(false);
            }

            if (key.equalsIgnoreCase("C")) {
                closePort();
                custom = new CustomWithdrawal(user);
                custom.setVisible(true);
                frame.setVisible(false);
//            frame.setVisible(false);
            }
            if(key.equalsIgnoreCase("*")){
                closePort();
                home = new HomeScreen(user);
                home.setVisible(true);
                frame.setVisible(false);
            }
//            if(key.equalsIgnoreCase("#")){
//                closePort();
//                home = new HomeScreen(user);
//                home.setVisible(true);
//                frame.setVisible(false);
//            }
        }

        //Homescreen
        if (frameIncome.equalsIgnoreCase("homescreen")) {
            if (key.equalsIgnoreCase("A")) {
                closePort();
                cashWithdrawl = new CashWithdrawl(user);
                cashWithdrawl.setVisible(true);
                frame.setVisible(false);
            }
            if(key.equalsIgnoreCase("B")){
                closePort();
                balance = new Balance(user);
                balance.setVisible(true);
                frame.setVisible(false);
            }
            if(key.equalsIgnoreCase("C")){
                closePort();
            fast = new FastWithdrawal(user);
            fast.setVisible(true);
            frame.setVisible(false);
            }
//            if(key.equalsIgnoreCase("#")){
//                closePort();
//                home = new HomeScreen(user);
//                home.setVisible(true);
//                frame.setVisible(false);
//            }
        }

        //Balance
        if(frameIncome.equalsIgnoreCase("balance")){
            if(key.equalsIgnoreCase("*")){
                closePort();
                cashWithdrawl = new CashWithdrawl(user);
                cashWithdrawl.setVisible(true);
                frame.setVisible(false);
            }
//            if(key.equalsIgnoreCase("#")){
//                closePort();
//                home = new HomeScreen(user);
//                home.setVisible(true);
//                frame.setVisible(false);
//            }
        }

        if(frameIncome.equalsIgnoreCase("options")){
            if(key.equalsIgnoreCase("*")){
                closePort();
                cashWithdrawl = new CashWithdrawl(user);
                cashWithdrawl.setVisible(true);
                frame.setVisible(false);
            }
//            if(key.equalsIgnoreCase("#")){
//                closePort();
//                home = new HomeScreen(user);
//                home.setVisible(true);
//                frame.setVisible(false);
//            }
        }

        if(frameIncome.equalsIgnoreCase("Fastwithdrawal")||frameIncome.equalsIgnoreCase("Custom")||frameIncome.equalsIgnoreCase("options")||frameIncome.equalsIgnoreCase("Bon")){
            closePort();
            if(key.equalsIgnoreCase("*")){
                closePort();
                cashWithdrawl = new CashWithdrawl(user);
                cashWithdrawl.setVisible(true);
                frame.setVisible(false);
            }
        }

        if(!frameIncome.equalsIgnoreCase("introscreen")){
            if(key.equalsIgnoreCase("#")){
                closePort();
//                begin = new IntroScreen();
//                begin.setVisible(true);
                thanks = new TankQScreen(user);
                thanks.setVisible(true);
                frame.setVisible(false);
            }
        }

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
        ErrorScreen error;
        if (input.length() > 10) {
            String rfid = getInput();
            closePort();
            if (rfidSelect.checkRFID(rfid)) {
                System.err.println("Correct pass");
                WelcomeScreen welcome = new WelcomeScreen(rfidSelect.selectRekeningNummer(rfid));
                welcome.setVisible(true);
                frame.setVisible(false);
            } else {
                error = new ErrorScreen("Not Found", "null");
                error.setVisible(true);
                frame.setVisible(false);
                System.err.println("NOT FOUND");
            }
        }
        clearInput();
    }

    public String keys(String input, String kind) throws InterruptedException {
        key = getInput();
        System.out.println(invoer);
        if(isCijfer(key)){
            invoer += key;
            if(kind.equalsIgnoreCase("Inlog")) {
                input += "X";
            }else{
                input += key;
            }
            clearInput();
        }
        clearInput();
        return input;
        }

    public void inlog(JFrame frame, String user) throws InterruptedException {
        ErrorScreen error;
        key = getInput();
        SelectApp pwSelect = new SelectApp();
        String correctPass = pwSelect.selectPin(user);
//        String currentUser = user
        String password = invoer;
//        if(isCijfer(key)) {
//            invoer += key;
//            clearInput();
//        }
        clearInput();
//        String correctPass = "0001";
        System.out.println(invoer);
        System.out.println(pwSelect.selectPogingen(user));
//        if(key.equalsIgnoreCase("B")) {
//            if (password.equals(correctPass)) {
            if (pwSelect.selectPogingen(user) >= 3) {
                //error SCREEN
                closePort();
                error = new ErrorScreen("Pass blocked", user);
                error.setVisible(true);
                frame.setVisible(false);
                System.err.println("teveel pogingen voor deze pas");
            } else if (password.equalsIgnoreCase(correctPass)) {
                pwSelect.resetPogingen(user);
                closePort();
                HomeScreen home = new HomeScreen(user);
                home.setVisible(true);
                frame.setVisible(false);
            } else {
                closePort();
                System.err.println("False");
                pwSelect.updatePogingen(user);
                error = new ErrorScreen("Pogingen", user);
                error.setVisible(true);
                frame.setVisible(false);
                invoer = "";
//                clearInput();
                return;
            }
        }
//        }

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
    //fast  (Check)
    //Bon printen (check)
    //error (fout poging(melding), pas geblokkeerd, not suffient balance, pass Not Valid) (Check) (even with return)
    //thanks (Done)
    //gebruiker kan zien of het erin komt.

//    Noah dit moet jij nog doen:
    //custom withdrawal & keuzes
    // wordt bij pinnen en custom te zien is.  !!!!!!!!!!!! (Dit moet uitgevonden worden)

    public boolean enoughMoney(String user, double total){
        if(dtb.selectSaldo(user)>=total){
            return true;
        }else{
            return false;
        }
    }

    public void pinBedrag(JFrame frame, String user, int bill10, int bill20, int bill50, int bon) throws IOException, InterruptedException {
        double bedrag = (double) bill10*10+bill20*20+bill50*50;
        String userDigits = user.substring(6,8);
        TankQScreen thanks;
        ErrorScreen error;
        ltd = LocalDateTime.now();
        datum = dtf.format(ltd);
        System.out.println("BILL10 "+bill10+" BILL20"+bill20+" BILL50"+bill50);
        if(enoughMoney(user, bedrag)){
            dtb.updateSaldo(user, bedrag);
            int id = dtb.getNextId();
            dtb.addTransactie(user, bedrag, datum, id);
            sendString(addZero(id,4)+userDigits+addZero(bill10,2)+addZero(bill20,2)+addZero(bill50,2)+bon+datum);
            thanks = new TankQScreen(user);
            thanks.setVisible(true);
            frame.setVisible(false);
            closePort();
        }else{
            error = new ErrorScreen("Not enough money", user);
            error.setVisible(true);
            frame.setVisible(false);
            closePort();
        }
        clearInput();
    }
    public String addZero(int getal, int totalLength){
        String s = String.valueOf(getal);
        for(int i=s.length(); i<totalLength; i++){
            s = "0" + s;
        }
        return s;
    }

    public void errorMessage(String error, String user, JFrame frame){
        CashWithdrawl cash;
        IntroScreen begin;
        WelcomeScreen inlog;
        CustomWithdrawal custom;

        closePort();
        if(error.equalsIgnoreCase("Not enough money")){
            cash = new CashWithdrawl(user);
            cash.setVisible(true);
            frame.setVisible(false);
        }
        else if(error.equalsIgnoreCase("Pas BLOCKED")){
            begin = new IntroScreen();
            begin.setVisible(true);
            frame.setVisible(false);
        }
        else if(error.equalsIgnoreCase("Pogingen")){
            inlog = new WelcomeScreen(user);
            inlog.setVisible(true);
            frame.setVisible(false);
        }
        else if(error.equalsIgnoreCase("not Found")){
            begin= new IntroScreen();
            begin.setVisible(true);
            frame.setVisible(false);
        }
        else if(error.equalsIgnoreCase("Not okay")){
            custom = new CustomWithdrawal(user);
            custom.setVisible(true);
            frame.setVisible(false);
        }
    }


  /*  public void customWithdrawal(String user, String amount) throws InterruptedException {
        key = getInput();
        String pinUser = user.substring(17, 19);
        System.out.println(pinUser);
        String keuze1;
        int BILL10;
        int BILL20;
        int BILL50;
        new CustomWithdrawal();
        while (true) {
            Thread.yield();
            //Get keypad input
            keuze1 = key;
            //If a number is pressed add this to the total amount
            if (keuze1 != null) {
                switch (keuze1) {
                    case "0":
                        amount = amount + "0";
                        break;
                    case "1":
                        amount = amount + "1";
                        break;
                    case "2":
                        amount = amount + "2";
                        break;
                    case "3":
                        amount = amount + "3";
                        break;
                    case "4":
                        amount = amount + "4";
                        break;
                    case "5":
                        amount = amount + "5";
                        break;
                    case "6":
                        amount = amount + "6";
                        break;
                    case "7":
                        amount = amount + "7";
                        break;
                    case "8":
                        amount = amount + "8";
                        break;
                    case "9":
                        amount = amount + "9";
                        break;
                    //If B is pressed check if the amount is dividable by 10, not larger than 250 and not smaller than 10 and then withdraw
                    case "B":
                        int i = Integer.parseInt(amount);
                        if (i % 10 > 0) WaitingScreen("The ATM only dispenses 10, 20 and 50 euro bills");
                        if (i > 250) WaitingScreen("The maximum amount this ATM dispenses is 250 euro's");
                        if (i < 10) WaitingScreen("The minimum amount of this ATM is 10 euro's");
                        else checkWithdrawal(amount, false);
                        break;
                    //If C is pressed clear the custom amount entered
                    case "C":
                        amount = "";
                        user.customAmount(amount);
                        break;
                    //If # is pressed abort the process
                    case "#":
                        new WelcomeScreen();
                        break;
                    //If * is pressed go to home screen
                    case "*":
                        new HomeScreen();
                        break;
                }
            }
        }
    }
*/

}