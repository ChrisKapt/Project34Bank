import com.fazecast.jSerialComm.*;
import java.util.Scanner;

public class Test_com {
    public static void main(String[] args) {
        SerialPort ports[] = SerialPort.getCommPorts();

        System.out.println("Select port: ");
        int i =1;
        for(SerialPort port: ports){
            System.out.println(i++ +  " " +port.getSystemPortName());
        }

        Scanner s = new Scanner(System.in);
        int chosenport = s.nextInt();

        SerialPort port = ports[chosenport -1];
        if(port.openPort()){
            System.out.println("Succesfully opened");
        }else{
            System.out.println("Failed to open");
            return;
        }

        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0,0);

        Scanner p = new Scanner(port.getInputStream());
        while(p.hasNextLine()){
            System.out.println(p.nextLine());
        }
    }
}
