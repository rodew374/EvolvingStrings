/**
 * FileName: Population.java
 * CreatedOn: August 17, 2022
 *
 * @author rodew
 * @version 1.0.081722
 */

/**
 * A class that contains a list of Genomes
 * representing all the Strings in a virtual world.
 * In this world, all that exists are strings of
 * characters from the set: {A,B,C,D,E,F,G,H,I,J,
 * K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,' ',-,'}
 * Strings in this world can reproduce new strings
 * and die if they are not fit enough.
 */
public class Population {

    /**
     * The number of genomes in the population.
     * For this assignment, population size is 100.
     */
    protected final int SIZE;

    /**
     * The number of times nextGeneration
     * has been called.
     */
    public int generation;

    /**
     * This stores the most fit genome, which
     * is the genome with the highest fitness
     * level.
     */
    public Genome mostFit;

    /**
     * This stores the Genomes in the population.
     * The initial population of size SIZE is
     * created in the constructor. The population
     * is updated every time nextGeneration is
     * called.
     */
    public MyLinkedList<Genome> population;

    /**
     * Default Constructor.
     * Calls the main constructor with
     * String "ZACHARY DYLAN INMAN" and
     * integer 100.
     */
    public Population() {   this("ZACHARY DYLAN INMAN", 100);    }

    /**
     * Constructor.
     * Initializes SIZE to populationSize.
     * Initializes generation to zero.
     * Initializes population to a linkedList with
     * 100 baby (Empty) Genomes.
     * Initializes Genome.Target to a linkedList
     * consisting of the characters from the target
     * String.
     * Initializes mostFit to the first Genome in the
     * population.
     * @param target the target Genome for evolution.
     * @param populationSize the number of Genomes in the
     * population.
     */
    public Population(String target, int populationSize) {

        SIZE = populationSize;
        generation = 0;
        population = new MyLinkedList<>();
        Genome.target = createTarget(target);

        for(int n = 0; n < SIZE; n++) population.addToEnd(new Genome());

        mostFit = population.first();

    }

    /**
     * Creates a MyLinkedList from passed String.
     * @param str the String from which the list is created.
     * @return the target linkedList.
     */
    public MyLinkedList<Character> createTarget(String str) {

        MyLinkedList<Character> target = new MyLinkedList<>();

        for(int i = 0; i < str.length(); i++) {

            char ch = str.charAt(i);

            target.addToEnd(ch);

        }

        return target;

    }

    /**
     * Updates the population with the next
     * generation. First, half the population
     * with the lowest fitness levels are deleted.
     * Second, new Genomes are created from the
     * remaining population. There is equal chance
     * that (1) a random remaining Genome will be
     * cloned and the clone mutated or (2) a random
     * remaining Genome will be cloned, the clone
     * crossed with another Genome, and then finally
     * mutated. Third, the population will be sorted
     * by fitness level. Last, the mostFit Genome will
     * be updated to the Genome with the highest fitness
     * level.
     */
    public void nextGeneration() {

        int select;
        int half = SIZE / 2;
        Genome adult;
        Genome offspring;

        population.first();

        for(int i = 0; i < half; i++) population.next();

        while (population.current() != null) population.remove();

        for(int n = 0; n < half; n++) {

            select = Genome.random() % half;
            adult = population.first();

            for (int i = 0; i < select; i++) adult = population.next();

            offspring = new Genome(adult);

            if (Genome.random() % 2 == 0) {

                select = Genome.random() % half;
                adult = population.first();

                for (int i = 0; i < select; i++) adult = population.next();

                offspring.crossover(adult);

            }

            offspring.mutate();
            population.addToEnd(offspring);

        }

        population.sort(true);
        generation++;

        mostFit = population.first();

    }

    public String toString() {

        StringBuilder str = new StringBuilder("[\n");
        Genome current = population.first();

        while (current != null) {

            str.append(current).append("\n");

            current = population.next();

        }

//        str = new StringBuilder(str.substring(0, str.length() - 2));


        return str.append("]").toString();

    }
}
