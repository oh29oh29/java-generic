package oh29oh29.study02;

/**
 * Generic Method
 * */
public class GenericsMethods {

    public static <T> boolean isEqual(T t1, T t2) {
        return t1.equals(t2);
    }

}