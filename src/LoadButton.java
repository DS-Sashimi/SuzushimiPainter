import java.awt.event.*;
import java.io.File;

import javax.swing.*;

public class LoadButton extends JButton {
    
    StateManager stateManager;
    
    public LoadButton(StateManager stateManager) {
        super("Load");
        addActionListener(new LoadListener());
        this.stateManager = stateManager;
    }
    
    class LoadListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent E) {
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(null); // ファイルロード用のダイアログを開く
            if (returnVal == JFileChooser.APPROVE_OPTION) { // OKボタンが押されたとき
                File file = fc.getSelectedFile();
                // ここで得たファイルをFileInputStreamに与える
                stateManager.canvas.getMediator().load(file);
            }
        }
    }
    
}