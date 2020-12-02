package firetask1;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

public class ControlPanel extends JPanel implements ActionListener, ChangeListener {

    private final String[] columnNames = {"Descripción", "Valor"};
    private final String[] columnValue = {"KPixels Totals", "KBytes Totals", "Pixels de alto", "Pixels de ancho", "Canal alpha"};
    private final String[][] data = new String[5][2];
    private byte[] original = null;
    private byte[] copy = null;
    private JTable infoTable;
    private JToggleButton picSelected1, picSelected2, picSelected3, greyButton, todo, recuadro;
    private JSlider sliderBrillo, redSlider, greenSlider, blueSlider, filterSlider, zoneSlider;
    private MyImage myImg;
    private MyImage myImg1;
    private MyImage myImg2;
    private MyImage myImg3;
    private MyImage imgSelected;
    private final Viewer viewer;
    private final Font boldFont = new Font("Arial", Font.BOLD, 18);
    private final Color gray = Color.gray;

    public ControlPanel(Viewer viewer) {
        super();
        this.setLayout(new GridBagLayout());
        this.viewer = viewer;

    }


    public Color getGray() {
        return gray;
    }

    public Font getBoldFont() {
        return boldFont;
    }

    public void createPane() {
        createFileZone();
        createImageZone();
        createBrightnessZone();
        createColorZone();
        createFilterZone();
    }

    private void createFileZone() {

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTHEAST;

        JLabel textFile = new JLabel("ARCHIVO", JLabel.LEFT);
        textFile.setFont(getBoldFont());
        c.ipadx = 20;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 0);
        c.gridwidth = 2;
        this.add(textFile, c);

        JButton selection = new JButton("Cargar Imagen");
        selection.addActionListener(this);
        selection.setFont(getBoldFont());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 0;
        c.gridwidth = 3;
        c.gridheight = 3;
        c.insets = new Insets(0, 0, 0, 0);
        this.add(selection, c);

        this.infoTable = new JTable(data, columnNames);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 3;
        c.gridwidth = 3;
        c.gridheight = 1;

        this.infoTable.setVisible(false);
        this.infoTable.getTableHeader().setVisible(false);
        this.add(infoTable.getTableHeader(), c);
        this.add(infoTable, c);
    }

    private void createImageZone() {
        GridBagConstraints c = new GridBagConstraints();

        JLabel picSelector = new JLabel("IMAGEN");
        c.fill = GridBagConstraints.HORIZONTAL;
        picSelector.setFont(getBoldFont());
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        c.insets = new Insets(200, 0, 20, 0);
        this.add(picSelector, c);

        this.picSelected1 = new JToggleButton("1");
        this.picSelected1.addActionListener(this);
        c.ipadx = 150;
        c.gridx = 2;
        c.gridy = 4;
        c.gridwidth = 1;
        this.add(picSelected1, c);

        this.picSelected2 = new JToggleButton("2");
        this.picSelected2.addActionListener(this);
        c.gridx = 3;
        c.gridy = 4;
        c.gridwidth = 1;
        this.add(picSelected2, c);

        this.picSelected3 = new JToggleButton("3");
        this.picSelected3.addActionListener(this);
        c.gridx = 4;
        c.gridy = 4;
        c.gridwidth = 1;
        this.add(picSelected3, c);

        JLabel zona = new JLabel("ZONA", JLabel.LEFT);
        zona.setFont(getBoldFont());
        c.ipadx = 0;
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 0, 0);
        this.add(zona, c);

        todo = new JToggleButton("TODO");
        todo.setFont(getBoldFont());
        todo.addActionListener(this);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 5;
        c.gridwidth = 1;
        this.add(todo, c);

        recuadro = new JToggleButton("RECUADRO");
        recuadro.setFont(getBoldFont());
        recuadro.addActionListener(this);
        c.gridx = 3;
        c.gridy = 5;
        c.gridwidth = 2;
        this.add(recuadro, c);

        c.insets = new Insets(0, 0, 0, 0);
        JLabel size = new JLabel("Tamaño");
        size.setForeground(getGray());
        c.anchor = GridBagConstraints.WEST;
        c.ipadx = 0;
        c.gridx = 0;
        c.gridy = 6;
        this.add(size, c);

        zoneSlider = new JSlider(0, 50, 0);
        zoneSlider.setMinorTickSpacing(12);
        zoneSlider.setPaintTicks(true);
        zoneSlider.addChangeListener(this);
        c.gridwidth = 3;
        c.gridx = 2;
        c.gridy = 6;
        this.add(zoneSlider, c);
    }

    private void createBrightnessZone() {

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;


        JLabel brillo = new JLabel("BRILLO");
        brillo.setFont(getBoldFont());
        c.ipadx = 0;
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 2;
        c.insets = new Insets(20, 0, 0, 0);
        this.add(brillo, c);

        JButton botonBrillo = new JButton("RESET BRILLO");
        botonBrillo.setFont(getBoldFont());
        botonBrillo.addActionListener(this);
        c.gridx = 2;
        c.gridy = 7;
        c.gridwidth = 3;
        this.add(botonBrillo, c);

        JLabel total = new JLabel("Total", JLabel.LEFT);
        total.setForeground(gray);
        c.ipadx = 0;
        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 2;
        this.add(total, c);

        this.sliderBrillo = new JSlider(0, -100, 100, 0);
        this.sliderBrillo.setName("sliderBrillo");
        sliderBrillo.setMinorTickSpacing(25);
        sliderBrillo.setPaintTicks(true);
        sliderBrillo.addChangeListener(this);
        c.gridwidth = 3;
        c.gridx = 2;
        c.gridy = 8;
        this.add(sliderBrillo, c);

        JLabel rojo = new JLabel("Canal rojo", JLabel.LEFT);
        rojo.setForeground(getGray());
        c.gridx = 0;
        c.gridy = 9;
        c.gridwidth = 2;
        this.add(rojo, c);

        this.redSlider = new JSlider(0, -100, 100, 0);
        this.redSlider.setName("redSlider");
        redSlider.setBackground(Color.red);
        redSlider.setMinorTickSpacing(25);
        redSlider.setPaintTicks(true);
        redSlider.setPaintLabels(true);
        redSlider.addChangeListener(this);
        c.gridwidth = 3;
        c.gridx = 2;
        c.gridy = 9;
        this.add(redSlider, c);

        JLabel verde = new JLabel("Canal verde", JLabel.LEFT);
        verde.setForeground(getGray());
        c.gridx = 0;
        c.gridy = 10;
        c.gridwidth = 2;
        this.add(verde, c);

        this.greenSlider = new JSlider(0, -100, 100, 0);
        greenSlider.setBackground(Color.green);
        greenSlider.setMinorTickSpacing(25);
        greenSlider.setPaintTicks(true);
        greenSlider.addChangeListener(this);
        c.gridwidth = 3;
        c.gridx = 2;
        c.gridy = 10;
        this.add(greenSlider, c);

        JLabel azul = new JLabel("Canal azul", JLabel.LEFT);
        azul.setForeground(getGray());
        c.gridx = 0;
        c.gridy = 11;
        c.gridwidth = 2;
        this.add(azul, c);

        this.blueSlider = new JSlider(0, -100, 100, 0);
        blueSlider.setBackground(Color.blue);
        blueSlider.setMinorTickSpacing(25);
        blueSlider.setPaintTicks(true);
        blueSlider.addChangeListener(this);
        c.gridwidth = 3;
        c.gridx = 2;
        c.gridy = 11;
        this.add(blueSlider, c);
    }

    private void createColorZone() {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel color = new JLabel("COLOR", JLabel.LEFT);
        color.setFont(getBoldFont());
        c.gridx = 0;
        c.gridy = 12;
        c.gridwidth = 2;
        c.insets = new Insets(20, 0, 0, 0);
        this.add(color, c);

        greyButton = new JToggleButton("Convertir a Gris");
        greyButton.addActionListener(this);
        greyButton.setFont(getBoldFont());
        c.gridx = 2;
        c.gridy = 12;
        c.gridwidth = 3;
        this.add(greyButton, c);
    }

    private void createFilterZone() {
        GridBagConstraints c = new GridBagConstraints();
        JLabel filters = new JLabel("Filtros", JLabel.LEFT);
        filters.setFont(getBoldFont());
        c.gridx = 0;
        c.gridy = 13;
        c.insets = new Insets(20, 0, 0, 0);
        this.add(filters, c);

        JLabel focus = new JLabel("Focus", JLabel.LEFT);
        focus.setForeground(gray);
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        c.gridy = 14;
        this.add(focus, c);

        this.filterSlider = new JSlider(-4, 4, 0);
        filterSlider.setMinorTickSpacing(1);
        filterSlider.setPaintTicks(true);
        filterSlider.addChangeListener(this);
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        c.gridx = 2;
        c.gridy = 14;
        this.add(filterSlider, c);

        JLabel unFocus = new JLabel("Focus", JLabel.LEFT);
        unFocus.setForeground(gray);
        c.gridx = 4;
        c.gridy = 14;
        this.add(unFocus, c);

    }

    //Listeners
    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        String event = actionEvent.getActionCommand();
        switch (event) {
            case "Cargar Imagen":
                reiniciarBotones();
                añadirImagen();
                this.viewer.paint(this.viewer.getGraphics());
                añadirContenidoTabla();

                crearCopia(myImg.getMyImg());
                break;
            case "1":
                imgSelected = myImg1;
                recogerInformacionImagen1();
                picSelected2.setSelected(false);
                picSelected3.setSelected(false);
                break;
            case "2":
                imgSelected = myImg2;
                recogerInformacionImagen2();
                picSelected1.setSelected(false);
                picSelected3.setSelected(false);
                break;
            case "3":
                imgSelected = myImg3;
                recogerInformacionImagen3();
                picSelected1.setSelected(false);
                picSelected2.setSelected(false);

                break;
            case "RESET BRILLO":
                sliderBrillo.setValue(0);
                redSlider.setValue(0);
                blueSlider.setValue(0);
                greenSlider.setValue(0);
                aplicarFiltros(sliderBrillo.getValue(), redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue(), filterSlider.getValue());

                break;
            case "Convertir a Gris":
                if (recuadro.isSelected()) {
                    if (greyButton.isSelected()) {
                        imgSelected.setGrisInterior(true);
                    } else {
                        imgSelected.setGrisInterior(false);
                    }
                } else {
                    if (greyButton.isSelected()) {
                        imgSelected.setGrisGeneral(true);
                    } else {
                        imgSelected.setGrisGeneral(false);
                    }

                }
                aplicarFiltros(sliderBrillo.getValue(), redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue(), filterSlider.getValue());

                break;
            case "TODO":
                imgSelected.setCuadrado(false);
                recuadro.setSelected(false);
                todo.setSelected(true);
                sliderBrillo.setValue(imgSelected.getAllChannels());
                redSlider.setValue(imgSelected.getRedChannel());
                blueSlider.setValue((imgSelected.getBlueChannel()));
                greenSlider.setValue((imgSelected.getGreenChannel()));
                filterSlider.setValue(imgSelected.getFilter());
                greyButton.setSelected(imgSelected.getGrisGeneral());
                aplicarFiltros(sliderBrillo.getValue(), redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue(), filterSlider.getValue());
                this.viewer.repaint();
                break;
            case "RECUADRO":
                recuadro.setSelected(true);
                todo.setSelected(false);
                imgSelected.setCuadrado(true);
                sliderBrillo.setValue(imgSelected.getGeneralInterior());
                redSlider.setValue(imgSelected.getRojoInterior());
                blueSlider.setValue((imgSelected.getAzulInterior()));
                greenSlider.setValue((imgSelected.getVerdeInterior()));
                filterSlider.setValue(imgSelected.getFiltroInterior());
                greyButton.setSelected(imgSelected.isGrisInterior());

                aplicarFiltros(sliderBrillo.getValue(), redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue(), filterSlider.getValue());
                this.viewer.repaint();
                break;
            default:
                System.out.println("opcion no valida");
        }

    }


    @Override
    public void stateChanged(ChangeEvent changeEvent) {
        int valorGeneral = sliderBrillo.getValue();
        int valorRojo = redSlider.getValue();
        int valorVerde = greenSlider.getValue();
        int valorAzul = blueSlider.getValue();
        int valorFiltro = filterSlider.getValue();

        if (changeEvent.getSource() == sliderBrillo) {
            if (recuadro.isSelected()) {
                imgSelected.setGeneralInterior(valorGeneral);
            } else {
                imgSelected.setAllChannels(valorGeneral);
            }
            aplicarFiltros(valorGeneral, valorRojo, valorVerde, valorAzul, valorFiltro);

        }
        if (changeEvent.getSource() == redSlider) {
            if (recuadro.isSelected()) {
                imgSelected.setRojoInterior(valorRojo);
            } else {
                imgSelected.setRedChannel(valorRojo);
            }
            aplicarFiltros(valorGeneral, valorRojo, valorVerde, valorAzul, valorFiltro);
        }
        if (changeEvent.getSource() == greenSlider) {
            if (recuadro.isSelected()) {
                imgSelected.setVerdeInterior(valorVerde);
            } else {
                imgSelected.setGreenChannel(valorVerde);
            }
            aplicarFiltros(valorGeneral, valorRojo, valorVerde, valorAzul, valorFiltro);

        }
        if (changeEvent.getSource() == blueSlider) {
            if (recuadro.isSelected()) {
                imgSelected.setAzulInterior(valorAzul);
            } else {
                imgSelected.setBlueChannel(valorAzul);
            }
            aplicarFiltros(valorGeneral, valorRojo, valorVerde, valorAzul, valorFiltro);

        }
        if (changeEvent.getSource() == filterSlider) {
            if (recuadro.isSelected()) {
                imgSelected.setFiltroInterior(valorFiltro);
            } else {
                imgSelected.setFilter(valorFiltro);
            }
            aplicarFiltros(valorGeneral, valorRojo, valorVerde, valorAzul, valorFiltro);

        }
        if (changeEvent.getSource() == zoneSlider) {
            aplicarFiltros(valorGeneral, valorRojo, valorVerde, valorAzul, valorFiltro);
            this.imgSelected.setTamañoCuadrado(zoneSlider.getValue());
        }

    }

    //Funciones de los listensers
    private void añadirImagen() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filterChooser = new FileNameExtensionFilter("JPG, PNG", "jpg", "png");
        fileChooser.setFileFilter(filterChooser);
        int selection = fileChooser.showSaveDialog(this);
        if (selection == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                BufferedImage img = ImageIO.read(file);
                BufferedImage img1 = ImageIO.read(file);
                BufferedImage img2 = ImageIO.read(file);
                BufferedImage img3 = ImageIO.read(file);
                this.myImg = new MyImage(img, false);
                this.myImg1 = new MyImage(img1, false);
                this.myImg2 = new MyImage(img2, false);
                this.myImg3 = new MyImage(img3, false);
                this.viewer.setImgs(this.myImg.getMyImg(), 0);
                this.viewer.setImgs(this.myImg1.getMyImg(), 1);
                this.viewer.setImgs(this.myImg2.getMyImg(), 2);
                this.viewer.setImgs(this.myImg3.getMyImg(), 3);
                this.imgSelected = this.myImg1;


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void añadirContenidoTabla() {
        this.data[0][0] = this.columnValue[0];
        this.data[0][1] = String.valueOf(this.viewer.getImg(0).getWidth() * this.viewer.getImg(0).getHeight());

        Raster raster = this.viewer.getImg(0).getRaster();
        DataBufferByte imageInBytes = (DataBufferByte) raster.getDataBuffer();
        byte[] data = imageInBytes.getData();

        this.data[1][0] = this.columnValue[1];
        this.data[1][1] = String.valueOf(data.length / 1024);

        this.data[2][0] = this.columnValue[2];
        this.data[2][1] = String.valueOf(this.viewer.getImg(0).getHeight());

        this.data[3][0] = this.columnValue[3];
        this.data[3][1] = String.valueOf(this.viewer.getImg(0).getWidth());

        ColorModel cm = this.viewer.getImg(0).getColorModel();
        this.data[4][0] = this.columnValue[4];
        if (cm.hasAlpha()) {
            this.data[4][1] = "Si";
        } else {
            this.data[4][1] = "No";
        }
        this.infoTable.getTableHeader().setVisible(true);
        this.infoTable.setVisible(true);
        this.repaint();
    }

    private void aplicarFiltros(int valorGeneral, int valorRojo, int valorVerde, int valorAzul, int valorFiltro) {
        int blueChannel = 0;
        int greenChannel = 1;
        int redChannel = 2;
        int tamaño = zoneSlider.getValue();
        int rojoInterior = imgSelected.getRojoInterior();
        int verdeInterior = imgSelected.getVerdeInterior();
        int azulInterior = imgSelected.getAzulInterior();
        imgSelected.dimensionarCuadrado(tamaño);


        this.imgSelected.cambiarBrilloGeneral(copy, valorGeneral);
        this.imgSelected.cambiarBrillo(copy, valorRojo, rojoInterior, redChannel);
        this.imgSelected.cambiarBrillo(copy, valorVerde, verdeInterior, greenChannel);
        this.imgSelected.cambiarBrillo(copy, valorAzul, azulInterior, blueChannel);

        this.imgSelected.aplicarFiltro(this.imgSelected.getFilter(), this.imgSelected.getMyImg(), tamaño);
        if (this.imgSelected.getGrisGeneral() || this.imgSelected.isGrisInterior()) {
            this.imgSelected.cambiarGrises(this.imgSelected.getMyImg(), tamaño);
        }
        this.viewer.repaint();

    }

    private void crearCopia(BufferedImage imagen) {
        original = ((DataBufferByte) imagen.getRaster().getDataBuffer()).getData();
        copy = new byte[original.length];
        for (int i = 0; i < this.original.length; i++) {
            copy[i] = this.original[i];
        }

    }

    private void recogerInformacionImagen1() {
        if (todo.isSelected()) {
            this.redSlider.setValue(this.myImg1.getRedChannel());
            this.greenSlider.setValue(this.myImg1.getGreenChannel());
            this.blueSlider.setValue(this.myImg1.getBlueChannel());
            this.sliderBrillo.setValue(this.myImg1.getAllChannels());
            filterSlider.setValue(myImg1.getFilter());
            greyButton.setSelected(myImg1.getGrisGeneral());
            zoneSlider.setValue(myImg1.getTamañoCuadrado());
        } else if (recuadro.isSelected()) {
            this.redSlider.setValue(this.myImg1.getRojoInterior());
            this.greenSlider.setValue(this.myImg1.getVerdeInterior());
            this.blueSlider.setValue(this.myImg1.getAzulInterior());
            this.sliderBrillo.setValue(this.myImg1.getGeneralInterior());
            filterSlider.setValue(myImg1.getFiltroInterior());
            greyButton.setSelected(myImg1.isGrisInterior());
            zoneSlider.setValue(myImg1.getTamañoCuadrado());

        }
    }

    private void recogerInformacionImagen2() {
        if (todo.isSelected()) {
            redSlider.setValue(myImg2.getRedChannel());
            greenSlider.setValue(myImg2.getGreenChannel());
            blueSlider.setValue(myImg2.getBlueChannel());
            sliderBrillo.setValue(myImg2.getAllChannels());
            filterSlider.setValue(myImg2.getFilter());
            greyButton.setSelected(myImg2.getGrisGeneral());
            zoneSlider.setValue(myImg2.getTamañoCuadrado());

        } else if (recuadro.isSelected()) {
            redSlider.setValue(myImg2.getRojoInterior());
            greenSlider.setValue(myImg2.getVerdeInterior());
            blueSlider.setValue(myImg2.getAzulInterior());
            sliderBrillo.setValue(myImg2.getGeneralInterior());
            filterSlider.setValue(myImg2.getFiltroInterior());
            greyButton.setSelected(myImg2.isGrisInterior());
            zoneSlider.setValue(myImg2.getTamañoCuadrado());
        }
    }

    private void recogerInformacionImagen3() {
        if (todo.isSelected()) {
            redSlider.setValue(myImg3.getRedChannel());
            greenSlider.setValue(myImg3.getGreenChannel());
            blueSlider.setValue(myImg3.getBlueChannel());
            sliderBrillo.setValue(myImg3.getAllChannels());
            filterSlider.setValue(myImg3.getFilter());
            greyButton.setSelected(myImg3.getGrisGeneral());
            zoneSlider.setValue(myImg3.getTamañoCuadrado());
        } else if (recuadro.isSelected()){
            redSlider.setValue(myImg3.getRojoInterior());
            greenSlider.setValue(myImg3.getVerdeInterior());
            blueSlider.setValue(myImg3.getAzulInterior());
            sliderBrillo.setValue(myImg3.getGeneralInterior());
            filterSlider.setValue(myImg3.getFiltroInterior());
            greyButton.setSelected(myImg3.isGrisInterior());
            zoneSlider.setValue(myImg3.getTamañoCuadrado());
        }
    }

    private void reiniciarBotones() {
        todo.setSelected(true);
        recuadro.setSelected(false);
        sliderBrillo.setValue(0);
        redSlider.setValue(0);
        blueSlider.setValue(0);
        greenSlider.setValue(0);
        zoneSlider.setValue(0);
        greyButton.setSelected(false);
        filterSlider.setValue(0);
        infoTable.setVisible(true);
        picSelected1.setSelected(true);
        picSelected2.setSelected(false);
        picSelected3.setSelected(false);
    }


}
