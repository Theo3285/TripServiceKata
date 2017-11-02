# TripServiceKata

## Objectives

The objective is to write tests for a piece of crappy Java code with no tests and then refactor to make it better. The code has the most common problems that much larger legacy applications have, like Singletons, static calls and feature envy. It also has some design problems. Fixing that is quite hard, mainly when we need to write all the tests before we start the refactoring. Another rule: We cannot change production code if it is not covered by tests but quite often we need to change the production code in order to be able to test it. How to solve this problem?

## Business Requirements

Imagine a social networking website for travellers

* You need to be logged in to see the content
* You need to be a friend to see someone else's trips

## Write Unit Test before Refactoring
 Start writing unit test for the shortest branch first and then the next deepest branch.
 Understand short and deep in terms of behavior and logic. The less dependencies, data,
 variables to enter a branch, the shortest it is. In this case:
 1. to enter the retrieval of trips for a friend, the user must be logged in. The
    shortest branch is the behavior when the user is not logged in, ie. throw an exception
    The problem is that the retrieval is done through a dependency (Singleton in this 
    case). The test must be free of any dependencies (other classes, db, services, etc).
    To fix that, we extract the dependency in a method and override the method in our
    test class (ie. getLoggedInUser() in the TestableTripService private class).
 2. The second shortest branch is when the user is logged in, we get the list of all
    friends. if a friend is found in the list of the logged in user, a flag is initialized
    to true.
 3. That flag is used to enter the the third shortest (and deepest) branch that will get
    the list of all trips for the friend. That branch has a dependency with a DAO. We apply
    here a second extract method and override it in our test class.

 Then do a little clean up in the test class by removing duplicates