import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;

public class ObjectSelector {
    public void initialize() {
        // aligning the objects automatically
        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.CENTER);

        // creating, configuring and positioning a window
        JFrame frame = new JFrame("Object Selection Window");
        frame.setSize(350, 175);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.setLayout(layout);
        frame.setVisible(true);

        // creating 2 Panels to make alignment easier
        JPanel dimensionSelectPanel = new JPanel();
        frame.add(dimensionSelectPanel);

        // description of the combo box that lets one select a number of dimensions
        JLabel dimensionSelectLabel = new JLabel("Please Select the Number of Dimensions");
        dimensionSelectPanel.add(dimensionSelectLabel);
        dimensionSelectLabel.setVisible(true);

        // dimensions to choose from
        String[] dimensions = new String[] {"2D", "3D"};

        // combo box to select dimensions
        final JComboBox<String> dimensionSelect = new JComboBox<>(dimensions);
        dimensionSelectPanel.add(dimensionSelect);
        dimensionSelect.setVisible(true);

        JPanel objectSelectPanel = new JPanel();
        frame.add(objectSelectPanel);

        // // description of the combo box that lets one select an object
        JLabel objectSelectLabel = new JLabel("Please Select the Render-Object");
        objectSelectPanel.add(objectSelectLabel);
        objectSelectLabel.setVisible(true);

        // 2D objects to choose from
        String[] objects2D = new String[] {"Square", "Triangle"};
        // 3D objects to choose from
        String[] objects3D = new String[] {"Sphere", "Pyramid"};

        // combo box to select an object
        final JComboBox<String> objectSelect = new JComboBox<>(objects2D);
        objectSelectPanel.add(objectSelect);
        objectSelect.setVisible(true);

        // adding an event that changes the contents of the object box based on the number of dimensions
        dimensionSelect.addActionListener(e -> {
            // remove all itmes
            objectSelect.removeAllItems();
            // figure out how many dimensions were selected
            if(dimensionSelect.getSelectedItem().equals(dimensions[0]))
                // adding the 2D items
                for (String s : objects2D)
                    objectSelect.addItem(s);
            else
                // adding the 3D items
                for (String s : objects3D)
                    objectSelect.addItem(s);
            objectSelect.setSelectedIndex(0);
        });

        // creating a button
        JButton submitButton = new JButton("Render");
        frame.add(submitButton);
        submitButton.setVisible(true);


        // the object is passed to the engine and rendered
        // when the button is clicked the render window is created
        submitButton.addActionListener(e -> {
            // prerendering object
            new RenderEngine().render((String) objectSelect.getSelectedItem(), null);
            // creating the window
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });

        // making sure everything is positioned properly
        frame.validate();
    }
}
