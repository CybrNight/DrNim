package com.nim.main;

import java.util.Scanner;

enum TURN{
    Player1,
    Nim,
}


public class NimMain implements Runnable{

    private boolean running = false;

    private Thread thread;

    private STATE gameState = STATE.Menu;
    private TURN currentTurn = TURN.Player1;

    private Scanner scanner = new Scanner(System.in);
    private int option;

    private int numMarbles = 12;
    private int playerCount = 1;

    private NimMain(){
        displayTitle();
        start();
    }

    public static void main(String[] args){
        new NimMain();
    }

    private synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    private synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tick(){
        if (gameState == STATE.Game){
            if (playerCount == 1){
                System.out.println();
                if (numMarbles > 0){
                    System.out.println("Number of Marbles Left: "+numMarbles);
                    for (int i = 0; i < numMarbles; i++){
                        System.out.print("* ");
                    }
                    System.out.println();

                    currentTurn = TURN.Player1;
                    System.out.print("How many marbles do you take? (1-3): ");
                    int marbles = scanner.nextInt();

                    if (marbles <= 3 && marbles > 0){
                        numMarbles -= marbles;

                        if  (numMarbles > 0){
                            currentTurn = TURN.Nim;
                            if (marbles == 1) marbles = 3;
                            else if (marbles == 2) marbles = 2;
                            else if (marbles == 3) marbles = 1;

                            System.out.println("Dr. Nim took "+marbles+" marbles");
                            numMarbles -= marbles;
                        }

                    }else{
                        System.out.println("Invalid Number!");
                    }
                }else{
                    System.out.println("There are zero marbles left!");
                    if (currentTurn == TURN.Nim)
                        System.out.println("██████╗ ██████╗     ███╗   ██╗██╗███╗   ███╗    ██╗    ██╗██╗███╗   ██╗███████╗██╗\n" +
                                           "██╔══██╗██╔══██╗    ████╗  ██║██║████╗ ████║    ██║    ██║██║████╗  ██║██╔════╝██║\n" +
                                           "██║  ██║██████╔╝    ██╔██╗ ██║██║██╔████╔██║    ██║ █╗ ██║██║██╔██╗ ██║███████╗██║\n" +
                                           "██║  ██║██╔══██╗    ██║╚██╗██║██║██║╚██╔╝██║    ██║███╗██║██║██║╚██╗██║╚════██║╚═╝\n" +
                                           "██████╔╝██║  ██║    ██║ ╚████║██║██║ ╚═╝ ██║    ╚███╔███╔╝██║██║ ╚████║███████║██╗\n" +
                                           "╚═════╝ ╚═╝  ╚═╝    ╚═╝  ╚═══╝╚═╝╚═╝     ╚═╝     ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚══════╝╚═╝\n" +
                                           "                                                                                  ");
                    else
                        System.out.println("██╗   ██╗ ██████╗ ██╗   ██╗    ██╗    ██╗██╗███╗   ██╗██╗\n" +
                                "╚██╗ ██╔╝██╔═══██╗██║   ██║    ██║    ██║██║████╗  ██║██║\n" +
                                " ╚████╔╝ ██║   ██║██║   ██║    ██║ █╗ ██║██║██╔██╗ ██║██║\n" +
                                "  ╚██╔╝  ██║   ██║██║   ██║    ██║███╗██║██║██║╚██╗██║╚═╝\n" +
                                "   ██║   ╚██████╔╝╚██████╔╝    ╚███╔███╔╝██║██║ ╚████║██╗\n" +
                                "   ╚═╝    ╚═════╝  ╚═════╝      ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═╝\n" +
                                "                                                         ");

                    System.out.println("1: Return to Menu 2: Exit");
                    if (option == 1){
                        option = scanner.nextInt();
                        System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                        displayTitle();
                        gameState = STATE.Menu;
                    }else{
                        System.exit(1);
                    }

                }
            }
        }else{
            System.out.print("Select Option: ");
            option = scanner.nextInt();

            if (option == 1){
                currentTurn = TURN.Player1;
                gameState = STATE.Game;
                playerCount = 1;
                numMarbles = 12;
            }else if (option == 2){
                System.out.println("The goal is to take the last marble from a set of 12.");
                System.out.println("You make take 1, 2, or 3 marbles on your turn.");
                System.out.println("After your turn, Dr. Nim will do the same.\n");
            }else if (option == 3){
                scanner.close();
                System.exit(1);
            }else{
                System.out.println("Please Enter a Valid Option");
                System.out.println("1:Start\n" +
                                   "2:Instructions\n" +
                                   "3:Exit");
            }
        }
    }

    public void run() {
        while (running){
            tick();
        }
    }

    private void displayTitle(){
        System.out.println("\n");
        System.out.println("  ██████╗ ██████╗     ███╗   ██╗██╗███╗   ███╗\n" +
                           "  ██╔══██╗██╔══██╗    ████╗  ██║██║████╗ ████║\n" +
                           "  ██║  ██║██████╔╝    ██╔██╗ ██║██║██╔████╔██║\n" +
                           "  ██║  ██║██╔══██╗    ██║╚██╗██║██║██║╚██╔╝██║\n" +
                           "  ██████╔╝██║  ██║    ██║ ╚████║██║██║ ╚═╝ ██║\n" +
                           "  ╚═════╝ ╚═╝  ╚═╝    ╚═╝  ╚═══╝╚═╝╚═╝     ╚═╝\n" +
                           "                                            ");

        System.out.println("1:Start\n" +
                           "2:Instructions\n" +
                           "3:Exit");
    }
}
