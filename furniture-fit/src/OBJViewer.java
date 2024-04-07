import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AIVector3D;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.assimp.Assimp.*;

public class OBJViewer extends JFrame implements GLEventListener, MouseMotionListener, MouseListener, KeyListener {

    private GLCanvas glCanvas;
    private GLU glu = new GLU();
    private List<String> furniturePaths = new ArrayList<>();
    private List<String> furnitureNames = new ArrayList<>();
    private List<AIScene> furnitureScenes = new ArrayList<>();
    private List<float[]> furniturePositions = new ArrayList<>();
    private List<float[]> furnitureRotations = new ArrayList<>();
    private Texture furnitureTexture;
    private float roomLength = 10.0f;
    private float roomWidth = 10.0f;
    private float rotationX = 0.0f;
    private float rotationY = 0.0f;
    private int lastMouseX, lastMouseY;
    private boolean isRotating = false;
    private float moveSpeed = 0.1f;
    private JList<String> furnitureList;
    private boolean is2DView = false;
    private JButton viewButton;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(OBJViewer::new);
    }

    public OBJViewer() {
        setTitle("Room Designer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        GLProfile glProfile = GLProfile.getDefault();
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);
        glCanvas = new GLCanvas(glCapabilities);
        glCanvas.addGLEventListener(this);
        glCanvas.addMouseMotionListener(this);
        glCanvas.addMouseListener(this);
        glCanvas.addKeyListener(this);

        initializeFurniture();
        JPanel controlPanel = createControlPanel();

        add(glCanvas, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.EAST);

        setSize(1080, 800);
        setLocationRelativeTo(null);
        setVisible(true);

        FPSAnimator animator = new FPSAnimator(glCanvas, 60);
        animator.start();
    }

    private void initializeFurniture() {
        furniturePaths.add("./assets/couch.fbx");
        furniturePaths.add("./assets/chair_A.fbx");
        furniturePaths.add("./assets/bed_double_A.fbx");
        furniturePaths.add("./assets/cabinet_medium.fbx");
        furniturePaths.add("./assets/lamp_standing.fbx");
        furniturePaths.add("./assets/table_medium.fbx");

        // Add more furniture paths as needed

        furnitureNames.add("Couch");
        furnitureNames.add("Chair");
        furnitureNames.add("Bed Double");
        furnitureNames.add("Cabinet");
        furnitureNames.add("Lamp");
        furnitureNames.add("Table_medium");
        // Add corresponding furniture names

        furniturePositions.add(new float[]{0.0f, 0.0f, 0.0f});
        furniturePositions.add(new float[]{0.0f, 0.0f, 0.0f});
        furniturePositions.add(new float[]{0.0f, 0.0f, 0.0f});

        furnitureRotations.add(new float[]{0.0f, 0.0f, 0.0f});
        furnitureRotations.add(new float[]{0.0f, 0.0f, 0.0f});
        furnitureRotations.add(new float[]{0.0f, 0.0f, 0.0f});
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel lengthLabel = new JLabel("Room Length:");
        JTextField lengthField = new JTextField(String.valueOf(roomLength));
        JLabel widthLabel = new JLabel("Room Width:");
        JTextField widthField = new JTextField(String.valueOf(roomWidth));
        JButton updateButton = new JButton("Update Room");
        updateButton.addActionListener(e -> {
            roomLength = Float.parseFloat(lengthField.getText());
            roomWidth = Float.parseFloat(widthField.getText());
        });

        // Navigation Bar Panel
        JPanel navBarPanel = new JPanel();
        navBarPanel.setBackground(new Color(214, 196, 153));
        navBarPanel.setLayout(new BoxLayout(navBarPanel, BoxLayout.X_AXIS));
        navBarPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Home Icon Label
        ImageIcon homeIcon = new ImageIcon("images/home.png");
        Image homeImage = homeIcon.getImage();
        Image newHomeImage = homeImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        homeIcon = new ImageIcon(newHomeImage);
        JLabel homeIconLabel = new JLabel(homeIcon);
        homeIconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        homeIconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Change cursor to hand when hovering
        homeIconLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                dispose();
                new HomePage("Leo Gavin"); // Create a new instance of HomePage
            }
        });

        // Logo Label
        ImageIcon logoIcon = new ImageIcon("images/FurnitureFitLogo.png");
        Image image = logoIcon.getImage();
        Image newImg = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(newImg);
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // User Icon Label
        ImageIcon userIcon = new ImageIcon("images/user.png");
        Image userImage = userIcon.getImage();
        Image newUserImage = userImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        userIcon = new ImageIcon(newUserImage);
        JLabel userIconLabel = new JLabel(userIcon);
        userIconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userIconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Change cursor to hand when hovering
        userIconLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose(); // Close the current frame
                new ProfilePage();
            }
        });

        // Add components to nav bar panel
        navBarPanel.add(homeIconLabel);
        navBarPanel.add(Box.createHorizontalGlue());
        navBarPanel.add(logoLabel);
        navBarPanel.add(Box.createHorizontalGlue());
        navBarPanel.add(userIconLabel);

        panel.add(navBarPanel, BorderLayout.NORTH);

        JLabel furnitureLabel = new JLabel("Furniture:");
        furnitureList = new JList<>(furnitureNames.toArray(new String[0]));
        JScrollPane furnitureScrollPane = new JScrollPane(furnitureList);
        JButton addButton = new JButton("Add Furniture");
        addButton.addActionListener(e -> {
            int selectedIndex = furnitureList.getSelectedIndex();
            if (selectedIndex != -1) {
                String furniturePath = furniturePaths.get(selectedIndex);
                AIScene scene = aiImportFile(furniturePath, aiProcess_Triangulate | aiProcess_FlipUVs);
                if (scene != null) {
                    furnitureScenes.add(scene);
                    furniturePositions.add(new float[]{0.0f, 0.0f, 0.0f});
                    furnitureRotations.add(new float[]{0.0f, 0.0f, 0.0f});
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to load furniture file: " + aiGetErrorString());
                }
            }
        });

        viewButton = new JButton("2D");
        viewButton.addActionListener(e -> {
            is2DView = !is2DView;
            if (is2DView) {
                viewButton.setText("3D");
                rotationX = 66.0f;
                rotationY = -0.5f;
            } else {
                viewButton.setText("2D");
                rotationX = 0.0f;
                rotationY = 0.0f;
            }
        });



        panel.add(lengthLabel);
        panel.add(lengthField);
        panel.add(widthLabel);
        panel.add(widthField);
        panel.add(updateButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(furnitureLabel);
        panel.add(furnitureScrollPane);
        panel.add(addButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(viewButton);

        return panel;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glEnable(GL2.GL_DEPTH_TEST);

        try {
            BufferedImage textureImage = ImageIO.read(new File("./assets/furniturebits_texture.png"));
            furnitureTexture = AWTTextureIO.newTexture(drawable.getGL().getGLProfile(), textureImage, true);
            furnitureTexture.setTexParameteri(drawable.getGL(), GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
            furnitureTexture.setTexParameteri(drawable.getGL(), GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0, (double) getWidth() / (double) getHeight(), 1.0, 100.0);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        glu.gluLookAt(0, 10, 20, 0, 0, 0, 0, 1, 0);

        if (!is2DView) {
            gl.glRotatef(rotationX, 1.0f, 0.0f, 0.0f);
            gl.glRotatef(rotationY, 0.0f, 1.0f, 0.0f);
        } else {
            gl.glRotatef(66.0f, 1.0f, 0.0f, 0.0f);
            gl.glRotatef(-0.5f, 0.0f, 1.0f, 0.0f);
        }

        renderFloor(gl);
        renderFurniture(gl);
    }

    private void renderFloor(GL2 gl) {
        gl.glBegin(GL2.GL_QUADS);
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glVertex3f(-roomWidth / 2, 0.0f, -roomLength / 2);
        gl.glVertex3f(roomWidth / 2, 0.0f, -roomLength / 2);
        gl.glVertex3f(roomWidth / 2, 0.0f, roomLength / 2);
        gl.glVertex3f(-roomWidth / 2, 0.0f, roomLength / 2);
        gl.glEnd();
    }

    private void renderFurniture(GL2 gl) {
        for (int i = 0; i < furnitureScenes.size(); i++) {
            AIScene scene = furnitureScenes.get(i);
            float[] position = furniturePositions.get(i);
            float[] rotation = furnitureRotations.get(i);
            if (scene != null && position != null && rotation != null) {
                gl.glPushMatrix();
                gl.glTranslatef(position[0], position[1], position[2]);
                gl.glRotatef(rotation[1], 0.0f, 1.0f, 0.0f);
                gl.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                renderScene(gl, scene);
                gl.glPopMatrix();
            }
        }
    }

    private void renderScene(GL2 gl, AIScene scene) {
        int numMeshes = scene.mNumMeshes();
        PointerBuffer meshesBuffer = scene.mMeshes();
        for (int i = 0; i < numMeshes; i++) {
            AIMesh mesh = AIMesh.create(meshesBuffer.get(i));
            renderMesh(gl, mesh);
        }
    }

    private void renderMesh(GL2 gl, AIMesh mesh) {
        AIVector3D.Buffer vertices = mesh.mVertices();
        AIVector3D.Buffer normals = mesh.mNormals();
        AIVector3D.Buffer textureCoords = mesh.mTextureCoords(0);
        AIFace.Buffer faces = mesh.mFaces();

        furnitureTexture.enable(gl);
        furnitureTexture.bind(gl);

        gl.glBegin(GL2.GL_TRIANGLES);
        for (int i = 0; i < faces.limit(); i++) {
            AIFace face = faces.get(i);
            IntBuffer indices = face.mIndices();
            for (int j = 0; j < indices.limit(); j++) {
                int index = indices.get(j);
                AIVector3D vertex = vertices.get(index);
                AIVector3D normal = normals.get(index);
                AIVector3D texCoord = textureCoords != null ? textureCoords.get(index) : null;

                if (texCoord != null) {
                    gl.glTexCoord2f(texCoord.x(), texCoord.y());
                }
                gl.glNormal3f(normal.x(), normal.y(), normal.z());
                gl.glVertex3f(vertex.x(), vertex.y(), vertex.z());
            }
        }
        gl.glEnd();

        furnitureTexture.disable(gl);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, width, height);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        for (AIScene scene : furnitureScenes) {
            aiReleaseImport(scene);
        }
        furnitureTexture.destroy(drawable.getGL());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (isRotating && !is2DView) {
            int dx = e.getX() - lastMouseX;
            int dy = e.getY() - lastMouseY;
            rotationX += dy * 0.5f;
            rotationY += dx * 0.5f;
            lastMouseX = e.getX();
            lastMouseY = e.getY();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1 && !is2DView) {
            isRotating = true;
            lastMouseX = e.getX();
            lastMouseY = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            isRotating = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_A) {
            moveFurniture(-moveSpeed, 0.0f, 0.0f);
        } else if (keyCode == KeyEvent.VK_D) {
            moveFurniture(moveSpeed, 0.0f, 0.0f);
        } else if (keyCode == KeyEvent.VK_W) {
            moveFurniture(0.0f, 0.0f, -moveSpeed);
        } else if (keyCode == KeyEvent.VK_S) {
            moveFurniture(0.0f, 0.0f, moveSpeed);
        } else if (keyCode == KeyEvent.VK_Q) {
            rotateFurniture(-5.0f);
        } else if (keyCode == KeyEvent.VK_E) {
            rotateFurniture(5.0f);
        } else if (keyCode == KeyEvent.VK_BACK_SPACE) {
            removeSelectedFurniture();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private void moveFurniture(float dx, float dy, float dz) {
        int selectedIndex = furnitureList.getSelectedIndex();
        if (selectedIndex != -1) {
            float[] position = furniturePositions.get(selectedIndex);
            position[0] += dx;
            position[1] += dy;
            position[2] += dz;
        }
    }

    private void rotateFurniture(float angle) {
        int selectedIndex = furnitureList.getSelectedIndex();
        if (selectedIndex != -1) {
            float[] rotation = furnitureRotations.get(selectedIndex);
            rotation[1] += angle;
        }
    }

    private void removeSelectedFurniture() {
        int selectedIndex = furnitureList.getSelectedIndex();
        if (selectedIndex != -1) {
            furnitureScenes.remove(selectedIndex);
            furniturePositions.remove(selectedIndex);
            furnitureRotations.remove(selectedIndex);
           
        }
    }}