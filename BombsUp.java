import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class BombsUp extends Power
{
    public BombsUp()
    {
        super(1);   
    }
    
    public void act()
    {
        super.act();
    }
    
    public void touchPower(Player player) 
    {
        if(player.getNumberBombs() < 5)
        {
            Greenfoot.playSound("PowerUp.wav");
            player.setNumberBombs(player.getNumberBombs()+1);
            ((BomberWorld)getWorld()).setMsgOnBox(player.getPlayer(), 2, player.getNumberBombs());
        }
        getWorld().removeObject(this);
    }    
}
