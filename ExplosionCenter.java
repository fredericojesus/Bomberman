import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class ExplosionCenter extends Explosion
{   
    public void act() 
    {  
        if(time == 0)
            super.act();
        
        if(time++ == 10 && this != null)
            getWorld().removeObject(this);
    }    
}
