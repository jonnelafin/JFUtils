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

package JFUtils.graphing;


import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Jonnelafin
 */
public class Graph extends JFrame{
    public int max = 100;
    private int lastMax = max;
    int h = 20;
    
    Random rnd = new Random(420);
    boolean running = true;
    
    public JLabel area;
    int tick = 0;
    float t = 0;
    String[][] map;
    Integer[] history;
    public Graph(){
        this.setTitle("JFUtils graphing");
        this.setSize(740, 240);
        this.setVisible(true);
        //this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JPanel cont = new JPanel( new GridLayout(1, 1));
        cont.setBackground(Color.black);
        
        area = new JLabel();
        area.setVisible(true);
        
        //area.setEditable(false);
        cont.add(area);
        
        this.add(cont);
        area.setText("please wait...");
        
        history = new Integer[max];
        zero(history);
        
        map = new String[h][max];
        empty(map);
        
    }
    private static void zero(Integer[] arr){
        for(Integer i : arr){
            i = 0;
        }
    }
    private static void empty(String[][] arr){
        for(String[] i : arr){
            for(String x : i){
                x = ".";
            }
        }
    }
    private static String toString(String[][] src){
        String out = "";
        for(String[] i : src){
            for(String x : i){
                if(x == null){
                    out = out + " ";
                }
                else{
                    out = out + x;
                }
            }
            out = out + "<br >";
        }
        return out;
    }
    private double value = 0;
    
    double lastTick = 0;
    public void update(double value, int tickC){
        this.value = value;
        t = t + 0.1F;
            if(max != lastMax){
                history = new Integer[max];
                zero(history);
                
                map = new String[h][max];
                empty(map);
            }
            
            tick++;
            if(tick > max){
                tick = 0;
            }
            
            
            int num = rnd.nextInt(100);
            
            int charge = (int) (t * 20);
            
            int place = tick;
            if(place < 0){
                place = 0;
            }
            if(place > max-1){
                place = max-1;
            }
            
            String normal = "<span color='rgb(0, 255, 0)'>#</span>";
            //System.out.println(lastTick - tickC);
            if(tick - tickC  > 0){
                normal = "<span color='rgb(255, 0, 0)'>#</span>";
            }
            
            
            double val = Math.sin(tick - t);
            //charge = (int) val * 10;
            
            //Rasterize
            for(int x : new JFUtils.Range(max)){
                for(int y : new JFUtils.Range(h)){
                    map[y][place] = "<span color='rgb(0, 0, 0)'>#</span>";
                    try {
                        map[y][place + 1] = "<span color='rgb(60, 120, 180)'>#</span>";
                    } catch (Exception e) {
                    }
                }
                //map[h-1][place] = "#\n";
            }
            map[19 - (int)(value)][place] = normal;
            
            String ready = toString(map);
            //RENDER
            area.setText("<html>" + ready + "</html>");
            
            Font labelFont = area.getFont();
            String labelText = area.getText();
            int stringWidth = area.getFontMetrics(labelFont).stringWidth(labelText);
            int componentWidth = area.getWidth();
            // Find out how much the font can grow in width.
            double widthRatio = (double)componentWidth / (double)stringWidth;

            int newFontSize = (int)(labelFont.getSize() * widthRatio);
            int componentHeight = area.getHeight();

            // Pick a new font size so it will not be larger than the height of label.
            int fontSizeToUse = Math.min(newFontSize, componentHeight);

            // Set the label's font size to the newly determined size.
            area.setFont(new Font(labelFont.getName(), Font.PLAIN, 8));
            //System.out.println(this.getSize());
            area.setForeground(Color.red);
            
            //area.setText(Math.abs(num) + "");
            lastTick = tickC;
    }
    
    public static void main(String[] args) throws InterruptedException {
        Graph a = new Graph();
        int tc = 0;
        while(true){
            a.update(0, 0);
            tc++;
        }
    }
}
