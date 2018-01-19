import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class MyTests {

	// check if the robot can turn off if it is active, in home location
	// and there is no dirt around the grid
	@Test
	void robotCanTurnOff() {
		Coordinate locationOfTheRobot = new Coordinate(0, 0);
		//Environment.homeLocation = locationOfTheRobot;
		Environment.homeLocation = new Coordinate(0, 0);

		boolean statusOfTheRobot = true;
		List<Coordinate> locationOfDirts = new ArrayList<Coordinate>();
		
		EnvironmentState es = new EnvironmentState(locationOfTheRobot,
				Orientation.NORTH, locationOfDirts, statusOfTheRobot);

        // assert statements
		assertTrue(es.canTurnOff());
	}

	@Test
	void robotCantTurnOffifNotHomeLocation() {
		Coordinate locationOfTheRobot = new Coordinate(0, 0);
		//Environment.homeLocation = locationOfTheRobot;
		Environment.homeLocation = new Coordinate(1, 1);

		boolean statusOfTheRobot = true;
		List<Coordinate> locationOfDirts = new ArrayList<Coordinate>();
		
		EnvironmentState es = new EnvironmentState(locationOfTheRobot,
				Orientation.NORTH, locationOfDirts, statusOfTheRobot);

        // assert statements
		assertFalse(es.canTurnOff());
	}

	@Test
	void robotCantTurnOffifThereIsDirtLeft() {
		Coordinate locationOfTheRobot = new Coordinate(0, 0);
		//Environment.homeLocation = locationOfTheRobot;
		Environment.homeLocation = new Coordinate(0, 0);

		boolean statusOfTheRobot = true;
		List<Coordinate> locationOfDirts = new ArrayList<Coordinate>();
		locationOfDirts.add(new Coordinate(4, 2));
		
		EnvironmentState es = new EnvironmentState(locationOfTheRobot,
				Orientation.NORTH, locationOfDirts, statusOfTheRobot);

        // assert statements
		assertFalse(es.canTurnOff());
	}
}
