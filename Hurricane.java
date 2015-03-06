import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Hurricane extends TimedActor
{
    private GreenfootImage [] imgsH;
    private int changeImage;
    private int currentImage;
    
    private int px;
    private int py;
    
    private int hcase;
    private boolean reachedPlayerPosition;
    
    public Hurricane(int cx, int cy)
    {
        imgsH = new GreenfootImage[4];
        for(int i = 0; i < 4; i++)
            imgsH[i] = new GreenfootImage("h" + i + ".png");
        changeImage = 0;
        currentImage = 0;
       
        px = cx;
        py = cy;
        
        reachedPlayerPosition = false;
    }
    
    public void act() 
    {
        if(hcase == 0)
            hcase();
            
        move();
        
        if(getX() < 16 || getX() > 464 || getY() < 48 || getY() > 496)
            getWorld().removeObject(this);
    }    
    
    private void hcase()
    {
        if(px > getX()-32 && px < getX()+32 && py > getY()) hcase = 5;
        else if(px > getX()-32 && px < getX()+32 && py < getY()) hcase = 6;
        else if(py > getY()-32 && py < getY()+32 && px > getX()) hcase = 7;
        else if(py > getY()-32 && py < getY()+32 && px < getX()) hcase = 8;
        else if(px > getX() && py > getY()) hcase = 1;
        else if(px > getX() && py < getY()) hcase = 2;
        else if(px < getX() && py > getY()) hcase = 3;
        else if(px < getX() && py < getY()) hcase = 4;
    }
    
    private int abs(int x)
    {
        if(x < 0)
            return -x;
        else 
            return x;
    }
    
    public void move()
    {
        int x = getX();
        int y = getY();
        
        if(px == x && py == y)
            reachedPlayerPosition = true;
            
        if(reachedPlayerPosition)
            setPxPy();

        if(abs(x-px) > abs(y-py))
        {
            if(x < px) 
                setLocation(x + 1, y);
            else 
                setLocation(x - 1, y);
        }
        else
        {
            if(y < py) 
                setLocation(x, y + 1);
            else 
                setLocation(x, y - 1);
                
        }
        
        Player player = (Player) getOneIntersectingObject(Player.class);
        if(player != null && time++ == 3)
        {
            if(!player.getTransparency())
                player.death();
            time = 0;
        }
        
        if(changeImage++ == 10)
        {   
            currentImage = (currentImage+1)%4;
            setImage(imgsH[currentImage]);
            changeImage = 0;
        }
    }
    
    private void setPxPy()
    {
        switch(hcase)
        {
            case 1: px++; py++; break;
            case 2: px++; py--; break;
            case 3: px--; py++; break;
            case 4: px--; py--; break;
            case 5: py++; break;
            case 6: py--; break;
            case 7: px++; break;
            case 8: px--; break;
            default: break;
        }
    }
}
