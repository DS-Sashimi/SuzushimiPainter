import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteButton extends JButton {
    
    StateManager stateManager;
    
    public DeleteButton(StateManager stateManager) {
        super("Delete");
        
        addActionListener(new DeleteListener());
        
        this.stateManager = stateManager;
    }
    
    class DeleteListener implements ActionListener {
        
        public void actionPerformed(ActionEvent E) {
            stateManager.canvas.getMediator().remove();
        }
    }
}