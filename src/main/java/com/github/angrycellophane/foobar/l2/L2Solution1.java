package com.github.angrycellophane.foobar.l2;

/**
 *  Problem:
 *  Write a function solution(s) which takes a string representing employees walking along a hallway
 *  and returns the number of times the employees will salute, employees salute when they meet.
 *
 * hallway string contains three different types of characters:
 * '>', an employee walking to the right;
 * '<', an employee walking to the left;
 * '-', an empty space.
 *
 * For example: "--->-><-><-->-"
 *
 * Solution:
 * To go through the hallway from left to right and count the number of employees moving right.
 * If an employee moving left met then that employee will meet and salute every employee moving right we've met before,
 * so the number of salutes increases by the number of employees met before and moving right multiplied by 2.
 */
public class L2Solution1 {
    public static int solution(String hallway) {
        int salutes = 0;
        int toRight = 0;
        for (int i=0; i<hallway.length(); i++) {
            char hallSpace = hallway.charAt(i);
            if (hallSpace == '<') {
                // will have to salute everyone moving right we've met before.
                salutes += toRight * 2;
            } else if (hallSpace == '>') {
                toRight++;
            }
        }
        return salutes;
    }
}
