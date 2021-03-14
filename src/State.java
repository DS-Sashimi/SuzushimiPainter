interface State {
    // 各Stateを表す定数（stateManagerのstateNumberになる）
    public static final int NULL_STATE = 0;
    public static final int RECTANGLE_STATE = 1;
    public static final int OVAL_STATE = 2;
    public static final int OCTAGONAL_STATE = 3;
    public static final int TRIANGLE_STATE = 4;
    public static final int SELECT_STATE = 10;
    public static final int SPOIT_LINECOLOR_STATE = 11;
    public static final int SPOIT_FILLCOLOR_STATE = 12;
    
    public void mouseDown(int x, int y);
    
    public void mouseDrag(int x, int y);
    
    public void mouseUp(int x, int y);
    
    // public void rightMouseClick(int x, int y);
}