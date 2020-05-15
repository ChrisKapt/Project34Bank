package net.sqlitetutorial;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.AttributedCharacterIterator;
import java.util.Arrays;
import java.util.Map;

//import com.fazecast.jSerialComm.*;


public class WelcomeScreen extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static JPasswordField passwordField;
    JFrame frame = new JFrame("WelcomeScreen");
    private String currentUser;


    public WelcomeScreen(String user) {

        currentUser=user;

        // construct components
        JLabel background = new JLabel();
        passwordField = new JPasswordField(4);
        JLabel name = new JLabel("9ucci");
        JButton buttonOK = new JButton("OK");
        JLabel labelPassword = new JLabel("Enter password:");

        // adjust size and set layout
        frame.setPreferredSize(new Dimension(1920, 1080));
        frame.setLayout(null);

        // add components
        frame.add(name);
        frame.add(labelPassword);
        frame.add(passwordField);
        frame.add(buttonOK);
        frame.add(background);



        // set component bounds (only needed by Absolute Positioning)
        background.setBounds(0, 0, 1980, 1080);
        passwordField.setBounds(725, 400, 100, 50);
        buttonOK.setBounds(725, 550, 100, 50);
        name.setBounds(650, 100, 500, 80);
        labelPassword.setBounds(700, 200, 300, 300);



        // costumize component
        name.setFont(new java.awt.Font("Arial", Font.BOLD, 100));
        labelPassword.setFont(new java.awt.Font("Arial", Font.BOLD, 20));
        passwordField.setToolTipText("Password must contain 4 characters");
        passwordField.setFont(new java.awt.Font("Arial", Font.BOLD, 20));
        name.setForeground(Color.decode("#C0C0C0"));
        labelPassword.setForeground(Color.decode("#C0C0C0"));


        //OKButton 
        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent passWord) {
                buttonOKActionPerformed(passWord);

            }
        });


        // background
        background.setOpaque(true);
        background.setBackground(Color.decode("#086DCD"));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }



    // Passwordcheck
    private void buttonOKActionPerformed(ActionEvent passWord) {
        String password = new String(passwordField.getPassword());
        SelectApp pwSelect = new SelectApp();
        String correctPass = pwSelect.selectPin(currentUser);
        if (password.equals(correctPass)) {
            JOptionPane.showMessageDialog(WelcomeScreen.this, "You entered the correct pincode.");
            HomeScreen home1 = new HomeScreen();
            home1.setVisible(true);
            frame.setVisible(false);



        } else {
            JOptionPane.showMessageDialog(WelcomeScreen.this, "Wrong pincode!");
        }
    }


    public static void main(String[] args) { new WelcomeScreen("test"); }



}