import java.io.IOException;

public class Testing_main {
    public static void main(String[] args) throws IOException, InterruptedException {
        String input;
//        Communication20 communication = new Communication20();
//        communication.sendString("1002003");
//        COMtest testing = new COMtest("COM5");
////        while(!testing.havingInput()){
////            System.out.println("No data");
////        }
//        while(!testing.havingInput()){
//
//        }
//        input = testing.getInput();
//        System.out.println(input);
//    }
        COM2Test communicate = new COM2Test("COM5");
//        input = communicate.getInput();
//        System.out.println(input);
//        communicate.sendString("13234");
//        input = communicate.getInput();
//        System.out.println(input);
        if (communicate.portOpen()) {
            for (int i = 0; i < 5; i++) {
                input = communicate.getInput();
                System.out.println(input);
                communicate.clearInput();
//            Thread.sleep(1000);
                //communicate.sendString(input);
            }
            communicate.closePort();
        }
    }
}
