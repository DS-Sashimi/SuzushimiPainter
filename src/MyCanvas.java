import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.Enumeration;

public class MyCanvas extends JPanel implements Printable {
    
    Mediator mediator;
    
    public MyCanvas() {
        this.mediator = new Mediator(this);
        setBackground(Color.white);
        this.addMouseMotionListener(new CanvasMouseMotionListener(mediator));
    }
    
    public Mediator getMediator() {
        return mediator;
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        
        Enumeration<MyDrawing> e = mediator.drawingsElements();
        while (e.hasMoreElements()) {
            MyDrawing d = e.nextElement();
            d.draw(g);
        }
    }
    
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        // 最初のページだけ印刷
        if (pageIndex >= 1) {
            return NO_SUCH_PAGE;
        }
        
        pageFormat.setOrientation(PageFormat.LANDSCAPE);
        
        // 描画処理
        paint(graphics);
        
        return PAGE_EXISTS;
    }
    
}

class CanvasMouseMotionListener implements MouseMotionListener {
    
    Mediator mediator;
    MyDrawing d;
    
    public CanvasMouseMotionListener(Mediator mediator) {
        this.mediator = mediator;
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        for (int i = mediator.drawings.size() - 1; i >= 0; i--) {
            d = mediator.drawings.get(i);
            if (d.contains(e.getX(), e.getY())) {
                if (d.getSelected()) {
                    e.getComponent().setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }
        }
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        e.getComponent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        if (mediator.getSelectedDrawings().size() == 0) {
            for (int i = mediator.drawings.size() - 1; i >= 0; i--) {
                d = mediator.drawings.get(i);
                if (d.contains(e.getX(), e.getY())) {
                    e.getComponent().setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }
        } else {
            for (int i = mediator.drawings.size() - 1; i >= 0; i--) {
                d = mediator.drawings.get(i);
                if (d.contains(e.getX(), e.getY())) {
                    if (d.getSelected()) {
                        e.getComponent().setCursor(new Cursor(Cursor.HAND_CURSOR));
                    } else {
                        e.getComponent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        return;
                    }
                }
            }
        }
    }
    
}