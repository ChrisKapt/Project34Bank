package GUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class Bon extends JPanel{

        private static final long serialVersionUID = 1L;

        public Bon( String user, int bill10, int bill20, int bill50) {
            JFrame frame = new JFrame("Bon");
            JLabel name = new JLabel("9ucci");
            JButton yes = new JButton("Yes");
            JButton no = new JButton("NO");
            JLabel choice = new JLabel("Do you want a receipt?");
//        Icon icon = new ImageIcon("C:\\Users\\Noah_\\Downloads\\arrow.png");
            JButton goBack = new JButton("Back");
//        Icon iconClose = new ImageIcon("C:\\Users\\Noah_\\Downloads\\closeButton.png");
            JButton close = new JButton("Cancel");
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
            frame.add(yes);
            frame.add(no);
            frame.add(choice);
            frame.add(close);
            frame.add(goBack);
            frame.add(name);
            frame.add(timeLabel);
            frame.add(dateLabel);
            frame.add(background);

            // set component bounds (only needed by Absolute Positioning)
            name.setBounds(650, 100, 500, 80);
            background.setBounds(0, 0, 1980, 1080);
            choice.setBounds(410,300,1000,80);
            yes.setBounds(75, 500, 500, 100);
            no.setBounds(1000, 500, 500, 100);
            goBack.setBounds(75, 700, 70, 70);
            close.setBounds(1380, 700, 70, 70);
            timeLabel.setBounds(1250, 100, 244, 54);
            dateLabel.setBounds(1250, 140, 300, 54);

            //costumize component
            name.setFont(new java.awt.Font("Arial", Font.BOLD, 100));
            yes.setFont(new java.awt.Font("Arial", Font.BOLD, 25));
            no.setFont(new java.awt.Font("Arial", Font.BOLD, 25));
            choice.setFont(new java.awt.Font("Arial", Font.BOLD, 75));
            choice.setForeground(Color.decode("#C0C0C0"));
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
                    if (com.haveInput()) {
                        try {
//                        com.fastWithdrawal(frame, user);
                            System.out.println(com.getInput());
                            System.out.println(" ::");
                            if(com.getInput().equalsIgnoreCase("A")) {
                                com.pinBedrag(frame, user, bill10, bill20, bill50, 1);
                            }
                            else if(com.getInput().equalsIgnoreCase("B")){
                                com.pinBedrag(frame,user,bill10,bill20,bill50,0);
                            }else if( com.getInput().equalsIgnoreCase("#")|| com.getInput().equalsIgnoreCase("*")){
                                com.hotkeyKeypad(frame, user);
                            }
                        } catch (InterruptedException | IOException e) {
                            e.printStackTrace();
                        }
                        com.clearInput();
                    }
                }
            };

            timer.scheduleAtFixedRate(repeatedTask, 0L, 1000L);
        }

    public static void main(String[] args) {
        new Bon("12345678", 1,0,1);
    }
    }
