import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;

public class ObjectSelector {
    public void initialize() {
        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.CENTER);

        JFrame frame = new JFrame("Object Selection Window");
        frame.setSize(350, 175);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.setLayout(layout);
        frame.setVisible(true);

        JPanel dimensionSelectPanel = new JPanel();
        frame.add(dimensionSelectPanel);

        JLabel dimensionSelectLabel = new JLabel("Please Select the Number of Dimensions");
        dimensionSelectPanel.add(dimensionSelectLabel);
        dimensionSelectLabel.setVisible(true);

        String[] dimensions = new String[] {"2D", "3D"};

        final JComboBox<String> dimensionSelect = new JComboBox<>(dimensions);
        dimensionSelectPanel.add(dimensionSelect);
        dimensionSelect.setVisible(true);

        JPanel objectSelectPanel = new JPanel();
        frame.add(objectSelectPanel);

        JLabel objectSelectLabel = new JLabel("Please Select the Render-Object");
        objectSelectPanel.add(objectSelectLabel);
        objectSelectLabel.setVisible(true);

        String[] objects2D = new String[] {"Circle", "Square", "Triangle"};
        String[] objects3D = new String[] {"Sphere", "Cube", "Pyramid"};

        final JComboBox<String> objectSelect = new JComboBox<>(objects2D);
        objectSelectPanel.add(objectSelect);
        objectSelect.setVisible(true);

        dimensionSelect.addActionListener(e -> {
            objectSelect.removeAllItems();
            if(dimensionSelect.getSelectedItem().equals(dimensions[0]))
                for (String s : objects2D)
                    objectSelect.addItem(s);
            else
                for (String s : objects3D)
                    objectSelect.addItem(s);
            objectSelect.setSelectedIndex(0);
        });

        JButton submitButton = new JButton("Render");
        frame.add(submitButton);
        submitButton.setVisible(true);

        submitButton.addActionListener(e -> {
            RenderEngine engine = new RenderEngine((String) objectSelect.getSelectedItem());
            engine.initialize();
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });

        frame.validate();
    }
}
