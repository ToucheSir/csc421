import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ProblemWaterJugs extends Problem {
	public static final int[] maxCaps = {12, 8, 3};

	@Override
	boolean goal_test(Object state) {
		StateWaterJugs jugs_state = (StateWaterJugs) state;
		for (int i = 0; i < jugs_state.jugs.length; i++) {
			if (jugs_state.jugs[i] == 1) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	Set<Object> getSuccessors(Object state) {
		Set<Object> set = new HashSet<Object>();
		StateWaterJugs curr_state = (StateWaterJugs) state;

		// Let's create without any constraint, then remove the illegal ones
		StateWaterJugs succ_state;
		
		// Case 1: filling or emptying jugs
		for (int i = 0; i < curr_state.jugs.length; i++) {
			if (curr_state.jugs[i] < maxCaps[i]) {
				succ_state = new StateWaterJugs(curr_state);
				succ_state.jugs[i] = maxCaps[i];
				set.add(succ_state);
			} else if (curr_state.jugs[i] > 0) {
				succ_state = new StateWaterJugs(curr_state);
				succ_state.jugs[i] = 0;
				set.add(succ_state);
			}
		}

		// Case 2: pouring between jugs
		for (int i = 0; i < curr_state.jugs.length; i++) {
			for (int j = 0; j < curr_state.jugs.length; j++) {
				if (i == j || curr_state.jugs[i] == 0) {
					continue;
				}

				if (curr_state.jugs[j] < maxCaps[j]) {
					succ_state = new StateWaterJugs(curr_state);
					int pourVolume = Math.min(curr_state.jugs[i], maxCaps[j] - curr_state.jugs[j]);
					succ_state.jugs[j] += pourVolume;
					succ_state.jugs[i] -= pourVolume;
					set.add(succ_state);
				}
			}
		}

//		System.out.println(state + ": " + set);
		return set;
	}

	double step_cost(Object fromState, Object toState) {
		int[] from = ((StateWaterJugs) fromState).jugs;
		int[] to = ((StateWaterJugs) toState).jugs;
		double totalCost = 0;
		for (int i = 0; i < from.length; i++) {
			double cost = ((double)Math.abs(to[i] - from[i]));
//			if (from[i] > to[i]) {
				for (int j = 0; j < to.length; j++) {
					if (i != j && Math.abs(to[j] - from[j]) == cost) {
						cost /= 2.0;
						break;
					}
				}
//			}
			totalCost += cost;
		}
		return totalCost;
	}

	public double h(Object state) {
		return 0;
	}

	public static void main(String[] args) throws Exception {
		ProblemWaterJugs problem = new ProblemWaterJugs();
		int[] jugsArray = new int[3];
		problem.initialState = new StateWaterJugs(jugsArray);

		Search search = new Search(problem);

		System.out.println("Uninformed");
		System.out.println("BreadthFirstTreeSearch:\t\t" + search.BreadthFirstTreeSearch());
		System.out.println("BreadthFirstGraphSearch:\t" + search.BreadthFirstGraphSearch());
		System.out.println("--------");

		System.out.println("DepthFirstTreeSearch:\t\t" + search.DepthFirstTreeSearch());
		System.out.println("DepthFirstGraphSearch:\t\t" + search.DepthFirstGraphSearch());
		System.out.println("--------");
		
		System.out.println("UniformCostTreeSearch:\t\t" + search.UniformCostTreeSearch());
		System.out.println("UniformCostGraphSearch:\t\t" + search.UniformCostGraphSearch());
		System.out.println("--------");
		
		System.out.println("IterativeDeepeningTreeSearch:\t\t" + search.IterativeDeepeningTreeSearch() );
		System.out.println("IterativeDeepeningGraphSearch:\t\t" + search.IterativeDeepeningGraphSearch());
		System.out.println("--------");
		
		System.out.println();

//		System.out.println("Informed");
//		System.out.println("GreedyBestFirstTreeSearch:\t\t" + search.GreedyBestFirstTreeSearch());
//		System.out.println("GreedyBestFirstGraphSearch:\t\t" + search.GreedyBestFirstGraphSearch());
		System.out.println("--------");
		
//		System.out.println("AstarTreeSearch:\t\t" + search.AstarTreeSearch());
//		System.out.println("AstarGraphSearch:\t\t" + search.AstarGraphSearch());
		System.out.println("--------");
	}
}
