package stack;

/**
 * A {@link LinkedStack} is a stack that is implemented using
 * a Linked List structure to allow for unbounded size.
 * <p></p>
 * The {@code isEmpty} and {@code size} methods are provided,
 * on the house.
 * @param <T> the elements stored in the stack
 */
public class LinkedStack<T> implements StackInterface<T> {

    private Node<T> first;
    private int size;

    /**
     * {@inheritDoc}
     * Removes the top most element on this stack. For convenience,
     * the top most element is returned. Implements pop similar to a
     * LinkedList's removeFirst (a special case of remove). If the list is
     * empty, throws StackUnderflowException.
     * @return the top most element of this stack
     * @throws StackUnderflowException if the stack is empty
     */
    @Override
    public T pop() throws StackUnderflowException {
        // Implement the pop method, which will be
        // similar to a removeFirst method for a LinkedList
        // (a special case of our remove method).
        // If the list is empty you should throw an exception.
        if (isEmpty()) {
            throw new StackUnderflowException("Cannot pop from an empty stack.");
        }
        T element = this.first.getElement();
        this.first = this.first.getNext();
        this.size--;
        return element;
    }

    /**
     * {@inheritDoc}
     * Returns the top most element of this stack. Implements top similar to a
     * LinkedList's getFirst (a special case of get). If the list is empty,
     * throws StackUnderflowException.
     * @return the top most element of this stack
     * @throws StackUnderflowException if the stack is empty
     */
    @Override
    public T top() throws StackUnderflowException {
        // Implement the top method, which will be
        //   similar to a getFirst method for a LinkedList
        //   (a special case of our get method).
        //   If the list is empty you should throw an exception.
        if (isEmpty()) {
            throw new StackUnderflowException("Cannot examine top of empty stack.");
        }
        return this.first.getElement();
    }

    /**
     * {@inheritDoc}
     * Pushes {@code elem} to the top of this stack. Implements push similar to a
     * LinkedList's addFirst. If {@code elem} is null, throws NullPointerException.
     * @param elem the element to push onto the stack
     * @throws NullPointerException if {@code elem} is null
     */
    @Override
    public void push(T elem) {
        // Implement the push method, which will be
        //   similar to the addFirst method for a LinkedList.
        //   If elem is null you should throw an exception.
        if (elem == null) {
            throw new NullPointerException("Cannot push a null element onto the stack.");
        }

        Node<T> newNode = new Node<>(elem);
        newNode.setNext(this.first);
        this.first = newNode;
        this.size++;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public int size() {
        return this.size;
    }
}