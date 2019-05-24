package State;

import java.awt.Graphics;

import entity.Player;
import game.Game;
import tiles.Tile;

public class GameState extends State{

	private Player player;
	
	public GameState(Game game) {
		super(game);
		player = new Player(game,100,100);
	}
	
	@Override
	public void tick() {
		player.tick();
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		player.render(g);
	}

}
