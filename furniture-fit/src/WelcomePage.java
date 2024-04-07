import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class WelcomePage extends JFrame implements GLEventListener {
    private GLCanvas glCanvas;
    private GLU glu;
    private double rotationAngle = 0.0;
    private int lastMouseX;

    public WelcomePage() {
        setTitle("Welcome Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create the OpenGL canvas
        glCanvas = new GLCanvas();
        glCanvas.addGLEventListener(this);
        add(glCanvas, BorderLayout.CENTER);

        // Add mouse listeners for rotation
        glCanvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastMouseX = e.getX();
            }
        });

        glCanvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int deltaX = e.getX() - lastMouseX;
                rotationAngle += deltaX;
                lastMouseX = e.getX();
                glCanvas.repaint();
            }
        });

        // Create a label for the welcome message
        JLabel welcomeLabel = new JLabel("Welcome to Furniture Fit");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(welcomeLabel, BorderLayout.NORTH);

        // Create an animator to drive the animation
        FPSAnimator animator = new FPSAnimator(glCanvas, 60);
        animator.start();
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        glu = new GLU();

        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glEnable(GL2.GL_DEPTH_TEST);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0, (double) getWidth() / getHeight(), 0.1, 100.0);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        glu.gluLookAt(0.0, 0.0, 5.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);

        // Apply rotation
        gl.glRotated(rotationAngle, 0.0, 1.0, 0.0);

        // Draw the chair
        drawChair(gl);
    }

    private void drawChair(GL2 gl) {
        // Enhanced Chair Seat
        gl.glPushMatrix();
        gl.glColor3f(0.0f, 0.0f, 1.0f); // Deep Blue
        gl.glTranslatef(0.0f, -0.5f, 0.0f);
        gl.glScalef(1.2f, 0.1f, 1.2f); // Slightly larger and thinner seat
        drawCube(gl);
        gl.glPopMatrix();

        // Enhanced Chair Back
        gl.glPushMatrix();
        gl.glColor3f(0.0f, 0.0f, 1.0f); // Deep Blue
        gl.glTranslatef(0.0f, 0.3f, -0.6f);
        gl.glScalef(1.2f, 1.2f, 0.1f); // Slightly larger back for better support
        drawCube(gl);
        gl.glPopMatrix();

        // Chair Armrests (New Addition)
        gl.glPushMatrix();
        gl.glColor3f(0.0f, 0.0f, 0.75f); // Slightly different blue for contrast
        gl.glTranslatef(-0.7f, -0.1f, 0.0f); // Left armrest
        gl.glScalef(0.1f, 0.4f, 1.0f);
        drawCube(gl);
        gl.glTranslatef(1.4f, 0.0f, 0.0f); // Right armrest
        drawCube(gl);
        gl.glPopMatrix();

        // Enhanced Chair Legs - Adjusted for new seat size
        drawChairLeg(gl, -0.6f, -1.0f, 0.6f);
        drawChairLeg(gl, 0.6f, -1.0f, 0.6f);
        drawChairLeg(gl, -0.6f, -1.0f, -0.6f);
        drawChairLeg(gl, 0.6f, -1.0f, -0.6f);
    }

    private void drawChairLeg(GL2 gl, float x, float y, float z) {
        gl.glPushMatrix();
        gl.glColor3f(0.5f, 0.35f, 0.05f); // Color adjusted for a wooden look
        gl.glTranslatef(x, y, z);
        gl.glScalef(0.15f, 1.0f, 0.15f); // Slightly thicker legs for visual balance
        drawCube(gl);
        gl.glPopMatrix();
    }

    private void drawCube(GL2 gl) {
        gl.glBegin(GL2.GL_QUADS);
        // Front face
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        // Back face
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        // Top face
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        // Bottom face
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        // Right face
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        // Left face
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
    }



    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, width, height);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WelcomePage welcomePage = new WelcomePage();
            welcomePage.setVisible(true);
        });
    }
}
