package tictactoe;

import tictactoe.agent.Agent;

public class Players {
    private final Agent agentA;
    private final Agent agentB;
    public boolean asTurn;

    public Players(Agent agentA, Agent agentB) {
        this.agentA = agentA;
        this.agentB = agentB;
        this.asTurn = true;
    }

    public void rotate() {
        asTurn = !asTurn;
    }

    public Agent getMoving() {
        if (asTurn) {
            return agentA;
        } else {
            return agentB;
        }
    }

    public Agent getWaiting() {
        if (!asTurn) {
            return agentA;
        } else {
            return agentB;
        }
    }
}
