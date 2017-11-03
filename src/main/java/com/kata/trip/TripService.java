package com.kata.trip;

import java.util.ArrayList;
import java.util.List;

import com.kata.exception.UserNotLoggedInException;
import com.kata.user.User;
import com.kata.user.UserSession;

/**
 * Refactoring : Start with the deepest branch. Look for feature envy.
 *
 * The class is iterating through the user collection to retrieve friends in order
 * to get the trips. Instead of searching for friend itself, the TripService class
 * should ask the User : User, are you friend with this user ?
 */
public class TripService {

	public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
		List<Trip> tripList = new ArrayList<Trip>();
		User loggedInUser = getLoggedInUser();

		if (loggedInUser != null) {
			boolean isFriend = false;
			isFriend = user.isFriendWith(loggedInUser);
			if (isFriend) {
				tripList = getTripsBy(user);
			}
			return tripList;
		} else {
			throw new UserNotLoggedInException();
		}
	}

	protected List<Trip> getTripsBy(User user) {
		return TripDAO.findTripsByUser(user);
	}

	protected User getLoggedInUser() {
		return UserSession.getInstance().getLoggedUser();
	}

}
