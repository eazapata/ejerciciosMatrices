import javax.swing.*;
import java.awt.*;

public class MyInterface extends JFrame {

    private Viewer viewer;
    private ControlPanel controlPanel;

    public  MyInterface(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width,screenSize.height);

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        addControlPanel(c);
        addViewer(c);
        this.controlPanel.setViewer(this.viewer);
    }

    public static void main(String[] args) {
        MyInterface fireTask = new MyInterface();
        fireTask.setVisible(true);
    }

    private void addControlPanel(GridBagConstraints c){
        this.controlPanel = new ControlPanel();
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.gridy = 0;
        c.gridx = 0;
        c.weighty = 1.0;
        c.weightx = 0.1;
        c.insets = new Insets(20, 20, 0, 0);
        this.add(controlPanel, c);
        controlPanel.setLayout(new GridBagLayout());
        controlPanel.createPane();
    }

    private void addViewer(GridBagConstraints c){
        this.viewer = new Viewer();

        c.fill = GridBagConstraints.BOTH;
        c.gridy = 0;
        c.gridx= 1;
        c.weighty = 1;
        c.weightx = 1.9;
        c.gridwidth = 1;
        this.add(this.viewer, c);
    }
}
