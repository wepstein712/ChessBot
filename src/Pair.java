public class Pair <T, S> {
    T t;
    S s;
    Pair() {

    }

    Pair(T t, S s) {
        this.t = t;
        this.s = s;
    }

    public T getFirst() {
        return this.t;
    }
    public S getSecond() {
        return this.s;
    }
    public void setFirst(T t) {
        this.t = t;
    }
    public void setSecond(S s) {
        this.s = s;
    }


}
