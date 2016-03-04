package tictactoe.agent;

import java.util.Scanner;

public class HumanAgent implements Agent {

    private final int id;
    private final Scanner scanner = new Scanner(System.in);

    public HumanAgent(int playerId) {
        this.id = playerId;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int move(int[] currentState) {
        int i = -1;
        while (i == -1) {
            System.out.print("\nYou: ");
            int v = scanner.nextInt() - 1;
            if (v < 0 || v > currentState.length -1 || currentState[v] != 0) {
                System.out.print("Try again: ");
            } else {
                i = v;
            }
        }
        return i;
    }

    @Override
    public void reward(double reward, int[] state) {
    }

}
