import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TriangleButton extends JButton {
    
    StateManager stateManager;
    
    public TriangleButton(StateManager stateManager) {
        super("Triangle");
        
        addActionListener(new RectListener());
        
        this.stateManager = stateManager;
    }
    
    class RectListener implements ActionListener {
        
        public void actionPerformed(ActionEvent E) {
            stateManager.setState(new TriangleState(stateManager), State.TRIANGLE_STATE);
        }
    }
}

class TriangleState implements State {
    
    StateManager stateManager;
    MyTriangle triangle;
    
    public TriangleState(StateManager stateManager) {
        this.stateManager = stateManager;
    }
    
    @Override
    public void mouseDown(int x, int y) {
        triangle = new MyTriangle(x, y, 0, 0);
        stateManager.addDrawing(triangle);
    }
    
    @Override
    public void mouseDrag(int x, int y) {
        triangle.setSize(x - triangle.getX(), y - triangle.getY());
    }
    
    @Override
    public void mouseUp(int x, int y) {
    }
    
}