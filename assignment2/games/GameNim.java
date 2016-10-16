import java.util.*;

public class GameNim extends Game {
    private static final int WINNING_SCORE = 1;
    private static final int LOSING_SCORE = -1;
    private static final int NEUTRAL_SCORE = 0;
    private static final int PILE_SIZE = 13;
    private static final int MAX_TAKE = 3;

    private GameNim() { currentState = new StateNim(PILE_SIZE); }

    @Override
    public boolean isWinState(State state) {
        StateNim nState = (StateNim)state;
        // Pile is empty
        return nState.coins == 0;
    }

    @Override
    public boolean isStuckState(State state) {
        return false;
    }

    @Override
    public Set<State> getSuccessors(State state) {
        if (isWinState(state) || isStuckState(state)) {
            return null;
        }

        Set<State> successors = new HashSet<>();
        StateNim nState = (StateNim)state;
        // Can't remove more coins than there are in the pile
        int maxRemovable = Math.min(nState.coins, MAX_TAKE);

        for (int i = 1; i <= maxRemovable; i++) {
            StateNim succ = new StateNim(nState.coins - i);
            succ.player = nState.player ^ 1;
            successors.add(succ);
        }

        return successors;
    }

    @Override
    public double eval(State state) {
        // Player who made the last move
        int prevPlayer = state.player ^ 1;
        if (isWinState(state)) {
            // If the human player (P1) took the last item(s), computer wins
            return prevPlayer == 1 ? WINNING_SCORE : LOSING_SCORE;
        }

        // This should never be reached!
        return NEUTRAL_SCORE;
    }

    public static void main(String[] args) {
        Game game = new GameNim();
        Search search = new Search(game);
        Scanner in = new Scanner(System.in);
        int depth = 5;

        while (true) {
            StateNim nextState;

            if (game.currentState.player == 1) {
                // Human turn
                System.out.print("Enter the number of coins to take (1-3)> ");
                int toRemove = in.nextInt();

                nextState = new StateNim((StateNim) game.currentState);
                nextState.player = 1;
                nextState.coins -= toRemove;
                System.out.println("Human: \n" + nextState);
            } else {
                // Computer turn
                nextState = (StateNim)search.bestSuccessorState(depth);
                nextState.player = 0;
                System.out.println("Computer: \n" + nextState);
            }

            game.currentState = nextState;
            // Change player (bitwise xor does 1->0 and 0->1)
            game.currentState.player ^= 1;

            if (game.isWinState(game.currentState)) {
                if (game.currentState.player == 1) {
                    System.out.println("You win!");
                } else {
                    System.out.println("Computer wins!");
                }
                break;
            }
        }
    }
}
