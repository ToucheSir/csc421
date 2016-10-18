public class CSPZebra extends CSP {
    private static final String[] colours = {"blue", "green", "ivory", "red", "yellow" };
    private static final String[] drinks = {"coffee", "milk", "orange juice", "tea", "water"};
    private static final String[] nationalities = {"englishman", "japanese", "norwegian", "spaniard", "ukrainian"};
    private static final String[] pets = {"dog", "fox", "horse", "snails", "zebra"};
    private static final String[] cigarettes = {"chesterfield", "kools", "lucky-strike", "old-gold", "parliament"};
    private static final Integer[] domains = { 1, 2, 3, 4, 5 };

    private static final String[][] equalPairs = {
            {"englishman", "red"},
            {"spaniard", "dog"},
            {"coffee", "green"},
            {"ukrainian", "tea"},
            {"old-gold", "snails"},
            {"kools", "yellow"},
            {"lucky-strike", "orange juice"},
            {"japanese", "parliament"},
    };

    private static final String[][] adjPairs = {
            {"chesterfield", "fox"},
            {"kools", "horse"},
            {"norwegian", "blue"},
    };

    @Override
    public boolean isGood(Object X, Object Y, Object x, Object y) {
        if (C.containsKey(X) && C.get(X).contains(Y) && x.equals(y)) {
            return false;
        }

        // _ at the same house as _
        for (String[] pair : equalPairs) {
            if (pairEquals(X, Y, pair[0], pair[1]) && !x.equals(y)) {
                return false;
            }
        }

        // The green house is directly to the right of the ivory house
        if (pairEquals(X, Y, "green", "ivory") && (int)x - (int)y != 1) {
            return false;
        }

        // _ is in the house next to _
        for (String[] pair : adjPairs) {
            if (pairEquals(X, Y, pair[0], pair[1]) && !adjacent(x, y)) {
                return false;
            }
        }

        return true;
    }

    private boolean adjacent(Object x, Object y) {
        return Math.abs((int)x - (int)y) == 1;
    }

    private boolean pairEquals(Object X, Object Y, String val1, String val2) {
        return X.equals(val1) && Y.equals(val2);
    }

    private static void addVar(String[] var, CSP csp) {
        for (String val : var) {
            csp.addDomain(val, domains);
            for (String val2 : var)
                if (!val.equals(val2))
                    csp.addArc(val, val2);
        }
    }

    public static void main(String[] args) {
        CSPZebra csp = new CSPZebra();

        addVar(colours, csp);
        addVar(drinks, csp);
        addVar(nationalities, csp);
        addVar(pets, csp);
        addVar(cigarettes, csp);

        // filter (reassign limited) domains for variables for which we know the house
        csp.addDomain("milk", new Integer[] {3});
        csp.addDomain("norwegian", new Integer[] {1});

        Search search = new Search(csp);
        System.out.println(search.BacktrackingSearch());
    }
}
