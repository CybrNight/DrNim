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
        printTitle();
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
                        printTitle();
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
                printCredits();
            }else if (option == 4){
                scanner.close();
                System.exit(1);
            }else{
                System.out.println("Please Enter a Valid Option");
                printMenu();
            }
        }
    }

    public void run() {
        //Game loop
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
            }
        }
        stop();
    }

    private void printCredits(){
        System.out.println("Based on the 1960s game Dr. Nim.\n" +
                "Written by Nathan Estrada\n" +
                "You cannot win. Trust me!");
        printMenu();
    }

    private void printMenu(){
        System.out.println("1:Start\n" +
                "2:Instructions\n" +
                "3:Credits\n" +
                "4:Exit\n");
    }

    private void printTitle(){
        System.out.println("     ___                                                      \n" +
                "    (   )                                 .-.                 \n" +
                "  .-.| |   ___ .-.             ___ .-.   ( __)  ___ .-. .-.   \n" +
                " /   \\ |  (   )   \\           (   )   \\  (''\") (   )   '   \\  \n" +
                "|  .-. |   | ' .-. ;           |  .-. .   | |   |  .-.  .-. ; \n" +
                "| |  | |   |  / (___)          | |  | |   | |   | |  | |  | | \n" +
                "| |  | |   | |                 | |  | |   | |   | |  | |  | | \n" +
                "| |  | |   | |                 | |  | |   | |   | |  | |  | | \n" +
                "| '  | |   | |         .-.     | |  | |   | |   | |  | |  | | \n" +
                "' `-'  /   | |        (   )    | |  | |   | |   | |  | |  | | \n" +
                " `.__,'   (___)        `-'    (___)(___) (___) (___)(___)(___)\n" +
                "                                                              \n");

        System.out.println("1:Start\n" +
                           "2:Instructions\n" +
                           "3:Credits\n" +
                           "4:Exit");
    }
}
