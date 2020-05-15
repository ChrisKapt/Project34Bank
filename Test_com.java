import com.fazecast.jSerialComm.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Test_com {
    public static void main(String[] args) {
        SerialPort port = SerialPort.getCommPort("COM5");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        if(port.openPort()){
            System.out.println("Succesfully opened");
        }else{
            System.out.println("Failed to open");
            return;
        }

        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0,0);

        Scanner p = new Scanner(port.getInputStream());
        while(p.hasNextLine()){
//            LocalDateTime ldt = LocalDateTime.now();
//            System.out.println(dtf.format(ldt));
            System.out.println(p.nextLine());
        }
    }
}
