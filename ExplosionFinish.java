import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class ExplosionFinish extends Explosion
{   
    public ExplosionFinish(int direction)
    {
        switch(direction)
        {
            case 0: setRotation(0); break;
            case 1: setRotation(90); break;
            case 2: setRotation(180); break;
            case 3: setRotation(270); break;
        }
    }
    
    public void act() 
    {    
        if(time == 0)
            super.act();
        
        Actor explosion = getOneIntersectingObject(ExplosionCenter.class);
        Actor explosionMiddle = getOneIntersectingObject(ExplosionMiddle.class);
        if(explosionMiddle != null || explosion != null)
            getWorld().removeObject(this);
        
        if(time++ == 10)
            getWorld().removeObject(this);
    }    
}
