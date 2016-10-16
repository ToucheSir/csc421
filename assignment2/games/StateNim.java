import java.util.Collections;

class StateNim extends State {
    int coins;

    StateNim(int coins) {
        this.coins = coins;
        player = 1;
    }

    StateNim(StateNim state) {
        coins = state.coins;
        player = state.player;
    }

    @Override
    public String toString() {
        return String.join("", Collections.nCopies(coins, "|"));
    }
}
