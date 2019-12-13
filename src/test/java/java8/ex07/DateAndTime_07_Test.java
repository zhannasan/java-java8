package java8.ex07;

import org.junit.Test;

import java.time.*;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Exercice 07 - Instant
 */
public class DateAndTime_07_Test {

    @Test
    public void test_date_to_localdate() throws Exception {

        // TODO Créer une date Java 1 (12/02/2017)
        Date date = new Date(117,1,12);

        // TODO transformer la date en Instant
        Instant inst = date.toInstant();
        // TODO transformer la date en LocalDate

        LocalDate result = inst.atZone(ZoneId.systemDefault()).toLocalDate();

        // TODO valoriser les différentes variables afin de rendre le test passant

        assertThat(result.getYear(), is(2017));
        assertThat(result.getMonth(), is(Month.FEBRUARY));
        assertThat(result.getDayOfMonth(), is(12));

    }
}
