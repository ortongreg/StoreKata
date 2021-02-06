# Henry's Grocery Kata

[Rules](rules.md)

**Assumption:** Only one deal of each kind can be applied to the order, (e.g. 4 soup 2 bread results in only one bread half off)

> I decided to go the route of having dynamic items and discounts instead of concrete classes.  Also, not having specific unit tests around the discount rules.  I did this because I want to be able to have those discounts be able to be dynamic going forward and having concrete classes did not fit with that.
> 
> I would definitely think this through a bit more and discuss with the team for a prod app, but I wanted to explore the idea here.
> 
> The important thing to note is that the Discount implementation is abstracted and decoupled from outside the DiscountRepository.  So if there were any issues with the "dynamic" implementation I believe the scope of change is limited.

### Test Doubles
Commits introducing a type of Test Double are called out by starting with TEST DOUBLE - {TYPE}

## Build tools
I used:
 - Gradle v 6.7
 - openjdk-14 (java version 14.0.1)

## To build and run tests
```
gradle build
```

## To run after build
Provide the input string as an argument to the call in the same format as the instructions provided similar to:

{ a | number } { unit } of { product }( , | and ) bought ( 'today | in # days time' ) 

example:
```
java -jar build/libs/StoreKata-1.0-SNAPSHOT.jar '3 tins of soup and 2 loaves of bread, bought today'
```