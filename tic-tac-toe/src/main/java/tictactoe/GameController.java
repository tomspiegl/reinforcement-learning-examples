package tictactoe;

import tictactoe.agent.Agent;

public class GameController {
    private BoardRenderer boardRenderer;

    public GameController(boolean trainingMode) {
        if (trainingMode) {
            boardRenderer = BoardRenderer.NoOp;
        } else {
            boardRenderer = BoardRenderer.Console;
        }
    }

    public Agent play(Players players) {
        int[] board = new int[9];
        for (;;) {
            Agent movingPlayer = players.getMoving();
            boardRenderer.renderStateForAgent(movingPlayer, board);
            Agent waitingPlayer = players.getWaiting();
            int move = movingPlayer.move(board);
            board[move] = movingPlayer.getId();
            if (hasWon(movingPlayer, board)) {
                movingPlayer.reward(1, board);
                waitingPlayer.reward(-1, board);
                boardRenderer.renderWinner(movingPlayer, board);
                return movingPlayer;
            } else if (boardFull(board)) {
                movingPlayer.reward(0.5, board);
                waitingPlayer.reward(0.5, board);
                boardRenderer.renderDraw(board);
                return null;
            }
            waitingPlayer.reward(0, board);
            players.rotate();
        }
    }

    public static boolean boardFull(int[] board) {
        for (int i : board) {
            if (i == 0) {
                return false;
            }
        }
        return true;
    }

    static final int[][] WINS = new int[8][];

    static {
        WINS[0] = new int[] {0,1,2};
        WINS[1] = new int[] {3,4,5};
        WINS[2] = new int[] {6,7,8};
        WINS[3] = new int[] {0,3,6};
        WINS[4] = new int[] {1,4,7};
        WINS[5] = new int[] {2,5,8};
        WINS[6] = new int[] {0,4,8};
        WINS[7] = new int[] {2,4,6};
    }

    public static boolean hasWon(Agent player, int[] board) {
        int playerId = player.getId();
        for (int[] t : WINS) {
            if (board[t[0]] == playerId && board[t[1]] == playerId && board[t[2]] == playerId) {
                return true;
            }
        }
        return false;
    }

}
