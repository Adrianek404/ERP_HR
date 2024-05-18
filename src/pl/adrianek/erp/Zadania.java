package pl.adrianek.erp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Zadania  extends JFrame {

    JTable tabelaZadan;
    DefaultTableModel model;

    Zadania(){
        setTitle("Zadania");

        getContentPane().setBackground(Color.WHITE);


        setSize(600, 400);
        setLocation(600, 350);

        String[] columnNames = {"Pracownik", "Treść", "Data zakończenia", "Status"};
        model = new DefaultTableModel(columnNames, 0);

        tabelaZadan = new JTable(model);
        tabelaZadan.setFillsViewportHeight(true);
        tabelaZadan.setEnabled(false);  // Disable editing
        JScrollPane scrollPane = new JScrollPane(tabelaZadan);
        add(scrollPane, BorderLayout.CENTER);

        wczytajZadania();

        setVisible(true);
    }

    private void wczytajZadania() {
        try (BufferedReader br = new BufferedReader(new FileReader("zadania.txt"))) {
            String linia;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date dzisiaj = dateFormat.parse(dateFormat.format(new Date()));

            while ((linia = br.readLine()) != null) {
                String[] dane = linia.split(";");
                String dataZakonczeniaStr = dane[2];
                String status = dane[3];
                try {
                    Date dataZakonczenia = dateFormat.parse(dataZakonczeniaStr);
                    if (dataZakonczenia.before(dzisiaj) && status.equals("zadane")) {
                        status = "nie zrobione na czas";
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                model.addRow(new Object[]{dane[0], dane[1], dataZakonczeniaStr, status});
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
