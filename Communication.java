import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Communication {
    private SerialPort port;
    private String input = null;

    public Communication(String com){
        port = SerialPort.getCommPort(com);
        port.setBaudRate(9600);
        port.openPort();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        port.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(SerialPortEvent serialPortEvent) {
                if (serialPortEvent.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
                    return;

                Scanner p =new Scanner(port.getInputStream());
                System.out.println(p.nextLine());
                LocalDateTime ldt = LocalDateTime.now();
                System.out.println(dtf.format(ldt));
            }
        });
    }

    public void clear(){
        input = null;
    }

    public String getInput(){
        return input;
    }

    }