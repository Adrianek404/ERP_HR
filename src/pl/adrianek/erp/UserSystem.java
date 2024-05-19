package pl.adrianek.erp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserSystem extends JFrame implements ActionListener {

    private String imie;
    private String nazwisko;
    private JPanel zadaniaPanel;
    private JButton wylogujButton;
    private List<String[]> zadaniaList;
    public UserSystem(String imie, String nazwisko) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        setTitle("Użytkownik: "+ imie +" " +nazwisko);

        setLayout(null);

        getContentPane().setBackground(Color.WHITE);

        setSize(800, 400);
        setLocation(600, 350);

        JLabel welcomeLabel = new JLabel("Witaj: " + imie + " " + nazwisko);
        welcomeLabel.setFont(new Font("Raleway", Font.BOLD, 14));
        welcomeLabel.setBounds(20, 20, 300, 30);
        add(welcomeLabel);

        zadaniaPanel = new JPanel();
        zadaniaPanel.setBounds(50, 70, 600, 200);
        zadaniaPanel.setLayout(new BoxLayout(zadaniaPanel, BoxLayout.Y_AXIS));
        add(zadaniaPanel);

        wylogujButton = new JButton("Wyloguj");
        wylogujButton.setBounds(450, 300, 100, 30);
        wylogujButton.setBorderPainted(false);
        wylogujButton.setOpaque(true);
        wylogujButton.setBackground(Color.BLACK);
        wylogujButton.setForeground(Color.WHITE);
        wylogujButton.addActionListener(this);
        add(wylogujButton);

        wczytajZadania();

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == wylogujButton) {
            setVisible(false);
            new LoginUser();
        }
    }

    private void wczytajZadania() {
        zadaniaList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("zadania.txt"))) {
            String linia;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date dzisiaj = new Date();

            while ((linia = br.readLine()) != null) {
                String[] dane = linia.split(";");
                if (dane[0].equalsIgnoreCase(imie + " " + nazwisko) && dane[3].equals("zadane")) {
                    zadaniaList.add(dane);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (zadaniaList.isEmpty()) {
            JLabel brakZadanLabel = new JLabel("Brak aktualnie zadań");
            brakZadanLabel.setFont(new Font("Raleway", Font.BOLD, 14));
            zadaniaPanel.add(brakZadanLabel);
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date dzisiaj = new Date();

            for (String[] zadanie : zadaniaList) {
                JPanel zadaniePanel = new JPanel();
                zadaniePanel.setLayout(new BorderLayout());

                JLabel zadanieLabel = new JLabel("Treść zadania: " + zadanie[1] + ", Czas wykonania: " + zadanie[2]);
                zadanieLabel.setFont(new Font("Arial", Font.PLAIN, 12));
                zadaniePanel.add(zadanieLabel, BorderLayout.CENTER);

                try {
                    Date dataZakonczenia = dateFormat.parse(zadanie[2]);
                    if (dataZakonczenia.before(dzisiaj)) {
                        JLabel notatkaLabel = new JLabel("Nie zdążyłeś wykonać zadania na czas");
                        notatkaLabel.setFont(new Font("Arial", Font.ITALIC, 10));
                        JPanel notatkaPanel = new JPanel(new BorderLayout());
                        notatkaPanel.add(notatkaLabel, BorderLayout.NORTH);
                        notatkaPanel.add(zadaniePanel, BorderLayout.CENTER);
                        zadaniaPanel.add(notatkaPanel);
                    } else {
                        JButton wykonaneButton = new JButton("Wykonane");
                        wykonaneButton.setBorderPainted(false);
                        wykonaneButton.setOpaque(true);
                        wykonaneButton.setBackground(Color.BLACK);
                        wykonaneButton.setForeground(Color.WHITE);
                        wykonaneButton.addActionListener(e -> oznaczJakoWykonane(zadanie));
                        zadaniePanel.add(wykonaneButton, BorderLayout.EAST);
                        zadaniaPanel.add(zadaniePanel);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        zadaniaPanel.revalidate();
        zadaniaPanel.repaint();
    }

    private void oznaczJakoWykonane(String[] zadanie) {
        zadaniaList.remove(zadanie);
        zadaniaPanel.removeAll();
        if (zadaniaList.isEmpty()) {
            JLabel brakZadanLabel = new JLabel("Brak aktualnie zadań");
            brakZadanLabel.setFont(new Font("Raleway", Font.BOLD, 14));
            zadaniaPanel.add(brakZadanLabel);
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date dzisiaj = new Date();

            for (String[] z : zadaniaList) {
                JPanel zadaniePanel = new JPanel();
                zadaniePanel.setLayout(new BorderLayout());

                JLabel zadanieLabel = new JLabel("Treść zadania: " + z[1] + ", Czas wykonania: " + z[2]);
                zadanieLabel.setFont(new Font("Arial", Font.PLAIN, 12));
                zadaniePanel.add(zadanieLabel, BorderLayout.CENTER);

                try {
                    Date dataZakonczenia = dateFormat.parse(z[2]);
                    if (dataZakonczenia.before(dzisiaj)) {
                        JLabel notatkaLabel = new JLabel("Nie zdążyłeś wykonać zadania na czas");
                        notatkaLabel.setFont(new Font("Arial", Font.ITALIC, 10));
                        JPanel notatkaPanel = new JPanel(new BorderLayout());
                        notatkaPanel.add(notatkaLabel, BorderLayout.NORTH);
                        notatkaPanel.add(zadaniePanel, BorderLayout.CENTER);
                        zadaniaPanel.add(notatkaPanel);
                    } else {
                        JButton wykonaneButton = new JButton("Wykonane");
                        wykonaneButton.setBorderPainted(false);
                        wykonaneButton.setOpaque(true);
                        wykonaneButton.setBackground(Color.BLACK);
                        wykonaneButton.setForeground(Color.WHITE);
                        wykonaneButton.addActionListener(e -> oznaczJakoWykonane(z));
                        zadaniePanel.add(wykonaneButton, BorderLayout.EAST);
                        zadaniaPanel.add(zadaniePanel);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        zadaniaPanel.revalidate();
        zadaniaPanel.repaint();

        try (BufferedReader br = new BufferedReader(new FileReader("zadania.txt"));
             PrintWriter pw = new PrintWriter(new FileWriter("zadania_temp.txt"))) {
            String linia;
            while ((linia = br.readLine()) != null) {
                String[] dane = linia.split(";");
                if (dane[0].equalsIgnoreCase(imie + " " + nazwisko) && dane[1].equals(zadanie[1]) && dane[2].equals(zadanie[2])) {
                    dane[3] = "wykonane";
                    pw.println(String.join(";", dane));
                } else {
                    pw.println(linia);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        File oryginalnyPlik = new File("zadania.txt");
        File tymczasowyPlik = new File("zadania_temp.txt");
        if (oryginalnyPlik.delete()) {
            tymczasowyPlik.renameTo(oryginalnyPlik);
        } else {
            JOptionPane.showMessageDialog(this, "Wystąpił błąd podczas aktualizacji pliku", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }
}
