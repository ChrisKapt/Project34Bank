package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.AttributedCharacterIterator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.fazecast.jSerialComm.*;


public class WelcomeScreen extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static JPasswordField passwordField;
    JFrame frame = new JFrame("WelcomeScreen");
    String input = "";


    public WelcomeScreen(String user) {

        // construct components
        JLabel background = new JLabel();
//        passwordField = new JPasswordField(4);
//        passwordField.setEchoChar('*');
        JLabel name = new JLabel("9ucci");
        JButton buttonOK = new JButton("OK");
        JLabel pincode = new JLabel(input);
        JLabel labelPassword = new JLabel("Enter password:");
        JLabel timeLabel = new JLabel("00:00:00");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("E, dd MMM yyyy");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        JLabel dateLabel = new JLabel("Thu, 26 May");
        java.util.Timer timer = new Timer("Timer");
        CommunicatieKeypad com = new CommunicatieKeypad("COM5");



        // adjust size and set layout
        frame.setPreferredSize(new Dimension(1920, 1080));
        frame.setLayout(null);

        // add components
        frame.add(name);
        frame.add(labelPassword);
        frame.add(pincode);
//        frame.add(passwordField);
        frame.add(buttonOK);
        frame.add(timeLabel);
        frame.add(dateLabel);
        frame.add(background);



        // set component bounds (only needed by Absolute Positioning)
        background.setBounds(0, 0, 1980, 1080);
//        passwordField.setBounds(725, 400, 100, 50);
        pincode.setBounds(725,400,100,50);
        buttonOK.setBounds(725, 550, 100, 50);
        name.setBounds(650, 100, 500, 80);
        labelPassword.setBounds(700, 200, 300, 300);
        timeLabel.setBounds(1250, 100, 244, 54);
        dateLabel.setBounds(1250, 140, 300, 54);



        // costumize component
        name.setFont(new java.awt.Font("Arial", Font.BOLD, 100));
        labelPassword.setFont(new java.awt.Font("Arial", Font.BOLD, 20));
        pincode.setFont(new java.awt.Font("Arial", Font.BOLD, 20));
        pincode.setForeground(Color.decode("#C0C0C0"));
        name.setForeground(Color.decode("#C0C0C0"));
        labelPassword.setForeground(Color.decode("#C0C0C0"));
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
                    try {
                        input = com.keys(input, "inlog");
                        pincode.setText(input);
                        if(com.getInput().equalsIgnoreCase("B")) {
                            com.inlog(frame, user);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        timer.scheduleAtFixedRate(repeatedTask, 0L, 1000L);



        //OKButton
//        buttonOK.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent passWord) {
//                buttonOKActionPerformed(passWord);
//
//            }
//        });


        // background
        background.setOpaque(true);
        background.setBackground(Color.decode("#086DCD"));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }



    // Passwordcheck
//    private void buttonOKActionPerformed(ActionEvent passWord) {
//        char[] password = passwordField.getPassword();
//        char[] correctPass = new char[] {'0','0','0','0'};
//        if (Arrays.equals(password, correctPass)) {
//            JOptionPane.showMessageDialog(WelcomeScreen.this, "You entered the correct pincode.");
//            HomeScreen home1 = new HomeScreen();
//            home1.setVisible(true);
//            frame.setVisible(false);
//
//
//
//        } else {
//            JOptionPane.showMessageDialog(WelcomeScreen.this, "Wrong pincode!");
//        }
//    }


//    public static void main(String[] args) {
//        new WelcomeScreen("test");
//    }



}