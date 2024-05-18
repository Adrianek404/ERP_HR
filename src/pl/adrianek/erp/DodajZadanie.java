package pl.adrianek.erp;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.List;

public class DodajZadanie  extends JFrame  implements ActionListener {

    JList<String> listaPracownikow;
    JTextArea trescZadania;
    JDateChooser dataZakonczenia;
    JButton dodajZadanie, powrot;

    DodajZadanie(){
        setLayout(null);
        setTitle("Dodaj zadanie");

        getContentPane().setBackground(Color.WHITE);

        setSize(600, 400);
        setLocation(600, 350);

        JLabel pracownicyLabel = new JLabel("Wybierz pracowników:");
        pracownicyLabel.setFont(new Font("Raleway", Font.BOLD, 14));
        pracownicyLabel.setBounds(20, 20, 200, 25);
        add(pracownicyLabel);

        listaPracownikow = new JList<>(wczytajPracownikow());
        listaPracownikow.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane pracownicyScrollPane = new JScrollPane(listaPracownikow);
        pracownicyScrollPane.setBounds(20, 50, 200, 200);
        add(pracownicyScrollPane);

        JLabel trescLabel = new JLabel("Treść zadania:");
        trescLabel.setFont(new Font("Raleway", Font.BOLD, 14));
        trescLabel.setBounds(250, 20, 200, 25);
        add(trescLabel);

        trescZadania = new JTextArea();
        JScrollPane trescScrollPane = new JScrollPane(trescZadania);
        trescScrollPane.setBounds(250, 50, 300, 100);
        add(trescScrollPane);

        JLabel dataZakonczeniaLabel = new JLabel("Data zakończenia:");
        dataZakonczeniaLabel.setFont(new Font("Raleway", Font.BOLD, 14));
        dataZakonczeniaLabel.setBounds(250, 170, 200, 25);
        add(dataZakonczeniaLabel);

        dataZakonczenia = new JDateChooser();
        dataZakonczenia.setBounds(250, 200, 200, 25);
        dataZakonczenia.setMinSelectableDate(new Date());
        add(dataZakonczenia);

        dodajZadanie = new JButton("Dodaj zadanie");
        dodajZadanie.setBounds(250, 250, 150, 30);
        dodajZadanie.setBorderPainted(false);
        dodajZadanie.setOpaque(true);
        dodajZadanie.setBackground(Color.BLACK);
        dodajZadanie.setForeground(Color.WHITE);
        dodajZadanie.addActionListener(this);
        add(dodajZadanie);

        powrot = new JButton("Powrót");
        powrot.setBounds(420, 250, 100, 30);
        powrot.setBorderPainted(false);
        powrot.setOpaque(true);
        powrot.setBackground(Color.BLACK);
        powrot.setForeground(Color.WHITE);
        powrot.addActionListener(this);
        add(powrot);

        setVisible(true);
    }

    private Vector<String> wczytajPracownikow() {
        Vector<String> pracownicy = new Vector<>();
        try (BufferedReader br = new BufferedReader(new FileReader("pracownicy.txt"))) {
            String linia;
            while ((linia = br.readLine()) != null) {
                pracownicy.add(linia.split(";")[0] + " " + linia.split(";")[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pracownicy;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(dodajZadanie)) {
            dodajZadanie();
        } else if (e.getSource().equals(powrot)) {
            setVisible(false);
            new ProjectManager().setVisible(true);
        }
    }

    private void dodajZadanie() {
        List<String> wybraniPracownicy = listaPracownikow.getSelectedValuesList();
        String tresc = trescZadania.getText();
        Date data = dataZakonczenia.getDate();

        if (wybraniPracownicy.isEmpty() || tresc.isEmpty() || data == null) {
            JOptionPane.showMessageDialog(this, "Wszystkie pola są wymagane", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dataString = dateFormat.format(data);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("zadania.txt", true))) {
            for (String pracownik : wybraniPracownicy) {
                writer.write(pracownik + ";" + tresc + ";" + dataString +";zadane" + System.lineSeparator());
            }
            JOptionPane.showMessageDialog(this, "Zadanie dodane pomyślnie", "Sukces", JOptionPane.INFORMATION_MESSAGE);
            setVisible(false);
            new ProjectManager().setVisible(true);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Wystąpił błąd podczas zapisu danych", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }
}
