import com.fazecast.jSerialComm.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

//Version 2.0
//Als er al een key ingedrukt is voordat de pas is gescant wordt de scan in verschillende delen gestuurd en kan je niks meer met deze informatie.

public class COM2Test{
    SerialPort sp;
    String input;

    public COM2Test(String com){
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

                //  MANIER 1:
                //Scanner scant steeds is er input vanuit de port inputstream
                Scanner p = new Scanner(sp.getInputStream());
////                Wanneer de pas wordt gestuurd zal deze zonder de while een 1 sturen
//                while(p.hasNextLine()){
//                    input = p.nextLine();
////                    System.out.println(input);
//                }

                //    MANIER 2:
                //Andere manier dat ook werkt ligt eraan wat we willen gebruiken zijn beide even snel (de scanner is wel accurater (op basis van de keren die ik getest hebt))
                byte[] newData = new byte[sp.bytesAvailable()];
                sp.readBytes(newData,newData.length);
                input = new String(newData);
                System.out.println(input);
            }
        });
    }

    public void sendString(String s) throws IOException, InterruptedException {

        //send string
        try {
            Thread.sleep(2000);
            s += "!"; //de arduino leest de string tot hij een ! tegenkomt
            sp.getOutputStream().write(s.getBytes());
            sp.getOutputStream().flush();
            System.out.println("Sent String: " + s);
            Thread.sleep(1000);
        } catch (Exception e) {
        }
    }


    public String getInput() throws InterruptedException {
//        is niet nodig maar weerhoud wel van krijgen van 0
        while(input == null){
            Thread.sleep(1000);
            System.out.println("Empty");
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
        input = null;
    }

    public boolean portOpen(){
        if(sp.isOpen()){
            return true;
        }
        return false;
    }
}
