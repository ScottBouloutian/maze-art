public class Neighbor {
    private Cell parent;
    private Direction direction;
    private Cell child;

    /**
     * Constructs a Neighbor object representing an edge from parent to child along a given
     *   direction.
     * @param  parent    The starting cell
     * @param  direction The direction of the edge
     * @param  child     The resulting cell after movement in the direction
     */
    public Neighbor(Cell parent, Direction direction, Cell child) {
        this.parent = parent;
        this.direction = direction;
        this.child = child;
    }

    /**
     * Gets the parent.
     * @return The parent cell
     */
    public Cell getParent() {
        return parent;
    }

    /**
     * Gets the direction.
     * @return The direction of the edge
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Gets the child.
     * @return The child cell
     */
    public Cell getChild() {
        return child;
    }
}
