import javax.swing.*;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;
import java.text.*;

public class DoND extends JFrame implements ActionListener 
{

	private JLabel lblMessage, lblPlayersCase;
	private JLabel[] lblMoney;
	private JButton[][] btnCases;
	private String[] moneyValue, verbalCases;
	private String secretCaseAmount, secretCaseNumber, lastCaseAmount, lastCaseNumber;
	private int[] moneyChecker;
	private int moneySetter, roundNumber, cases, caseAmount, casesSelected, casesRemaining, counter;
	private double bankersOffer, totalMoney;
	private Random rnd;
	private DecimalFormat fm, fm1;
	private boolean firstCase;
	private Object[] options;

	public static void main(String[] args) {

		new DoND();
	}

	public DoND() 
	{

		//Declare randomizer and decimal formats. Declare certain integer values at the start of the program.
		rnd = new Random();
		fm = new DecimalFormat("$,###.##"); //Decimal format generally for the case values. No definite rounding.
		fm1 = new DecimalFormat("$,###.00"); //Decimal format for the calculations. Rounded to the nearest tenth of a decimal
		caseAmount = 6;
		cases = caseAmount;
		casesRemaining = 26;
		roundNumber = 1;

		//Declare 1D arrays, 2D arrays, and ragged arrays.
		lblMoney = new JLabel[26];
		btnCases = new JButton[5][];
		options = new Object[]{"DEAL!", "NO DEAL!"}; //Array for placing your own values on certain JOptionPane dialogs.
		verbalCases = new String[]{"one", "two", "three", "four", "five", "six"}; //Array for lblMessage text
		moneyValue = new String[]{"0.01", "1", "5", "10", "25", "50", "75", "100", "200", "300", "400", "500", "750", "1000", "5000", "10000", "25000",
				"50000", "75000", "100000", "200000", "300000", "400000", "500000", "750000", "1000000"};
		moneyChecker = new int[moneyValue.length];

		//Add all the money values to get a definite total
		for (int i = 0; i < moneyValue.length; i++)
		{
			totalMoney += Double.parseDouble(moneyValue[i]);
		}

		//Give JButton row 0 to 3 to 5 columns
		for (int i = 0; i < btnCases.length - 1; i++)
		{
			btnCases[i] = new JButton[5];
		}

		//JButton row 4 will have 6 columns
		btnCases[4] = new JButton[6];

		//Declare JLabel and set preferences (picture, size, centring, etc)
		JLabel lblTitle = new JLabel();
		lblTitle.setIcon(new ImageIcon("images\\dond_logo.png"));
		lblTitle.setPreferredSize(new Dimension(660, 90));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

		//Declare North, East, South, West, and Centre panels for the mainPanel which has a BorderLayout, set preferences
		JPanel northPanel = new JPanel();
		northPanel.setBackground(Color.BLACK);
		northPanel.add(lblTitle);

		JPanel westPanel = new JPanel();
		westPanel.setLayout(new GridLayout(13, 1, 10, 0));
		westPanel.setBackground(Color.BLACK);

		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new GridLayout(13, 1, 10, 0));
		eastPanel.setBackground(Color.BLACK);

		/* Declare 1D array of JLabels and set the picture to the array string at the index of the counter.
		 * Also set the name of the value to the JLabel at its index to be stored for later use.
		 * Include it into a double for-loop that checks all rows at a certain column index. If the counter is less than
		 * or equal to 12, add the JLabels to the west panel. When it is greater than 12, add it to the east panel
		 */

		for (int i = 0; i < lblMoney.length; i++)
		{
			lblMoney[i] = new JLabel(new ImageIcon("images\\money\\" + moneyValue[counter] + ".png"));
			lblMoney[i].setName(moneyValue[i]);

			if (counter <= 12)
			{
				westPanel.add(lblMoney[i]);
			}
			else
			{
				eastPanel.add(lblMoney[i]);
			}
			counter++;
		}

		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.BLACK);

		/* Declare 2D ragged array of JButtons. Use a double for loop for all columns at a certain row index.
		 * Set the JButton width to 70 so that with a FlowLayout, max amount of JButtons per row is 5. With the 
		 * declaration of a timer, do this until the timer is greater than or equal to 20, then reduce the width
		 * size of the JButtons to 65 to the max amount of JButtons on a row is 6. Set preferences for each JButton and use
		 * the same counter to set the ImageIcon. Set the actionCommand to the counter + 1 to get the case numbers.
		 * 
		 */

		counter = 0;
		int width = 70;

		for (int i = 0; i < btnCases.length; i++)
		{
			for (int j = 0; j < btnCases[i].length; j++)
			{
				btnCases[i][j] = new JButton();

				btnCases[i][j].setPreferredSize(new Dimension(width, 62));
				btnCases[i][j].setBackground(Color.BLACK);
				btnCases[i][j].setFocusable(false);
				btnCases[i][j].setContentAreaFilled(false);
				btnCases[i][j].setBorder(BorderFactory.createEmptyBorder());
				btnCases[i][j].addActionListener(this);
				btnCases[i][j].setActionCommand("" + (counter + 1));
				btnCases[i][j].setIcon(new ImageIcon("images\\suitcases\\case" + (counter + 1) + ".png"));

				/* Using a randomizer, randomize a number as big as the length of the moneyValue array (26). Set the randomized number
				 * to the array counter and check if that number is equal to one in a while loop. As long as the counter in the array is
				 * 1, re-randomize. Otherwise, add 1 to the counter and set the name of the JButton to the array of money values at the
				 * randomized index. This will give all JButtons a random money value
				 */

				do
				{
					moneySetter = rnd.nextInt(moneyValue.length);
				} 
				while (moneyChecker[moneySetter] == 1);

				moneyChecker[moneySetter]++;

				btnCases[i][j].setName(moneyValue[moneySetter]);

				centerPanel.add(btnCases[i][j]);

				counter++;

				if (counter >= 20) // After 20 JButtons have been assigned, change the width to be small enough for 6 buttons per row
				{
					width = 65;
				}
			}
		}

		//Declare and set preferences for all JLabels
		lblMessage = new JLabel();
		lblMessage.setPreferredSize(new Dimension(430, 50));
		lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessage.setFont(new Font("Arial", Font.BOLD, 18));
		lblMessage.setForeground(new Color(252, 234, 151));
		lblMessage.setVerticalAlignment(SwingConstants.TOP);
		lblMessage.setText("Choose one of the briefcases!");

		lblPlayersCase = new JLabel();
		lblPlayersCase.setPreferredSize(new Dimension(100, 50));
		lblPlayersCase.setHorizontalAlignment(SwingConstants.CENTER);

		//Declare/set preferences for remaining sub-panels
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		southPanel.setBackground(Color.BLACK);
		southPanel.add(lblPlayersCase);
		southPanel.add(lblMessage);
		southPanel.add(Box.createRigidArea(new Dimension(100, 50)));

		//Add all sub-panels to mainPanel, which has a BorderLayout
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBackground(Color.BLACK);
		mainPanel.add(northPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(eastPanel, BorderLayout.EAST);
		mainPanel.add(westPanel, BorderLayout.WEST);
		mainPanel.add(southPanel, BorderLayout.SOUTH);

		//Add preferences to JFrame. Add mainPanel to JFrame.
		setContentPane(mainPanel);
		setSize(680, 550);
		setTitle("Deal or No Deal");
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		Image icon = Toolkit.getDefaultToolkit().getImage("images//logo.jpg"); // Set the frame icon
		setIconImage(icon);
		addWindowListener(new CloseListener());
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) 
	{
		JButton btnCase = (JButton) e.getSource(); //To determine which JButton was clicked, convert all Components with an ActionListener
		                                          // to a JButton
		
		/* The first case clicked will be the secret case you keep until the end of the game. Use a boolean to differentiate between
		 * the first case clicked and the cases proceeding after this. Store the JButton's getName and getActionCommand values to use
		 * at the end of the game when telling the user what he could have gone home with should he/she take the DEAL. Set the clicked JButton
		 * to null to make it appear as if it has disappeared and prevent the user from clicking it again. Change the bottom text to tell 
		 * the user to pick a certain amount of cases. Change the PlayerCase icon to the picture of the case picked with the stored 
		 * name value. Set the boolean to true to ignore this if statement for the rest of the program.
		 */
		if (firstCase == false)
		{
			secretCaseAmount = btnCase.getName();
			secretCaseNumber = btnCase.getActionCommand();

			btnCase.setIcon(null);
			btnCase.setEnabled(false);

			lblPlayersCase.setIcon(new ImageIcon("images\\suitcases\\case" + secretCaseNumber + ".png"));
			lblMessage.setText("Open " + verbalCases[cases - 1] + " (" + (cases) + ") briefcase(s)!");

			firstCase = true;
		}

		/* For the rest of the briefcases, each click will make the clicked briefcase disappear along with its respective money label.
		 * Since all money labels stored their values, if this value is equal to the name value of the button clicked, said money label will
		 * change it's picture to the image called null (an black image is used instead of setting the picture to null because the centerPanel 
		 * is a FlowLayout. A FlowLayout will fit as money JCOmponents it can onto one row. Setting the money JLabel to null will reduce its
		 * width to virtually nothing, allowing the FlowLayout to fit more JComponents in one row. This is not desired, so an image the same
		 * size of the JLabel is used in place of null).
		 */
		else
		{
			btnCase.setIcon(null);
			btnCase.setEnabled(false);

			for (int i = 0; i < lblMoney.length; i++)
			{
				if (lblMoney[i].getName() == btnCase.getName())
				{
					lblMoney[i].setIcon(new ImageIcon("images\\money\\null.png"));
				}
			}

			/* Every brief case selected will continuously add to casesSelected. casesRemaining will continuously subtract 1 for later calculations
			 * of the banker's offer. TotalMoney will always be subtracted by the value of the clicked button's getName value for later 
			 * calculations.
			 */
			casesSelected++;
			casesRemaining--;
			cases--;
			totalMoney -= Double.parseDouble(btnCase.getName());
			
			//JOptionPane to let the user know which case was selected and the money value inside the case.
			JOptionPane.showMessageDialog(null, "Case #" + btnCase.getActionCommand() + " contains " + 
					fm.format(Double.parseDouble(btnCase.getName())) + "!", "Deal or No Deal", 0, 
					new ImageIcon("images\\suitcases\\case" + btnCase.getActionCommand() + ".png"));

			/* When casesSelected is equal to how many cases need to be selected until the next round, the program will calculate the banker's 
			 * offer and ask the user if he/she wants to walk home with the money or continue playing.
			 */

			if (casesSelected == caseAmount)
			{
				bankersOffer = ((totalMoney / casesRemaining) * roundNumber / 10);

				int choice = JOptionPane.showOptionDialog(null, "The banker's offer is " + fm1.format(bankersOffer) +
						"!\nDeal or no deal?", "Deal or No Deal", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options, options[1]); //Object array used for customisable JButton text

				/* If user clicks 'DEAL', the program will relay a JOptionPane message with the banker has offered, along with a message of 
				 * what the user could have won with the money in his/her secret case. The String secretCaseAmount is used again for this
				 * case
				 */
				if (choice == JOptionPane.YES_OPTION)
				{
					JOptionPane.showMessageDialog(null, "Congratulations, you're going home with " + fm1.format(bankersOffer) + "!", 
							"Deal or No Deal", JOptionPane.WARNING_MESSAGE);

					JOptionPane.showMessageDialog(null, "You could have gone home with " + fm.format(Double.parseDouble(secretCaseAmount)) + "!",
							"Deal or No Deal", 0, new ImageIcon("images\\suitcases\\case" + secretCaseNumber + ".png"));

					playAgain(); //Separate class asking the user if they want to play again to frequency of use
				}
				/* If the user clicks 'NO DEAL', the game will continue. The round number will increase, 'caseAmount', being the amount of cases
				 * needed to be opened for the next round, will decrease, and casesSelected will be set back to 0.
				 */
				else
				{
					roundNumber++;
					caseAmount--;
					casesSelected = 0;
					if (caseAmount <= 1)
					{
						caseAmount = 1;
					}
					
					cases = caseAmount; // Integer value of how many cases left needed to be opened for that round

					/* If there are only two cases left in the game (user's secret case and guessing case), the program will check
					 * all JButtons to see which one is enabled (only one JButton should be enabled at this point). When the program find said
					 * JButton, it will store the value's case number and the money value.
					 */
					if (casesRemaining == 2)
					{
						for (int i = 0; i < btnCases.length; i++)
						{
							for (int j = 0; j < btnCases[i].length; j++)
							{
								if (btnCases[i][j].isEnabled())
								{
									lastCaseAmount = btnCases[i][j].getName();
									lastCaseNumber = btnCases[i][j].getActionCommand();
								}
							}
						}

                        //The program asks the user if they want to swap cases before continuing.
						int swap = JOptionPane.showConfirmDialog(null, "There are only two cases left! Would you like to keep your case?", 
								"Deal or No Deal", JOptionPane.YES_NO_OPTION);

						/* If the user does not want to keep their case, the program relays a message of the remaining case value, which it just 
						 * previously retrieved from the remaining JButton. The program then tells the user the money they could have earned from the 
						 * first stored String values of the secret briefcase.
						 */
						if (swap == JOptionPane.NO_OPTION)
						{
							JOptionPane.showMessageDialog(null, "Congratulations, you're going home with " + 
									fm.format(Double.parseDouble(lastCaseAmount)) +  "!", "Deal or No Deal", 0, 
									new ImageIcon("images\\suitcases\\case" + lastCaseNumber + ".png"));

							JOptionPane.showMessageDialog(null, "You could have gone home with " 
									+ fm.format(Double.parseDouble(secretCaseAmount)) + "!", "Deal or No Deal", 0, 
									new ImageIcon("images\\suitcases\\case" + secretCaseNumber + ".png"));
						}

						/* If the user wants to keep their case, the program relays a message of the secret case value, with the secret case's 
						 * number and money value. The program tells the user what he/she could have made with the remaining case in the game.
						 */
						else
						{
							JOptionPane.showMessageDialog(null, "Congratulations, you're going home with " + 
									fm.format(Double.parseDouble(secretCaseAmount)) +  "!", "Deal or No Deal", 0, 
									new ImageIcon("images\\suitcases\\case" + secretCaseNumber + ".png"));

							JOptionPane.showMessageDialog(null, "You could have gone home with " 
									+ fm.format(Double.parseDouble(lastCaseAmount)) + "!", "Deal or No Deal", 0, 
									new ImageIcon("images\\suitcases\\case" + lastCaseNumber + ".png"));
						}

						playAgain(); //Program asks user if he/she wants to play again.
					}
				}
			}
			
			if (firstCase == true)
			{
				//The bottom text will change to the appropriate amount of cases needing to be opened.
				lblMessage.setText("Open " + verbalCases[cases - 1] + " (" + (cases) + ") briefcase(s)!");
			}
		}
	}

	//Make a nested class for the top exit button of the frame without implementing unnecessary methods
	class CloseListener extends WindowAdapter
	{
		public void windowClosing(WindowEvent e)
		{
			//Ask the user if they want to quit in the form of an integer if they click the exit button on the frame
			int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Deal or No Deal", 
					JOptionPane.YES_NO_OPTION, 0, new ImageIcon("images//logo.jpg"));

			// If the the user clicks YES, terminate the program
			if (option == JOptionPane.YES_OPTION)
			{
				exiting();
			}

			/* If the user clicks NO or clicks the exit button on the JOptionPane, doing nothing will bring 
			 * the user back to the main frame
			 */
		}
	};

	//Inner class taking care of thanking the user and exiting the program, for efficiency of code.
	public void exiting()
	{
		JOptionPane.showMessageDialog(null, "Thank you for playing Deal or No Deal!", "Deal or No Deal", 
				JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}
	
	//Inner class asking the user if they want to play again
	public void playAgain()
	{
		int replay = JOptionPane.showConfirmDialog(null, "Would you like to play again?", "Deal or No Deal", 
				JOptionPane.YES_NO_OPTION);
		
		/* If the user clicks YES, set counter and casesSelected to zero. Reset totalMoney to zero so the program can do a
		 * recount of all money (needed to prevent program to adding on top of money still remaining in the program). Reset roundNumber to
		 * one and casesRemaining to 26. caseAmount will be set back to 6 cases needing to be opened in round 1, and cases, the 
		 * amount of cases needed to be opened after ever click will be equal to caseAmount. firstCase will be set back to false so the 
		 * user can re-select their secret case, and the original secret case picture will be set to null. Bottom text will be set back to
		 * asking the user to choose a brief case.
		 */
		if (replay == JOptionPane.YES_OPTION)
		{
			counter = 0;
			roundNumber = 1;
			casesSelected = 0;
			casesRemaining = 26;
			totalMoney = 0;
			caseAmount = 6;
			cases = caseAmount;
			
			firstCase = false;
			lblPlayersCase.setIcon(null);
			
			lblMessage.setText("Choose one of the briefcases!");
			
			/* The array of money counters must all be set back to zero, since the program will re-randomize the values. The totalMoney will
			 * also re-add all the money values to get a total of all the money.
			 */
			for (int i = 0; i < moneyChecker.length; i++)
			{
				moneyChecker[i] = 0;
				totalMoney += Double.parseDouble(moneyValue[i]);
			}
			
			/* JButtons will be re-enabled, and also set back to their respective images. While loop will once again check for multiple 
			 * randoms of the same thing and re-randomize, JButtons will assign the name of the randomized value to themselves, and
			 * the money JLabels will all reappear with their respective images.
			 */
			for (int i = 0; i < btnCases.length; i++)
			{
				for (int j = 0; j < btnCases[i].length; j++)
				{
					btnCases[i][j].setEnabled(true);
					btnCases[i][j].setIcon(new ImageIcon("images\\suitcases\\case" + (counter + 1) + ".png"));
					
					do
					{
						moneySetter = rnd.nextInt(moneyValue.length);
					} 
					while (moneyChecker[moneySetter] == 1);

					moneyChecker[moneySetter]++;

					btnCases[i][j].setName(moneyValue[moneySetter]);
					
					lblMoney[counter].setIcon(new ImageIcon("images\\money\\" + moneyValue[counter] + ".png"));
					
					counter++;
				}
			}
			
		}
		//If the user does not want to play again, the program will initiate the exiting method; thanking the user and terminating
		else
		{
			exiting();
		}
	}
}