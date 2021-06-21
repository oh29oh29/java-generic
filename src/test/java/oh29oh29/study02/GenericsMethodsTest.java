package oh29oh29.study02;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GenericsMethodsTest {

    @Test
    void GenericMethod_example() {
        String s1 = "자바";
        String s2 = "자바";

        assertTrue(GenericsMethods.isEqual(s1, s2));
    }

    @Test
    void GenericMethod_example2() {
        String s1 = "자바";
        String s2 = "코틀린";

        assertFalse(GenericsMethods.isEqual(s1, s2));
    }

    @Test
    void GenericMethod_example3() {
        int i1 = 10;
        int i2 = 10;

        assertTrue(GenericsMethods.isEqual(i1, i2));
    }

    @Test
    void GenericMethod_example4() {
        int i1 = 10;
        int i2 = 20;

        assertFalse(GenericsMethods.isEqual(i1, i2));
    }

    @Test
    void GenericMethod_example5() {
        int i1 = 10;
        String s1 = "십";

        assertFalse(GenericsMethods.isEqual(i1, s1));
    }
}
