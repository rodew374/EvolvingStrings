/**
 * FileName: Genome.java
 * CreatedOn: August 17, 2022
 *
 * @author ZacInman
 * @version 1.0.081722
 */

/**
 * A class that contains a list of characters
 * representing a String in a virtual world.
 * In this world, all that exists are strings of
 * characters from the set: {A,B,C,D,E,F,G,H,I,J,
 * K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,' ',-,'}
 * Strings in this world can reproduce new strings
 * and die if they are not fit enough.
 */
public class Genome implements Comparable<Genome>{

    /**
     * Seed for random().
     */
    protected static Integer seed = 2067734636;

    /**
     * A list of characters representing the string
     * encoded by the genome.
     */
    protected MyLinkedList<Character> genes;

    /**
     * The population's mutation rate.
     * Accurate only to two decimal places.
     * Default: 5% chance of mutation.
     */
    protected static double mutationRate = 0.05;

    /**
     * The target string.
     */
    protected static MyLinkedList<Character> target = new MyLinkedList<>();

    /**
     * The set of characters all strings are made from.
     */
    protected static final MyLinkedList<Character> genePool = getDNA();

    /**
     * Constructor
     * Initializes genes to an empty list.
     */
    public Genome() {   genes = new MyLinkedList<>();       }

    /**
     * Copy constructor.
     * Initializes genes to the passed genome's genes.
     * @param genome the list of characters to be copied.
     */
    public Genome(Genome genome) {  genes = genome.genes;       }

    /**
     * My random number generator.
     * @return a random 32 bit integer.
     */
    protected static int random() {

        int a = 923743637;
        int b = 3;
        long c = (long) Math.pow(2, 48);

        seed = (int) ((a * seed + b) % c);

        return seed;
    }

    /**
     * Initializes the genePool with characters:
     * {A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,
     * U,V,W,X,Y,Z,' ',-,'}
     * @return the genePool
     */
    protected static MyLinkedList<Character> getDNA() {

        MyLinkedList<Character> characters = new MyLinkedList<>();
        int index = 65;

        while (index < 91) characters.addToEnd((char) index++);       // Characters 'A'-'Z'

        characters.addToEnd((char) 32);         //  ' ' character
        characters.addToEnd((char) 45);         //  '-' character
        characters.addToEnd((char) 39);         //  ''' character

        return characters;
    }

    /**
     * Mutation chance simulator.
     * @return true if successful; false otherwise.
     */
    protected static boolean mutationSuccess() {

        double threshold = mutationRate * 100;

        return (random() % 100) <= threshold;

    }

    /**
     * Mutates the genes.
     * With mutationRate chance, add a randomly selected
     * character to a randomly selected position in genes.
     * With mutationRate chance, delete a single character
     * from a randomly selected position in genes; only
     * if genes.size() >= 1.
     * Each character in genes, with mutationRate chance
     * is replaced by a random character.
     */
    public void mutate() {

        int select;
        char gene;

        if (mutationSuccess() & genes.first() != null) {

            select = random() % 29;
            gene = genePool.first();

            genes.first();

            for (int i = 0; i < select; i++) gene = genePool.next();

            select = random() % genes.size();

            for (int i = 0; i < select; i++) genes.next();

            genes.remove();
            genes.addBefore(gene);

        }

        if (mutationSuccess() & genes.first() != null) {

            select = random() % genes.size();

            for (int i = 0; i < select; i++) genes.next();

            genes.remove();

        }

        genes.first();

        while (genes.current() != null) {

            if(mutationSuccess()) {

                genes.remove();

                select = random() % 29;
                gene = genePool.first();

                for (int i = 0; i < select; i++) gene = genePool.next();

                genes.addBefore(gene);

            } else genes.next();
        }
    }

    /**
     * This updates genes by crossing it with the passed genome.
     * The new genes list is created by randomly selecting one
     * of the parent genomes and adding that character to the
     * new genes list; if the position does not exist in the
     * parent, the new list will end at that position.
     * @param parent the genome to be crossed with.
     */
    public void crossover(Genome parent) {

        MyLinkedList<Character> child = new MyLinkedList<>();
        Character mother = genes.first();
        Character father = parent.genes.first();

        while (mother != null) {

            if (random() % 2 == 0) child.addToEnd(mother);

            else if (father != null) child.addToEnd(father);

            else break;

            mother = genes.next();
            father = parent.genes.next();

        }

    }

    /**
     * Returns the fitness level of the genome using
     * the following formula: -(l + d), where l is the
     * difference in length between the target and the
     * genes and d is the number of characters that
     * are incorrect.
     * @return the fitness level.
     */
    public int fitness() {

        int d = 25;
        int l = target.size() - genes.size();

        Character self = genes.first();
        Character other = target.first();

        while (self != null & other != null) {

            if (self.compareTo(other) == 0) d--;

            self = genes.next();
            other = target.next();

        }

        while (self != null) {

            d++;
            self = genes.next();

        }

       return -(l + d);
    }

    /**
     * Compares the fitness level of the genome with
     * the fitness level of the other genome.
     * @param other the genome to be compared with.
     * @return a negative integer, zero, or a positive
     * integer as this genome's fitness level is less
     * than, equal to, or greater than the specified
     * genome's fitness level.
     */
    public int compareTo(Genome other) {    return this.fitness() - other.fitness();        }

    /**
     * Displays the genome and its fitness level
     * in this format: ("genes", fitness)
     * @return the string format of the genome.
     */
    public String toString() {

        if (genes.isEmpty()) return "(\"\", -50)";

        StringBuilder string = new StringBuilder("(");
        String genome = genes.toString();

        genome = genome.replace('[', '"');
        genome = genome.replace(']', '"');
        genome = genome.replaceAll(",", "");

        string.append(genome).append(", ").append(this.fitness()).append(')');

        return string.toString();

    }
}
