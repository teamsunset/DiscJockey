import java.awt.event.*;
import javax.swing.*;

public class PrintMouseWheel implements MouseWheelListener
{

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.addMouseWheelListener(new PrintMouseWheel());

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setVisible(true);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        System.out.println("Mouse wheel rotated: " + e.getWheelRotation());
    }

}
