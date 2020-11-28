import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    private final Viewer viewer;
    public ControlPanel(Viewer viewer) {
        super();
        this.setLayout(new GridBagLayout());
        this.viewer = viewer;

    }
}
