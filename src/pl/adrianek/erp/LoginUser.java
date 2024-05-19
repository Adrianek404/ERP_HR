package pl.adrianek.erp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginUser extends JFrame  implements ActionListener {

    JTextField imieField, nazwiskoField;
    JButton zalogujButton;
    LoginUser(){
        setTitle("Logowanie");

        setLayout(null);

        getContentPane().setBackground(Color.WHITE);

        setSize(600, 400);
        setLocation(600, 350);

        JLabel imieLabel = new JLabel("Imię:");
        imieLabel.setFont(new Font("Raleway", Font.BOLD, 14));
        imieLabel.setBounds(100, 100, 100, 30);
        add(imieLabel);

        imieField = new JTextField();
        imieField.setBounds(200, 100, 200, 30);
        add(imieField);

        JLabel nazwiskoLabel = new JLabel("Nazwisko:");
        nazwiskoLabel.setFont(new Font("Raleway", Font.BOLD, 14));
        nazwiskoLabel.setBounds(100, 150, 100, 30);
        add(nazwiskoLabel);

        nazwiskoField = new JTextField();
        nazwiskoField.setBounds(200, 150, 200, 30);
        add(nazwiskoField);

        zalogujButton = new JButton("Zaloguj");
        zalogujButton.setBounds(250, 200, 100, 30);
        zalogujButton.setBorderPainted(false);
        zalogujButton.setOpaque(true);
        zalogujButton.setBackground(Color.BLACK);
        zalogujButton.setForeground(Color.WHITE);
        zalogujButton.addActionListener(this);
        zalogujButton.addActionListener(this);
        add(zalogujButton);

        setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == zalogujButton) {
            String imie = imieField.getText().trim();
            String nazwisko = nazwiskoField.getText().trim();
            boolean zalogowano = sprawdzDaneLogowania(imie, nazwisko);

            if (zalogowano) {
                JOptionPane.showMessageDialog(this, "Zalogowano pomyślnie!", "Sukces", JOptionPane.INFORMATION_MESSAGE);
                setVisible(false);
                new UserSystem(imie, nazwisko).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Błędne dane logowania", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean sprawdzDaneLogowania(String imie, String nazwisko) {
        try (BufferedReader br = new BufferedReader(new FileReader("pracownicy.txt"))) {
            String linia;

            while ((linia = br.readLine()) != null) {
                String[] dane = linia.split(";");
                if (dane.length >= 2) {
                    String imiePliku = dane[0].trim();
                    String nazwiskoPliku = dane[1].trim();
                    if (imie.equalsIgnoreCase(imiePliku) && nazwisko.equalsIgnoreCase(nazwiskoPliku)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
