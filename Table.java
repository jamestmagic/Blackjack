import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Table extends JPanel implements ActionListener
{
	private ArrayList<Card> deck;
	private ArrayList<Card> playerCards;
	private ArrayList<Card> dealerCards;
	//private Win[] confetti;
	private int userIndex;
	private JButton hitButton;
	private JButton standButton;
	private JButton newgameButton;
	private int totalPlayerValue;
	private int totalDealerValue;
	private int points;
	private boolean finish;
	private int winnings;
	private int playerScore;
	private int dealerScore;
	private Color white;
	private int x;
	private int y;
	private int x2;
	private int y2;
	private boolean seeDealer;
	private boolean over21;
	private boolean equal21;
	private boolean win;
	private boolean lose;
	private boolean tie;
	private boolean dealerBust;
	private boolean playerBust;
	
	public Table()
	{
		deck = new ArrayList<Card>();
		deck.add(new Card(2, "2", "hearts"));
		deck.add(new Card(3, "3", "hearts"));
		deck.add(new Card(4, "4", "hearts"));
		deck.add(new Card(5, "5", "hearts"));
		deck.add(new Card(6, "6", "hearts"));
		deck.add(new Card(7, "7", "hearts"));
		deck.add(new Card(8, "8", "hearts"));
		deck.add(new Card(9, "9", "hearts"));
		deck.add(new Card(10, "10", "hearts"));
		deck.add(new Card(10, "J", "hearts"));
		deck.add(new Card(10, "Q", "hearts"));
		deck.add(new Card(10, "K", "hearts"));
		deck.add(new Card(11, "A", "hearts"));
		deck.add(new Card(2, "2", "diamonds"));
		deck.add(new Card(3, "3", "diamonds"));
		deck.add(new Card(4, "4", "diamonds"));
		deck.add(new Card(5, "5", "diamonds"));
		deck.add(new Card(6, "6", "diamonds"));
		deck.add(new Card(7, "7", "diamonds"));
		deck.add(new Card(8, "8", "diamonds"));
		deck.add(new Card(9, "9", "diamonds"));
		deck.add(new Card(10, "10", "diamonds"));
		deck.add(new Card(10, "J", "diamonds"));
		deck.add(new Card(10, "Q", "diamonds"));
		deck.add(new Card(10, "K", "diamonds"));
		deck.add(new Card(11, "A", "diamonds"));
		deck.add(new Card(2, "2", "spade"));
		deck.add(new Card(3, "3", "spade"));
		deck.add(new Card(4, "4", "spade"));
		deck.add(new Card(5, "5", "spade"));
		deck.add(new Card(6, "6", "spade"));
		deck.add(new Card(7, "7", "spade"));
		deck.add(new Card(8, "8", "spade"));
		deck.add(new Card(9, "9", "spade"));
		deck.add(new Card(10, "10", "spade"));
		deck.add(new Card(10, "J", "spade"));
		deck.add(new Card(10, "Q", "spade"));
		deck.add(new Card(10, "K", "spade"));
		deck.add(new Card(11, "A", "spade"));
		deck.add(new Card(2, "2", "clubs"));
		deck.add(new Card(3, "3", "clubs"));
		deck.add(new Card(4, "4", "clubs"));
		deck.add(new Card(5, "5", "clubs"));
		deck.add(new Card(6, "6", "clubs"));
		deck.add(new Card(7, "7", "clubs"));
		deck.add(new Card(8, "8", "clubs"));
		deck.add(new Card(9, "9", "clubs"));
		deck.add(new Card(10, "10", "clubs"));
		deck.add(new Card(10, "J", "clubs"));
		deck.add(new Card(10, "Q", "clubs"));
		deck.add(new Card(10, "K", "clubs"));
		deck.add(new Card(11, "A", "clubs"));
		
		shuffle();
		
		x = 20;
		y = 150;
		x2 = 20;
		y2 = 400;
		
		
		
		seeDealer = false;
		over21 = false;
		equal21 = false;
		win = false;
		lose = false;
		tie = false;
		dealerBust = false;
		playerBust = false;
		
		playerCards = new ArrayList<Card>();
		playerCards.add(deck.get(0));
		playerCards.add(deck.get(1));
		deck.remove(0);
		deck.remove(0);
		dealerCards = new ArrayList<Card>();
		dealerCards.add(deck.get(0));
		dealerCards.add(deck.get(1));
		deck.remove(0);
		deck.remove(0);
		
		hitButton = new JButton("Hit");
		hitButton.setBounds(10,50,200,30);
		hitButton.addActionListener(this);
		this.add(hitButton);
		
		standButton = new JButton("Stand");
		standButton.setBounds(230,50,200,30);
		standButton.addActionListener(this);
		this.add(standButton);
		
		newgameButton = new JButton("New Game");
		newgameButton.setBounds(250,300,200,30);
		newgameButton.addActionListener(this);
		
		
		this.setFocusable(true); 
		
	}
	
	public Dimension getPreferredSize() 
    {
        //Sets the size of the panel
        return new Dimension(1000,600);
    }
	
	public void paintComponent(Graphics g)
    {   
        super.paintComponent(g);
         
        g.setColor(Color.green);
        g.fillRect(0,0,1000,600);
		
		x = 20;
		y = 100;
		x2 = 20;
		y2 = 350;
		totalPlayerValue = findPlayerValue();
		for(int i = 0;i<playerCards.size();i++)
		{	
			playerCards.get(i).drawMe(g,x,y);
			x += 80;

		}
		
		for(int i = 0;i<dealerCards.size();i++)
		{
			dealerCards.get(i).drawMe(g,x2,y2);
			x2 += 80;
		}
		
		if(seeDealer == false)
		{
			g.setColor(Color.white);
			g.fillRect(100,y2,120,150);
			g.setColor(Color.black);
			g.drawRect(100,y2,120,150);
			g.drawString("Dealer Value: " + dealerCards.get(0).getValue() , 50,550);
		}
		
		g.setColor(Color.black);
		g.drawString("Player Value: " + totalPlayerValue , 50,50);
		if(seeDealer == true)
		{
			g.drawString("Dealer Value: " + findDealerValue() , 50,550);
		}
		
		g.drawString("Player Score: " + playerScore , 600,50);
		g.drawString("Dealer Score: " + dealerScore , 600,550);
		
		
		if(tie == true)
		{
			g.drawString("It's a tie!", 700,300);
		}
		if(lose == true)
		{
			g.drawString("You lost!", 700,300);
		}
		if(win == true)
		{
			g.drawString("You won!", 700,300);
		}
		if(dealerBust == true)
		{
			g.drawString("Dealer Bust!",700,300);
		}
		if(playerBust == true)
		{
			g.drawString("Player Bust!",700,300);
		}
		
	}
	
	public void shuffle()
    {
		for(int i = 0;i<deck.size();i++)
		{
			int j = (int)(Math.random()*deck.size());
			Card temp = deck.get(i);
			deck.set(i,deck.get(j));
			deck.set(j,temp);
		}
    }
	
	public int findPlayerValue()
    {
    	int total = 0;
    	for(int i = 0; i<playerCards.size();i++)
    	{
    		total = total + playerCards.get(i).getValue();
    	}
    	return total;
    }
    
   	public int findDealerValue()
    {
    	int total = 0;
    	for(int i = 0; i<dealerCards.size();i++)
    	{
    		total = total + dealerCards.get(i).getValue();
    	}
    	return total;
    }
	
	public void choice()
	{
		totalPlayerValue = findPlayerValue();
		if(deck.get(0).getValue() + totalPlayerValue > 21 && totalPlayerValue <= 21 && equal21 == false)
		{
			standsound();
		}
			
		if(deck.get(0).getValue() + totalPlayerValue <= 21 && totalPlayerValue <= 21)
		{
			hitsound();
		}
	}
	
	public void actionPerformed(ActionEvent e) 
    {
    	
		if(e.getSource() == hitButton)
		{
			playerCards.add(deck.get(0));
			deck.remove(0);
			totalPlayerValue = findPlayerValue();
			if(totalPlayerValue > 21)
			{
				over21 = true;
			}
			if(totalPlayerValue == 21)
			{
				equal21 = true;
			}
			
			
			choice();
		}
		
		if(e.getSource() == standButton || equal21 == true)
		{
			seeDealer = true;
			totalDealerValue = findDealerValue();
			if(totalDealerValue < 17)
			{
				dealerCards.add(deck.get(0));
				deck.remove(0);
				
			}
			totalDealerValue = findDealerValue();
			if(totalDealerValue < 17)
			{
				dealerCards.add(deck.get(0));
				deck.remove(0);
				
			}
			totalDealerValue = findDealerValue();
			if(totalDealerValue < 17)
			{
				dealerCards.add(deck.get(0));
				deck.remove(0);
				
			}
			totalDealerValue = findDealerValue();
			if(totalDealerValue < 17)
			{
				dealerCards.add(deck.get(0));
				deck.remove(0);
				
			}
			totalDealerValue = findDealerValue();
			totalPlayerValue = findPlayerValue();
			if(totalDealerValue >= 17 && totalDealerValue <=21 && totalPlayerValue >= 17 && totalPlayerValue <=21 && totalDealerValue > totalPlayerValue)
			{
				dealerScore++;
				lose = true;
				losesound();
			}
			if(totalDealerValue >= 17 && totalDealerValue <=21 && totalPlayerValue >= 17 && totalPlayerValue <=21 && totalDealerValue < totalPlayerValue)
			{
				playerScore++;
				win = true;
				winsound();
			}
			if(totalDealerValue >= 17 && totalDealerValue <=21 && totalPlayerValue > 21)
			{
				dealerScore++;
				//lose = true;
				playerBust = true;
				losesound();
			}
			if(totalPlayerValue >= 17 && totalPlayerValue <=21 && totalDealerValue > 21)
			{
				playerScore++;
				dealerBust = true;
				winsound();
			}
			if(totalDealerValue >= 17 && totalDealerValue <=21 && totalPlayerValue < 17)
			{
				dealerScore++;
				lose = true;
				losesound();
			}
			if(totalDealerValue == totalPlayerValue)
			{
				tie = true;
			}
			
			
			removeAll();	
			this.add(newgameButton);
				 
		}
		if(over21 == true)
		{
			dealerScore++;
			removeAll();	
			this.add(newgameButton);
			over21 = false;
			//lose = true;
			playerBust = true;
			losesound();
		}
		if(e.getSource() == newgameButton)
		{
			for(int i = 0; i<playerCards.size(); i++)
			{
				deck.add(playerCards.get(i));
			}
			for(int i = 0; i<dealerCards.size(); i++)
			{
				deck.add(dealerCards.get(i));
			}
			
			playerCards.clear();
			dealerCards.clear();
			shuffle();
			removeAll();
			
			this.add(standButton);
			this.add(hitButton);
			seeDealer = false;
			over21 = false;
			equal21 = false;
			playerCards.add(deck.get(0));
			playerCards.add(deck.get(1));
			deck.remove(0);
			deck.remove(0);
			dealerCards.add(deck.get(0));
			dealerCards.add(deck.get(1));
			deck.remove(0);
			deck.remove(0);
			x = 20;
			y = 150;
			x2 = 20;
			y2 = 400;
			totalDealerValue = findDealerValue();
			totalPlayerValue = findPlayerValue();
			
			win = false;
			lose = false;
			tie = false;
			dealerBust = false;
			playerBust = false;
			
			choice();
		}
		
		
		
		repaint();
	}
	
	
	public void winsound()
	{
		  try
 
        {
 
            URL url = this.getClass().getClassLoader().getResource("sounds/win.wav");
			
 
            Clip clip = AudioSystem.getClip();
 
            clip.open(AudioSystem.getAudioInputStream(url));
 
            clip.start();
 
        }
 
        catch (Exception exc)
 
        {
 
            exc.printStackTrace(System.out);
 
        }
	}
	
	public void losesound()
	{
		
		  try
 
        {
 
            URL url = this.getClass().getClassLoader().getResource("sounds/lose.wav");
			
 
            Clip clip = AudioSystem.getClip();
 
            clip.open(AudioSystem.getAudioInputStream(url));
 		
            clip.start();
 
        }
 
        catch (Exception exc)
 
        {
 
            exc.printStackTrace(System.out);
 
        }
	}
	public void hitsound()
	{
		  try
 
        {
 
            URL url = this.getClass().getClassLoader().getResource("sounds/hit.wav");
			
 
            Clip clip = AudioSystem.getClip();
 
            clip.open(AudioSystem.getAudioInputStream(url));
 
            clip.start();
 
        }
 
        catch (Exception exc)
 
        {
 
            exc.printStackTrace(System.out);
 
        }
	}
	public void standsound()
	{
		  try
 
        {
 
            URL url = this.getClass().getClassLoader().getResource("sounds/stand.wav");
			
 
            Clip clip = AudioSystem.getClip();
 
            clip.open(AudioSystem.getAudioInputStream(url));
 
            clip.start();
 
        }
 
        catch (Exception exc)
 
        {
 
            exc.printStackTrace(System.out);
 
        }
	}
}








