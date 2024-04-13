package pl.adrianek.erp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DodajPracownika extends JFrame implements ActionListener {

    JTextField Imie, Nazwisko, Umiejetnosci, Doswiadczenie;
    JButton dodaj;

    DodajPracownika(){
        setTitle("Dodaj pracownika");

        setLayout(null);

        getContentPane().setBackground(Color.WHITE);

        setSize(600, 400);
        setLocation(600, 350);

        JLabel ImieL = new JLabel("Imię: ");
        ImieL.setFont(new Font("Raleway", Font.BOLD, 28));
        ImieL.setBounds(20, 20, 200, 50);
        add(ImieL);

        JLabel NazwiskoL = new JLabel("Nazwisko: ");
        NazwiskoL.setFont(new Font("Raleway", Font.BOLD, 28));
        NazwiskoL.setBounds(20, 70, 200, 50);
        add(NazwiskoL);

        JLabel UmiejetnosciL = new JLabel("Umiejętności: ");
        UmiejetnosciL.setFont(new Font("Raleway", Font.BOLD, 28));
        UmiejetnosciL.setBounds(20, 120, 200, 50);
        add(UmiejetnosciL);

        JLabel DoswiadczenieL = new JLabel("Doświadczenie: ");
        DoswiadczenieL.setFont(new Font("Raleway", Font.BOLD, 28));
        DoswiadczenieL.setBounds(20, 170, 250, 50);
        add(DoswiadczenieL);

        Imie = new JTextField();
        Imie.setFont(new Font("Arial", Font.BOLD, 14));
        Imie.setBounds(240, 20, 200, 40);
        add(Imie);

        Nazwisko = new JTextField();
        Nazwisko.setFont(new Font("Arial", Font.BOLD, 14));
        Nazwisko.setBounds(240, 70, 200, 40);
        add(Nazwisko);

        Umiejetnosci = new JTextField();
        Umiejetnosci.setFont(new Font("Arial", Font.BOLD, 14));
        Umiejetnosci.setBounds(240, 120, 200, 40);
        add(Umiejetnosci);

        Doswiadczenie = new JTextField();
        Doswiadczenie.setFont(new Font("Arial", Font.BOLD, 14));
        Doswiadczenie.setBounds(240, 170, 200, 40);
        add(Doswiadczenie);

        dodaj = new JButton("Dodaj");
        dodaj.setBounds(230, 250, 120, 30);
        dodaj.setBorderPainted(false);
        dodaj.setOpaque(true);
        dodaj.setBackground(Color.BLACK);
        dodaj.setForeground(Color.WHITE);
        dodaj.addActionListener(this);
        add(dodaj);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dodaj) {
            String imie = Imie.getText();
            String nazwisko = Nazwisko.getText();
            String umiejetnosci = Umiejetnosci.getText();
            String doswiadczenie = Doswiadczenie.getText();

            if (!imie.isEmpty() && !nazwisko.isEmpty() && !umiejetnosci.isEmpty() && !doswiadczenie.isEmpty()) {
                dodajDoPliku(imie, nazwisko, umiejetnosci, doswiadczenie);
                this.setVisible(false);
                new Pracownicy().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Wypełnij wszystkie pola", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void dodajDoPliku(String imie, String nazwisko, String umiejetnosci, String doswiadczenie) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("pracownicy.txt", true))) {
            String linia = String.format("%s;%s;%s;%s%n", imie, nazwisko, umiejetnosci, doswiadczenie);
            writer.write(linia);
            writer.flush();
            JOptionPane.showMessageDialog(this, "Pracownik dodany pomyślnie", "Sukces", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Wystąpił błąd podczas zapisu danych", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }
}
