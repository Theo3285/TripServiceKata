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

 ## Clean Test Class
 
 Then do a little clean up in the test class by removing duplicates
 
 Introduce a BuilderPattern for Friends and Trips.
 
 Tip: Write the code the way you want to read it and implement methods from there.
 
 Tip: There is always only three numbers in programming: 0, One or Many. Use varargs arbitrary
 parameters - eg. method(Type... arg) - to pass an array of parameters.
 
 Tip: Always good practice to combine method name with parameter - eg. addTripsTo(user) or
 addFriendsTo(user). It always makes it better to read and avoid duplications in names.
 
 ## Refactoring
 
 When refactoring, unlike when writing unit tests, start with the deepest branch. In real 
 legacy code, there are lots of dependencies and variables. Normally, the deepest branch 
 has all the data prepared before hence it makes it easier to start with.
 
 Look for feature envy:
 The TripService class is iterating through the user collection to retrieve friends in order
 to get the trips. Instead of searching for friend itself, the TripService class
 should ask the User : User, are you friend with this user ?
 
 Add a UserTest class.
 
 Tips : Start writing negative test form as it is usually simpler to implement.
 
 Start with an assertion that calls the method the way it is intended to be called:
 user.isFriendWith(User aUser)
 
 Tips: When refactoring, stay in the green for as long as possible. Do small steps and allow 
 yourself to do ctrl+z or git reset hard to get back to green
 
 Tip: in legacy code, variable can be declared all over the place. Try to bring them together 
 where it is used.
 
 Tip: some namings may be wrong, try to rename as soon as you figure out what they are. 
 i.e. LoggedUser -> LoggedInUser
 
 Tip: Try to get rid of the variables when refactoring. The less variables there is, the easier
 it is to refactor.
 
 ## Design Problem
 
 Design Problem: The service has a dependency with the User session and is
 responsible for getting the logged-in user. It is better to pass the logged-in user
 as parameter to the service and change the getTripByUser method signature. When
 doing so, all the client classes will need to change their method call so be careful
 when refactoring real legacy production code.
 
 Once done, remove the getLoggedInUser() method calls and use the parameter instead.
 In the TripServiceTest class, do the same in the TestableTripService extension and replace
 the loggedInUser variable with the Constants. Finally, remove the getLoggedInUser() methods
 from both the TripService class and extension.