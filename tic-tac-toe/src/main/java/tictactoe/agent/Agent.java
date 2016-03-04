package tictactoe.agent;

public interface Agent {
    int getId();

    int move(int[] currentState);

    void reward(double reward, int[] state);
}
