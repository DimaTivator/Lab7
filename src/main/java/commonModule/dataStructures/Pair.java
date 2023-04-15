package commonModule.dataStructures;

/**
 * Class that implements a generic pair of two objects.
 * @param <F> The type of the first object in the pair.
 * @param <S> The type of the second object in the pair.
 */
public class Pair<F, S> {

    private F first;
    private S second;

    /**
     * Constructor for a Pair.
     *
     * @param first The first object in the Pair.
     * @param second The second object in the Pair.
     */
    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Set the first object in the Pair.
     *
     * @param first The object to be set as the first element in the Pair.
     */
    public void setFirst(F first) {
        this.first = first;
    }

    /**
     * Get the first object in the Pair.
     *
     * @return The first object in the Pair.
     */
    public F getFirst() {
        return first;
    }

    /**
     * Set the second object in the Pair.
     *
     * @param second The object to be set as the second element in the Pair.
     */
    public void setSecond(S second) {
        this.second = second;
    }

    /**
     * Get the second object in the Pair.
     *
     * @return The second object in the Pair.
     */
    public S getSecond() {
        return second;
    }
}
