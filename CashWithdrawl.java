package Gui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CashWithdrawl extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    CashWithdrawl() {
        // construct components
        JFrame frame = new JFrame("CashWithdrawal");
        JLabel name = new JLabel("9ucci");
        JButton balance = new JButton("Balance");
        JButton fastwithdrawal = new JButton("Fast Withdrawal €70");
        JButton withdrawalCash = new JButton("Withdrawal Cash");
        Icon icon = new ImageIcon("C:\\Users\\Noah_\\Downloads\\arrow.png");
        JButton goBack = new JButton(icon);
        Icon iconClose = new ImageIcon("C:\\Users\\Noah_\\Downloads\\closeButton.png");
        JButton close = new JButton(iconClose);
        JLabel background = new JLabel();

        // adjust size and set layout
        frame.setPreferredSize(new Dimension(1920, 1080));
        setLayout(null);

        // add components
        frame.add(balance);
        frame.add(fastwithdrawal);
        frame.add(withdrawalCash);
        frame.add(close);
        frame.add(goBack);
        frame.add(name);
        frame.add(background);

        // set component bounds (only needed by Absolute Positioning)
        name.setBounds(650, 100, 500, 80);
        background.setBounds(0, 0, 1980, 1080);;
        fastwithdrawal.setBounds(75, 525, 350, 100);
        withdrawalCash.setBounds(1100, 525, 350, 100);
        balance.setBounds(75, 350, 350, 100);
        goBack.setBounds(75, 700, 70, 70);
        close.setBounds(1380, 700, 70, 70);


        //costumize component
        name.setFont(new java.awt.Font("Arial", Font.BOLD, 100));
        fastwithdrawal.setFont(new java.awt.Font("Arial", Font.BOLD, 25));
        withdrawalCash.setFont(new java.awt.Font("Arial", Font.BOLD, 25));
        balance.setFont(new java.awt.Font("Arial", Font.BOLD, 25));
        name.setForeground(Color.decode("#C0C0C0"));
        background.setOpaque(true);
        background.setBackground(Color.decode("#086DCD"));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        //Actions

        withdrawalCash.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent withdrawal) {
                Withdrawal_Options withdrawal_options1 = new Withdrawal_Options();
                withdrawal_options1.setVisible(true);
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


        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                HomeScreen home1 = new HomeScreen();
                home1.setVisible(true);
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
        new CashWithdrawl();
    }
}