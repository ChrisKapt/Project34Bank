package GUI;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class CustomWithdrawal extends JPanel {
    private String amount;
    private int bill10,bill20,bill50;

    public CustomWithdrawal(String user) {
        // construct components
        JFrame frame = new JFrame("Custom");
        JLabel name = new JLabel("9ucci");
        JLabel custom = new JLabel(amount);
        JLabel enter = new JLabel("Enter a custom amount:");
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
        CommunicatieKeypad com = new CommunicatieKeypad("COM5");
        amount = "";

        // adjust size and set layout
        frame.setPreferredSize(new Dimension(1920, 1080));
        setLayout(null);

        // add components
        frame.add(custom);
        frame.add(enter);
        frame.add(close);
        frame.add(goBack);
        frame.add(name);
        frame.add(timeLabel);
        frame.add(dateLabel);
        frame.add(background);

        // set component bounds (only needed by Absolute Positioning)
        name.setBounds(650, 100, 500, 80);
        enter.setBounds(450,360,2000,80);
        background.setBounds(0, 0, 1980, 1080);
        custom.setBounds(725,450,100,60);
        goBack.setBounds(75, 700, 70, 70);
        close.setBounds(1380, 700, 70, 70);
        timeLabel.setBounds(1250, 100, 244, 54);
        dateLabel.setBounds(1250, 140, 300, 54);


        //costumize component
        name.setFont(new java.awt.Font("Arial", Font.BOLD, 100));
        name.setForeground(Color.decode("#C0C0C0"));
        enter.setFont(new java.awt.Font("Arial", Font.BOLD, 50));
        enter.setForeground(Color.decode("#C0C0C0"));
        custom.setFont(new java.awt.Font("Arial", Font.BOLD, 35));
        custom.setForeground(Color.decode("#C0C0C0"));
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
                if (com.haveInput()) {
                    try {
//                        custom.setText(amount);
                        amount = com.keys(amount, "Custom");
                        custom.setText(amount);
                        if(com.getInput().equalsIgnoreCase("B")){
                            int bedrag = Integer.parseInt(amount);
                            if(bedrag% 10 == 0 && bedrag<=250 && bedrag>= 10) {
                                com.closePort();
                                Withdrawal_Options options = new Withdrawal_Options(user,bedrag);
                                options.setVisible(true);
                                frame.setVisible(false);
                            }else if( com.getInput().equalsIgnoreCase("#")|| com.getInput().equalsIgnoreCase("*")){
                                com.hotkeyKeypad(frame, user);
                            }else{
                                com.closePort();
                                ErrorScreen error = new ErrorScreen("Not okay", user);
                                error.setVisible(true);
                                frame.setVisible(false);
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        timer.scheduleAtFixedRate(repeatedTask, 0L, 1000L);
    }

    public static void main(String[] args) {
        new CustomWithdrawal("12345678");
    }
}
