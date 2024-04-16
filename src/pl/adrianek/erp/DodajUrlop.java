package pl.adrianek.erp;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DodajUrlop extends JFrame implements ActionListener {

    JButton dodaj, usun, powrot;
    JDateChooser Uod, Udo;
    String imie, nazwisko;
    DodajUrlop(String imie, String nazwisko){
        this.imie = imie;
        this.nazwisko = nazwisko;
        setTitle("Zarządzanie urlopami");

        setLayout(null);

        getContentPane().setBackground(Color.WHITE);

        setSize(600, 400);
        setLocation(600, 350);

        JLabel ImieL = new JLabel("Pracownik: "+ imie + " "+ nazwisko);
        ImieL.setFont(new Font("Raleway", Font.BOLD, 28));
        ImieL.setBounds(20, 20, 500, 50);
        add(ImieL);

        JLabel aktualny = new JLabel("Aktualny urlop: "+ sprawdzUrlop());
        aktualny.setFont(new Font("Arial", Font.BOLD, 14));
        aktualny.setBounds(20, 70, 500, 50);
        add(aktualny);

        JLabel odtext = new JLabel("Od");
        odtext.setFont(new Font("Raleway", Font.BOLD, 28));
        odtext.setBounds(20, 140, 50, 50);
        add(odtext);

        Uod = new JDateChooser();
        Uod.setBounds(70, 140, 100, 50);
        Uod.setForeground(new Color(105, 105,105));
        Uod.setDate(new Date());
        Uod.setMinSelectableDate(new Date());
        add(Uod);

        JLabel dotext = new JLabel("Do");
        dotext.setFont(new Font("Raleway", Font.BOLD, 28));
        dotext.setBounds(180, 140, 50, 50);
        add(dotext);

        Udo = new JDateChooser();
        Udo.setBounds(240, 140, 100, 50);
        Udo.setForeground(new Color(105, 105, 105));
        Udo.setMinSelectableDate(new Date());
        Udo.setDate(new Date());
        add(Udo);

        powrot = new JButton("Powrót");
        powrot.setBounds((sprawdzUrlop().equalsIgnoreCase("brak") ? 150 : 100), 250, 120, 30);
        powrot.setBorderPainted(false);
        powrot.setOpaque(true);
        powrot.setBackground(Color.BLACK);
        powrot.setForeground(Color.WHITE);
        powrot.addActionListener(this);
        add(powrot);

        dodaj = new JButton("Dodaj");
        dodaj.setBounds((sprawdzUrlop().equalsIgnoreCase("brak") ? 280 : 230), 250, 120, 30);
        dodaj.setBorderPainted(false);
        dodaj.setOpaque(true);
        dodaj.setBackground(Color.BLACK);
        dodaj.setForeground(Color.WHITE);
        dodaj.addActionListener(this);
        add(dodaj);

        if (!sprawdzUrlop().equalsIgnoreCase("brak")){
            usun = new JButton("Usuń");
            usun.setBounds(360, 250, 120, 30);
            usun.setBorderPainted(false);
            usun.setOpaque(true);
            usun.setBackground(Color.BLACK);
            usun.setForeground(Color.WHITE);
            usun.addActionListener(this);
            add(usun);
        }

        setVisible(true);
    }

    public String sprawdzUrlop(){
        try (BufferedReader br = new BufferedReader(new FileReader("urlopy.txt"))) {
            String linia;
            while ((linia = br.readLine()) != null) {
                String[] dane = linia.split(";");
                if (dane[0].equals(imie) && dane[1].equals(nazwisko)) {
                    return dane[2] + "-" + dane[3];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Brak";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(powrot)){
            setVisible(false);
            new Pracownicy().setVisible(true);
        }
        if (e.getSource().equals(dodaj)){
            Date dataOd = Uod.getDate();
            Date dataDo = Udo.getDate();
            if (dataOd != null && dataDo != null && dataOd.before(dataDo)) {
                dodajUrlop(imie, nazwisko, dataOd, dataDo);
                setVisible(false);
                new Pracownicy().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Nieprawidłowy zakres dat", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource().equals(usun)){
            usunUrlop(imie, nazwisko);
        }

    }

    private void dodajUrlop(String imie, String nazwisko, java.util.Date dataOd, java.util.Date dataDo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("urlopy.txt", true))) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            String linia = String.format("%s;%s;%s;%s%n", imie, nazwisko, dateFormat.format(dataOd), dateFormat.format(dataDo));
            writer.write(linia);
            writer.flush();
            JOptionPane.showMessageDialog(this, "Urlop dodany pomyślnie", "Sukces", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Wystąpił błąd podczas zapisu danych", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void usunUrlop(String imie, String nazwisko) {
        File inputFile = new File("urlopy.txt");
        File tempFile = new File("temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String linia;
            while ((linia = reader.readLine()) != null) {
                String[] dane = linia.split(";");
                if (!(dane[0].equals(imie) && dane[1].equals(nazwisko))) {
                    writer.write(linia + System.getProperty("line.separator"));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (inputFile.delete()) {
            if (!tempFile.renameTo(inputFile)) {
                JOptionPane.showMessageDialog(this, "Wystąpił błąd podczas zmiany nazwy tymczasowego pliku", "Błąd", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Urlop usunięty pomyślnie", "Sukces", JOptionPane.INFORMATION_MESSAGE);
                setVisible(false);
                new Pracownicy().setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Wystąpił błąd podczas usuwania pliku z urlopami", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }
}
