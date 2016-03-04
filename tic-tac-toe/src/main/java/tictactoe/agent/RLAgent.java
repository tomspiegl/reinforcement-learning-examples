package tictactoe.agent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class RLAgent implements Agent {
    // init values
    private final int id;
    private final double alpha;
    private final double gamma;
    // local state
    private final Stack<int[]> states = new Stack<>();
    private final Stack<Integer> actions = new Stack<>();
    private final QValues values;
    // functions
    private final QLearningFunc learningFunc = new QLearningFunc();
    private final SoftMaxPolicy policy = new SoftMaxPolicy();

    public RLAgent(int id) {
        this.id = id;
        this.values = new QValues();
        this.alpha = 0.3;
        this.gamma = 0.9;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int move(int[] state) {
        states.push(Arrays.copyOf(state, state.length));
        int selectedAction = policy.selectAction(state);
        this.actions.push(selectedAction);
        return selectedAction;
    }

    public void reward(double reward, int[] state) {
        learningFunc.learn(reward, state);
    }

    class QLearningFunc {
        public void learn(double reward, int[] state) {
            int[] subsequentState = state;

            while (states.size() > 1) {
                int[] currentState = states.pop();
                int currentAction = actions.pop();

                int[] subsequentActions = getAvailableActions(subsequentState);
                double maxSubsequentQ = 0;
                for (int a : subsequentActions) {
                    double q = values.getQValue(subsequentState, a);
                    if (q > maxSubsequentQ) {
                        maxSubsequentQ = q;
                    }
                }
                double currentQ = values.getQValue(currentState, currentAction);
                double newQ = currentQ + alpha * ((reward + gamma * maxSubsequentQ) - currentQ);
                values.setQValue(currentState, currentAction, newQ);
                subsequentState = currentState;
            }
        }
    }

    class SoftMaxPolicy {
        public int selectAction(int[] state) {
            int[] actions = getAvailableActions(state);

            double temp = 1.0;
            double denominator = 0;
            for (int action : actions) {
                denominator += Math.exp(values.getQValue(state, action) / temp);
            }
            double maxP = -1000000000;
            int selectedAction = -1;
            for (int action : actions) {
                double q = Math.exp(values.getQValue(state, action) / temp) / denominator;
                if (q > maxP) {
                    maxP = q;
                    selectedAction = action;
                }
            }
            return selectedAction;
        }

    }

    private int[] getAvailableActions(int[] state) {
        int[] moves = new int[state.length];
        int length = 0;
        for (int i = 0; i < state.length; i++) {
            if (state[i] == 0) {
                moves[length++] = i;
            }
        }
        return Arrays.copyOf(moves, length);
    }

    static class QValues {

        private Map<String, Double> qValues;

        public QValues() {
            this.qValues = new HashMap<>(10000);
        }

        public double getQValue(int[] state, int action) {
            String key = getQKey(state, action);
            Double q = qValues.get(key);
            if (q == null) {
                q = 0.5;
                qValues.put(key, q);
            }
            return q;
        }

        public void setQValue(int[] state, int action, Double value) {
            String actionId = getQKey(state, action);
            qValues.put(actionId, value);
        }
        private String getQKey(int[] state, int action) {
            StringBuilder buf = new StringBuilder(state.length + 1);
            for (int b : state) {
                buf.append(b);
            }
            buf.append(action);
            return buf.toString();
        }

    }


}
