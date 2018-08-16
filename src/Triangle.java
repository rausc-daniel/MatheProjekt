import java.awt.Color;

// data class that holds a triangle (they make up the objects)
public class Triangle {
    Vertex v1;
    Vertex v2;
    Vertex v3;
    Color color;
    Triangle(Vertex v1, Vertex v2, Vertex v3, Color color) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.color = color;
    }

    public Vertex[] getVertices (){
        return new Vertex[] {v1, v2, v3};
    }
}
