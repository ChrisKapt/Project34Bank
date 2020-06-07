package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;


public class HomeScreen extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    HomeScreen(String user) {
        // construct components
        JFrame frame = new JFrame("HomeScreen");
        JButton withdrawal = new JButton("Cash Withdrawal");
        JLabel name = new JLabel("9ucci");
        JButton balance = new JButton("Balance");
        JButton fastWithdrawal = new JButton("Fast Withdrawal €70");
        Icon iconClose = new ImageIcon("C:\\Users\\Noah_\\Downloads\\closeButton.png");
        JButton close = new JButton(iconClose);
        JLabel background = new JLabel();
        JLabel timeLabel = new JLabel("00:00:00");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("E, dd MMM yyyy");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        JLabel dateLabel = new JLabel("Thu, 26 May");
        java.util.Timer timer = new Timer("Timer");
        CommunicatieKeypad com = new CommunicatieKeypad("COM5");



        // adjust size and set layout
        frame.setPreferredSize(new Dimension(1920, 1080));
        setLayout(null);

        // add components

        frame.add(withdrawal);
        frame.add(name);
        frame.add(balance);
        frame.add(fastWithdrawal);
        frame.add(close);
        frame.add(timeLabel);
        frame.add(dateLabel);
        frame.add(background);

        // set component bounds (only needed by Absolute Positioning)
        withdrawal.setBounds(75, 350, 350, 100);
        name.setBounds(650, 100, 500, 80);
        balance.setBounds(75, 525, 350, 100);
        fastWithdrawal.setBounds(1100,325,350,100);
        close.setBounds(1380, 700, 70, 70);
        timeLabel.setBounds(1250, 100, 244, 54);
        dateLabel.setBounds(1250, 140, 300, 54);
        background.setBounds(0, 0, 1980, 1080);

        //customize component
        withdrawal.setFont(new java.awt.Font("Arial", Font.BOLD, 25));
        name.setFont(new java.awt.Font("Arial", Font.BOLD, 100));

        balance.setFont(new java.awt.Font("Arial", Font.BOLD, 25));
        fastWithdrawal.setFont(new java.awt.Font("Arial", Font.BOLD, 25));
        name.setForeground(Color.decode("#C0C0C0"));
        background.setOpaque(true);
        background.setBackground(Color.decode("#086DCD"));
        timeLabel.setForeground(Color.decode("#C0C0C0"));
        timeLabel.setFont(new Font("Arial", Font.BOLD, 60));
        dateLabel.setForeground(Color.decode("#C0C0C0"));
        dateLabel.setFont(new Font("Arial", Font.BOLD, 30));

        //Timer

        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                LocalDateTime localDateTime = LocalDateTime.now();

                timeLabel.setText(localDateTime.format(timeFormat));
                dateLabel.setText(localDateTime.format(dateFormat));
                if(com.haveInput()){
                    try{
                        com.hotkeyKeypad(frame,user);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };

        timer.scheduleAtFixedRate(repeatedTask, 0L, 1000L);


        //Actions

//        withdrawal.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent withdrawal) {
//                CashWithdrawl withdrawal1 = new CashWithdrawl();
//                withdrawal1.setVisible(true);
//                frame.setVisible(false);
//            }
//        });
//
//        balance.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent withdrawal) {
//                Balance balance1 = new Balance();
//                balance1.setVisible(true);
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


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);




    }

//    public static void main(final String[] args) {
//        new HomeScreen();
//    }
}