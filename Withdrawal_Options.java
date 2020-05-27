package Gui;


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


    Withdrawal_Options() {
        // construct components
        JFrame frame = new JFrame("Withdrawal");
        JLabel  name = new JLabel("9ucci");
        JButton biljet10 = new JButton("€10");
        JButton biljet20 = new JButton("€20");
        JButton biljet50 = new JButton("€50");
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
        frame.add(biljet10);
        frame.add(biljet20);
        frame.add(biljet50);
        frame.add(close);
        frame.add(goBack);
        frame.add(name);
        frame.add(timeLabel);
        frame.add(dateLabel);
        frame.add(background);

        // set component bounds (only needed by Absolute Positioning)
        name.setBounds(650, 100, 500, 80);
        background.setBounds(0, 0, 1980, 1080);;
        biljet20.setBounds(75, 525, 350, 100);
        biljet50.setBounds(1100, 525, 350, 100);
        biljet10.setBounds(75, 350, 350, 100);
        goBack.setBounds(75, 700, 70, 70);
        close.setBounds(1380, 700, 70, 70);
        timeLabel.setBounds(1250, 100, 244, 54);
        dateLabel.setBounds(1250, 140, 300, 54);






        //costumize component
        name.setFont(new java.awt.Font("Arial", Font.BOLD, 100));
        biljet20.setFont(new java.awt.Font("Arial", Font.BOLD, 25));
        biljet50.setFont(new java.awt.Font("Arial", Font.BOLD, 25));
        biljet10.setFont(new java.awt.Font("Arial", Font.BOLD, 25));
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
            }
        };

        timer.scheduleAtFixedRate(repeatedTask, 0L, 1000L);


        //Actions
        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                CashWithdrawl withdrawl1 = new CashWithdrawl();
                withdrawl1.setVisible(true);
                frame.setVisible(false);

            }
        });

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent close) {
                HomeScreen home1 = new HomeScreen();
                frame.setVisible(false);
            }
        });




    }

    public static void main(final String[] args) {
        new Withdrawal_Options();
    }
}