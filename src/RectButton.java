import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RectButton extends JButton {
    
    StateManager stateManager;
    
    public RectButton(StateManager stateManager) {
        super("Rectangle");
        addActionListener(new RectListener());
        this.stateManager = stateManager;
    }
    
    class RectListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            stateManager.setState(new RectState(stateManager), State.RECTANGLE_STATE);
        }
    }
}

class RectState implements State {
    
    StateManager stateManager;
    MyRectangle rect;
    
    public RectState(StateManager stateManager) {
        this.stateManager = stateManager;
    }
    
    @Override
    public void mouseDown(int x, int y) {
        rect = new MyRectangle(x, y, 0, 0);
        stateManager.addDrawing(rect);
    }
    
    @Override
    public void mouseUp(int x, int y) {
    }
    
    @Override
    public void mouseDrag(int x, int y) {
        rect.setSize(x - rect.getX(), y - rect.getY());
    }
    
}