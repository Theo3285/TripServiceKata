# TripServiceKata

## Objectives

The objective is to write tests for a piece of crappy Java code with no tests and then refactor to make it better. The code has the most common problems that much larger legacy applications have, like Singletons, static calls and feature envy. It also has some design problems. Fixing that is quite hard, mainly when we need to write all the tests before we start the refactoring. Another rule: We cannot change production code if it is not covered by tests but quite often we need to change the production code in order to be able to test it. How to solve this problem?

## Business Requirements

Imagine a social networking website for travellers

* You need to be logged in to see the content
* You need to be a friend to see someone else's trips
