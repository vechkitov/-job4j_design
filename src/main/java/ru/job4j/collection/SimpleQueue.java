package ru.job4j.collection;

public class SimpleQueue<T> {

    private final SimpleStack<T> in = new SimpleStack<>();
    private final SimpleStack<T> out = new SimpleStack<>();

    public T poll() {
        moveElements(in, out);
        return out.pop();
    }

    public void push(T value) {
        moveElements(out, in);
        in.push(value);
    }

    private void moveElements(SimpleStack<T> src, SimpleStack<T> dest) {
        if (dest.size() == 0) {
            while (src.size() > 0) {
                dest.push(src.pop());
            }
        }
    }
}
