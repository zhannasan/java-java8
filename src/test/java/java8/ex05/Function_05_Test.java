package java8.ex05;

import java8.data.Data;
import java8.data.Person;
import org.junit.Test;

import java.util.List;
import java.util.function.Consumer;

/**
 * Exercice 5 - java.util.function.Consumer
 */
public class Function_05_Test {

    //tag::functions[]
    // TODO compléter la fonction
    // TODO modifier le mot de passe en "secret"
    Consumer<Person> changePasswordToSecret = p->p.setPassword("secret");


    // TODO compléter la fonction
    // TODO vérifier que l'age > 4 avec une assertion JUnit
     Consumer<Person> verifyAge = p-> p.getAge();

	@Test
	public void test_consumer1() throws Exception {

		List<Person> personList = Data.buildPersonList();
		
		List<Person> result = personList.stream().verifyAge.accept(p).filter(p-> p.getAge()>4);
		}
		
		assert ;

	}

    // TODO compléter la fonction
    // TODO vérifier que le mot de passe est "secret" avec une assertion JUnit
    Consumer<Person> verifyPassword = p-> p.getPassword();
    //end::functions[]


    @Test
    public void test_consumer() throws Exception {
        List<Person> personList = Data.buildPersonList();
        personList.forEach(p->p.setPassword("secret"));
        // TODO invoquer la méthode personList.forEach pour modifier les mots de passe en "secret"
        // personList.forEach...

        // TODO remplacer la boucle for par l'invocation de la méthode forEach
        // TODO Utiliser la méthode andThen pour chaîner les vérifications verifyAge et verifyPassword
        // personList.forEach...
        personList.forEach(Person p-> verifyAge.accept(p)).andforEach(p-> verifyPassword.accept(p));
        }
    }
}
