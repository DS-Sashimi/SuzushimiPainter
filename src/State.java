interface State {
    
    // 各Stateを表す定数（stateManagerのstateNumberになる）
    int NULL_STATE = 0;
    int RECTANGLE_STATE = 1;
    int OVAL_STATE = 2;
    int OCTAGONAL_STATE = 3;
    int TRIANGLE_STATE = 4;
    int SELECT_STATE = 10;
    int SPOIT_LINECOLOR_STATE = 11;
    int SPOIT_FILLCOLOR_STATE = 12;
    
    void mouseDown(int x, int y);
    
    void mouseDrag(int x, int y);
    
    void mouseUp(int x, int y);
    
    // public void rightMouseClick(int x, int y);
}