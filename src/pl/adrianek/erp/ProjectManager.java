package pl.adrianek.erp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProjectManager extends JFrame implements ActionListener {

    JButton DodajZadanie, ListaZadan;

    ProjectManager(){
        setTitle("Project Manager");

        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        setSize(600, 400);
        setLocation(600, 350);

        DodajZadanie = new JButton("Dodaj zadanie");
        DodajZadanie.setBounds(150, 250, 120, 30);
        DodajZadanie.setBorderPainted(false);
        DodajZadanie.setOpaque(true);
        DodajZadanie.setBackground(Color.BLACK);
        DodajZadanie.setForeground(Color.WHITE);
        DodajZadanie.addActionListener(this);
        add(DodajZadanie);

        ListaZadan = new JButton("Lista Zadań");
        ListaZadan.setBounds(280, 250, 120, 30);
        ListaZadan.setBorderPainted(false);
        ListaZadan.setOpaque(true);
        ListaZadan.setBackground(Color.BLACK);
        ListaZadan.setForeground(Color.WHITE);
        ListaZadan.addActionListener(this);
        add(ListaZadan);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(DodajZadanie)){
            setVisible(false);
            new DodajZadanie().setVisible(true);
        }
        if (e.getSource().equals(ListaZadan)){
            setVisible(false);
            new Zadania().setVisible(true);
        }
    }
}
