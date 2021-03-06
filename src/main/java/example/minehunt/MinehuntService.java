package example.minehunt;

/**
 *
 */
public interface MinehuntService {

    /**
     * create a new grid
     *
     * @param line the count of lines
     * @param col the count of columns
     * @param mineCount the count of mines
     * @return the new grid
     * An IllegalArgumentException is thrown if you ask for more mines than
     * there are cells in the desired rectangular grid, or if any of the
     * parameters is negative
     */
    Grid createGrid(int line, int col, int mineCount)
         throws IllegalArgumentException;

}
