package datastructure.ringarraybuffer;

/**
 * @Author Sanmu
 * @Date 2017/5/15 0015.
 */
public class DefaultIRingBufferArrayImpl<E> extends AbstractIRingBufferArray<E> {


    public DefaultIRingBufferArrayImpl(int capacity) {
        super(capacity);
    }

    @Override
    public boolean put(E element) {
        if (this.freedomSize() != 0) {
            if (this.writeOps >= this.capacity) {
                this.writeOps = 0;
            }
            this.container[this.writeOps] = element;
            this.writeOps++;
            this.available++;
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E take() {
        if (!this.isEmpty()) {
            int readOps = this.getReadOps();
            Integer element = (Integer) this.container[readOps];
            this.available--;
            this.container[readOps] = null;//remove the take element.
            return (E) element;
        }
        return null;
    }

    @Override
    public int putAll(E[] elements, int length) {
        int availableLength = Math.min(length, this.freedomSize());
        if (availableLength > 0) {
            if (this.writeOps >= this.getReadOps()) {// not wrapped around
                int rightLeftSize = this.container.length - this.writeOps;
                if (rightLeftSize < availableLength) {//the left size is not enough for elements.
                    System.arraycopy(elements, 0, this.container, this.writeOps, rightLeftSize);
                    System.arraycopy(elements, rightLeftSize, this.container, 0, this.getReadOps());
                } else {
                    System.arraycopy(elements, 0, this.container, this.writeOps, availableLength);
                }

            } else {//wrapped around
                System.arraycopy(elements, 0, this.container, this.writeOps, availableLength);
            }
        }
        this.available += availableLength;
        this.writeOps = this.movePosition(this.writeOps, availableLength);
        return availableLength;
    }

    @Override
    public int takeAll(E[] container, int from, int length) {
        E[] availableElements = this.toArray();
        int availableLength = Math.min(length, this.available);
        System.arraycopy(availableElements, 0, container, from, availableLength);
        int readOps = this.getReadOps();
        if (this.isFlip()) {
            int rightSize = this.capacity - readOps;
            if (rightSize >= availableLength) {
                this.setNull(readOps, readOps + availableLength);
            } else {
                this.setNull(readOps, readOps + rightSize);
                this.setNull(0, availableLength - rightSize);
            }
        } else {
            this.setNull(readOps, readOps + availableLength);
        }
        this.available -= availableLength;
        return availableLength;
    }

    @Override
    public int getReadOps() {
        int nextReadIndex = this.writeOps - this.available;
        if (nextReadIndex < 0) {
            nextReadIndex += this.capacity;
        }
        this.readOps = nextReadIndex;
        return super.getReadOps();
    }

    @Override
    public void reset() {
        this.writeOps = 0;
        this.available = 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E[] toArray() {
        Object[] toArray = new Object[this.available];
        int readOps = this.getReadOps();
        int writeOps = this.getWriteOps();
        if (this.isFlip()) {// whether flip
            System.arraycopy(this.container, readOps, toArray, 0, this.capacity - readOps);
            System.arraycopy(this.container, 0, toArray, this.capacity - readOps, writeOps);
        } else {
            readOps = this.freedomSize() == 0 ? 0 : readOps;
            System.arraycopy(this.container, readOps, toArray, 0, this.available);
        }
        return (E[]) toArray;
    }
}
