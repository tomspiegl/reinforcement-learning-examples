package tictactoe;

import tictactoe.agent.Agent;
import tictactoe.agent.HumanAgent;

public abstract class BoardRenderer {

    public void renderStateForAgent(Agent agent, int[] state) {
        if (agent instanceof HumanAgent) {
            print(state);
        }
    }

    public void renderWinner(Agent agent, int[] state) {
        print(state);
        if (agent instanceof HumanAgent) {
            System.out.println("--- Congrats you have won ---");
        } else {
            System.out.println("--- Computer has won ---");
        }
    }

    public void renderDraw(int[] state) {
        print(state);
        System.out.println("--- Draw ---");
    }

    static final String BOARD_FORMAT = "%n%s  %s  %s%n%s  %s  %s%n%s  %s  %s%n";

    private static void print(int[] state) {
        System.out.printf(BOARD_FORMAT,
                toChar(state, 0), toChar(state, 1), toChar(state, 2),
                toChar(state, 3), toChar(state, 4) ,toChar(state, 5),
                toChar(state, 6),toChar(state, 7),toChar(state, 8));
    }

    private static String toChar(int[] state, int p) {
        switch (state[p]) {
            case 0: return String.format(" %s ", Integer.toString(p + 1));
            case 1: return "(O)";
            case 2: return "(X)";
            default: throw new IllegalStateException(Integer.toString(state[p]));
        }
    }

    public static BoardRenderer Console = new BoardRenderer() {
    };

    public static BoardRenderer NoOp = new BoardRenderer() {
        @Override
        public void renderStateForAgent(Agent agent, int[] state) {
        }

        @Override
        public void renderWinner(Agent agent, int[] state) {
        }

        @Override
        public void renderDraw(int[] board) {
        }
    };
}
