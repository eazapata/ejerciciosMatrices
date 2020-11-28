import javax.swing.*;
import java.awt.*;

public class MyRestaurant extends JFrame {
    private Viewer viewer;
    private ControlPanel controlPanel;

    public  MyRestaurant(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLayout(new GridBagLayout());
        this.setSize(screenSize.width,screenSize.height);
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        this.viewer = new Viewer();
        this.controlPanel = new ControlPanel(viewer);

        addControlPanel(c);
        addViewer(c);
    }
    public static void main(String[] args) {


    }
    private void addControlPanel(GridBagConstraints c){
        this.controlPanel = new ControlPanel(this.viewer);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.gridy = 0;
        c.gridx = 0;
        c.weighty = 1.0;
        c.weightx = 0.1;
        c.insets = new Insets(20, 20, 0, 0);
        this.add(controlPanel, c);
        controlPanel.setLayout(new GridBagLayout());
        //controlPanel.createPane();
    }

    private void addViewer(GridBagConstraints c){
        c.fill = GridBagConstraints.BOTH;
        c.gridy = 0;
        c.gridx= 1;
        c.weighty = 1;
        c.weightx = 1.9;
        c.gridwidth = 1;
        this.add(viewer, c);
    }
}
