package com.company;

import javax.swing.*;
import java.awt.*;


public class FireTask extends JFrame {

    public static void main(String[] args) {

        FireTask fireTask = new FireTask();
        fireTask.setVisible(true);

        fireTask.setSize(1920, 1080);
        fireTask.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        ControlPanel controlPanel = new ControlPanel();
        Viewer viewer = new Viewer();

        c.gridy = 0;
        c.gridx = 0;
        c.gridwidth = 1;
        controlPanel.createButtons();
        fireTask.add(controlPanel, c);

        c.gridy = 0;
        c.gridx = 1;
        c.gridwidth = 2;
        fireTask.add(viewer, c);



    }
}