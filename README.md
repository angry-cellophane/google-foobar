# google-foobar

All solutions written in java 8.

Tests written with [Spock Framework](http://spockframework.org/).

### Level 1

---
### Task 1
#### Description N/A

#### Solution: [L1Solution1.java](src/main/java/com/github/angrycellophane/foobar/l1/L1Solution1.java)
#### Test cases: [L1SolutionTest](src/test/java/com/github/angrycellophane/foobar/l1/L1Solution1Test.groovy)

----

### Level 2

----

### Task 1
####Description:

Commander Lambda loves efficiency and hates anything that wastes time. She's a busy lamb, after all! She generously rewards henchmen who identify sources of inefficiency and come up with ways to remove them. You've spotted one such source, and you think solving it will help you build the reputation you need to get promoted.

Every time the Commander's employees pass each other in the hall, each of them must stop and salute each other - one at a time - before resuming their path. A salute is five seconds long, so each exchange of salutes takes a full ten seconds (Commander Lambda's salute is a bit, er, involved). You think that by removing the salute requirement, you could save several collective hours of employee time per day. But first, you need to show her how bad the problem really is.

Write a program that counts how many salutes are exchanged during a typical walk along a hallway. The hall is represented by a string. For example: "--->-><-><-->-"

Each hallway string will contain three different types of characters: '>', an employee walking to the right; '<', an employee walking to the left; and '-', an empty space. Every employee walks at the same speed either to right or to the left, according to their direction. Whenever two employees cross, each of them salutes the other. They then continue walking until they reach the end, finally leaving the hallway. In the above example, they salute 10 times.

Write a function answer(s) which takes a string representing employees walking along a hallway and returns the number of times the employees will salute. s will contain at least 1 and at most 100 characters, each one of -, >, or <.

#### Solution: [L2Solution1.java](src/main/java/com/github/angrycellophane/foobar/l2/L2Solution1.java)

#### Test cases: [L2Solution1Test](src/test/java/com/github/angrycellophane/foobar/l2/L2Solution1Test.groovy)

----

### Task 2
#### Description:


The LAMBCHOP's engineers have given you lists identifying the placement of groups of pegs along various support beams. You need to place a gear on each peg (otherwise the gears will collide with unoccupied pegs). The engineers have plenty of gears in all different sizes stocked up, so you can choose gears of any size, from a radius of 1 on up. Your goal is to build a system where the last gear rotates at twice the rate (in revolutions per minute, or rpm) of the first gear, no matter the direction. Each gear (except the last) touches and turns the gear on the next peg to the right.

Given a list of distinct positive integers named pegs representing the location of each peg along the support beam, write a function solution(pegs) which, if there is a solution, returns a list of two positive integers a and b representing the numerator and denominator of the first gear's radius in its simplest form in order to achieve the goal above, such that radius = a/b. The ratio a/b should be greater than or equal to 1. Not all support configurations will necessarily be capable of creating the proper rotation ratio, so if the task is impossible, the function solution(pegs) should return the list [-1, -1].

For example, if the pegs are placed at [4, 30, 50], then the first gear could have a radius of 12, the second gear could have a radius of 14, and the last one a radius of 6. Thus, the last gear would rotate twice as fast as the first one. In this case, pegs would be [4, 30, 50] and solution(pegs) should return [12, 1].

The list pegs will be given sorted in ascending order and will contain at least 2 and no more than 20 distinct positive integers, all between 1 and 10000 inclusive.

#### Solution: [L2Solution2.java](src/main/java/com/github/angrycellophane/foobar/l2/L2Solution2.java)

#### Test cases: [L2Solution2Test](src/test/java/com/github/angrycellophane/foobar/l2/L2Solution2Test.groovy)

----

### Level 3

---
### Task 1
#### Description

 Doomsday Fuel
 
 Making fuel for the LAMBCHOP's reactor core is a tricky process because of the exotic matter involved. It starts as raw ore, then during processing, begins randomly changing between forms, eventually reaching a stable form. There may be multiple stable forms that a sample could ultimately reach, not all of which are useful as fuel.
 
 Commander Lambda has tasked you to help the scientists increase fuel creation efficiency by predicting the end state of a given ore sample. You have carefully studied the different structures that the ore can take and which transitions it undergoes. It appears that, while random, the probability of each structure transforming is fixed. That is, each time the ore is in 1 state, it has the same probabilities of entering the next state (which might be the same state).  You have recorded the observed transitions in a matrix. The others in the lab have hypothesized more exotic forms that the ore can become, but you haven't seen all of them.
 
 Write a function solution(m) that takes an array of array of nonnegative ints representing how many times that state has gone to the next state and return an array of ints for each terminal state giving the exact probabilities of each terminal state, represented as the numerator for each state, then the denominator for all of them at the end and in simplest form. The matrix is at most 10 by 10. It is guaranteed that no matter which state the ore is in, there is a path from that state to a terminal state. That is, the processing will always eventually end in a stable state. The ore starts in state 0. The denominator will fit within a signed 32-bit integer during the calculation, as long as the fraction is simplified regularly.
 
 For example, consider the matrix m:
 [
   [0,1,0,0,0,1],  # s0, the initial state, goes to s1 and s5 with equal probability
   [4,0,0,3,2,0],  # s1 can become s0, s3, or s4, but with different probabilities
   [0,0,0,0,0,0],  # s2 is terminal, and unreachable (never observed in practice)
   [0,0,0,0,0,0],  # s3 is terminal
   [0,0,0,0,0,0],  # s4 is terminal
   [0,0,0,0,0,0],  # s5 is terminal
 ]
 So, we can consider different paths to terminal states, such as:
 s0 -> s1 -> s3
 s0 -> s1 -> s0 -> s1 -> s0 -> s1 -> s4
 s0 -> s1 -> s0 -> s5
 Tracing the probabilities of each, we find that
 s2 has probability 0
 s3 has probability 3/14
 s4 has probability 1/7
 s5 has probability 9/14
 So, putting that together, and making a common denominator, gives an answer in the form of
 [s2.numerator, s3.numerator, s4.numerator, s5.numerator, denominator] which is
 [0, 3, 2, 9, 14].

#### Solution: [L3Solution1.java](src/main/java/com/github/angrycellophane/foobar/l3/L3Solution1.java)
#### Test cases: [L3Solution1Test](src/test/java/com/github/angrycellophane/foobar/l3/L3Solution1Test.groovy)

------
### Task 2
#### Description 

Bomb, Baby!

You're so close to destroying the LAMBCHOP doomsday device you can taste it! But in order to do so, you need to deploy special self-replicating bombs designed for you by the brightest scientists on Bunny Planet. There are two types: Mach bombs (M) and Facula bombs (F). The bombs, once released into the LAMBCHOP's inner workings, will automatically deploy to all the strategic points you've identified and destroy them at the same time.

But there's a few catches. First, the bombs self-replicate via one of two distinct processes:
Every Mach bomb retrieves a sync unit from a Facula bomb; for every Mach bomb, a Facula bomb is created;
Every Facula bomb spontaneously creates a Mach bomb.

For example, if you had 3 Mach bombs and 2 Facula bombs, they could either produce 3 Mach bombs and 5 Facula bombs, or 5 Mach bombs and 2 Facula bombs. The replication process can be changed each cycle.

Second, you need to ensure that you have exactly the right number of Mach and Facula bombs to destroy the LAMBCHOP device. Too few, and the device might survive. Too many, and you might overload the mass capacitors and create a singularity at the heart of the space station - not good!

And finally, you were only able to smuggle one of each type of bomb - one Mach, one Facula - aboard the ship when you arrived, so that's all you have to start with. (Thus it may be impossible to deploy the bombs to destroy the LAMBCHOP, but that's not going to stop you from trying!)

You need to know how many replication cycles (generations) it will take to generate the correct amount of bombs to destroy the LAMBCHOP. Write a function solution(M, F) where M and F are the number of Mach and Facula bombs needed. Return the fewest number of generations (as a string) that need to pass before you'll have the exact number of bombs necessary to destroy the LAMBCHOP, or the string "impossible" if this can't be done! M and F will be string representations of positive integers no larger than 10^50. For example, if M = "2" and F = "1", one generation would need to pass, so the solution would be "1". However, if M = "2" and F = "4", it would not be possible.

#### Solution: [L3Solution2.java](src/main/java/com/github/angrycellophane/foobar/l3/L3Solution2.java)
#### Test cases: [L3Solution2Test](src/test/java/com/github/angrycellophane/foobar/l3/L3Solution2Test.groovy)

------
### Task 3
#### Description 

Fuel Injection Perfection

Commander Lambda has asked for your help to refine the automatic quantum antimatter fuel injection system for her LAMBCHOP doomsday device. It's a great chance for you to get a closer look at the LAMBCHOP - and maybe sneak in a bit of sabotage while you're at it - so you took the job gladly.

Quantum antimatter fuel comes in small pellets, which is convenient since the many moving parts of the LAMBCHOP each need to be fed fuel one pellet at a time. However, minions dump pellets in bulk into the fuel intake. You need to figure out the most efficient way to sort and shift the pellets down to a single pellet at a time.

The fuel control mechanisms have three operations:

1) Add one fuel pellet
2) Remove one fuel pellet
3) Divide the entire group of fuel pellets by 2 (due to the destructive energy released when a quantum antimatter pellet is cut in half, the safety controls will only allow this to happen if there is an even number of pellets)

Write a function called solution(n) which takes a positive integer as a string and returns the minimum number of operations needed to transform the number of pellets to 1. The fuel intake control panel can only display a number up to 309 digits long, so there won't ever be more pellets than you can express in that many digits.*

#### Solution: [L3Solution3.java](src/main/java/com/github/angrycellophane/foobar/l3/L3Solution3.java)
#### Test cases: [L3Solution3Test](src/test/java/com/github/angrycellophane/foobar/l3/L3Solution3Test.groovy)
