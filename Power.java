import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public abstract class Power extends Actor
{
    private GreenfootImage powerB;
    private GreenfootImage powerR;
    private int changeImage;
    
    private int power;
    
    protected abstract void touchPower(Player player);
    
    public Power(int p)
    {
        power = p;
        
        powerB = new GreenfootImage("power" + power + "b" + ".png");
        powerR = new GreenfootImage("power" + power + "r" + ".png");
    }
   
    public void act() 
    {
        setImage();
    }
    
    public void setImage()
    {
        if(changeImage++ == 10)
        {
            if(getImage() == powerB)
                setImage(powerR);
            else
                setImage(powerB);
                
            changeImage = 0;
        }
    }
}
