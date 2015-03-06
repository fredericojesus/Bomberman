import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Button extends Actor
{
    private int image;
    private int position;
    private int level;
    private boolean mouseOn;
    
    public Button(int i, int p, int l)
    {
        image = i;
        position = p;
        level = l;
        mouseOn = false;
        
        switch(image)
        {
            case 1: setImage("singleplayer1.png"); break;
            case 2: setImage("multiplayer1.png"); break;
            case 3: setImage("help1.png"); break;
            case 4: setImage("quit1.png"); break;
            case 5: setImage("playagainB1.png"); break;
            case 6: setImage("menuB1.png"); break;
            case 7: setImage("playagainO1.png"); break;
            case 8: setImage("menuO1.png"); break;
            case 9: setImage("back1.png"); break;
            default: break;
        }
    }
    
    public void act() 
    {
        mouseAction();
        changeImage();
    }    
    
    /** function that will detect if the mouse is on a button position in the map
     *  and if the mouse is clicked on that position, it changes level
     */
    public void mouseAction()
    {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if(mouse != null)
        {
            //detects position 0, example: singleplayer
            if(mouse.getX() > 263 && mouse.getX() < 437 && mouse.getY() > 275 && mouse.getY() < 325 && position == 0)
                mouseOn = true;
            //detects position 1, example: multiplayer
            else if(mouse.getX() > 263 && mouse.getX() < 437 && mouse.getY() > 325 && mouse.getY() < 375 && position == 1)
                mouseOn = true;
            //detects position 2, example: help
            else if(mouse.getX() > 263 && mouse.getX() < 437 && mouse.getY() > 375 && mouse.getY() < 425 && position == 2)
                mouseOn = true;
            //detects position 3, example: quit
            else if(mouse.getX() > 263 && mouse.getX() < 437 && mouse.getY() > 425 && mouse.getY() < 475 && position == 3)
            {
                mouseOn = true;
                if(Greenfoot.mouseClicked(this))
                {
                    ((BomberWorld)getWorld()).end();
                    return;
                }
            }
            //detects position 4, example: playagain of multiplayer mode
            else if(mouse.getX() > 138 && mouse.getX() < 312 && mouse.getY() > 375 && mouse.getY() < 425 && position == 4)
                mouseOn = true;
            //detects position 5, example: menu of multiplayer mode
            else if(mouse.getX() > 138 && mouse.getX() < 312 && mouse.getY() > 425 && mouse.getY() < 475 && position == 5)
                mouseOn = true;
            //detects position 6, example: back
            else if(mouse.getX() > 13 && mouse.getX() < 187 && mouse.getY() > 475 && mouse.getY() < 525 && position == 6)
                mouseOn = true;
            else
                mouseOn = false;
                
            if(Greenfoot.mouseClicked(this))
                ((BomberWorld)getWorld()).changeLevel(level);
        }
    }
    
    public void changeImage()
    {
        if(mouseOn && image == 1)
            setImage("singleplayer2.png");
        else if(!mouseOn && image == 1)
            setImage("singleplayer1.png");
        else if(mouseOn && image == 2)
            setImage("multiplayer2.png");
        else if(!mouseOn && image == 2)
            setImage("multiplayer1.png");
        else if(mouseOn && image == 3)
            setImage("help2.png");
        else if(!mouseOn && image == 3)
            setImage("help1.png");
        else if(mouseOn && image == 4)
            setImage("quit2.png");
        else if(!mouseOn && image == 4)
            setImage("quit1.png");
        else if(mouseOn && image == 5)
            setImage("playagainB2.png");
        else if(!mouseOn && image == 5)
            setImage("playagainB1.png");
        else if(mouseOn && image == 6)
            setImage("menuB2.png");
        else if(!mouseOn && image == 6)
            setImage("menuB1.png");
        else if(mouseOn && image == 7)
            setImage("playagainO2.png");
        else if(!mouseOn && image == 7)
            setImage("playagainO1.png");
        else if(mouseOn && image == 8)
            setImage("menuO2.png");
        else if(!mouseOn && image == 8)
            setImage("menuO1.png");
        else if(mouseOn && image == 9)
            setImage("back2.png");
        else //if(!mouseOn && image == 9)
            setImage("back1.png");
    }
}
