import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main
{

	/**
	 * starts the game player and waits for messages from the game master <br>
	 * Command line options: [port]
	 */
	public static void main(String[] args)
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int choice = 0;
		
		do
		{
			System.out.println("Please choose between the following search algorithm");
			System.out.println("Press 1 to enter Breadth First Search");
			System.out.println("Press 2 to enter Depth First Search");
			System.out.println("Press 3 to enter Uniform Cost Search");
			System.out.println("Press 4 to enter AStar Search");
			try
			{
				System.out.print("Waiting for number: ");
				choice = Integer.parseInt(br.readLine());
				if (choice > 4 || choice < 1)
				{
					throw new NumberFormatException();
				}
			} catch (NumberFormatException | IOException nfe)
			{
				System.err.println("Are acepted number between 1 and 4");
			}
		}
		while (choice > 4 || choice < 1);
		
		try
		{
			// TODO: put in your agent here
			Agent agent = new VacuumCleanerAgent(choice);

			int port = 4001;
			if (args.length >= 1)
			{
				port = Integer.parseInt(args[0]);
			}
			GamePlayer gp = new GamePlayer(port, agent);
			gp.waitForExit();
		} catch (Exception ex)
		{
			ex.printStackTrace();
			System.exit(-1);
		}
	}
}
