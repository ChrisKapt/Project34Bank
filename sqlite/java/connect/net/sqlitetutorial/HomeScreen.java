package net.sqlitetutorial;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class HomeScreen extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    

    HomeScreen() {
        // construct components
        JFrame frame = new JFrame("HomeScreen");
        JButton withdrawal = new JButton("Cash Withdrawal");
        JLabel name = new JLabel("9ucci");
        JButton donations = new JButton("Donations");
        JButton balance = new JButton("Balance");
        JButton fastWithdrawal = new JButton("Fast Withdrawal €70");
        Icon iconClose = new ImageIcon("C:\\Users\\Noah_\\Downloads\\closeButton.png");
        JButton close = new JButton(iconClose);
        JLabel background = new JLabel();
        //JFrame.dispose();

        // adjust size and set layout
        frame.setPreferredSize(new Dimension(1920, 1080));
        setLayout(null);

        // add components

        frame.add(withdrawal);
        frame.add(name);
        frame.add(donations);
        frame.add(balance);
        frame.add(fastWithdrawal);
        frame.add(close);
        frame.add(background);

        // set component bounds (only needed by Absolute Positioning)
        withdrawal.setBounds(75, 350, 350, 100);
        name.setBounds(650, 100, 500, 80);
        donations.setBounds(75, 525, 350, 100);
        balance.setBounds(1100, 525, 350, 100);
        fastWithdrawal.setBounds(1100,325,350,100);
        close.setBounds(1380, 700, 70, 70);
        background.setBounds(0, 0, 1980, 1080);

        //customize component
        withdrawal.setFont(new java.awt.Font("Arial", Font.BOLD, 25));
        name.setFont(new java.awt.Font("Arial", Font.BOLD, 100));
        donations.setFont(new java.awt.Font("Arial", Font.BOLD, 25));
        balance.setFont(new java.awt.Font("Arial", Font.BOLD, 25));
        fastWithdrawal.setFont(new java.awt.Font("Arial", Font.BOLD, 25));
        name.setForeground(Color.decode("#C0C0C0"));
        background.setOpaque(true);
        background.setBackground(Color.decode("#086DCD"));

        //Actions

        withdrawal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent withdrawal) {
                CashWithdrawl withdrawal1 = new CashWithdrawl();
                withdrawal1.setVisible(true);
                frame.setVisible(false);
            }
        });

        balance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent withdrawal) {
                Balance balance1 = new Balance();
                balance1.setVisible(true);
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


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);




    }
    

    public static void main(final String[] args) {
        new HomeScreen();
    }
}