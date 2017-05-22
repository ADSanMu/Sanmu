package datastructure.resizablearray;

import java.util.Arrays;

/**
 * @Author Sanmu
 * @Date 2017/5/22 0022.
 */
public class ResizableArray {

    private int offset;
    private int capacity;
    private int length;
    private byte[] container;

    public ResizableArray(int offset, int capacity, byte[] container) {
        this.offset = offset;
        this.capacity = capacity;
        this.container = container;
    }

    public int writeToArray() {

        return 0;
    }

    public int getOffset() {
        return offset;
    }

    void setOffset(int offset) {
        this.offset = offset;
    }

    public int getCapacity() {
        return capacity;
    }

    void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public byte[] getContainer() {
        return container;
    }

    void setContainer(byte[] container) {
        this.container = container;
    }

    public int getLength() {
        return length;
    }

    void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "ResizableArray{" +
                "offset=" + offset +
                ", capacity=" + capacity +
                ", length=" + length +
                ", container=" + Arrays.toString(container) +
                '}';
    }
}
