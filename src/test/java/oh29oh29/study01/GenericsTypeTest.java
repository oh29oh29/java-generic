package oh29oh29.study01;

import org.junit.jupiter.api.Test;

public class GenericsTypeTest {

    @Test
    void GenericClass_Example() {
        final GenericsType<String> sample = new GenericsType<>();

        sample.set("TEST");
        System.out.println(sample.get());
    }

    @Test
    void GenericClass_Example2() {
        final GenericsType sample = new GenericsType();

        sample.set("TEST"); // valid
        System.out.println(sample.get() instanceof String);
        sample.set(10);     // valid and autoboxing support
        System.out.println(sample.get() instanceof Integer);
    }
}
