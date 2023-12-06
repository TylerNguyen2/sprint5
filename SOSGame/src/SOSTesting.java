import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SOSTesting {

    private SOSGame game;

    @Before
    public void setUp() {
        game = new SOSGame(3);
    }

    @Test
    public void testInitialBoardSize() {
        Assert.assertEquals(3, game.getTotalRows());
    }
//fail
    @Test
    public void testInitialTurn() {
        Assert.assertEquals('B', game.getTurn());
    }

    @Test
    public void testInitialCellState() {
        for (int i = 0; i < game.getTotalRows(); i++) {
            for (int j = 0; j < game.getTotalRows(); j++) {
                Assert.assertEquals(SOSGame.Cell.EMPTY, game.getCell(i, j));
            }
        }
    }

	@Test
	public void testValidOMove() {
		game.makeMove(0, 0, 1);
		Assert.assertEquals(SOSGame.Cell.O, game.getCell(0, 0));
	}
	@Test
	public void testValidSMove() {
		game.makeMove(0, 0, 0);
		Assert.assertEquals(SOSGame.Cell.S, game.getCell(0, 0));
	}

    @Test
    public void testInvalidMoveOutOfBounds() {
        game.makeMove(-1, -1,-1);
        Assert.assertEquals('B', game.getTurn());

        game.makeMove(-4, -4,-4);
        Assert.assertEquals('B', game.getTurn());
    }

	@Test
	public void testInvalidMove() {
	
		game.makeMove(0, 3, 0);
		Assert.assertNull(game.getCell(0, 3));
		
		
		game.makeMove(0, 0, 0);
		game.makeMove(0, 0, 1);
		Assert.assertEquals(SOSGame.Cell.S, game.getCell(0, 0));
	}
    
    
    
    
    
    @Test
    public void testGetCellOutOfBounds() {
        Assert.assertNull(game.getCell(-1, 0));
        Assert.assertNull(game.getCell(0, 4));
        Assert.assertNull(game.getCell(4, 4));
    }
    public void BlueSimpleWin() {
		game.makeMove(0, 0, 0);
		game.makeMove(0, 1, 1);
		game.makeMove(0, 2, 0);
		Assert.assertEquals(game.getGameState(), SOSGame.GameState.BLUE_WON);
	}
	
	@Test
	public void RedSimpleWin() {
		game.makeMove(2, 2, 0);
		game.makeMove(0, 0, 0);
		game.makeMove(0, 1, 1);
		game.makeMove(0, 2, 0);
		Assert.assertEquals(game.getGameState(), SOSGame.GameState.RED_WON);
	}
	
	@Test
	public void SimpleDraw() {
		game.makeMove(0, 0, 0);
		game.makeMove(0, 1, 0);
		game.makeMove(0, 2, 0);
		game.makeMove(1, 0, 0);
		game.makeMove(1, 1, 0);
		game.makeMove(1, 2, 0);
		game.makeMove(2, 0, 0);
		game.makeMove(2, 1, 0);
		game.makeMove(2, 2, 0);
		Assert.assertEquals(game.getGameState(), SOSGame.GameState.DRAW);
	}
	
	//Making sure board size initialized is correct
	@Test
	public void BlueGeneralWin() {
		game.setCurrentGameType(SOSGame.GameType.General);
		game.makeMove(0, 0, 0);
		game.makeMove(0, 1, 1);
		game.makeMove(0, 2, 0);
		game.makeMove(1, 0, 0);
		game.makeMove(1, 1, 0);
		game.makeMove(1, 2, 0);
		game.makeMove(2, 0, 0);
		game.makeMove(2, 1, 0);
		game.makeMove(2, 2, 0);
		Assert.assertEquals(game.getGameState(), SOSGame.GameState.BLUE_WON);
	}
	
	@Test
	public void RedGeneralWin() {
		game.setCurrentGameType(SOSGame.GameType.General);
		game.makeMove(2, 2, 0);
		game.makeMove(0, 0, 0);
		game.makeMove(0, 1, 1);
		game.makeMove(0, 2, 0);
		game.makeMove(1, 0, 0);
		game.makeMove(1, 1, 0);
		game.makeMove(1, 2, 0);
		game.makeMove(2, 0, 0);
		game.makeMove(2, 1, 0);
		Assert.assertEquals(game.getGameState(), SOSGame.GameState.RED_WON);
	}
	
	@Test
	public void GeneralDraw() {
		game.setCurrentGameType(SOSGame.GameType.General);
		game.makeMove(0, 0, 0);
		game.makeMove(0, 1, 0);
		game.makeMove(0, 2, 0);
		game.makeMove(1, 0, 0);
		game.makeMove(1, 1, 0);
		game.makeMove(1, 2, 0);
		game.makeMove(2, 0, 0);
		game.makeMove(2, 1, 0);
		game.makeMove(2, 2, 0);
		Assert.assertEquals(game.getGameState(), SOSGame.GameState.DRAW);
	}
	
	
	
	
	// Check if red can make move
		@Test
		public void checkRedComp() {
			game.makeRandomMove();
			Assert.assertEquals(game.getTurn(), 'R');
		}
		
		//Check if Blue can make a move
		@Test
		public void checkBlueComp() {
			game.makeRandomMove();
			game.makeRandomMove();
			Assert.assertEquals(game.getTurn(), 'B');
		}
		
		//Check if Bot can finish General Game
		@Test
		public void checkCompGen() {
			game.setCurrentGameType(SOSGame.GameType.General);
			while(game.getGameState() == SOSGame.GameState.PLAYING) {
				game.makeRandomMove();
			}
			Assert.assertEquals(game.getNumberOfEmptyCells(), 0);
		}
		
		//Check if Bot can finish simple game
		@Test
		public void checkCompSimple() {
			
			while(game.getGameState() == SOSGame.GameState.PLAYING) {
				game.makeRandomMove();
			}
			
			Assert.assertTrue(game.getGameState() == SOSGame.GameState.DRAW 
					|| game.getGameState() == SOSGame.GameState.RED_WON 
					|| game.getGameState() == SOSGame.GameState.BLUE_WON);
		}
}