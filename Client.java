package Database;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("145.", 8443);

        DataOutputStream dos = new DataOutputStream(s.getOutputStream());

        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));

        //Hier moet code voor input vanaf intern

        String str , str1;

//        while (!(str == .....).equals("Exit")){
//            dos.writeBytes(str + "\n");
            str1 = br.readLine();
            System.out.println(str1);
//        }

        dos.close();
        br.close();
//        ... . close();

        s.close();
    }

    //hahaiavaogsisvsj
    //substring(14,17)
    //vsj
}
