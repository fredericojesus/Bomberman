import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Three extends Enemie
{
    private int timeMaxSpeed;
    private boolean rightNumber;
    
    public Three(int x, int y)
    {
        super(3, x, y, 1);
        
        timeMaxSpeed = 0;
        rightNumber = false;
    }
    
    public void act() 
    {
        super.act();
        maxSpeed();
    }    
    
    private void maxSpeed()
    {
        int rand = Greenfoot.getRandomNumber(100);
        if(rand == 3 || rightNumber)
        {
            speed = 4;
            rightNumber = true;
            if(timeMaxSpeed++ == 50)
            {
                speed = 1;
                rightNumber = false;
                timeMaxSpeed = 0;
            }
        }
    }
}
