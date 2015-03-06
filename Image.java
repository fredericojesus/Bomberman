import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Image extends Actor
{
    public Image(String i)
    {
        setImage(i + ".png");
    }
    
    public Image(GreenfootImage img)
    {
        setImage(img);
    }    
}
