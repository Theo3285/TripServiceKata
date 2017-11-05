package com.kata.trip;

import java.util.ArrayList;
import java.util.List;

import com.kata.exception.UserNotLoggedInException;
import com.kata.user.User;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Refactoring : Start with the deepest branch. Look for feature envy.
 *
 * The class is iterating through the user collection to retrieve friends in order
 * to get the trips. Instead of searching for friend itself, the TripService class
 * should ask the User : User, are you friend with this user ?
 *
 * Design Problem: The service has a dependency with the User session and is
 * responsible for getting the logged-in user. It is better to pass the logged-in user
 * as parameter to the service and change the getTripByUser method signature. When
 * doing so, all the client classes will need to change their method call so be careful
 * when refactoring real legacy production code.
 *
 * TripDAO is a static method. It would be better to inject it by providing an instance method.
 * removing the static declare in the Trip
 */
public class TripService {

	@Autowired
	private TripDAO tripDAO;

	public List<Trip> getFriendTrips(User friend, User loggedInUser) throws UserNotLoggedInException {

		validate(loggedInUser);

		return (friend.isFriendWith(loggedInUser))
				? getTripsBy(friend)
				: noTrips();

	}

	private void validate(User loggedInUser) {
		if (loggedInUser == null) {
			throw new UserNotLoggedInException();
		}
	}

	private ArrayList<Trip> noTrips() {
		return new ArrayList<Trip>();
	}

	private List<Trip> getTripsBy(User user) {
		return tripDAO.tripsBy(user);
	}
}
