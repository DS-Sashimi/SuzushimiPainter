import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OvalButton extends JButton {
    
    StateManager stateManager;
    
    public OvalButton(StateManager stateManager) {
        super("Oval");
        
        addActionListener(new OvalListener());
        
        this.stateManager = stateManager;
    }
    
    class OvalListener implements ActionListener {
        
        public void actionPerformed(ActionEvent E) {
            stateManager.setState(new OvalState(stateManager), State.OVAL_STATE);
        }
        
    }
}

class OvalState implements State {
    
    StateManager stateManager;
    MyOval oval;
    
    public OvalState(StateManager stateManager) {
        this.stateManager = stateManager;
    }
    
    @Override
    public void mouseDown(int x, int y) {
        oval = new MyOval(x, y, 0, 0);
        stateManager.addDrawing(oval);
        
    }
    
    @Override
    public void mouseUp(int x, int y) {
    }
    
    @Override
    public void mouseDrag(int x, int y) {
        oval.setSize(x - oval.getX(), y - oval.getY());
    }
    
}