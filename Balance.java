package Gui;


import javax.swing.*;
import java.awt.*;


public class Balance extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    Balance() {
        // construct components
        JFrame frame = new JFrame("HomeScreen");
        JLabel name = new JLabel("9ucci");
        JLabel yourBalance = new JLabel("Your balance is: xxx");
        Icon icon = new ImageIcon("C:\\Users\\Noah_\\Downloads\\arrow.png");
        JButton goBack = new JButton(icon);
        Icon iconClose = new ImageIcon("C:\\Users\\Noah_\\Downloads\\closeButton.png");
        JButton close = new JButton(iconClose);
        JLabel background = new JLabel();

        // adjust size and set layout
        frame.setPreferredSize(new Dimension(1920, 1080));
        setLayout(null);

        // add components
        frame.add(yourBalance);
        frame.add(goBack);
        frame.add(close);
        frame.add(name);
        frame.add(background);

        // set component bounds (only needed by Absolute Positioning)
        name.setBounds(650, 100, 500, 80);
        yourBalance.setBounds(550, 400, 500, 50);
        background.setBounds(0, 0, 1980, 1080);
        goBack.setBounds(75, 700, 70, 70);
        close.setBounds(175, 700, 70, 70);



        //costumize component
        name.setFont(new java.awt.Font("Arial", Font.BOLD, 100));
        yourBalance.setFont(new java.awt.Font("Arial", Font.BOLD, 50));
        background.setOpaque(true);
        background.setBackground(Color.decode("#b9f1fa"));







        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);



    }

    public static void main(final String[] args) {
        new Balance();
    }
}