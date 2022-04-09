import java.io.*;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JOptionPane;

class Game {
	
	JButton[][] player1AllyField;
	JButton[][] player1EnemyField;
	JButton[][] player2AllyField;
	JButton[][] player2EnemyField;
	
	boolean player1ships[][] = new boolean[10][10];
	boolean player2ships[][] = new boolean[10][10];

	int player1ShipCount = 25;
	int player2ShipCount = 25;

	Game(View player1, View player2) {
		
		player1AllyField = player1.get_ally_field();
		player1EnemyField = player1.get_enemy_field();
		player2AllyField = player2.get_ally_field();
		player2EnemyField = player2.get_enemy_field();

		player1ships = player1.get_ship_map();
		player2ships = player2.get_ship_map();

		JOptionPane.showMessageDialog(null, "Players, position your ships");
		
		while (player1.totalShips != 50) { System.out.println(" "); }

		assign_battle_logic();

	}
	
	void assign_battle_logic() {

		for (int x = 0; x != 10; ++x) {
			
			for (int y = 0; y != 10; ++y) {
				
				int xx = x;
				int yy = y;

				player1AllyField[xx][yy].setEnabled(false);
				player1EnemyField[xx][yy].setEnabled(true);

				player2AllyField[xx][yy].setEnabled(false);
				player2EnemyField[xx][yy].setEnabled(true);

				player1EnemyField[x][y].addActionListener(e -> { 
					
					if (player2ships[xx][yy] == true ) {
						
						player2AllyField[xx][yy].setBackground(Color.red); 
						player1EnemyField[xx][yy].setBackground(Color.red); 
						player1EnemyField[xx][yy].setEnabled(false);
						player2ships[xx][yy] = false;

						--player2ShipCount;
						if (player2ShipCount == 0) {

							JOptionPane.showMessageDialog(null, "Player 1 Wins!");
							System.exit(0);
						}

					}
					else {

						player2AllyField[xx][yy].setBackground(Color.black); 
						player1EnemyField[xx][yy].setBackground(Color.black); 
						player1EnemyField[xx][yy].setEnabled(false);

					}
					
				
				});

				player2EnemyField[x][y].addActionListener(e -> { 
					
					if (player1ships[xx][yy] == true ) {
						
						player1AllyField[xx][yy].setBackground(Color.red); 
						player2EnemyField[xx][yy].setBackground(Color.red); 
						player2EnemyField[xx][yy].setEnabled(false);
						player1ships[xx][yy] = false;

						--player1ShipCount;
						if (player1ShipCount == 0) {

							JOptionPane.showMessageDialog(null, "Player 2 Wins!");
							System.exit(0);
						}

					}
					else {

						player1AllyField[xx][yy].setBackground(Color.black); 
						player2EnemyField[xx][yy].setBackground(Color.black); 
						player2EnemyField[xx][yy].setEnabled(false);

					}
				
				});
				
			}
			
		}

	}
	
}