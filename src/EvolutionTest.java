/**
 * FileName: EvolutionTest.java
 * CreatedOn: August 18, 2022
 *
 * @author ZacInman
 * @version 1.0.081822
 */

import org.junit.Test;
/**
 * JUnit test for my Population and Genome Classes
 */
public class EvolutionTest {

    @Test
    public final void construct() {

        System.out.println("Strings are now evolving...");

        long delta, startTime = System.currentTimeMillis();

        Population pop = new Population("ZACHARY DYLAN INMAN", 100);

        while(pop.mostFit.fitness() < 0) {

            System.out.print("... Generation " + pop.generation + " ");
            System.out.println(pop.mostFit);

            pop.nextGeneration();

        }

        delta = System.currentTimeMillis() - startTime;

        System.out.println("Evolution took " + delta + " milliseconds.");
        System.out.println();
    }
}
