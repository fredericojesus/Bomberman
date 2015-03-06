import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Font;
import java.awt.Color;

public class TextBox extends Actor
{
    private Font font;
    private Color color;
    
    /** initialize textImage object */
    public TextBox(String m)
    {
        font = new Font("Arial", Font.BOLD, 16);
        color = Color.BLACK;
        GreenfootImage image = new GreenfootImage(32, 32);
        setImage(image);
        setText(m);
    }    
    
    /** function that changes the text in the textBox */
    public void setText(String msg)
    {
        GreenfootImage image = getImage();
        
        image.clear();
        image.setFont(font);
        image.setColor(color);
        
        image.drawString(msg, 16, 16);
    }
    
    /** function that changes the atribute color */
    public void setColor(Color color)
    {
        this.color = color;
    }
}
