package com.company;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {

    public void createButtons() {

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel textFile = new JLabel();
        textFile.setText("ARCHIVO");
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.SOUTHWEST;
        this.add(textFile, c);

        JButton seleccionar = new JButton("Cargar Imagen");
        c.gridx = 1;
        c.gridy = 0;
        this.add(seleccionar, c);
/*
        JTable infoTable = new JTable();
        c.gridx = 1;
        c.gridy = 1;
        this.add(infoTable, c);
*/


        JLabel picSelector = new JLabel();
        picSelector.setText("Imagen");
        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.SOUTHWEST;
        this.add(picSelector, c);

        JButton picSelected1 = new JButton("1");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
        this.add(picSelected1, c);


        JButton picSelected2 = new JButton("2");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 2;

        c.gridwidth = 1;
        this.add(picSelected2, c);


        JButton picSelected3 = new JButton("3");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 2;

        c.gridwidth = 1;
        this.add(picSelected3, c);

/*
        JFileChooser fileChooser = new JFileChooser("Cargar imagen");
        c.gridx = 1;
        c.gridy = 0;
        this.add(fileChooser, c);*/

    }

}
