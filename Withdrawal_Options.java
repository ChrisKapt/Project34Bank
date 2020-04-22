package Gui;


import javax.swing.*;
import java.awt.*;



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
        frame.add(background);

        // set component bounds (only needed by Absolute Positioning)
        name.setBounds(650, 100, 500, 80);
        background.setBounds(0, 0, 1980, 1080);;
        biljet20.setBounds(75, 525, 350, 100);
        biljet50.setBounds(1100, 525, 350, 100);
        biljet10.setBounds(75, 350, 350, 100);
        goBack.setBounds(75, 700, 70, 70);
        close.setBounds(175, 700, 70, 70);






        //costumize component
        name.setFont(new java.awt.Font("Arial", Font.BOLD, 100));
        biljet20.setFont(new java.awt.Font("Arial", Font.BOLD, 25));
        biljet50.setFont(new java.awt.Font("Arial", Font.BOLD, 25));
        biljet10.setFont(new java.awt.Font("Arial", Font.BOLD, 25));

        background.setOpaque(true);
        background.setBackground(Color.decode("#b9f1fa"));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);



    }

    public static void main(final String[] args) {
        new Withdrawal_Options();
    }
}