/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JFUtils;

//import PBEngine.Supervisor;
import JFUtils.Point2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;

/**
 *
 * @author Jonnelafin
 */
public class Input implements KeyListener, MouseMotionListener, MouseListener {
    
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
    public boolean[] keys = new boolean[tableSize];
    public HashMap<Character, Boolean> chars = new HashMap<>();
    public final static int tableSize = 65535;
    
    
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
    
    public int up(){return(this.up);}
    public int down(){return(this.down);}
    public int right(){return(this.right);}
    public int left(){return(this.left);}
    
    public int up2(){return(this.up2);}
    public int down2(){return(this.down2);}
    public int right2(){return(this.right2);}
    public int left2(){return(this.left2);}
    
    public int mouseX(){return(this.mouseX);}
    public int mouseY(){return(this.mouseY);}
    
    public boolean mouseDown = false;
    
    public KeyEvent keyPressed = null;
    
    @Override
    public void keyPressed(KeyEvent e) {
        keyPressed = e;
        ke = e.getKeyChar();
        kee = e.getKeyCode();
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
    }
    
    public Input(InputActivated k){
        this.ki = k;
        initChars();
    }
    private void initChars(){
        for(int i : new Range(tableSize)){
            char uc = (char) i;
            chars.put(uc, false);
        }
    }
    public char ke;
    public int kee;
    @Override
    public void keyReleased(KeyEvent e) {
        ke = e.getKeyChar();
        kee = e.getKeyCode();
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
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        lX = mouseX;
        lY = mouseY;
        mouseX = e.getX();
        mouseY = e.getY();
        cX = mouseX - lX;
        cY = mouseY - lY;
        //System.out.println("x, y: " + cX + " ," + cY);
    }
    
    public int cX = 0;
    public int cY = 0;
    public int lX = 0;
    public int lY = 0;
    @Override
    public void mouseMoved(MouseEvent e) {
        lX = mouseX;
        lY = mouseY;
        mouseX = e.getX();
        mouseY = e.getY();/*
        cX = mouseX - lX;
        cY = mouseY - lY;
        System.out.println("x, y: " + cX + " ," + cY);
        */
    }
    public int mousestate = 0;
    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        this.mouseDown = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //mouseDown = true;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseDown = false;
        ////throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public Point2D reverseMouse(InputActivated k){
        return new Point2D((mouseX() / k.zoomfactor) - 1, (mouseY() / k.zoomfactor) - 3);
    }
}
