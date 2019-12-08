package java8.ex05;

import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;


/**
 * Exercice 06 - Stream Parallel
 */
public class Stream_05_Test {

    private static final long NB = 10_000_000;

    // Soit une méthode impérative qui permet de construire une somme des chiffres de 1 à n
    private long imperativeSum(long n) {
        long result = 0;

        for (long i = 1L; i < n; i++) {
            result += i;
        }
        return result;
    }

    // TODO compléter la méthode iterateSum
    // TODO utiliser la méthode Stream.iterate
    // TODO cette méthode doit produire le même résultat que imperativeSum
    private long iterateSum(long n) {
    	Long result = Stream.iterate(1L, i->i+1).limit(n-1).reduce(0L, Long::sum);
        return result;
    }

    // TODO exécuter le test pour vérifier que les méthodes imperativeSum et iterateSum produisent le même résultat
    @Test
    public void test_imperativeSum_vs_iterateSum() {

        Stream.of(1L, 1000L, NB).forEach(n -> {
            long result1 = imperativeSum(n);
            long result2 = iterateSum(n);

            assertThat(result1, is(result2));
        });
    }

    // TODO compléter la méthode parallelIterateSum
    // TODO utiliser la méthode Stream.iterate
    // TODO transformer en stream parallel (.parallel())
    private long parallelIterateSum(long n) {
    	Long result = Stream.iterate(1L, i->i+1).limit(n-1).parallel().reduce(0L, Long::sum);
        return result;
    }

    // TODO exécuter le test pour vérifier que les méthodes imperativeSum, iterateSum et parallelIterateSum produisent le même résultat
    @Test
    public void test_imperativeSum_vs_iterateSum_vs_parallelIterateSum() {

        Stream.of(1L, 1000L, NB).forEach(n -> {
            long result1 = imperativeSum(n);
            long result2 = iterateSum(n);
            long result3 = parallelIterateSum(n);

            assertThat(result1, is(result2));
            assertThat(result1, is(result3));
        });
    }

    // Essayons maintenant d'avoir une indication sur les performances des 3 traitements

    // Voici une méthode qui exécute 10 fois un traitement et retourne le meilleur temps (le plus court)
    private long monitor(Consumer<Long> fn, long n) {

        long fastest = Long.MAX_VALUE;

        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            fn.accept(n);
            long end = System.nanoTime();
            long duration = (end - start) / 1_000_000;
            if (duration < fastest) fastest = duration;
        }
        return fastest;
    }

    // TODO compléter le test pour invoquer la méthode monitor dans chaque cas
    // TODO visualiser les temps d'exécution
    Consumer<Long> iterateSumC = n->Stream.iterate(1L, i->i+1).limit(n-1).reduce(0L, Long::sum);
    Consumer<Long> imperativeSumC= n-> {
        long result = 0;
        for (long i = 1L; i < n; i++) {
            result += i;
        }
    };
    Consumer<Long> parallelIterateSumC = n-> Stream.iterate(1L, i->i+1).limit(n-1).parallel().reduce(0L, Long::sum);
     
    @Test
    public void monitor_imperativeSum_vs_iterateSum_vs_parallelIterateSum() {
        Logger.getAnonymousLogger().info("imperativeSum => " + monitor(imperativeSumC, NB)+" ms");
        Logger.getAnonymousLogger().info("iterateSum => " + monitor(iterateSumC, NB)+" ms");
        Logger.getAnonymousLogger().info("parallelIterateSum => " +  monitor(parallelIterateSumC, NB)+" ms");
    }

    // Quel résultat obtenez-vous ?
    // Sur ma machine, le gagnant est ... imperativeSum ! pareil!
    // INFO: imperativeSum => 3 ms vs INFOS: imperativeSum => 6 ms
    // INFO: iterateSum => 103 ms vs INFOS: iterateSum => 103 ms
    // INFO: parallelIterateSum => 103 ms vs INFOS: parallelIterateSum => 92 ms

    // Ecrivons à présent, autrement cette somme

    // TODO compléter la méthode rangeSum
    // TODO utiliser la méthode LongStream.rangeClosed
    private long rangeSum(long n) {
        return LongStream.rangeClosed(0, n-1).reduce(0L, Long::sum);
    }

    // TODO vérifier que l'implémentation de rangeSum
    @Test
    public void test_imperativeSum_vs_rangeSum() {

        Stream.of(1L, 20L, 1000L, NB).forEach(n -> {
            long result1 = imperativeSum(n);
            long result2 = rangeSum(n);

            assertThat(result1, is(result2));
        });
    }

    // TODO compléter la méthode rangeSum
    // TODO utiliser la méthode LongStream.rangeClosed
    // TODO transformer en stream parallel (.parallel())
    private long rangeParallelSum(long n) {
        return LongStream.rangeClosed(0, n-1).parallel().reduce(0L, Long::sum);
    }

    // TODO vérifier que l'implémentation de rangeParallelSum
    @Test
    public void test_imperativeSum_vs_rangeSum_vs_rangeParallelSum() {

        Stream.of(1L, 20L, 1000L, NB).forEach(n -> {
            long result1 = imperativeSum(n);
            long result2 = rangeSum(n);
            long result3 = rangeParallelSum(n);

            assertThat(result1, is(result2));
            assertThat(result1, is(result3));
        });
    }

    Consumer<Long> rangeSum = n-> LongStream.rangeClosed(0, n-1).reduce(0L, Long::sum);
    Consumer<Long> rangeParallelSum = n-> LongStream.rangeClosed(0, n-1).parallel().reduce(0L, Long::sum);
    
    @Test
    public void monitor_imperativeSum_vs_iterateSum_vs_parallelIterateSum_vs_rangeSum_vs_rangeParallelSum() {
        Logger.getAnonymousLogger().info("imperativeSum => " + monitor(imperativeSumC, NB)+" ms");
        Logger.getAnonymousLogger().info("iterateSum => " + monitor(iterateSumC, NB)+ " ms");
        Logger.getAnonymousLogger().info("parallelIterateSum => " +monitor(parallelIterateSumC, NB)+ " ms");
        Logger.getAnonymousLogger().info("rangeSum => " +monitor(rangeSum, NB)+" ms");
        Logger.getAnonymousLogger().info("rangeParallelSum => "+ monitor(rangeParallelSum, NB) + " ms");
    }

    // Quel résultat obtenez-vous ?
    // Sur ma machine, le gagnant est ... rangeParallelSum ! aussi!
    // INFO: imperativeSum => 3 ms vs INFOS: imperativeSum => 6 ms
    // INFO: iterateSum => 100 ms vs INFOS: iterateSum => 95 ms
    // INFO: parallelIterateSum => 90 ms vs INFOS: parallelIterateSum => 72 ms
    // INFO: rangeSum => 4 ms vs INFOS: rangeSum => 3 ms
    // INFO: rangeParallelSum => 1 ms vs INFOS: rangeParallelSum => 1 ms

    // Les performances de traitements en parallèle dépendent de la capacité d'une structure à se décomposer.
    // Stream.iterate() conçu pour générer un flux continue infinie ne se décompose pas alors qu'une structure finie comme
    // LongStream.rangeClosed se décompose aisément.
}
