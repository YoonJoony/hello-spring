package hello.hellospring.javatest;

public class ramda {
    public static void main(String[] args) {
        /**
        Myfuntion f = new Myfuntion() {
            public int max(int a, int b) {
                return a + b;
            }
        };
        **/

        Myfuntion f = (a, b) -> a > b ? a : b;

        int v = f.max(1, 2);
    }
}

interface Myfuntion {
    int max(int a, int b);
}
