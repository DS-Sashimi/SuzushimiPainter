import java.awt.*;
import java.io.*;
import java.util.Enumeration;
import java.util.Vector;

public class Mediator {
    
    Vector<MyDrawing> drawings;
    MyCanvas canvas;
    Vector<MyDrawing> selectedDrawings;
    Vector<MyDrawing> buffers; // Cut，Copy バッファ
    
    public Mediator(MyCanvas canvas) {
        this.canvas = canvas;
        drawings = new Vector<MyDrawing>();
        selectedDrawings = new Vector<MyDrawing>();
        buffers = new Vector<MyDrawing>();
    }
    
    // drawingsの要素を返す
    public Enumeration<MyDrawing> drawingsElements() {
        return drawings.elements();
    }
    
    // キャンバスを再描画する
    public void repaint() {
        canvas.repaint();
    }
    
    // 図形dをdrawingsに追加する
    public void addDrawing(MyDrawing d) {
        drawings.add(d);
        resetSelected();
        setSelectedDrawing(d);
        repaint();
    }
    
    // （新しく作った）図形dを選択状態にする
    private void setSelectedDrawing(MyDrawing d) {
        //resetSelected();
        selectedDrawings.add(d);
        d.setSelected(true);
    }
    
    // 選択されている図形を返す
    public Vector<MyDrawing> getSelectedDrawings() {
        return selectedDrawings;
    }
    
    // すべての選択を解除する
    public void resetSelected() {
        if (selectedDrawings.size() != 0) {
            for (MyDrawing d : selectedDrawings) {
                d.setSelected(false);
            }
        }
        selectedDrawings.clear();
    }
    
    // 使ってない
    public void setSelected(int x, int y) {
        // 選択状態をリセットする
        resetSelected();
        
        MyDrawing d;
        
        for (int i = drawings.size() - 1; i >= 0; i--) {
            d = drawings.get(i);
            d.setSelected(d.contains(x, y));
            if (d.contains(x, y)) {
                selectedDrawings.add(d);
                break;
            }
        }
        
        repaint();
    }
    
    public void setSelected(Rectangle regionRect) {
        MyDrawing d;
        for (int i = drawings.size() - 1; i >= 0; i--) {
            d = drawings.get(i);
            d.setSelected(regionRect.contains((Rectangle) d.getRegion()));
            if (regionRect.contains((Rectangle) d.getRegion())) {
                selectedDrawings.add(d);
            }
        }
        repaint();
    }
    
    // 図形dを削除する
    public void removeDrawing(MyDrawing d) {
        drawings.remove(d);
        // selectedDrawings.remove(d);  // これは必要？ 不要？
    }
    
    public void remove() {
        if (selectedDrawings.size() != 0) {
            for (MyDrawing d : selectedDrawings) {
                removeDrawing(d);
            }
        }
        repaint();
    }
    
    public void move(int dx, int dy) {
        if (selectedDrawings.size() != 0) {
            for (MyDrawing d : selectedDrawings) {
                d.move(dx, dy);
            }
        }
        repaint();
    }
    
    public void setLineColor(Color lineColor) {
        if (selectedDrawings.size() != 0) {
            for (MyDrawing d : selectedDrawings) {
                d.setLineColor(lineColor);
            }
        }
        repaint();
    }
    
    public void setFillColor(Color fillColor) {
        if (selectedDrawings.size() != 0) {
            for (MyDrawing d : selectedDrawings) {
                d.setFillColor(fillColor);
            }
        }
        repaint();
    }
    
    public void setLineWidth(int lineWidth) {
        if (selectedDrawings.size() != 0) {
            for (MyDrawing d : selectedDrawings) {
                d.setLineWidth(lineWidth);
            }
        }
        repaint();
    }
    
    public void setDashed(boolean isDashed) {
        if (selectedDrawings.size() != 0) {
            for (MyDrawing d : selectedDrawings) {
                d.setDashed(isDashed);
            }
        }
        repaint();
    }
    
    public void setShadowed(boolean isShadowed) {
        if (selectedDrawings.size() != 0) {
            for (MyDrawing d : selectedDrawings) {
                d.setShadowed(isShadowed);
            }
        }
        repaint();
    }
    
    public void clearBuffer() {
        buffers.clear();
    }
    
    public void copy() {
        if (selectedDrawings.size() != 0) {
            clearBuffer(); // バッファをクリアする
            for (MyDrawing d : selectedDrawings) {
                buffers.add(d.clone());
            }
        }
    }
    
    public void cut() {
        if (selectedDrawings.size() != 0) {
            clearBuffer(); // バッファをクリアする
            for (MyDrawing d : selectedDrawings) {
                buffers.add(d.clone());
            }
            // drawings から selectedDrawings を削除
            remove();
			/* 上の1行は，これの代わりになる．
			for (MyDrawing d : selectedDrawings) {
				removeDrawing(d);
			}
			repaint();
			*/
        }
    }
    
    // ・コピー・カットされた図形をクリックされた位置にペーストする
    public void paste(int rightX, int rightY, int copyX, int copyY) {
        if (buffers.size() != 0) {
            MyDrawing clone;
            for (int i = 0; i < buffers.size(); i++) {
                MyDrawing d = buffers.get(i);
                clone = d.clone();
                clone.move(rightX - copyX, rightY - copyY);
                // addDrawing(clone); の代わりに次の2行を使う．
                drawings.add(clone);
                setSelectedDrawing(clone);
            }
            repaint();
        }
    }
    
    public void load(File file) {
        // File入力
        try {
            FileInputStream fin = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fin);
            
            drawings = (Vector) in.readObject();
            fin.close();
            
            repaint();
        } catch (Exception ex) {
        }
    }
    
    public void save(File file) {
        // File出力
        try {
            FileOutputStream fout = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fout);
            
            out.writeObject(drawings);
            out.flush();
            
            fout.close();
            
            // repaint();
        } catch (Exception ex) {
        }
    }
    
    // 最前面へ
    public void bringToFront() {
        Vector<MyDrawing> newDrawings = new Vector<MyDrawing>();
        MyDrawing d;
        for (int i = 0; i < drawings.size(); i++) {
            d = drawings.get(i);
            if (!(selectedDrawings.contains(d))) {
                newDrawings.add(d);
            }
        }
        for (int i = selectedDrawings.size() - 1; i >= 0; i--) {
            d = selectedDrawings.get(i);
            newDrawings.add(d);
        }
        drawings = newDrawings;
        repaint();
    }
    
    // 前面へ
    public void bringForward() {
        int index = drawings.indexOf(selectedDrawings.get(0));
        if (index < drawings.size() - 1) {
            MyDrawing tmpDrawing = drawings.elementAt(index);
            drawings.remove(index);
            drawings.insertElementAt(tmpDrawing, index + 1);
            repaint();
        }
    }
    
    // 背面へ
    public void sendBackward() {
        int index = drawings.indexOf(selectedDrawings.get(0));
        if (index > 0) {
            MyDrawing tmpDrawing = drawings.elementAt(index);
            drawings.remove(index);
            drawings.insertElementAt(tmpDrawing, index - 1);
            repaint();
        }
    }
    
    // 最背面へ
    public void sendToBack() {
        Vector<MyDrawing> newDrawings = new Vector<MyDrawing>();
        MyDrawing d;
        for (int i = selectedDrawings.size() - 1; i >= 0; i--) {
            d = selectedDrawings.get(i);
            newDrawings.add(d);
        }
        for (int i = 0; i < drawings.size(); i++) {
            d = drawings.get(i);
            if (!(selectedDrawings.contains(d))) {
                newDrawings.add(d);
            }
        }
        drawings = newDrawings;
        repaint();
    }
    
}