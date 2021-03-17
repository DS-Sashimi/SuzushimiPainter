import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyApplication extends JFrame {
    
    StateManager stateManager;
    MyCanvas canvas;
    
    JPopupMenu popup = new JPopupMenu();
    int rightX, rightY; // 右クリックされたときのマウス座標
    int copyX, copyY; // コピーしたときのマウス座標
    
    // コンストラクタ
    public MyApplication() {
        super("Suzushimi Painter");
        
        canvas = new MyCanvas();
        canvas.setBackground(Color.white);
        
        stateManager = new StateManager(canvas);
        
        Container contentPane = getContentPane();
        
        // メニューを構成する
        JPanel jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
        
        JPanel menuPanel1 = new JPanel();
        menuPanel1.setLayout(new FlowLayout());
        
        // 保存
        SaveButton saveButton = new SaveButton(stateManager);
        menuPanel1.add(saveButton);
        
        // ロード
        LoadButton loadButton = new LoadButton(stateManager);
        menuPanel1.add(loadButton);
        
        // 印刷
        PrintButton printButton = new PrintButton(stateManager);
        menuPanel1.add(printButton);
        
        JPanel menuPanel2 = new JPanel();
        menuPanel2.setLayout(new FlowLayout());
        
        // 矩形
        RectButton rectButton = new RectButton(stateManager);
        menuPanel2.add(rectButton);
        
        // 楕円
        OvalButton ovalButton = new OvalButton(stateManager);
        menuPanel2.add(ovalButton);
        
        // 八角形
        OctagonalButton octagonalButton = new OctagonalButton(stateManager);
        menuPanel2.add(octagonalButton);
        
        // 三角形
        TriangleButton triangleButton = new TriangleButton(stateManager);
        menuPanel2.add(triangleButton);
        
        JPanel menuPanel3 = new JPanel();
        menuPanel3.setLayout(new FlowLayout());
        
        // 選択
        SelectButton selectButton = new SelectButton(stateManager);
        menuPanel3.add(selectButton);
        
        // 削除
        DeleteButton deleteButton = new DeleteButton(stateManager);
        menuPanel3.add(deleteButton);
        
        // 破線・実線（チェックボックス）
        JCheckBox dashCheck = new JCheckBox("dash line");
        DashCheckListener dashCheckListener = new DashCheckListener(stateManager);
        dashCheck.addItemListener(dashCheckListener);
        menuPanel3.add(dashCheck);
        
        // 影（チェックボックス）
        JCheckBox shadowCheck = new JCheckBox("drop shadow");
        ShadowCheckListener shadowCheckListener = new ShadowCheckListener(stateManager);
        shadowCheck.addItemListener(shadowCheckListener);
        menuPanel3.add(shadowCheck);
        
        JPanel menuPanel4 = new JPanel();
        menuPanel4.setLayout(new FlowLayout());
        
        // 線の太さ
        menuPanel4.add(new JLabel("線の太さ"));
        JComboBox<LineWidthMenuItem> lineWidthMenu = new JComboBox<LineWidthMenuItem>();
        lineWidthMenu.addItem(new LineWidthMenuItem(stateManager, 1, "1"));
        lineWidthMenu.addItem(new LineWidthMenuItem(stateManager, 3, "3"));
        lineWidthMenu.addItem(new LineWidthMenuItem(stateManager, 5, "5"));
        lineWidthMenu.addItem(new LineWidthMenuItem(stateManager, 10, "10"));
        lineWidthMenu.addItem(new LineWidthMenuItem(stateManager, 20, "20"));
        lineWidthMenu.addActionListener(new LineWidthActionListener());
        menuPanel4.add(lineWidthMenu);
        
        // 線の色（コンボボックス）
        menuPanel4.add(new JLabel("線の色"));
        JComboBox<LineColorMenuItem> lineColorMenu = new JComboBox<LineColorMenuItem>();
        lineColorMenu.addItem(new LineColorMenuItem(stateManager, Color.black, "black"));
        lineColorMenu.addItem(new LineColorMenuItem(stateManager, Color.red, "red"));
        lineColorMenu.addItem(new LineColorMenuItem(stateManager, Color.green, "green"));
        lineColorMenu.addItem(new LineColorMenuItem(stateManager, Color.blue, "blue"));
        lineColorMenu.addItem(new LineColorMenuItem(stateManager, Color.cyan, "cyan"));
        lineColorMenu.addItem(new LineColorMenuItem(stateManager, Color.magenta, "magenta"));
        lineColorMenu.addItem(new LineColorMenuItem(stateManager, Color.yellow, "yellow"));
        lineColorMenu.addItem(new LineColorMenuItem(stateManager, Color.white, "white"));
        lineColorMenu.addItem(new LineColorMenuItem(stateManager, null, "Other Colors"));
        lineColorMenu.addItem(new LineColorMenuItem(stateManager, null, "Spoit"));
        lineColorMenu.addActionListener(new LineColorActionListener(stateManager));
        menuPanel4.add(lineColorMenu);
        
        // 塗り色（コンボボックス）
        menuPanel4.add(new JLabel("塗りつぶし色"));
        JComboBox<FillColorMenuItem> fillColorMenu = new JComboBox<FillColorMenuItem>();
        fillColorMenu.addItem(new FillColorMenuItem(stateManager, Color.white, "white"));
        fillColorMenu.addItem(new FillColorMenuItem(stateManager, Color.red, "red"));
        fillColorMenu.addItem(new FillColorMenuItem(stateManager, Color.green, "green"));
        fillColorMenu.addItem(new FillColorMenuItem(stateManager, Color.blue, "blue"));
        fillColorMenu.addItem(new FillColorMenuItem(stateManager, Color.cyan, "cyan"));
        fillColorMenu.addItem(new FillColorMenuItem(stateManager, Color.magenta, "magenta"));
        fillColorMenu.addItem(new FillColorMenuItem(stateManager, Color.yellow, "yellow"));
        fillColorMenu.addItem(new FillColorMenuItem(stateManager, Color.black, "black"));
        fillColorMenu.addItem(new FillColorMenuItem(stateManager, null, "Other Colors"));
        fillColorMenu.addItem(new FillColorMenuItem(stateManager, null, "Spoit"));
        fillColorMenu.addActionListener(new FillColorActionListener(stateManager));
        menuPanel4.add(fillColorMenu);
        
        jp.add(menuPanel1);
        jp.add(menuPanel2);
        jp.add(menuPanel3);
        jp.add(menuPanel4);
        
        contentPane.setLayout(new BorderLayout());
        contentPane.add(jp, BorderLayout.NORTH);
        contentPane.add(canvas, BorderLayout.CENTER);
        
        addPopupMenuItem("Copy", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (stateManager.canvas.getMediator().selectedDrawings.size() > 0) {
                    // Copy の処理
                    copyX = rightX;
                    copyY = rightY;
                    stateManager.canvas.getMediator().copy();
                } else {
                    JOptionPane.showMessageDialog(canvas, "何かしらの図形を選択してからでないと，コピーできません．");
                }
            }
        });
        addPopupMenuItem("Cut", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (stateManager.canvas.getMediator().selectedDrawings.size() > 0) {
                    // Cut の処理
                    copyX = rightX;
                    copyY = rightY;
                    stateManager.canvas.getMediator().cut();
                } else {
                    JOptionPane.showMessageDialog(canvas, "何かしらの図形を選択してからでないと，カットできません．");
                }
            }
        });
        addPopupMenuItem("Paste", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (stateManager.canvas.getMediator().buffers.size() > 0) {
                    // Paste の処理
                    stateManager.canvas.getMediator().paste(rightX, rightY, copyX, copyY);
                } else {
                    JOptionPane.showMessageDialog(canvas, "何かしらの図形をコピー（またはカット）してからでないと，ペーストできません．");
                }
            }
        });
        popup.addSeparator();
        addPopupMenuItem("Bring to Front（最前面へ）", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 最前面へ
                stateManager.canvas.getMediator().bringToFront();
            }
        });
        addPopupMenuItem("Bring Forward（前面へ）", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (stateManager.canvas.getMediator().selectedDrawings.size() == 1) {
                    // 前面へ
                    stateManager.canvas.getMediator().bringForward();
                } else {
                    JOptionPane.showMessageDialog(canvas, "ちょうど1個の図形を選択した状態でないと，この操作は実行できません．ご勘弁ください．");
                }
            }
        });
        addPopupMenuItem("Send Backward（背面へ）", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (stateManager.canvas.getMediator().selectedDrawings.size() == 1) {
                    // 背面へ
                    stateManager.canvas.getMediator().sendBackward();
                } else {
                    JOptionPane.showMessageDialog(canvas, "ちょうど1個の図形を選択した状態でないと，この操作は実行できません．ご勘弁ください．");
                }
            }
        });
        addPopupMenuItem("Send to Back（最背面へ）", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 最背面へ
                stateManager.canvas.getMediator().sendToBack();
            }
        });
        
        canvas.addMouseListener(new MouseAdapter() {
            // 現在の状態の mouseDown 処理を呼び出す
            @Override
            public void mousePressed(MouseEvent e) {
                //stateManager.mouseDown(e.getX(), e.getY());
                if (SwingUtilities.isLeftMouseButton(e)) {
                    stateManager.mouseDown(e.getX(), e.getY());
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    rightX = e.getX();
                    rightY = e.getY();
                    popup.show(canvas, rightX, rightY);
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                stateManager.mouseUp(e.getX(), e.getY());
                if (stateManager.canvas.getMediator().selectedDrawings.size() > 0) {
                    dashCheck.removeItemListener(dashCheckListener);
                    dashCheck.setSelected(stateManager.canvas.getMediator().selectedDrawings.get(0).getDashed());
                    dashCheck.addItemListener(dashCheckListener);
                    shadowCheck.removeItemListener(shadowCheckListener);
                    shadowCheck.setSelected(stateManager.canvas.getMediator().selectedDrawings.get(0).getShadowed());
                    shadowCheck.addItemListener(shadowCheckListener);
                }
                // System.out.println(stateManager.canvas.getMediator().selectedDrawings + ", " + stateManager.canvas.getMediator().selectedDrawings.size());
            }
            
        });
        
        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    stateManager.mouseDrag(e.getX(), e.getY());
                }
            }
        });
        
        // WindowEvent リスナを設定（無名クラスを利用している）
        this.addWindowListener(new WindowAdapter() {
            // ウインドウが閉じたら終了する
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
    }
    
    /** メインメソッド */
    public static void main(String[] args) {
        MyApplication app = new MyApplication();
        //app.setSize(400, 300);
        app.setSize(750, 750);
        app.setVisible(true);
    }
    
    // メニューのためのメソッド
    
    /** メニュー（右クリックで表示されるポップアップメニュー）に項目を追加するメソッド */
    private void addPopupMenuItem(String name, ActionListener actionListener) {
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(actionListener);
        popup.add(item);
    }
    
}

/** メニューのアイテム */
class LineColorMenuItem {
    
    StateManager stateManager;
    Color lineColor;
    String label;
    
    public LineColorMenuItem(StateManager stateManager, Color lineColor, String label) {
        this.stateManager = stateManager;
        this.lineColor = lineColor;
        this.label = label;
    }
    
    public String toString() {
        return label;
    }
    
    public void changeLineColor() {
        stateManager.setLineColor(lineColor);
    }
}

class FillColorMenuItem {
    
    StateManager stateManager;
    Color fillColor;
    String label;
    
    public FillColorMenuItem(StateManager stateManager, Color fillColor, String label) {
        this.stateManager = stateManager;
        this.fillColor = fillColor;
        this.label = label;
    }
    
    public String toString() {
        return label;
    }
    
    public void changeFillColor() {
        stateManager.setFillColor(fillColor);
    }
}

class LineWidthMenuItem {
    
    StateManager stateManager;
    int lineWidth;
    String label;
    
    public LineWidthMenuItem(StateManager stateManager, int lineWidth, String label) {
        this.stateManager = stateManager;
        this.lineWidth = lineWidth;
        this.label = label;
    }
    
    public String toString() {
        return label;
    }
    
    public void changeLineWidth() {
        stateManager.setLineWidth(lineWidth);
    }
    
}

/** リスナ */
class DashCheckListener implements ItemListener {
    
    StateManager stateManager;
    
    public DashCheckListener(StateManager stateManager) {
        this.stateManager = stateManager;
    }
    
    @Override
    public void itemStateChanged(ItemEvent e) {
        stateManager.setDashed(e.getStateChange() == ItemEvent.SELECTED);
    }
    
}

class ShadowCheckListener implements ItemListener {
    
    StateManager stateManager;
    
    public ShadowCheckListener(StateManager stateManager) {
        this.stateManager = stateManager;
    }
    
    @Override
    public void itemStateChanged(ItemEvent e) {
        stateManager.setShadowed(e.getStateChange() == ItemEvent.SELECTED);
    }
    
}

class LineColorActionListener implements ActionListener {
    
    StateManager stateManager;
    
    public LineColorActionListener(StateManager stateManager) {
        this.stateManager = stateManager;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox<LineColorMenuItem> cb = (JComboBox<LineColorMenuItem>) e.getSource();
        LineColorMenuItem selectedItem = (LineColorMenuItem) cb.getSelectedItem();
        if (selectedItem.label == "Other Colors") {
            JColorChooser colorchooser = new JColorChooser();
            selectedItem.lineColor = JColorChooser.showDialog(colorchooser, "色を選択してください。", Color.white);
        } else if (selectedItem.label == "Spoit") {
            stateManager.setState(new SpoitLineColorState(stateManager), State.SPOIT_LINECOLOR_STATE);
        }
        if (selectedItem.lineColor != null) {
            selectedItem.changeLineColor();
        }
    }
    
}

class FillColorActionListener implements ActionListener {
    
    StateManager stateManager;
    
    public FillColorActionListener(StateManager stateManager) {
        this.stateManager = stateManager;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox<FillColorMenuItem> cb = (JComboBox<FillColorMenuItem>) e.getSource();
        FillColorMenuItem selectedItem = (FillColorMenuItem) cb.getSelectedItem();
        if (selectedItem.label == "Other Colors") {
            JColorChooser colorchooser = new JColorChooser();
            selectedItem.fillColor = JColorChooser.showDialog(colorchooser, "色を選択してください。", Color.white);
        } else if (selectedItem.label == "Spoit") {
            stateManager.setState(new SpoitFillColorState(stateManager), State.SPOIT_FILLCOLOR_STATE);
        }
        if (selectedItem.fillColor != null) {
            selectedItem.changeFillColor();
        }
    }
    
}

class LineWidthActionListener implements ActionListener {
    
    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox<LineWidthMenuItem> cb = (JComboBox<LineWidthMenuItem>) e.getSource();
        ((LineWidthMenuItem) cb.getSelectedItem()).changeLineWidth();
    }
    
}