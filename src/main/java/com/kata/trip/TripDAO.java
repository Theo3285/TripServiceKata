package com.kata.trip;

import java.util.List;

import com.kata.trip.Trip;
import com.kata.exception.CollaboratorCallException;
import com.kata.user.User;

/**
 * Don't remove static signature or the entire code base would need to be changed.
 * Instead, add an instance method that executes the static method. once
 * the entire code base is changed, move the contant of the static method
 * into the instance method and remove the static method. Every new code change
 * that requires the DAO will have to invoke the instance method.
 */
public class TripDAO {

	public static List<Trip> findTripsByUser(User user) {
		throw new CollaboratorCallException(
				"TripDAO should not be invoked on an unit test.");
	}


	public List<Trip> tripsBy(User user) {
		return TripDAO.findTripsByUser(user);
	}
}