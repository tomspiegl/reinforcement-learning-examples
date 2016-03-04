package tictactoe;

import tictactoe.agent.Agent;
import tictactoe.agent.HumanAgent;
import tictactoe.agent.RLAgent;

public class Main {
    public static void main(String... args) {
        Agent learningAgent = new RLAgent(1);

        // first we train the RLAgent
        train(learningAgent);

        // then we play against it
        play(learningAgent);
    }

    private static void train(Agent learningAgent) {
        GameController gameController = new GameController(true);
        System.out.println("Playing training episodes");
        Stats stats = new Stats();
        int learningGames = 50000;
        Players players = new Players(learningAgent, new RLAgent(2));
        for (int i = 0; i < learningGames; i++) {
            Agent winner = gameController.play(players);
            stats.gamePlayed();
            if (winner != null)  {
                stats.winner(winner.getId());
            } else {
                stats.draw();
            }
            stats.logProgress();
            players.rotate();
        }
        stats.log();
        System.out.println("\nTraining done, you may start playing. You are player (X). Good luck!\n");
    }

    private static void play(Agent learningAgent) {
        HumanAgent humanAgent = new HumanAgent(2);
        GameController gameController = new GameController(false);
        Players players = new Players(humanAgent, learningAgent);
        //noinspection InfiniteLoopStatement
        for (;;) {
            gameController.play(players);
        }
    }
    static class Stats {

        private int stats[] = new int[4];

        void gamePlayed() {
            stats[0]++;
        }

        public void draw() {
            stats[3]++;
        }

        void winner(int winnerId) {
            if (winnerId == 1) {
                stats[1]++;
            } else if (winnerId == 2) {
                stats[2]++;
            } else {
                throw new IllegalStateException();
            }
        }

        private void log() {
            System.out.printf("%nTraining done: games played:%d, draw:%d, A won:%d B won:%d%n", stats[0], stats[3], stats[1], stats[2]);
        }

        long t = System.currentTimeMillis();

        public void logProgress() {
            if (System.currentTimeMillis() - t > 300) {
                System.out.print(".");
                t = System.currentTimeMillis();
            }
        }
    }


}
