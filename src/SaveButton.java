import java.awt.event.*;
import java.io.File;

import javax.swing.*;

public class SaveButton extends JButton {
    StateManager stateManager;
    
    public SaveButton(StateManager stateManager) {
        super("Save");
        addActionListener(new SaveListener());
        this.stateManager = stateManager;
    }
    
    class SaveListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showSaveDialog(null); // ファイルロード用のダイアログを開く
            if (returnVal == JFileChooser.APPROVE_OPTION) { // OKボタンが押されたとき
                File file = fc.getSelectedFile();
                // ここで得たファイルをFileInputStreamに与える
                stateManager.canvas.getMediator().save(file);
            }
            
        }
        
    }
}