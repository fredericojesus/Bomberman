import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public abstract class DynamicActor extends Actor
{
    protected GreenfootImage [] imgsDown;
    protected GreenfootImage [] imgsUp;
    protected GreenfootImage [] imgsLeft;
    protected GreenfootImage [] imgsRight;
    protected GreenfootImage [] imgsDeath;
    protected int changeImage;
    protected int currentImage;
    protected final int NUMBER_OF_IMAGES;
    
    protected String actor; //defines if it is a player("b") or an enemie("e")
    protected int actorNumber; //defines the number of the players or enemies
    protected int speed;
    protected int timeMove;
    protected boolean underBomb;
    
    protected int lifes;
    
    protected int changeImageDeath;
    protected int currentImageDeath;
    protected boolean die;
    
    public DynamicActor(String a, int an, int n)
    {
        actor = a;
        actorNumber = an;  
        NUMBER_OF_IMAGES = n;
        
        imgsDown = new GreenfootImage[NUMBER_OF_IMAGES];
        imgsUp = new GreenfootImage[NUMBER_OF_IMAGES];
        imgsLeft = new GreenfootImage[NUMBER_OF_IMAGES];
        imgsRight = new GreenfootImage[NUMBER_OF_IMAGES];
        imgsDeath = new GreenfootImage[NUMBER_OF_IMAGES+2];
        changeImage = 0;
        currentImage = 0;
            
        speed = 1;
        timeMove = 1;
        underBomb = false;
        
        changeImageDeath = 0;
        currentImageDeath = 0;
        die = false;
    }
    
    //overloading
    public DynamicActor(String a, int an)
    {
        actor = a;
        actorNumber = 0;
        NUMBER_OF_IMAGES = 16;
        
        imgsRight = new GreenfootImage[NUMBER_OF_IMAGES];
        imgsDeath = new GreenfootImage[7];
        
        speed = 2;
        underBomb = false;
        
        changeImageDeath = 0;
        currentImageDeath = 0;
        die = false;
    }
    
    public void setImage(String direction)
    {
        if(changeImage++ == 10)
        {   
            currentImage = (currentImage+1)%NUMBER_OF_IMAGES;
            
            if(direction == "d")
                setImage(imgsDown[currentImage]);
            else if(direction == "u")
                setImage(imgsUp[currentImage]);
            else if(direction == "l")
                setImage(imgsLeft[currentImage]);
            else if(direction == "r")
                setImage(imgsRight[currentImage]);
                
            changeImage = 0;
        }
    }
    
    protected void underBomb()
    {
        Actor bomb = getOneIntersectingObject(Bomb.class);
        
        if(bomb == null)
            this.underBomb = false;
    }
    
    public void setUnderBomb()
    {
        underBomb = true;
    }
    
    public void setTimeMove(int n)
    {
        timeMove = n;
    }
    
    protected int droppingObjectCoords(int coordXY)
    {
        for(int i = 64; i < 512; i+=32)
        {
            if(coordXY < i)
                return i - 16;
        }
        return 28;
    }
    
    //função que incrementa o atributo speed
    public void setSpeed(int n)
    {
        speed = n;
    }
    
    //funcção que retorna o atributo speed
    public int getSpeed()
    {
        return speed;
    }
}
