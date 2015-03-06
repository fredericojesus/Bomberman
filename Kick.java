import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Kick extends Power
{
    public Kick()
    {
        super(4);   
    }
    
    public void act()
    {
        super.act();
    }
    
    public void touchPower(Player player) 
    {
        if(!player.getKick())
        {
            Greenfoot.playSound("PowerUp.wav");
            player.setKick();
            ((BomberWorld)getWorld()).setKickImage(player.getPlayer());
        }
        getWorld().removeObject(this);
    }    
}
