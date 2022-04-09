import java.io.*;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

public class View {
	
	//Declare components and parameters
    private JFrame frame;
    private JPanel mainPanel;

	private int winPosX = 300;
	private int winPosY = 100;

	private JButton[][] allyField;
	private JButton[][] enemyField;
	private boolean[][] ships;
	private int shipCount = 0;
	static public int totalShips = 0;
	private static int player = 0;

    public View() {
		
		++player;
		if (player == 2) winPosY += 440;
		else if (player > 2) return;
		
        //Construct components
        frame = new JFrame();
        mainPanel = new JPanel();
		allyField = new JButton[10][10];
		enemyField = new JButton[10][10];
		ships = new boolean[10][10];

        //Configure mainPanel
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.white);
        mainPanel.setBounds(0, 0, 795, 640);
		
		//Setup Game field
		int xpos = 40, ypos = 38;
		
		for (int x = 0; x != 10; ++x) {
			
			for (int y = 0; y != 10; ++y) {
				
				int xx = x;
				int yy = y;

				JButton allyCell = new JButton();
				JButton enemyCell = new JButton();
				
				allyCell.setBounds(xpos, ypos, 30, 30);
				allyCell.setFocusable(false);
				allyCell.addActionListener(e -> {

					ships[xx][yy] = true;
					allyField[xx][yy].setBackground(Color.green);
					allyField[xx][yy].setEnabled(false);

					shipCount += 1;
					totalShips += 1;

					if (shipCount == 25) {

						for (int i = 0; i != 10; ++i) {
			
							for (int j = 0; j != 10; ++j) {
								
								int ii = i;
								int jj = j;

								allyField[ii][jj].setEnabled(false);

							}

						}

					}

				});
				
				enemyCell.setBounds(xpos + 380, ypos, 30, 30);
				enemyCell.setFocusable(false);
				enemyCell.setEnabled(false);
				
				mainPanel.add(allyCell);
				mainPanel.add(enemyCell);
				
				allyField[x][y] = allyCell;
				enemyField[x][y] = enemyCell;
				
				xpos += 32;
				
			}
			
			ypos += 32;
			xpos = 40;
			
		}

        //Configure frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setTitle("Battleships: Player " + player);
        frame.setBounds(winPosX, winPosY, 795, 440);
        frame.setVisible(true);

        //Add panel to the frame
        frame.add(mainPanel);

    }
	
	public JButton[][] get_ally_field() { return allyField; }
	public JButton[][] get_enemy_field() { return enemyField; }
	public boolean[][] get_ship_map() { return ships; }
	int get_ship_count() { return shipCount; }
	
}