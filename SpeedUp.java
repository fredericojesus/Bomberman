import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class SpeedUp extends Power
{
    public SpeedUp()
    {
        super(2);   
    }
    
    public void act()
    {
        super.act();
    }
    
    public void touchPower(Player player) 
    {
        if(player.getSpeed() < 3)
        {
            Greenfoot.playSound("PowerUp.wav");
            player.setSpeed(player.getSpeed()+1);
            ((BomberWorld)getWorld()).setMsgOnBox(player.getPlayer(), 3, player.getSpeed());
        }
        getWorld().removeObject(this);
    }    
}
