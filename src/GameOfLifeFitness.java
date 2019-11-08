// Created by Harshdeep Kahlon
// 11/08/2019
// GameOfLifeFitness.java

import java.util.Random;

public class GameOfLifeFitness extends GameOfLife {

    int bestFitness = 0;
    Boolean[][] bestStarter;
    boolean[][] currentBoard;

    public GameOfLifeFitness() {
        super(32, 32);
        currentBoard = new boolean[32][32];
    }

    public int fitness(Object[][] starter) {
        if (starter.length == 0 || starter.length < 8 || starter[0].length < 8) {
          System.out.print("Inputted 2D array needs to be 8x8.");
          return 0;
        }
        if (Integer[][].class.isInstance(starter)) {
            var starterBool = new Boolean[8][8];
            for (int i = 0; i < starter.length; i++) {
                for (int j = 0; j < starter[i].length; j++) starterBool[i][j] = (Integer) starter[i][j] == 1;
            }
            currentBoard = insertInto32x32(starterBool);
            var gameBoard = new GameOfLife(currentBoard);
            for (int i = 0; i < 1000; i++) gameBoard.nextIteration();
            return gameBoard.getNumAlive();
        } else if (Boolean[][].class.isInstance(starter)) {
            currentBoard = insertInto32x32((Boolean[][]) starter);
            var gameBoard = new GameOfLife(currentBoard);
            for (int i = 0; i < 1000; i++) gameBoard.nextIteration();
            return gameBoard.getNumAlive();
        }
        System.out.println("Error: fitness does not work on the given class type");
        return 0;
    }

    public void bruteForce(double timeInHours) {
        long startTime = System.currentTimeMillis();
        int iterationCount = 0;
        while (System.currentTimeMillis() - startTime < timeInHours * 3600000) {
            var starter = generate8x8Starter();
            int fit = fitness(starter);
            if (fit > bestFitness) {
                System.out.printf("\nNew Best Fit Found: %d on Iteration #%d", fit, iterationCount);
                bestFitness = fit;
                bestStarter = starter;
            }
            iterationCount++;
        }
        System.out.printf("\n\n\nThe best fitness after %f hours is %d. The starter board is shown below:\n\n", timeInHours, fitness(bestStarter));
        System.out.println("Regular:");
        dumpStartUserFriendly(bestStarter);
        System.out.println("\nCompact:");
        dumpStartCompact(bestStarter);
    }

    private Boolean[][] generate8x8Starter() {
        var rand = new Random();
        Boolean[][] starter = new Boolean[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                starter[i][j] = rand.nextBoolean();
            }
        }
        return starter;
    }

    private boolean[][] insertInto32x32(Boolean[][] starter) {
        var board = new boolean[32][32];
        for (int i = 0; i < starter.length; i++) {
            for (int j = 0; j < starter[i].length; j++) {
                board[i + 15][j + 15] = starter[i][j];
            }
        }
        return board;
    }

    void dumpStartUserFriendly(Boolean[][] starter) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (starter[i][j]) System.out.print(1);
                else System.out.print(0);
            }
            System.out.println();
        }
    }

    void dumpStartCompact(Boolean[][] starter) {
        String toConvert = "";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                toConvert = toConvert + (starter[i][j] ? 1 : 0);
                if (toConvert.length() == 4) {
                    int binary = Integer.parseInt(toConvert, 2);
                    String hex = Integer.toHexString(binary);
                    System.out.print(hex);
                    toConvert = "";
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        var fitnessBoard = new GameOfLifeFitness();

        Integer[][] designOne = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
        };

        Integer[][] designTwo = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
        };

        System.out.printf("Design One has a fitness of %d\n", fitnessBoard.fitness(designOne));
        System.out.printf("Design Two has a fitness of %d\n", fitnessBoard.fitness(designTwo));

        fitnessBoard.bruteForce(0.1);
    }
}
