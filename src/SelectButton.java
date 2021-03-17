import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectButton extends JButton {
    
    StateManager stateManager;
    
    public SelectButton(StateManager stateManager) {
        super("Select");
        
        addActionListener(new SelectListener());
        
        this.stateManager = stateManager;
    }
    
    class SelectListener implements ActionListener {
        
        public void actionPerformed(ActionEvent E) {
            stateManager.setState(new SelectState(stateManager), State.SELECT_STATE);
        }
    }
    
}

class SelectState implements State {
    
    StateManager stateManager;
    int clickX, clickY; // ドラッグの際に差分を求めるために保っておく座標
    MySelectRegionRect selectRegionRect;
    boolean isMoveMode;
    
    public SelectState(StateManager stateManager) {
        this.stateManager = stateManager;
        isMoveMode = true;
    }
    
    @Override
    public void mouseDown(int x, int y) {
        clickX = x;
        clickY = y;
        
        MyDrawing d;
        
        for (int i = stateManager.canvas.getMediator().drawings.size() - 1; i >= 0; i--) {
            d = stateManager.canvas.getMediator().drawings.get(i);
            if (d.contains(x, y)) {
                if (stateManager.canvas.getMediator().selectedDrawings.contains(d)) {
                    isMoveMode = true;
                } else {
                    isMoveMode = true;
                    stateManager.canvas.getMediator().resetSelected();
                    d.setSelected(true);
                    stateManager.canvas.getMediator().selectedDrawings.add(d);
                }
                stateManager.canvas.getMediator().repaint();
                return;
            }
        }
        
        isMoveMode = false;
        selectRegionRect = new MySelectRegionRect(x, y, 0, 0);
        stateManager.addDrawing(selectRegionRect);
        stateManager.canvas.getMediator().repaint();
    }
    
    @Override
    public void mouseDrag(int x, int y) {
        if (isMoveMode) {
            stateManager.canvas.getMediator().move(x - clickX, y - clickY);
            clickX = x;
            clickY = y;
        } else {
            selectRegionRect.setSize(x - selectRegionRect.getX(), y - selectRegionRect.getY());
        }
        
    }
    
    @Override
    public void mouseUp(int x, int y) {
        // 範囲選択用の矩形（selectRegionRect）をdrawings，selectedDrawingsから削除
        stateManager.canvas.getMediator().removeDrawing(selectRegionRect);
        stateManager.canvas.getMediator().selectedDrawings.remove(selectRegionRect);
        
        if (!isMoveMode) {
            // selectRegionRect内の図形をすべて選択
            stateManager.canvas.getMediator().setSelected((Rectangle) selectRegionRect.getRegion());
        }
        
        isMoveMode = true;
        // リペイント
        stateManager.canvas.getMediator().repaint();
    }
    
}