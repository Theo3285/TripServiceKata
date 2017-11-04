package com.kata.trip;

import com.kata.exception.UserNotLoggedInException;
import com.kata.user.User;

import org.junit.Before;
import org.junit.Test;


import java.util.List;

import static com.kata.trip.UserBuilder.aUser;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Start writing unit test for the shortest branch first and then the next deepest branch.
 * Understand short and deep in terms of behavior and logic. The less dependencies, data,
 * variables to enter a branch, the shortest it is. In this case:
 * 1. to enter the retrieval of trips for a friend, the user must be logged in. The
 *    shortest branch is the behavior when the user is not logged in, ie. throw an exception
 *    The problem is that the retrieval is done through a dependency (Singleton in this
 *    case). The test must be free of any dependencies (other classes, db, services, etc).
 *    To fix that, we extract the dependency in a method and override the method in our
 *    test class (ie. getLoggedInUser() in the TestableTripService private class).
 * 2. The second shortest branch is when the user is logged in, we get the list of all
 *    friends. if a friend is found in the list of the logged in user, a flag is initialized
 *    to true.
 * 3. That flag is used to enter the the third shortest (and deepest) branch that will get
 *    the list of all trips for the friend. That branch has a dependency with a DAO. We apply
 *    here a second extract method and override it in our test class.
 *
 * Then do a little clean up in the test class by removing duplicates.
 *
 * Introduce a BuilderPattern for Friends and Trips.
 *
 * Tip: Write the code the way you want to read it and implement methods from there.
 *
 * Tip: There is always only three numbers in programming: 0, One or Many. Use varargs arbitrary
 * parameters - eg. method(Type... arg) - to pass an array of parameters.
 *
 * Tip: Always good practice to combine method name with parameter - eg. addTripsTo(user) or
 * addFriendsTo(user). It always makes it better to read and avoid duplications in names.
 */
public class TripServiceTest {

    private static final User GUEST = null; //introduce domain language
    private static final User UNUSED_USER = null; // naming makes test explicit
    private static final User REGISTERED_USER = new User();
    private static final User ANOTHER_USER = new User();
    private static final Trip TO_FRANCE = new Trip();
    private static final Trip TO_LONDON = new Trip();

    private TripService tripService;

    @Before
    public void setUp() {

        tripService = new TestableTripService();
    }

    @Test (expected = UserNotLoggedInException.class)
    public void should_throw_an_exception_when_user_is_not_logged_in() {

        tripService.getTripsByUser(UNUSED_USER, GUEST);

    }

    @Test
    public void should_not_return_trips_when_users_are_not_friends() {

        User friend = aUser()
                .friendsWith(ANOTHER_USER)
                .withTrips(TO_FRANCE)
                .build();

        List<Trip> friendTrips = tripService.getTripsByUser(friend, REGISTERED_USER);

        assertThat(friendTrips.size(), is(0));

    }

    @Test
    public void should_return_trips_when_users_are_friends() {

        User friend = aUser()
                .friendsWith(REGISTERED_USER, ANOTHER_USER)
                .withTrips(TO_FRANCE, TO_LONDON)
                .build();

        List<Trip> friendTrips = tripService.getTripsByUser(friend, REGISTERED_USER);

        assertThat(friendTrips.size(), is(2));

    }

    /*
     * This class overrides dependencies within service class so the
     * class can be tested independently
     */
    private class TestableTripService extends TripService {

        @Override
        protected List<Trip> getTripsBy(User user) {
            return user.trips();
        }

     }

}
