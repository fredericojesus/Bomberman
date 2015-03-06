import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class LifeUp extends Power
{
    public LifeUp()
    {
        super(0);   
    }
    
    public void act()
    {
        super.act();
    }
    
    public void touchPower(Player player) 
    {
        Greenfoot.playSound("LifeUp.wav");
        player.setLifes(player.getLifes()+1);
        ((BomberWorld)getWorld()).setMsgOnBox(player.getPlayer(), 1, player.getLifes());
        getWorld().removeObject(this);
    }    
}
