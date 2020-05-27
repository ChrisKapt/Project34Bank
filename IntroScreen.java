package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;


public class IntroScreen {
    JFrame frame = new JFrame("IntroScreen");

    public IntroScreen() {

        // construct components
        JLabel background = new JLabel();
        JLabel name = new JLabel("9ucci");
        JLabel welcomeSign = new JLabel("Welkom bij onze bank");
        JLabel insertCardText = new JLabel("Plaats kaart om door te gaan");
        Icon iconInsert = new ImageIcon("C:\\Users\\Noah_\\Downloads\\insertCardRe1.png");
        JLabel insert = new JLabel(iconInsert);
        JLabel timeLabel = new JLabel("00:00:00");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("E, dd MMM yyyy");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        JLabel dateLabel = new JLabel("Thu, 26 May");
        Timer timer = new Timer("Timer");



        // adjust size and set layout
        frame.setPreferredSize(new Dimension(1920, 1080));
        frame.setLayout(null);

        // add components
        frame.add(name);
        frame.add(insert);
        frame.add(timeLabel);
        frame.add(dateLabel);
        frame.add(welcomeSign);
        frame.add(insertCardText);
        frame.add(background);



        // set component bounds (only needed by Absolute Positioning)
        name.setBounds(650, 100, 500, 80);
        insert.setBounds(605, 300, 350,350);
        welcomeSign.setBounds(650, 225, 400, 50 );
        insertCardText.setBounds(645, 650, 400, 50);
        timeLabel.setBounds(1250, 100, 244, 54);
        dateLabel.setBounds(1250, 140, 300, 54);
        background.setBounds(0, 0, 1980, 1080);

        // costumize component
        name.setFont(new java.awt.Font("Arial", Font.BOLD, 100));
        name.setForeground(Color.decode("#C0C0C0"));
        welcomeSign.setFont(new java.awt.Font("Arial", Font.BOLD, 25));
        welcomeSign.setForeground(Color.decode("#C0C0C0"));
        insertCardText.setFont(new java.awt.Font("Arial", Font.BOLD, 20));
        insertCardText.setForeground(Color.decode("#C0C0C0"));
        timeLabel.setForeground(Color.decode("#C0C0C0"));
        timeLabel.setFont(new Font("Arial", Font.BOLD, 60));
        dateLabel.setForeground(Color.decode("#C0C0C0"));
        dateLabel.setFont(new Font("Arial", Font.BOLD, 30));



        // background
        background.setOpaque(true);
        background.setBackground(Color.decode("#086DCD"));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);




        //Timer

        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                LocalDateTime localDateTime = LocalDateTime.now();

                timeLabel.setText(localDateTime.format(timeFormat));
                dateLabel.setText(localDateTime.format(dateFormat));
            }
        };

        timer.scheduleAtFixedRate(repeatedTask, 0L, 1000L);

        //Actions

        background.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent start) {
                WelcomeScreen welcome1 = new WelcomeScreen();
                welcome1.setVisible(true);
                frame.setVisible(false);

            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });


    }

    public static void main(String[] args) {
        new IntroScreen();
    }


}
