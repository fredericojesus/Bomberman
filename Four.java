import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Four extends Enemie
{
    private int sendPoop;
    
    public Four(int x, int y)
    {
        super(4, x, y, 2);
        
        sendPoop = 0;
    }
    
    public void act() 
    {
        super.act();
        sendPoop();
    }    
    
    private void sendPoop()
    {
        if(sendPoop++ == 800)
        {
            getWorld().addObject(new Poop(), droppingObjectCoords(getX()), droppingObjectCoords(getY()));
            sendPoop = 0;
        }
    }
}
