package oh29oh29.study01;

/**
 * Generic Class
 * */
public class GenericsType<T> {

    private T t;

    public void set(T t1) {
        this.t = t1;
    }

    public T get() {
        return this.t;
    }

}