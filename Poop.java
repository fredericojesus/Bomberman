import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Poop extends TimedActor
{
    public void act() 
    {
        if(time++ == 1600)
        {
            getWorld().removeObject(this);
        }
    }
}
