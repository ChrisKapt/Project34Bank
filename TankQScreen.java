package GUI;

import Database.SelectApp;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class TankQScreen extends JPanel {
    int seconds;
    IntroScreen begin;

    public TankQScreen(String user){
    SelectApp data = new SelectApp();
    JFrame frame = new JFrame("ErrorScreen");
    JLabel name = new JLabel("9ucci");
    JLabel thanks = new JLabel("Thank you for");
    JLabel thanks1 = new JLabel("choosing 9ucci bank");
    JLabel background = new JLabel();
    JLabel timeLabel = new JLabel("00:00:00");
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("E, dd MMM yyyy");
    DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
    JLabel dateLabel = new JLabel("Thu, 26 May");
    java.util.Timer timer = new Timer("Timer");
    CommunicatieKeypad com = new CommunicatieKeypad("COM5");
    seconds = 0;

    // adjust size and set layout
        frame.setPreferredSize(new Dimension(1920, 1080));
    setLayout(null);

    // add component
        frame.add(name);
        frame.add(thanks);
        frame.add(thanks1);
        frame.add(timeLabel);
        frame.add(dateLabel);
        frame.add(background);

    // set component bounds (only needed by Absolute Positioning)
        name.setBounds(650, 100, 500, 80);
        thanks.setBounds(400,360,1000,80);
        thanks1.setBounds(200,460,1000,100);
        background.setBounds(0, 0, 1980, 1080);
        timeLabel.setBounds(1250, 100, 244, 54);
        dateLabel.setBounds(1250, 140, 300, 54);
//        money1.setFont(new java.awt.Font("Arial", Font.BOLD, 100));
//        money1.setForeground(Color.red);
//        money2.setFont(new java.awt.Font("Arial", Font.BOLD, 100));
//        money2.setForeground(Color.red);
    //costumize component
        name.setFont(new java.awt.Font("Arial", Font.BOLD, 100));
        name.setForeground(Color.decode("#C0C0C0"));
        thanks.setFont(new java.awt.Font("Arial", Font.BOLD, 100));
        thanks.setForeground(Color.decode("#C0C0C0"));
        thanks1.setFont(new java.awt.Font("Arial", Font.BOLD, 100));
        thanks1.setForeground(Color.decode("#C0C0C0"));
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
            if(seconds == 5){
                com.closePort();
                begin = new IntroScreen();
                begin.setVisible(true);
                frame.setVisible(false);
            }
            seconds++;
        }
    };

        timer.scheduleAtFixedRate(repeatedTask, 0L, 1000L);
    }

//    public static void main(String[] args) {
//        new TankQScreen("12345678");
//    }
}
