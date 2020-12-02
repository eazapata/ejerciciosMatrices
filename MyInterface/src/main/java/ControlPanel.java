import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

public class ControlPanel extends JPanel implements ActionListener, ChangeListener {


    private JTable infoTable;
    private JToggleButton picSelected1, picSelected2, picSelected3, grayButton, todo, recuadro;
    private JSlider sliderBrillo, redSlider, greenSlider, blueSlider, filterSlider, zoneSlider;
    private MyImage myImg;
    private MyImage myImg1;
    private MyImage myImg2;
    private MyImage myImg3;
    private MyImage imgSelected;
    private Viewer viewer;


    public ControlPanel() {
        super();
        this.setLayout(new GridBagLayout());
    }

//--------------------FUNCIONES PARA CREAR EL PANEL ---------------------------------------
    public void createPane() {
        createFileZone();
        createImageZone();
        createBrightnessZone();
        createColorZone();
        createFilterZone();
    }

    private void createFileZone() {
        final Font boldFont = new Font("Arial", Font.BOLD, 18);
        String[][] data = new String[][]{
                {"KPixels totals", ""},
                {"KBytes Totales", ""},
                {"Pixels Ancho", ""},
                {"Pixels alto", ""},
                {"Canal Alpha", ""}};
        String[] column = new String[]{"Descripción", "Valor"};
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTHEAST;

        JLabel textFile = new JLabel("ARCHIVO", JLabel.LEFT);
        textFile.setFont(boldFont);
        c.ipadx = 20;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 0);
        c.gridwidth = 2;
        this.add(textFile, c);

        JButton selection = new JButton("Cargar Imagen");
        selection.addActionListener(this);
        selection.setFont(boldFont);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 0;
        c.gridwidth = 3;
        c.gridheight = 3;
        c.insets = new Insets(0, 0, 20, 0);
        this.add(selection, c);


        c.fill = GridBagConstraints.HORIZONTAL;
        this.infoTable = new JTable(data, column);
        c.gridx = 2;
        c.gridy = 3;
        c.gridwidth = 3;
        c.gridheight = 1;
        this.infoTable.getTableHeader().setVisible(true);
        this.add(this.infoTable.getTableHeader(), c);
        c.insets = new Insets(20, 0, 0, 0);
        this.add(infoTable, c);
        this.infoTable.setVisible(true);
    }

    private void createImageZone() {
        Font boldFont = new Font("Arial", Font.BOLD, 18);
        Color gray = Color.gray;
        GridBagConstraints c = new GridBagConstraints();
        JLabel picSelector = new JLabel("IMAGEN");
        c.fill = GridBagConstraints.HORIZONTAL;
        picSelector.setFont(boldFont);
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        c.insets = new Insets(150, 0, 20, 0);
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
        zona.setFont(boldFont);
        c.ipadx = 0;
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 0, 0);
        this.add(zona, c);

        todo = new JToggleButton("TODO");
        todo.setFont(boldFont);
        todo.addActionListener(this);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 5;
        c.gridwidth = 1;
        this.add(todo, c);

        recuadro = new JToggleButton("RECUADRO");
        recuadro.setFont(boldFont);
        recuadro.addActionListener(this);
        c.gridx = 3;
        c.gridy = 5;
        c.gridwidth = 2;
        this.add(recuadro, c);

        c.insets = new Insets(50, 0, 0, 0);
        JLabel size = new JLabel("Tamaño");
        size.setForeground(gray);
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
        Font boldFont = new Font("Arial", Font.BOLD, 18);
        Color gray = Color.gray;
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;


        JLabel brillo = new JLabel("BRILLO");
        brillo.setFont(boldFont);
        c.ipadx = 0;
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 2;
        c.insets = new Insets(20, 0, 0, 0);
        this.add(brillo, c);

        JButton botonBrillo = new JButton("RESET BRILLO");
        botonBrillo.setFont(boldFont);
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
        rojo.setForeground(gray);
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 0;
        c.gridy = 9;
        c.gridwidth = 2;
        this.add(rojo, c);

        this.redSlider = new JSlider(0, -100, 100, 0);
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
        verde.setForeground(gray);
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
        azul.setForeground(gray);
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
        Font boldFont = new Font("Arial", Font.BOLD, 18);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel color = new JLabel("COLOR", JLabel.LEFT);
        color.setFont(boldFont);
        c.gridx = 0;
        c.gridy = 12;
        c.gridwidth = 2;
        c.insets = new Insets(20, 0, 0, 0);
        this.add(color, c);

        grayButton = new JToggleButton("Convertir a Gris");
        grayButton.addActionListener(this);
        grayButton.setFont(boldFont);
        c.gridx = 2;
        c.gridy = 12;
        c.gridwidth = 3;
        this.add(grayButton, c);
    }

    private void createFilterZone() {
        final Font boldFont = new Font("Arial", Font.BOLD, 18);
        final Color gray = Color.gray;
        GridBagConstraints c = new GridBagConstraints();
        JLabel filters = new JLabel("Filtros", JLabel.LEFT);
        filters.setFont(boldFont);
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

        JLabel unFocus = new JLabel("Unfocus", JLabel.LEFT);
        unFocus.setForeground(gray);
        c.gridx = 4;
        c.gridy = 14;
        this.add(unFocus, c);

    }

    //----------------------------------------Listeners-----------------------------
    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        String event = actionEvent.getActionCommand();

        if (event.equals("Cargar Imagen")) {
            resetButtons();
            addImg();
            this.viewer.paint(this.viewer.getGraphics());
            addTableContent();
            this.infoTable.setVisible(true);
        }
        if (imgSelected != null) {
            switch (event) {
                case "1":
                    this.imgSelected = myImg1;
                    getImgData(this.myImg1);
                    this.picSelected2.setSelected(false);
                    this.picSelected3.setSelected(false);
                    break;
                case "2":
                    this.imgSelected = this.myImg2;
                    getImgData(this.myImg2);
                    this.picSelected1.setSelected(false);
                    this.picSelected3.setSelected(false);
                    break;
                case "3":
                    this.imgSelected = myImg3;
                    getImgData(this.myImg3);
                    this.picSelected1.setSelected(false);
                    this.picSelected2.setSelected(false);
                    break;
                case "RESET BRILLO":
                    this.sliderBrillo.setValue(0);
                    this.redSlider.setValue(0);
                    this.blueSlider.setValue(0);
                    this.greenSlider.setValue(0);
                    applyFilters();
                    break;
                case "Convertir a Gris":
                    if (this.recuadro.isSelected()) {
                        this.imgSelected.setGrayInside(this.grayButton.isSelected());
                    } else {
                        this.imgSelected.setGeneralGray(this.grayButton.isSelected());
                    }
                    applyFilters();
                    break;
                case "TODO":
                    this.recuadro.setSelected(false);
                    this.todo.setSelected(true);
                    this.sliderBrillo.setValue(this.imgSelected.getGeneralBrightness());
                    this.redSlider.setValue(this.imgSelected.getRedChannelValue());
                    this.blueSlider.setValue((this.imgSelected.getBlueChannelValue()));
                    this.greenSlider.setValue((this.imgSelected.getGreenChannelValue()));
                    this.filterSlider.setValue(this.imgSelected.getFilter());
                    this.grayButton.setSelected(this.imgSelected.getGeneralGray());
                    applyFilters();
                    break;
                case "RECUADRO":
                    this.recuadro.setSelected(true);
                    this.todo.setSelected(false);
                    this.sliderBrillo.setValue(this.imgSelected.getBrightInside());
                    this.redSlider.setValue(this.imgSelected.getRedInside());
                    this.blueSlider.setValue(this.imgSelected.getBlueInside());
                    this.greenSlider.setValue(this.imgSelected.getGreenInside());
                    this.filterSlider.setValue(this.imgSelected.getFilterInside());
                    this.grayButton.setSelected(this.imgSelected.isGrayInside());
                    applyFilters();
                    break;
                default:
                    System.out.println("opcion no valida");
            }

        }
    }

    @Override
    public void stateChanged(ChangeEvent changeEvent) {

        if (this.imgSelected != null) {
            if (changeEvent.getSource() == this.sliderBrillo) {
                if (this.recuadro.isSelected()) {
                    this.imgSelected.setBrightInside(this.sliderBrillo.getValue());
                } else {
                    this.imgSelected.setGeneralBrightness(this.sliderBrillo.getValue());
                }
                applyFilters();
            }
            if (changeEvent.getSource() == this.redSlider) {
                if (this.recuadro.isSelected()) {
                    this.imgSelected.setRedInside(this.redSlider.getValue());
                } else {
                    this.imgSelected.setRedChannelValue(this.redSlider.getValue());
                }
                applyFilters();
            }
            if (changeEvent.getSource() == this.greenSlider) {
                if (this.recuadro.isSelected()) {
                    this.imgSelected.setGreenInside(this.greenSlider.getValue());
                } else {
                    this.imgSelected.setGreenChannelValue(this.greenSlider.getValue());
                }
                applyFilters();
            }
            if (changeEvent.getSource() == this.blueSlider) {
                if (this.recuadro.isSelected()) {
                    this.imgSelected.setBlueInside(this.blueSlider.getValue());
                } else {
                    this.imgSelected.setBlueChannelValue(this.blueSlider.getValue());
                }
                applyFilters();
            }
            if (changeEvent.getSource() == this.filterSlider) {
                if (this.recuadro.isSelected()) {
                    this.imgSelected.setFilterInside(this.filterSlider.getValue());
                } else {
                    this.imgSelected.setFilter(this.filterSlider.getValue());
                }
                applyFilters();
            }
            if (changeEvent.getSource() == this.zoneSlider) {
                applyFilters();
                this.imgSelected.setSquareSize(this.zoneSlider.getValue());
            }
        }
    }

    //-----------------------------------Funciones de los listensers---------------------
    private void addImg() {
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
                this.myImg = new MyImage(img);
                this.myImg1 = new MyImage(img1);
                this.myImg2 = new MyImage(img2);
                this.myImg3 = new MyImage(img3);
                this.viewer.setImgs(this.myImg.myImg(), 0);
                this.viewer.setImgs(this.myImg1.myImg(), 1);
                this.viewer.setImgs(this.myImg2.myImg(), 2);
                this.viewer.setImgs(this.myImg3.myImg(), 3);
                this.imgSelected = this.myImg1;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addTableContent() {
        JTable table = this.infoTable;
        table.setValueAt(Integer.toString(this.viewer.getImg(0).getWidth() * this.viewer.getImg(0).getHeight() / 1000), 0, 1);
        table.setValueAt(Integer.toString(this.imgSelected.getVector().length / 1024), 1, 1);
        table.setValueAt(Integer.toString(this.imgSelected.myImg().getWidth()), 2, 1);
        table.setValueAt(Integer.toString(this.imgSelected.myImg().getWidth()), 3, 1);
        if (imgSelected.myImg().getColorModel().hasAlpha()) {
            table.setValueAt("Si", 4, 1);
        } else {
            table.setValueAt("No", 4, 1);
        }
    }

    private void applyFilters() {
        byte[] original;
        byte[] copy;
        int blueChannel = 0;
        int greenChannel = 1;
        int redChannel = 2;
        this.imgSelected.sizeSquare(this.zoneSlider.getValue());

        original = ((DataBufferByte) this.viewer.getImg(0).getRaster().getDataBuffer()).getData();
        copy = new byte[original.length];
        System.arraycopy(original, 0, copy, 0, original.length);

        this.imgSelected.changeGeneralBrightness(copy);
        this.imgSelected.changeBrightnessChannel(redChannel);
        this.imgSelected.changeBrightnessChannel(greenChannel);
        this.imgSelected.changeBrightnessChannel(blueChannel);
        this.imgSelected.applyFilter();
        if (this.imgSelected.getGeneralGray() || this.imgSelected.isGrayInside()) {
            this.imgSelected.grayChange();
        }
        this.viewer.repaint();

    }

    private void getImgData(MyImage myImg) {
        if (this.todo.isSelected()) {
            this.redSlider.setValue(myImg.getRedChannelValue());
            this.greenSlider.setValue(myImg.getGreenChannelValue());
            this.blueSlider.setValue(myImg.getBlueChannelValue());
            this.sliderBrillo.setValue(myImg.getGeneralBrightness());
            this.filterSlider.setValue(myImg.getFilter());
            this.grayButton.setSelected(myImg.getGeneralGray());
            this.zoneSlider.setValue(myImg.getSquareSize());
        } else if (this.recuadro.isSelected()) {
            this.redSlider.setValue(myImg.getRedInside());
            this.greenSlider.setValue(myImg.getGreenInside());
            this.blueSlider.setValue(myImg.getBlueInside());
            this.sliderBrillo.setValue(myImg.getBrightInside());
            this.filterSlider.setValue(myImg.getFilterInside());
            this.grayButton.setSelected(myImg.isGrayInside());
            this.zoneSlider.setValue(myImg.getSquareSize());
        }
    }

    private void resetButtons() {
        this.todo.setSelected(true);
        this.recuadro.setSelected(false);
        this.sliderBrillo.setValue(0);
        this.redSlider.setValue(0);
        this.blueSlider.setValue(0);
        this.greenSlider.setValue(0);
        this.zoneSlider.setValue(0);
        this.grayButton.setSelected(false);
        this.filterSlider.setValue(0);
        this.picSelected1.setSelected(true);
        this.picSelected2.setSelected(false);
        this.picSelected3.setSelected(false);
    }

    public void setViewer(Viewer v) {
        this.viewer = v;
    }

}
