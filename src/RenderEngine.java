import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

public class RenderEngine {

    private JFrame frame = new JFrame("Render Engine");

    // panel to hold the slider
    private JPanel pane = new JPanel();

    // slider to control x-transformation
    private JSlider xSlider = new JSlider(JSlider.VERTICAL, -180, 180, 0);

    // slider to control y-transformation
    private JSlider ySlider = new JSlider(JSlider.VERTICAL, -180, 180, 0);

    // slider to control z-transformation
    private JSlider zSlider = new JSlider(JSlider.VERTICAL, -180, 180, 0);

    // operation selection
    private final JComboBox<String> transformationSelect = new JComboBox<>(new String[] {"Translate", "Rotate", "Scale"});

    // panel that renders the object
    private JPanel renderPanel;

    private void initialize() {
        //positioning and configuring the window
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pane.setLayout(new BorderLayout());

        //resetting the object when the operation changes
        transformationSelect.addActionListener(e -> {
            xSlider.setValue(transformationSelect.getSelectedItem() == "Scale" ? -33 : 0);
            ySlider.setValue(transformationSelect.getSelectedItem() == "Scale" ? -33 : 0);
            zSlider.setValue(transformationSelect.getSelectedItem() == "Scale" ? -33 : 0);
            renderPanel.repaint();
        });

        // adding and configuring components
        transformationSelect.setVisible(true);

        xSlider.setVisible(true);
        ySlider.setVisible(true);
        zSlider.setVisible(true);

        pane.add(transformationSelect, BorderLayout.NORTH);

        pane.add(xSlider, BorderLayout.WEST);
        pane.add(ySlider, BorderLayout.CENTER);
        pane.add(zSlider, BorderLayout.EAST);

        xSlider.addChangeListener(e -> renderPanel.repaint());
        ySlider.addChangeListener(e -> renderPanel.repaint());
        zSlider.addChangeListener(e -> renderPanel.repaint());

        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    public void render(String renderObject) {
        initialize();

        if(renderPanel != null) frame.remove(renderPanel);

        // panel to display render results
        renderPanel = new JPanel() {
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.BLACK);
                g2.fillRect(0, 0, getWidth(), getHeight());

                List<Triangle> tris = new ArrayList<>();

                // creating the 2D objects (Triangle)
                if(renderObject.equals("Triangle") || renderObject.equals("Square")) {
                    if(renderObject.equals("Triangle")) {
                        tris.add(new Triangle(new Vertex(-100, -100, 0),
                                new Vertex(100, -100, 0),
                                new Vertex(0, 100, 0),
                                Color.WHITE));
                    } else {
                        tris.add(new Triangle(new Vertex(-100, -100, 0),
                                new Vertex(-100, 100, 0),
                                new Vertex(100, -100, 0),
                                Color.BLUE));
                        tris.add(new Triangle(new Vertex(100, -100, 0),
                                new Vertex(100, 100, 0),
                                new Vertex(-100, 100, 0),
                                Color.GREEN));
                    }

                    // configuring the render engine to allow 2D transformation
                    xSlider.setVisible(false);
                    ySlider.setVisible(false);
                    zSlider.setVisible(false);
                    pane.remove(xSlider);
                    pane.remove(ySlider);
                    pane.remove(zSlider);
                    zSlider.setVisible(true);
                    pane.add(zSlider, BorderLayout.CENTER);
                    frame.validate();
                } else {
                    // creating the 3D objects (Pyramid)
                    tris.add(new Triangle(new Vertex(100, 100, 100),
                            new Vertex(-100, -100, 100),
                            new Vertex(-100, 100, -100),
                            Color.WHITE));
                    tris.add(new Triangle(new Vertex(100, 100, 100),
                            new Vertex(-100, -100, 100),
                            new Vertex(100, -100, -100),
                            Color.RED));
                    tris.add(new Triangle(new Vertex(-100, 100, -100),
                            new Vertex(100, -100, -100),
                            new Vertex(100, 100, 100),
                            Color.GREEN));
                    tris.add(new Triangle(new Vertex(-100, 100, -100),
                            new Vertex(100, -100, -100),
                            new Vertex(-100, -100, 100),
                            Color.BLUE));
                }

                // changing pyramid to sphere
                if(renderObject.equals("Sphere"))
                    for (int i = 0; i < 4; i++)
                            tris = inflate(tris);

                // declaring transformation matrices
                Matrix3 transform = new Matrix3(new double[] {1, 0, 0, 0, 1, 0, 0, 0, 1});
                Matrix3 xTransform, yTransform, zTransform;

                // creating translation matrices
                if(transformationSelect.getSelectedItem() == "Translate") {
//                    double xTranslation = Math.toRadians(xSlider.getValue());
//                    xTransform = new Matrix3(new double[]{
//                            xTranslation, 0, 0,
//                            0, 0, 0,
//                            0, 0, 0
//                    });
//
//                    double yTranslation = Math.toRadians(ySlider.getValue());
//                    yTransform = new Matrix3(new double[]{
//                            0,0,0,
//                            0, yTranslation, 0,
//                            0,0,0
//                    });
//
//                    double zTranslation = Math.toRadians(zSlider.getValue());
//                    zTransform = new Matrix3(new double[]{
//                            0,0,0,
//                            0,0,0,
//                            0, 0, zTranslation,
//                    });
//
                    // combining matrices for each axis to one (makes the calculation shorter)
//                    transform = xTransform.add(yTransform).add(zTransform);

                    } else if(transformationSelect.getSelectedItem() == "Scale") {
                    // creating scaling matrices
                    double xScale = Math.toRadians(xSlider.getValue() + 90);
                    xTransform = new Matrix3(new double[]{
                            xScale, 0, 0,
                            0, 1, 0,
                            0, 0, 1
                    });

                    double yScale = Math.toRadians(ySlider.getValue() + 90);
                    yTransform = new Matrix3(new double[]{
                            1,0,0,
                            0, yScale, 0,
                            0,0,1
                    });

                    double zScale = Math.toRadians(zSlider.getValue() + 90);
                    zTransform = new Matrix3(new double[]{
                            1,0,0,
                            0,1,0,
                            0, 0, zScale,
                    });

                    // combining them to one
                    transform = xTransform.multiply(yTransform).multiply(zTransform);

                } else if(transformationSelect.getSelectedItem() == "Rotate") {
                    // creating rotation matrices
                    double xRotation = Math.toRadians(xSlider.getValue());
                    xTransform = new Matrix3(new double[]{
                            1, 0, 0,
                            0, Math.cos(xRotation), Math.sin(xRotation),
                            0, -Math.sin(xRotation), Math.cos(xRotation)
                    });

                    double yRotation = Math.toRadians(ySlider.getValue());
                    yTransform = new Matrix3(new double[]{
                            Math.cos(yRotation), 0, -Math.sin(yRotation),
                            0, 1, 0,
                            Math.sin(yRotation), 0, Math.cos(yRotation)
                    });

                    double zRotation = Math.toRadians(zSlider.getValue());
                    zTransform = new Matrix3(new double[]{
                            Math.cos(zRotation), -Math.sin(zRotation), 0,
                            Math.sin(zRotation), Math.cos(zRotation), 0,
                            0, 0, 1,
                    });
                    // combining them to one
                    transform = xTransform.multiply(yTransform).multiply(zTransform);
                }


                // TODO: was?
                BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

                // TODO: warum?
                double[] zBuffer = new double[img.getWidth() * img.getHeight()];

                // initialize array with extremely far away depths
                // TODO: warun?
                for (int q = 0; q < zBuffer.length; q++) {
                    zBuffer[q] = Double.NEGATIVE_INFINITY;
                }

                // TODO: was passiert hier
                for (Triangle t : tris) {
                    Vertex v1 = transform.transform(t.v1);
                    v1.x += getWidth() / 2;
                    v1.y += getHeight() / 2;
                    Vertex v2 = transform.transform(t.v2);
                    v2.x += getWidth() / 2;
                    v2.y += getHeight() / 2;
                    Vertex v3 = transform.transform(t.v3);
                    v3.x += getWidth() / 2;
                    v3.y += getHeight() / 2;

                    Vertex ab = new Vertex(v2.x - v1.x, v2.y - v1.y, v2.z - v1.z);
                    Vertex ac = new Vertex(v3.x - v1.x, v3.y - v1.y, v3.z - v1.z);
                    Vertex norm = new Vertex(
                            ab.y * ac.z - ab.z * ac.y,
                            ab.z * ac.x - ab.x * ac.z,
                            ab.x * ac.y - ab.y * ac.x
                    );
                    double normalLength = Math.sqrt(norm.x * norm.x + norm.y * norm.y + norm.z * norm.z);
                    norm.x /= normalLength;
                    norm.y /= normalLength;
                    norm.z /= normalLength;

                    double angleCos = Math.abs(norm.z);

                    int minX = (int) Math.max(0, Math.ceil(Math.min(v1.x, Math.min(v2.x, v3.x))));
                    int maxX = (int) Math.min(img.getWidth() - 1, Math.floor(Math.max(v1.x, Math.max(v2.x, v3.x))));
                    int minY = (int) Math.max(0, Math.ceil(Math.min(v1.y, Math.min(v2.y, v3.y))));
                    int maxY = (int) Math.min(img.getHeight() - 1, Math.floor(Math.max(v1.y, Math.max(v2.y, v3.y))));

                    double triangleArea = (v1.y - v3.y) * (v2.x - v3.x) + (v2.y - v3.y) * (v3.x - v1.x);

                    for (int y = minY; y <= maxY; y++) {
                        for (int x = minX; x <= maxX; x++) {
                            double b1 = ((y - v3.y) * (v2.x - v3.x) + (v2.y - v3.y) * (v3.x - x)) / triangleArea;
                            double b2 = ((y - v1.y) * (v3.x - v1.x) + (v3.y - v1.y) * (v1.x - x)) / triangleArea;
                            double b3 = ((y - v2.y) * (v1.x - v2.x) + (v1.y - v2.y) * (v2.x - x)) / triangleArea;
                            if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1 && b3 >= 0 && b3 <= 1) {
                                double depth = b1 * v1.z + b2 * v2.z + b3 * v3.z;
                                int zIndex = y * img.getWidth() + x;
                                if (zBuffer[zIndex] < depth) {
                                    img.setRGB(x, y, getShade(t.color, angleCos).getRGB());
                                    zBuffer[zIndex] = depth;
                                }
                            }
                        }
                    }

                }

                g2.drawImage(img, 0, 0, null);
            }
        };

        // displaying render panel and sliders
        frame.add(renderPanel);
        frame.add(pane, BorderLayout.EAST);

        // making sure everything is placed properly
        frame.validate();
    }

    // coloring in triangles
    // TODO: wie
    private static Color getShade(Color color, double shade) {
        double redLinear = Math.pow(color.getRed(), 2.4) * shade;
        double greenLinear = Math.pow(color.getGreen(), 2.4) * shade;
        double blueLinear = Math.pow(color.getBlue(), 2.4) * shade;

        int red = (int) Math.pow(redLinear, 1/2.4);
        int green = (int) Math.pow(greenLinear, 1/2.4);
        int blue = (int) Math.pow(blueLinear, 1/2.4);

        return new Color(red, green, blue);
    }

    // TODO: lesen wie das funktioniert
    private static List<Triangle> inflate(List<Triangle> tris) {
        List<Triangle> result = new ArrayList<>();
        for (Triangle t : tris) {
            Vertex m1 = new Vertex((t.v1.x + t.v2.x)/2, (t.v1.y + t.v2.y)/2, (t.v1.z + t.v2.z)/2);
            Vertex m2 = new Vertex((t.v2.x + t.v3.x)/2, (t.v2.y + t.v3.y)/2, (t.v2.z + t.v3.z)/2);
            Vertex m3 = new Vertex((t.v1.x + t.v3.x)/2, (t.v1.y + t.v3.y)/2, (t.v1.z + t.v3.z)/2);
            result.add(new Triangle(t.v1, m1, m3, t.color));
            result.add(new Triangle(t.v2, m1, m2, t.color));
            result.add(new Triangle(t.v3, m2, m3, t.color));
            result.add(new Triangle(m1, m2, m3, t.color));
        }
        for (Triangle t : result) {
            for (Vertex v : new Vertex[] { t.v1, t.v2, t.v3 }) {
                double l = Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z) / Math.sqrt(30000);
                v.x /= l;
                v.y /= l;
                v.z /= l;
            }
        }
        return result;
    }
}
