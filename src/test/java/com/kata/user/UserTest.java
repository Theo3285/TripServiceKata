package com.kata.user;

import com.kata.trip.UserBuilder;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

/**
 * Created by christophe on 03/11/2017.
 *
 * Tips : Start writing negative test form as it is usually simpler to implement.
 * Start with an assertion that calls the method the way it is intended to be called:
 * user.isFriendWith(User aUser)
 */
public class UserTest {

    private static final User BOB = new User();
    private static final User PAUL = new User();

    @Test
    public void should_inform_when_users_are_not_be_friends() {
        User friend = UserBuilder.aUser()
                .friendsWith(BOB)
                .build();

        assertThat(friend.isFriendWith(PAUL),is(false));
    }

    @Test
    public void should_inform_when_users_are_friends() {
        User friend = UserBuilder.aUser()
                .friendsWith(BOB, PAUL)
                .build();

        assertThat(friend.isFriendWith(PAUL), is(true));

    }
}
