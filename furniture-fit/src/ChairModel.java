import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class ChairModel extends JFrame implements GLEventListener {
    private GLU glu = new GLU();
    private List<float[]> vertices = new ArrayList<>();
    private List<int[]> faces = new ArrayList<>();
    private List<float[]> textureCoords = new ArrayList<>();
    private List<float[]> normals = new ArrayList<>();
    private Map<String, Material> materialLibs = new HashMap<>();
    private String currentMaterial = "";
    private float rotateX = 0.0f;
    private float rotateY = 0.0f;
    private int lastX, lastY;


    class Material {
        public float[] ambientColor = new float[3];
        public float[] diffuseColor = new float[3]; // For simplicity, focusing on diffuse color
        public float[] specularColor = new float[3];
        // Add other material properties as needed.
    }


    public ChairModel() {
        super("Chair Model");
        GLCanvas canvas = new GLCanvas();
        canvas.addGLEventListener(this);
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastX = e.getX();
                lastY = e.getY();
            }
        });

        canvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                // Calculate rotation differences
                float thetaY = 360.0f * (x - lastX) / canvas.getWidth();
                float thetaX = 360.0f * (y - lastY) / canvas.getHeight();

                // Accumulate rotations to the model
                rotateX += thetaX;
                rotateY += thetaY;

                // Request redraw
                canvas.display();

                // Update last positions
                lastX = x;
                lastY = y;
            }
        });
        this.getContentPane().add(canvas);
        this.setSize(640, 480);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        initializeModel();
    }


    private void initializeModel() {
        String objFilePath = "src/chair.obj"; // Update this path
        Map<String, Material> materials = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(objFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("v ")) {
                    // Parse vertex line
                    String[] vertexLine = line.split("\\s+");
                    float x = Float.parseFloat(vertexLine[1]);
                    float y = Float.parseFloat(vertexLine[2]);
                    float z = Float.parseFloat(vertexLine[3]);
                    vertices.add(new float[]{x, y, z});
                } else if (line.startsWith("f ")) {
                    // Parse face line
                    String[] faceLine = line.split("\\s+");
                    int[] faceVertices = new int[faceLine.length - 1];
                    for (int i = 1; i < faceLine.length; i++) {
                        // Extract vertex index; assumes faces are defined with only vertex indices (no texture/normals)
                        // Adjust for OBJ 1-based indexing and potential face format "v/vt/vn"
                        String[] vertexIndexParts = faceLine[i].split("/");
                        int vertexIndex = Integer.parseInt(vertexIndexParts[0]);
                        faceVertices[i - 1] = vertexIndex;
                    }
                    faces.add(faceVertices);
                } else if (line.startsWith("vt ")) {
                    // Parse texture coordinate line
                    String[] textureLine = line.split("\\s+");
                    float u = Float.parseFloat(textureLine[1]);
                    float v = Float.parseFloat(textureLine[2]);
                    textureCoords.add(new float[]{u, v});
                } else if (line.startsWith("vn ")) {
                    // Parse vertex normal line
                    String[] normalLine = line.split("\\s+");
                    float x = Float.parseFloat(normalLine[1]);
                    float y = Float.parseFloat(normalLine[2]);
                    float z = Float.parseFloat(normalLine[3]);
                    normals.add(new float[]{x, y, z});
                } else if (line.startsWith("usemtl ")) {
                    // Parse material usage line
                    currentMaterial = line.substring(7).trim();
                } else if (line.startsWith("mtllib ")) {
                    // Parse material library line
                    String materialLibFileName = line.substring(7).trim();
                    materialLibs.put(materialLibFileName, null); // Placeholder for material library parsing
                    // You would need to implement additional parsing for the material library file
                }
                if (line.startsWith("mtllib ")) {
                    // Parse material library line
                    String materialLibFileName = line.substring(7).trim();
                    parseMaterialLibrary(materialLibFileName, materials);
                }

            }
        } catch (IOException e) {
            System.err.println("Error reading the OBJ file: " + e.getMessage());
        }

        materialLibs = materials;
    }
    private void parseMaterialLibrary(String mtlFilePath, Map<String, Material> materials) {
        // Assuming the material library files are in the same directory as the OBJ file
        String dirPath = "src/"; // Update this path as necessary
        mtlFilePath = dirPath + mtlFilePath;

        try (BufferedReader br = new BufferedReader(new FileReader(mtlFilePath))) {
            String line;
            Material currentMaterial = null;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("newmtl ")) {
                    String materialName = line.substring(7).trim();
                    currentMaterial = new Material();
                    materials.put(materialName, currentMaterial);
                } else if (line.startsWith("Ka ") && currentMaterial != null) {
                    // Parse ambient color
                    String[] parts = line.split("\\s+");
                    currentMaterial.ambientColor[0] = Float.parseFloat(parts[1]);
                    currentMaterial.ambientColor[1] = Float.parseFloat(parts[2]);
                    currentMaterial.ambientColor[2] = Float.parseFloat(parts[3]);
                } else if (line.startsWith("Kd ") && currentMaterial != null) {
                    // Parse diffuse color
                    String[] parts = line.split("\\s+");
                    currentMaterial.diffuseColor[0] = Float.parseFloat(parts[1]);
                    currentMaterial.diffuseColor[1] = Float.parseFloat(parts[2]);
                    currentMaterial.diffuseColor[2] = Float.parseFloat(parts[3]);
                } else if (line.startsWith("Ks ") && currentMaterial != null) {
                    // Parse specular color
                    String[] parts = line.split("\\s+");
                    currentMaterial.specularColor[0] = Float.parseFloat(parts[1]);
                    currentMaterial.specularColor[1] = Float.parseFloat(parts[2]);
                    currentMaterial.specularColor[2] = Float.parseFloat(parts[3]);
                }
                // Extend this to parse other material properties
            }
        } catch (IOException e) {
            System.err.println("Error reading the MTL file: " + e.getMessage());
        }

    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        glu.gluLookAt(0.0, 0.0, 10.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
        gl.glRotatef(rotateX, 1.0f, 0.0f, 0.0f); // Rotate on X-axis
        gl.glRotatef(rotateY, 0.0f, 1.0f, 0.0f); // Rotate on Y-axis

        // Enable material properties
        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        gl.glColorMaterial(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE);

        for (int[] face : faces) {
            Material material = materialLibs.get(currentMaterial); // Assuming currentMaterial is set
            if (material != null) {
                // Apply the diffuse color of the material
                float[] color = material.diffuseColor;
                gl.glColor3f(color[0], color[1], color[2]);
            }

            gl.glBegin(GL2.GL_TRIANGLES);
            for (int vertexIndex : face) {
                float[] v = vertices.get(vertexIndex - 1); // Adjust for 0-based indexing
                gl.glVertex3f(v[0], v[1], v[2]);
            }
            gl.glEnd();
        }
    }


    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        if (height <= 0) height = 1;
        float aspect = (float) width / height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0, aspect, 0.1, 100.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public static void main(String[] args) {
        new ChairModel();
    }
}
