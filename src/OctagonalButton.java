import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OctagonalButton extends JButton {
    
    StateManager stateManager;
    
    public OctagonalButton(StateManager stateManager) {
        super("Octagon");
        
        addActionListener(new OctagonalListener());
        
        this.stateManager = stateManager;
    }
    
    class OctagonalListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent E) {
            stateManager.setState(new OctagonalState(stateManager), State.OCTAGONAL_STATE);
        }
    }
}

class OctagonalState implements State {
    
    StateManager stateManager;
    MyOctagonal octagon;
    
    public OctagonalState(StateManager stateManager) {
        this.stateManager = stateManager;
    }
    
    @Override
    public void mouseDown(int x, int y) {
        octagon = new MyOctagonal(x, y, 0, 0);
        stateManager.addDrawing(octagon);
    }
    
    @Override
    public void mouseDrag(int x, int y) {
        octagon.setSize(x - octagon.getX(), y - octagon.getY());
    }
    
    @Override
    public void mouseUp(int x, int y) {
    }
    
}