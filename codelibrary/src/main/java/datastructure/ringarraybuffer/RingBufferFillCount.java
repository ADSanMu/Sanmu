package datastructure.ringarraybuffer;

/**
 * @Author Sanmu
 * @Date 2017/5/16 0016.
 */
public class RingBufferFillCount {
    public Object[] elements = null;

    public int capacity = 0;
    public int writePos = 0;
    public int available = 0;

    public RingBufferFillCount(int capacity) {
        this.capacity = capacity;
        this.elements = new Object[capacity];
    }

    public void reset() {
        this.writePos = 0;
        this.available = 0;
    }

    public int remainingCapacity() {
        return this.capacity - this.available;
    }


    public boolean put(Object element) {

        if (available < capacity) {
            if (writePos >= capacity) {
                writePos = 0;
            }
            elements[writePos] = element;
            writePos++;
            available++;
            return true;
        }

        return false;
    }

    public int put(Object[] newElements) {
        return put(newElements, newElements.length);
    }

    public int put(Object[] newElements, int length) {
        int readPos = 0;
        if (this.writePos > this.available) {
            //space above writePos is all empty

            if (length <= this.capacity - this.writePos) {
                //space above writePos is sufficient to insert batch

                for (; readPos < length; readPos++) {
                    this.elements[this.writePos++] = newElements[readPos];
                }
                this.available += readPos;
                return length;

            } else {
                //both space above writePos and below writePos is necessary to use
                //to insert batch.

                int lastEmptyPos = writePos - available;

                for (; this.writePos < this.capacity; this.writePos++) {
                    this.elements[this.writePos] = newElements[readPos++];
                }

                //fill into bottom of array too.
                this.writePos = 0;

                int endPos = Math.min(length - readPos, capacity - available - readPos);
                for (; this.writePos < endPos; this.writePos++) {
                    this.elements[this.writePos] = newElements[readPos++];
                }
                this.available += readPos;
                return readPos;
            }
        } else {
            int endPos = this.capacity - this.available + this.writePos;

            for (; this.writePos < endPos; this.writePos++) {
                this.elements[this.writePos] = newElements[readPos++];
            }
            this.available += readPos;

            return readPos;
        }

    }


    public Object take() {
        if (available == 0) {
            return null;
        }
        int nextSlot = writePos - available;
        if (nextSlot < 0) {
            nextSlot += capacity;
        }
        Object nextObj = elements[nextSlot];
        available--;
        return nextObj;
    }


    public int take(Object[] into) {
        return take(into, into.length);
    }


    public int take(Object[] into, int length) {
        int intoPos = 0;

        if (available <= writePos) {
            int nextPos = writePos - available;
            int endPos = nextPos + Math.min(available, length);

            for (; nextPos < endPos; nextPos++) {
                into[intoPos++] = this.elements[nextPos];
            }
            this.available -= intoPos;
            return intoPos;
        } else {
            int nextPos = writePos - available + capacity;

            int leftInTop = capacity - nextPos;
            if (length <= leftInTop) {
                //copy directly
                for (; intoPos < length; intoPos++) {
                    into[intoPos] = this.elements[nextPos++];
                }
                this.available -= length;
                return length;

            } else {
                //copy top
                for (; nextPos < capacity; nextPos++) {
                    into[intoPos++] = this.elements[nextPos];
                }

                //copy bottom - from 0 to writePos
                nextPos = 0;
                int leftToCopy = length - intoPos;
                int endPos = Math.min(writePos, leftToCopy);

                for (; nextPos < endPos; nextPos++) {
                    into[intoPos++] = this.elements[nextPos];
                }

                this.available -= intoPos;

                return intoPos;
            }
        }
    }
}