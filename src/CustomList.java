package src;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Iterator;

public class CustomList<E> implements Collection<E> {
    private int size = 0;
    private ListObject<E> first;

    @Nullable
    @Contract(pure = true)
    private ListObject<E> getLast() {
        ListObject<E> it = first;
        for (int i = 0; i < size - 1; i++) {
            it = it.getNext();
        }
        return it;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        ListObject<E> it = first;
        while (true) {
            try {
                if (it.getValue().equals(o)) {
                    return true;
                }
                it = it.getNext();
            } catch (NullPointerException nex) {
                return false;
            }
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            private int currentindex = 0;

            @Override
            public boolean hasNext() {
                return currentindex < size;
            }

            @Override
            public E next() {
                return (E) get(currentindex);
            }
        };
    }

    @NotNull
    @Override
    public Object[] toArray() {
        ListObject<E> it = first;
        Object[] val = new Object[size];
        int last = 0;
        while (true) {
            try {
                val[last] = it.getValue();
                it = it.getNext();
                last++;
            } catch (NullPointerException nex) {
                return val;
            }
        }
    }

    public Object get(int index) {
        ListObject<E> it = first;
        if (index > size) {
            return new NullPointerException();
        }
        for (int i = 0; i < index; i++) {
            it = it.getNext();
        }
        return it;
    }

    @Override
    public boolean add(E o) {
        try {
            this.getLast().bind(ListObject.makeObject(size, o));
            size++;
            return true;
        } catch (NullPointerException nex) {
            first = ListObject.makeObject(size, o);
            size++;
            return true;
        }
    }

    @Override
    public boolean remove(Object o) {
        ListObject<E> it = first;
        ListObject<E> before = it, after = it.getNext();
        while (true) {
            if (after == null) {
                if (after.equals(o)) {
                    first = null;
                    return true;
                }
                return false;
            }
            try {
                if (it.getValue().equals(o)) {
                    before.bind(after);
                    size--;
                }
                before = it;
                after = it.getNext();
                it = it.getNext();
            } catch (NullPointerException nex) {
                return true;
            }
        }
    }

    @Override
    public boolean addAll(@NotNull Collection c) {
        return false;
    }

    @Override
    public void clear() {
        first = null;
        size = 0;
    }

    @Override
    public boolean retainAll(@NotNull Collection c) {
        return false;
    }

    @Override
    public boolean removeAll(@NotNull Collection c) {
        return false;
    }

    @Override
    public boolean containsAll(@NotNull Collection c) {
        return false;
    }

    @NotNull
    @Override
    public Object[] toArray(@NotNull Object[] a) {
        return new Object[0];
    }

    @Override
    public String toString() {
        ListObject<E> it = first;
        String s = "[";
        for (int i = 0; i < size; i++) {
            s += it.toString() + ", ";
            it = it.getNext();
        }
        return s.substring(0, s.length() - 2) + "]";
    }
}
