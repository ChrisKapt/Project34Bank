package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;


public class Withdrawal_Options extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    int bedrag1;
    int bedrag2;
    int bedrag3;
    int bedrag4;
    int[][] bills = new int[4][3];

    Withdrawal_Options(String user, int bedrag) {
        // construct components
        CommunicatieKeypad com = new CommunicatieKeypad("COM5");

        bedrag1= bedrag;
        bedrag2 = bedrag;
        bedrag3 = bedrag;
        bedrag4 = bedrag;

        while(bedrag1 >= 50){
            bedrag1-=50;
            bills[0][2]++;
        }
        while(bedrag1 >= 20){
            bedrag1 -= 20;
            bills[0][1]++;
        }
        while(bedrag1 >=10){
            bedrag1-=10;
            bills[0][0]++;
        }

        if(bedrag2>10) {
            bedrag2 -= 20;
            bills[1][1]++;
            while (bedrag2 >= 50) {
                bedrag2 -= 50;
                bills[1][2]++;
            }
            while (bedrag2 >= 20) {
                bedrag2 -= 20;
                bills[1][1]++;
            }
            while (bedrag2 >= 10) {
                bedrag2 -= 10;
                bills[1][0]++;
            }
        }

        if(bedrag3 >= 20){
            bedrag3 -= 20;
            bills[2][0] += 2;
            while (bedrag3 >= 50) {
                bedrag3 -= 50;
                bills[2][2]++;
                System.out.println("50");
            }
            while (bedrag3 >= 20) {
                bedrag3 -= 20;
                bills[2][1]++;
                System.out.println("20");
            }
            while (bedrag3 >= 10) {
                bedrag3 -= 10;
                bills[2][0]++;
                System.out.println("10");
            }
        }

        if(bedrag4> 40){
            bedrag4-= 40;
            bills[3][1] += 2;
            while (bedrag4 >= 50) {
                bedrag4 -= 50;
                bills[3][2]++;
                System.out.println("50");
            }
            while (bedrag4 >= 20) {
                bedrag4 -= 20;
                bills[3][1]++;
                System.out.println("20");
            }
            while (bedrag4 >= 10) {
                bedrag4 -= 10;
                bills[3][0]++;
                System.out.println("10");
            }

        }

        JFrame frame = new JFrame("Options");
        JLabel  name = new JLabel("9ucci");
        JButton choice1 = new JButton(bills[0][0]+"X10 "+bills[0][1]+"X20 "+bills[0][2]+"X50");
        JButton choice2 = new JButton(bills[1][0]+"X10 "+bills[1][1]+"X20 "+bills[1][2]+"X50");
        JButton choice3 = new JButton(bills[2][0]+"X10 "+bills[2][1]+"X20 "+bills[2][2]+"X50");
        JButton choice4 = new JButton(bills[3][0]+"X10 "+bills[3][1]+"X20 "+bills[3][2]+"X50");
        Icon icon = new ImageIcon("C:\\Users\\Noah_\\Downloads\\arrow.png");
        JButton goBack = new JButton(icon);
        Icon iconClose = new ImageIcon("C:\\Users\\Noah_\\Downloads\\closeButton.png");
        JButton close = new JButton(iconClose);
        JLabel background = new JLabel();
        JLabel timeLabel = new JLabel("00:00:00");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("E, dd MMM yyyy");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        JLabel dateLabel = new JLabel("Thu, 26 May");
        java.util.Timer timer = new Timer("Timer");

        // adjust size and set layout
        frame.setPreferredSize(new Dimension(1920, 1080));
        setLayout(null);

        // add components
        if(bedrag1==0) {
            frame.add(choice1);
        }
        if(bedrag2 == 0) {
            frame.add(choice2);
        }
        if(bedrag3 == 0) {
            frame.add(choice3);
        }
        if(bedrag4 == 0) {
            frame.add(choice4);
        }
        frame.add(close);
        frame.add(goBack);
        frame.add(name);
        frame.add(timeLabel);
        frame.add(dateLabel);
        frame.add(background);

        // set component bounds (only needed by Absolute Positioning)
        name.setBounds(650, 100, 500, 80);
        background.setBounds(0, 0, 1980, 1080);
        choice2.setBounds(75, 525, 350, 100);
        choice4.setBounds(1100, 525, 350, 100);
        choice1.setBounds(75, 350, 350, 100);
        choice3.setBounds(1100, 350 , 350, 100);
        goBack.setBounds(75, 700, 70, 70);
        close.setBounds(1380, 700, 70, 70);
        timeLabel.setBounds(1250, 100, 244, 54);
        dateLabel.setBounds(1250, 140, 300, 54);

        //costumize component
        name.setFont(new java.awt.Font("Arial", Font.BOLD, 100));
        choice2.setFont(new java.awt.Font("Arial", Font.BOLD, 25));
        choice3.setFont(new java.awt.Font("Arial", Font.BOLD, 25));
        choice1.setFont(new java.awt.Font("Arial", Font.BOLD, 25));
        choice4.setFont(new java.awt.Font("Arial", Font.BOLD, 25));
        name.setForeground(Color.decode("#C0C0C0"));
        background.setOpaque(true);
        background.setBackground(Color.decode("#086DCD"));
        timeLabel.setForeground(Color.decode("#C0C0C0"));
        timeLabel.setFont(new Font("Arial", Font.BOLD, 60));
        dateLabel.setForeground(Color.decode("#C0C0C0"));
        dateLabel.setFont(new Font("Arial", Font.BOLD, 30));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        //Timer

        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                LocalDateTime localDateTime = LocalDateTime.now();

                timeLabel.setText(localDateTime.format(timeFormat));
                dateLabel.setText(localDateTime.format(dateFormat));
                if(com.haveInput()){
                    try{
                        System.out.println(com.getInput());
                        System.out.println(bedrag1);
                        if(com.getInput().equalsIgnoreCase("A")&& bedrag1 == 0){
                            com.closePort();
                            Bon bon = new Bon(user, bills[0][0],bills[0][1],bills[0][2]);
                            bon.setVisible(true);
                            frame.setVisible(false);
                        }else if(com.getInput().equalsIgnoreCase("B")&& bedrag2 == 0){
                            com.closePort();
                            Bon bon = new Bon(user, bills[1][0],bills[1][1],bills[1][2]);
                            bon.setVisible(true);
                            frame.setVisible(false);
                        }else if(com.getInput().equalsIgnoreCase("C")&& bedrag3==0){
                            com.closePort();
                            Bon bon = new Bon(user, bills[2][0],bills[2][1],bills[2][2]);
                            bon.setVisible(true);
                            frame.setVisible(false);
                        }else if(com.getInput().equalsIgnoreCase("D")&& bedrag4==0){
                            com.closePort();
                            Bon bon = new Bon(user, bills[3][0],bills[3][1],bills[3][2]);
                            bon.setVisible(true);
                            frame.setVisible(false);
                        }else if( com.getInput().equalsIgnoreCase("#")|| com.getInput().equalsIgnoreCase("*")){
                            com.hotkeyKeypad(frame, user);
                        }
                        com.clearInput();
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };

        timer.scheduleAtFixedRate(repeatedTask, 0L, 1000L);


        //Actions
//        goBack.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                CashWithdrawl withdrawl1 = new CashWithdrawl();
//                withdrawl1.setVisible(true);
//                frame.setVisible(false);
//
//            }
//        });
//
//        close.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent close) {
//                HomeScreen home1 = new HomeScreen();
//                frame.setVisible(false);
//            }
//        });




    }

    public static void main(final String[] args) {
        new Withdrawal_Options("12345678", 30);
    }
}