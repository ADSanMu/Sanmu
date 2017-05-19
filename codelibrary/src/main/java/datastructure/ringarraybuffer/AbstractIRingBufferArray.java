package datastructure.ringarraybuffer;

/**
 * @Author Sanmu
 * @Date 2017/5/15 0015.
 */
public abstract class AbstractIRingBufferArray<E> implements IRingBufferArray<E> {

    private static final String READMARK = "R";
    private static final String WRITEMARK = "W";

    protected volatile Object[] container;
    protected int readOps;
    protected int writeOps;
    protected int available;
    protected int capacity;

    public AbstractIRingBufferArray() {
        this(IRingBufferArray.DEFAULT_SIZE);
    }

    public AbstractIRingBufferArray(int capacity) {
        this.capacity = capacity;
        this.container = new Object[capacity];

    }


    public int getReadOps() {
        return readOps < this.capacity ? readOps : 0;
    }

    public int getWriteOps() {
        return writeOps < this.capacity ? writeOps : 0;
    }

    protected int movePosition(int currentPosition, int increasePosition) {
        currentPosition += increasePosition;
        if (currentPosition >= this.capacity) {
            currentPosition -= this.capacity;
        }
        return currentPosition;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        int writeOps = this.getWriteOps();
        int readOps = this.getReadOps();
        for (int i = 0; i < this.capacity; i++) {
            builder.append(this.container[i]);
            if (i == writeOps) {
                builder.append("(").append(i).append(":").append(WRITEMARK).append(")");
            }
            if (i == readOps) {
                builder.append("(").append(i).append(":").append(READMARK).append(")");
            }
            if (i != this.capacity - 1) {
                builder.append(",");
            }
        }
        builder.append("]");
        return builder.toString();
    }

    protected void setNull(int startIdx, int endIdx) {
        for (int i = startIdx; i < endIdx; i++) {
            this.container[i] = null;
        }
    }

    @Override
    public boolean isFlip() {
        return this.getReadOps() > this.getWriteOps() || (this.getReadOps() == this.getWriteOps() && this.freedomSize() == 0);
    }

    @Override
    public int remainAvailable() {
        return this.available;
    }

    @Override
    public int capacity() {
        return this.capacity;
    }

    @Override
    public boolean put(E element) {
        throw new RuntimeException("please impl this method");
    }

    @Override
    public int putAll(E[] elements, int length) {
        throw new RuntimeException("please impl this method");
    }

    @Override
    public E take() {
        throw new RuntimeException("please impl this method");
    }

    @Override
    public int takeAll(E[] container, int from, int length) {
        throw new RuntimeException("please impl this method");
    }

    @Override
    public E[] toArray() {
        throw new RuntimeException("please impl this method");
    }

}
