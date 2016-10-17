import java.util.Objects;

public class CSPZebra extends CSP {
    private static final String[] colours = {"blue", "green", "ivory", "red", "yellow" };
    private static final String[] drinks = {"coffee", "milk", "orange juice", "tea", "water"};
    private static final String[] nationalities = {"englishman", "japanese", "norwegian", "spaniard", "ukrainian"};
    private static final String[] pets = {"dog", "fox", "horse", "snails", "zebra"};
    private static final String[] cigarettes = {"chesterfield", "kools", "lucky-strike", "old-gold", "parliament"};
    private static final Integer[] domains = { 1, 2, 3, 4, 5 };


    @Override
    public boolean isGood(Object X, Object Y, Object x, Object y) {
        System.out.println(X);
        System.out.println(Y);
        System.out.println(x);
        System.out.println(y);
        boolean good = _isGood(X, Y, x, y);
        System.out.println(good);
        System.out.println("~~~");

        return good;
    }

    private boolean _isGood(Object X, Object Y, Object x, Object y) {
        if (binaryEquals(X, Y, "spaniard", "dog", x, y)) {
            return true;
        }
        if (binaryEquals(X, Y, "coffee", "green", x, y)) {
            return true;
        }
        if (binaryEquals(X, Y, "ukrainian", "tea", x, y)) {
            return true;
        }
        if (X.equals("green") && Y.equals("ivory") && (int)x - (int)y == 1) {
            return true;
        }
        if (binaryEquals(X, Y, "old-gold", "snails", x, y)) {
            return true;
        }
        if (binaryEquals(X, Y, "kools", "yellow", x, y)) {
            return true;
        }
        if (X.equals("chesterfield") && Y.equals("fox") && Math.abs((int)x - (int)y) == 1) {
            return true;
        }
        if (X.equals("kools") && Y.equals("horse") && Math.abs((int)x - (int)y) == 1) {
            return true;
        }
        if (X.equals("lucky-strike") && Y.equals("orange juice") && x == y) {
            return true;
        }
        if (X.equals("japanese") && Y.equals("parliament") && x == y) {
            return true;
        }
        if (X.equals("norwegian") && Y.equals("blue") && Math.abs((int)x - (int)y) == 1) {
            return true;
        }

        if (!x.equals(y)) {
            return true;
        }

        return false;
    }

    private boolean binaryEquals(Object X, Object Y, String xVal, String yVAl, Object x, Object y) {
        if ((X.equals(xVal) && Y.equals(yVAl)) && x.equals(y)) {
            return true;
        }

        return false;
    }

    private static void addVar(String[] var, CSP csp) {
        for (String val : var) {
            csp.addDomain(val, domains);
//            for (String val2 : var)
//                if (!val.equals(val2))
//                    csp.addArc(val, val2);
        }
    }

    public static void main(String[] args) {
        CSPZebra csp = new CSPZebra();

        addVar(colours, csp);
        addVar(drinks, csp);
        addVar(nationalities, csp);
        addVar(pets, csp);
        addVar(cigarettes, csp);

        csp.addDomain("milk", new Integer[] {3});
        csp.addDomain("norwegian", new Integer[] {1});

        Search search = new Search(csp);
        System.out.println(search.BacktrackingSearch());
    }
}
