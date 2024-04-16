package pl.adrianek.erp;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Pracownicy extends JFrame {

    private JPanel panel;
    Pracownicy(){

        setTitle("Pracownicy");

        getContentPane().setBackground(Color.WHITE);

        setSize(600, 400);
        setLocation(600, 350);

        panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        add(panel, BorderLayout.CENTER);

        JButton dodajButton = new JButton("Dodaj");
        dodajButton.setBorderPainted(false);
        dodajButton.setOpaque(true);
        dodajButton.setBackground(Color.BLACK);
        dodajButton.setForeground(Color.WHITE);
        dodajButton.addActionListener(e -> {
            setVisible(false);
            new DodajPracownika().setVisible(true);
        });
        add(dodajButton, BorderLayout.NORTH);

        dodajPracownikowZPliku();

        setVisible(true);
    }

    private void dodajPracownikowZPliku() {
        try (BufferedReader br = new BufferedReader(new FileReader("pracownicy.txt"))) {
            String linia;
            while ((linia = br.readLine()) != null) {
                String[] dane = linia.split(";");
                if (dane.length >= 4) {
                    dodajPracownika(dane[0], dane[1], dane[2], dane[3]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dodajPracownika(String imie, String nazwisko, String umiejetnosci, String doswiadczenie) {
        JPanel pracownikPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pracownikPanel.add(new JLabel("Imię: " + imie));
        pracownikPanel.add(new JLabel("Nazwisko: " + nazwisko));
        pracownikPanel.add(new JLabel("Umiejętności: " + umiejetnosci));
        pracownikPanel.add(new JLabel("Doświadczenie: " + doswiadczenie));

        JButton usunButton = new JButton("Usuń");
        usunButton.addActionListener(e -> {
            int odpowiedz = JOptionPane.showConfirmDialog(this, "Czy na pewno chcesz usunąć tego pracownika?", "Potwierdzenie", JOptionPane.YES_NO_OPTION);
            if (odpowiedz == JOptionPane.YES_OPTION) {
                usunPracownika(imie, nazwisko, umiejetnosci, doswiadczenie);
                panel.remove(pracownikPanel);
                revalidate();
                repaint();
            }
        });
        JButton urlopButton = new JButton("Urlop");
        urlopButton.addActionListener(e -> {
            this.setVisible(false);
            new DodajUrlop(imie, nazwisko);
        });

        pracownikPanel.add(usunButton);
        pracownikPanel.add(urlopButton);

        panel.add(pracownikPanel);
        revalidate();
    }

    private void usunPracownika(String imie, String nazwisko, String umiejetnosci, String doswiadczenie) {
        String liniaDoUsuniecia = String.format("%s;%s;%s;%s", imie, nazwisko, umiejetnosci, doswiadczenie);
        try (BufferedReader br = new BufferedReader(new FileReader("pracownicy.txt"));
             FileWriter fw = new FileWriter("temp.txt")) {
            String linia;
            while ((linia = br.readLine()) != null) {
                if (!linia.equals(liniaDoUsuniecia)) {
                    fw.write(linia + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Files.move(Paths.get("temp.txt"), Paths.get("pracownicy.txt"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
