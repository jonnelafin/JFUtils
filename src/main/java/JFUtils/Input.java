/*
 * The MIT License
 *
 * Copyright 2020 Elias Eskelinen.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package JFUtils;

//import PBEngine.Supervisor;
import JFUtils.point.Point2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jonnelafin (Elias Arno Eskelinen)
 */
public class Input implements KeyListener, MouseMotionListener, MouseListener, MouseWheelListener {
    
    /**
     * use this for verbose control
     */
    public boolean verbodose = false;
    private InputActivated ki;
    private int up = 0, down = 0;
    private int right = 0, left = 0;
    private int up2 = 0, down2 = 0;
    private int right2 = 0, left2 = 0;
    private int mouseX = 0, mouseY = 0;
    public int rt = 0;
    public int ve = 0;
    public boolean tog = false;
    public boolean tog2 = false;
    public boolean map = false;

    /**
     * A list of all of the keydown states of the avaible ASCII characters.
     */
    public boolean[] keys = new boolean[tableSize];

    /**
     * A map of all of the keydown states of the avaible ASCII characters.
     */
    public HashMap<Character, Boolean> chars = new HashMap<>();
    final static int tableSize = 65535;
    
    public boolean isControlDown = false;
    public boolean isShiftDown = false;
    public boolean isEscDown = false;
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    private boolean mapKeyed = false;
    public boolean mapKeyAction(){
        if(mapKeyed != map){
            mapKeyed = map;
            return true;
        }
        return false;
    }
    
    private LinkedList<InputListener> listeners = new LinkedList<>();
    public synchronized void addListener(InputListener il){
        if(!listeners.contains(il)){
            listeners.add(il);
        }
        else{
            System.out.println("InputListener " + il + " already registered, no action taken.");
        }
    }
    public synchronized void removeListener(InputListener il){
        if(listeners.contains(il)){
            listeners.remove(il);
        }
        else{
            System.out.println("InputListener " + il + " not already registered, no action taken.");
        }
    }
    private void alertListeners(char key, int keycode, boolean status){
        listeners.forEach(l -> {
            l.handleInput(key, keycode, status);
        });
    }
    private void alertListenersOnMouse(int x, int y){
        listeners.forEach(l -> {
            l.handleMouse(x, y);
        });
    }
    private void alertListenersOnMouseExtra(boolean pressed1, boolean pressed2, boolean pressed3, int mouseWheelState){
        listeners.forEach(l -> {
            l.handleMouseExtra(pressed1, pressed2, pressed3, mouseWheelState);
        });
    }
    
    
    public int up(){return(this.up);}
    public int down(){return(this.down);}
    public int right(){return(this.right);}
    public int left(){return(this.left);}
    
    public int up2(){return(this.up2);}
    public int down2(){return(this.down2);}
    public int right2(){return(this.right2);}
    public int left2(){return(this.left2);}
    
    /**
     * Keeps track of the mouse position X
     * (Updated every time the mouse moves)
     * @return the X coordinate of the mouse
     */
    public int mouseX(){return(this.mouseX);}
    /**
     * Keeps track of the mouse position Y
     * (Updated every time the mouse moves)
     * @return the Y coordinate of the mouse
     */
    public int mouseY(){return(this.mouseY);}
    
    public boolean mouseDown = false;
    
    public KeyEvent keyPressed = null;
    
    @Override
    public void keyPressed(KeyEvent e) {
        keyPressed = e;
        ke = e.getKeyChar();
        kee = e.getKeyCode();
        alertListeners(ke, kee, true);
//        System.out.println(kee);
        if(ke == 'w'){
            up = -1;
        }
        if(ke == 's'){
            down = 1;
        }
        if(ke == 'a'){
            left = -1;
        }
        if(ke == 'd'){
            right = 1;
        }
        
        if(kee == 37){
        //arrow left "a"
            left2 = 1;
        }
        if(kee == 38){
        //arrow up "w"
            up2 = -1;
        }
        if(kee == 39){
        //arrow right "d"
            right2 = -1;
        }
        if(kee == 40){
        //arrow down "s"
            down2 = 1;
        }
        if(ke == 'e'){ki.tog();}
        if(ke == 'r'){rt = 1;tog = !tog;}
        if(ke == 'v'){ve = 1;}
        if(ke == 'm'){map = true;}
        
        if(chars.containsKey(ke)){
            chars.put(ke, true);
            if(verbodose){System.out.println(ke + ", " + kee + ": " +chars.get(ke));}
        }
        if(keys.length >= kee){
            keys[kee] = true;
        }
        isControlDown = e.isControlDown();
        isShiftDown = e.isShiftDown();
        isEscDown = keys[27];
    }
    
    public Input(InputActivated k){
        this.ki = k;
        initChars();
    }

    /**
     * Init the character lists and maps
     */
    public void initChars(){
        for(int i : new Range(tableSize)){
            char uc = (char) i;
            chars.put(uc, false);
            keys[i] = false;
        }
    }
    public char ke;
    public int kee;
    @Override
    public void keyReleased(KeyEvent e) {
        ke = e.getKeyChar();
        kee = e.getKeyCode();
        alertListeners(ke, kee, false);
        if(ke == 'w'){
            up = 0;
        }
        if(ke == 's'){
            down = 0;
        }
        if(ke == 'a'){
            left = 0;
        }
        if(ke == 'd'){
            right = 0;
        }
        
        if(kee == 37){
            left2 = 0;
        }
        if(kee == 38){
            up2 = 0;
        }
        if(kee == 39){
            right2 = 0;
        }
        if(kee == 40){
            down2 = 0;
        }
        if(ke == 'r'){rt = 0;}
        if(ke == 'v'){ve = 0;}
        if(ke == 'm'){map = false;}
        
        if(chars.containsKey(ke)){
            chars.put(ke, false);
            if(verbodose){System.out.println(ke + ", " + kee + ": " +chars.get(ke));}
        }
        if(keys.length >= kee){
            keys[kee] = false;
        }
        
        isControlDown = e.isControlDown();
        isShiftDown = e.isShiftDown();
        isEscDown = keys[27];
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        lX = mouseX;
        lY = mouseY;
        mouseX = e.getX();
        mouseY = e.getY();
        cX = mouseX - lX;
        cY = mouseY - lY;
        alertListenersOnMouse(mouseX, mouseY);
        //System.out.println("x, y: " + cX + " ," + cY);
    }
    
    public int cX = 0;
    public int cY = 0;

    public int cXa = 0;
    public int cYa = 0;
    
    public int lX = 0;

    
    public int lY = 0;
    @Override
    public void mouseMoved(MouseEvent e) {
        lX = mouseX;
        lY = mouseY;
        mouseX = e.getX();
        mouseY = e.getY();
        cXa = mouseX - lX;
        cYa = mouseY - lY;
        cX = mouseX - lX;
        cY = mouseY - lY;
        alertListenersOnMouse(mouseX, mouseY);
        /*
        cX = mouseX - lX;
        cY = mouseY - lY;
        System.out.println("x, y: " + cX + " ," + cY);
        */
    }
    public int mousestate = 0;
    int latestMouseMod = 0;

    /**
     * Is set true, if latest mouse-event happened inside the parent frame
     */
    public boolean parentInFocus = false;
    @Override
    public void mouseClicked(MouseEvent e) {
        parentInFocus = true;
        latestMouseMod = e.hashCode();
        this.mouseDown = true;
        alertListenersOnMouseExtra(mouseDown, false, false, mouseWheel);
        try {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            Thread.sleep(25);
        } catch (InterruptedException ex) {
           // Logger.getLogger(Input.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(latestMouseMod == e.hashCode()){
            this.mouseDown = false;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        latestMouseMod = e.hashCode();
        mouseDown = true;
        alertListenersOnMouseExtra(mouseDown, false, false, mouseWheel);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        latestMouseMod = e.hashCode();
        mouseDown = false;
        alertListenersOnMouseExtra(mouseDown, false, false, mouseWheel);
        ////throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        parentInFocus = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        parentInFocus = false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public Point2D reverseMouse(InputActivated k){
        return new Point2D((mouseX() / k.zoomfactor) - 1, (mouseY() / k.zoomfactor) - 3);
    }

    /**
     * 2: up
     * 1: default or processed
     * 0: down
     */
    public int mouseWheel = 1;
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.isControlDown() || true) {
            if (e.getWheelRotation() < 0) {
                mouseWheel = 2;
                if(verbodose){System.out.println("mouse wheel Up");}
            } else {
                mouseWheel = 0;
                if(verbodose){System.out.println("mouse wheel Down");}
            }
        }
        alertListenersOnMouseExtra(mouseDown, false, false, mouseWheel);
    }
}
