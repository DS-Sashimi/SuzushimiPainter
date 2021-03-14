import java.awt.event.*;
import java.awt.print.*;

import javax.swing.JButton;

public class PrintButton extends JButton {
    StateManager stateManager;
    
    public PrintButton(StateManager stateManager) {
        super("Print");
        addActionListener(new PrintListener());
        this.stateManager = stateManager;
    }
    
    class PrintListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            // 図形の選択を解除
            stateManager.canvas.getMediator().resetSelected();
            
            PrinterJob pj = PrinterJob.getPrinterJob();
            pj.setPrintable(stateManager.canvas);
            
            // ダイアログを表示
            if (pj.printDialog()) {
                try {
                    pj.print();
                } catch (PrinterException e1) {
                    e1.printStackTrace();
                }
            }
        }
        
    }
}