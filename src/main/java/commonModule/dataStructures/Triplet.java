package commonModule.dataStructures;

public class Triplet<F, S, T> {
    private F first;
    private S second;
    private T third;

    /**
     * Constructor for a Pair.
     *
     * @param first The first object in the Pair.
     * @param second The second object in the Pair.
     */
    public Triplet(F first, S second, T third) {
        this.first = first;
        this.second = second;
        this.third = third;
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

    public T getThird() {
        return third;
    }

    public void setThird(T third) {
        this.third = third;
    }
}
