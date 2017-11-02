package com.kata.trip;

import java.util.ArrayList;
import java.util.List;

import com.kata.exception.UserNotLoggedInException;
import com.kata.user.User;
import com.kata.user.UserSession;

public class TripService {

	public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
		List<Trip> tripList = new ArrayList<Trip>();
		User loggedUser = getLoggedInUser();
		boolean isFriend = false;
		if (loggedUser != null) {
			for (User friend : user.getFriends()) {
				if (friend.equals(loggedUser)) {
					isFriend = true;
					break;
				}
			}
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
