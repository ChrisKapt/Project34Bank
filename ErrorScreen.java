package GUI;

import Database.SelectApp;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class ErrorScreen extends JPanel{
    private static final long serialVersionUID = 1L;
    private int pogingLeft;
    private int seconds;

    public ErrorScreen(String errorMessage, String user) {
        SelectApp data = new SelectApp();
        JFrame frame = new JFrame("ErrorScreen");
        JLabel name = new JLabel("9ucci");
        JLabel money1 = new JLabel("INSUFFICIENT");
        JLabel money2 = new JLabel("AMOUNT");
        JLabel blocked = new JLabel("Pass BLOCKED");
        JLabel pogingen;
        pogingLeft = (3-data.selectPogingen(user));
        if(pogingLeft >0){
               pogingen = new JLabel("Tries left: "+pogingLeft);
        }else{
               pogingen = new JLabel("Pass Blocked");
        }
        JLabel notFound = new JLabel("Pass Not Found");
        JLabel notOkay = new JLabel("Please enter a valid amount");
        JLabel notOkay1 = new JLabel("Between 10 and 250 in tens");
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
        if(errorMessage.equalsIgnoreCase("Not enough money")) {
            frame.add(money1);
            frame.add(money2);
        }else if(errorMessage.equalsIgnoreCase("Pas BLOCKED")) {
            frame.add(blocked);
        }else if(errorMessage.equalsIgnoreCase("Pogingen")){
            frame.add(pogingen);
        }else if(errorMessage.equalsIgnoreCase("Not found")){
            frame.add(notFound);
        }else if(errorMessage.equalsIgnoreCase("Not Okay")){
            frame.add(notOkay);
            frame.add(notOkay1);
        }
        frame.add(timeLabel);
        frame.add(dateLabel);
        frame.add(background);

        // set component bounds (only needed by Absolute Positioning)
        name.setBounds(650, 100, 500, 80);
        background.setBounds(0, 0, 1980, 1080);
        money1.setBounds(400, 360, 1000, 80);
        money2.setBounds(420, 460, 1000, 80);
        blocked.setBounds(410, 400, 1000, 80);
        pogingen.setBounds(410,400,1000,80);
        notFound.setBounds(410,400,1000,80);
        notOkay.setBounds(250, 360, 2000, 80);
        notOkay1.setBounds(250, 460, 2000, 80);
        timeLabel.setBounds(1250, 100, 244, 54);
        dateLabel.setBounds(1250, 140, 300, 54);


        //costumize component
        name.setFont(new java.awt.Font("Arial", Font.BOLD, 100));
        name.setForeground(Color.decode("#C0C0C0"));
            money1.setFont(new java.awt.Font("Arial", Font.BOLD, 100));
            money1.setForeground(Color.red);
            money2.setFont(new java.awt.Font("Arial", Font.BOLD, 100));
            money2.setForeground(Color.red);
            blocked.setFont(new java.awt.Font("Arial", Font.BOLD, 100));
            blocked.setForeground(Color.red);
            pogingen.setFont(new java.awt.Font("Arial", Font.BOLD, 100));
            pogingen.setForeground(Color.red);
            notFound.setFont(new java.awt.Font("Arial", Font.BOLD, 100));
        notFound.setForeground(Color.red);
        notOkay.setFont(new java.awt.Font("Arial", Font.BOLD, 80));
        notOkay.setForeground(Color.red);
        notOkay1.setFont(new java.awt.Font("Arial", Font.BOLD, 80));
        notOkay1.setForeground(Color.red);
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
                if(seconds == 2){
                    com.errorMessage(errorMessage,user,frame);
                }
                seconds++;
            }
        };

        timer.scheduleAtFixedRate(repeatedTask, 0L, 1000L);
    }

    public static void main(String[] args) {
        new ErrorScreen("Not okay", "12345678");
    }
}
