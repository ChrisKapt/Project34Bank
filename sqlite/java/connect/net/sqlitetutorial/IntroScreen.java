package net.sqlitetutorial;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class IntroScreen {
    JFrame frame = new JFrame("IntroScreen");
    private static JTextField rfidField;

    public IntroScreen() {

        // construct components
        JLabel background = new JLabel();
        JLabel name = new JLabel("9ucci");
        JLabel welcomeSign = new JLabel("Welkom bij onze bank");
        JLabel insertCardText = new JLabel("Plaats kaart om door te gaan");
        Icon iconInsert = new ImageIcon("C:\\Users\\Noah_\\Downloads\\insertCardRe1.png");
        JLabel insert = new JLabel(iconInsert);
        rfidField = new JTextField();



        // adjust size and set layout
        frame.setPreferredSize(new Dimension(1920, 1080));
        frame.setLayout(null);

        // add components
        frame.add(name);
        frame.add(insert);
        frame.add(welcomeSign);
        frame.add(insertCardText);
        frame.add(background);
        frame.add(rfidField);


        // set component bounds (only needed by Absolute Positioning)
        name.setBounds(650, 100, 500, 80);
        insert.setBounds(605, 300, 350,350);
        welcomeSign.setBounds(650, 225, 400, 50 );
        insertCardText.setBounds(645, 650, 400, 50);
        background.setBounds(0, 0, 1980, 1080);
        rfidField.setBounds(725, 400, 100, 50);

        // costumize component
        name.setFont(new java.awt.Font("Arial", Font.BOLD, 100));
        name.setForeground(Color.decode("#C0C0C0"));
        welcomeSign.setFont(new java.awt.Font("Arial", Font.BOLD, 25));
        welcomeSign.setForeground(Color.decode("#C0C0C0"));
        insertCardText.setFont(new java.awt.Font("Arial", Font.BOLD, 20));
        insertCardText.setForeground(Color.decode("#C0C0C0"));
        rfidField.setFont(new java.awt.Font("Arial", Font.BOLD, 20));


        // background
        background.setOpaque(true);
        background.setBackground(Color.decode("#086DCD"));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


        //Actions

        background.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent start) {
                SelectApp rfidSelect = new SelectApp();
                String rfid = rfidField.getText();
                String[] rfids = rfidSelect.selectRFID();
                for(int i=0; i<10; i++){
                    if(rfid.equals(rfids[i])){
                        System.out.println("goede RFID gevonden");
                        WelcomeScreen welcome1 = new WelcomeScreen(rfid);
                        welcome1.setVisible(true);
                        frame.setVisible(false);
                        break;
                    }
                }
                

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

    public static void main(String[] args) { new IntroScreen(); }


}
