package datastructure.resizablearray;

import datastructure.ringarraybuffer.DefaultIRingBufferArrayImpl;
import datastructure.ringarraybuffer.IRingBufferArray;

/**
 * @Author Sanmu
 * @Date 2017/5/22 0022.
 */
public class ResizableArrayByCoping {

    private static final int KB = 1024;
    private static final int MB = KB << 10;
    private static final int DEFAULT_SMALL_BLOCK_SIZE = KB << 2;
    private static final int DEFAULT_MEDIUM_SIZE = KB << 7;
    private static final int DEFAULT_LARGE_BLOCK_SIZE = MB;
    private static final int DEFAULT_SMALL_BLOCK_COUNT = 1024;
    private static final int DEFAULT_MEDIUM_COUNT = 32;
    private static final int DEFAULT_LARGE_BLOCK_COUNT = 4;
    private final int smallBlockSize;
    private final int mediumBlockSize;
    private final int largeBlockSize;
    private final byte[] smallContainer;
    private final byte[] mediumContainer;
    private final byte[] largeContainer;

    private final IRingBufferArray<Integer> smallBlockSign;
    private final IRingBufferArray<Integer> mediumBlockSign;
    private final IRingBufferArray<Integer> largeBlockSign;

    public ResizableArrayByCoping() {
        this(DEFAULT_SMALL_BLOCK_SIZE, DEFAULT_SMALL_BLOCK_COUNT, DEFAULT_MEDIUM_SIZE, DEFAULT_MEDIUM_COUNT, DEFAULT_LARGE_BLOCK_SIZE, DEFAULT_LARGE_BLOCK_COUNT);
    }

    public ResizableArrayByCoping(int smallBlockSize, int smallBlockCount, int mediumBlockSize, int mediumBlockCount, int largeBlockSize, int largeBlockCount) {
        this.smallBlockSize = smallBlockSize;
        this.mediumBlockSize = mediumBlockSize;
        this.largeBlockSize = largeBlockSize;
        this.smallContainer = new byte[smallBlockSize * smallBlockCount];
        this.mediumContainer = new byte[mediumBlockSize * mediumBlockCount];
        this.largeContainer = new byte[largeBlockSize * largeBlockCount];
        this.smallBlockSign = new DefaultIRingBufferArrayImpl<>(smallBlockCount);
        this.mediumBlockSign = new DefaultIRingBufferArrayImpl<>(mediumBlockCount);
        this.largeBlockSign = new DefaultIRingBufferArrayImpl<>(largeBlockCount);

        //initialize cutting the container to blocks
        for (int i = 0; i < this.smallContainer.length; i += this.smallBlockSize) {
            smallBlockSign.put(i);
        }
        for (int i = 0; i < this.mediumContainer.length; i += this.mediumBlockSize) {
            mediumBlockSign.put(i);
        }
        for (int i = 0; i < this.largeContainer.length; i += this.largeBlockSize) {
            largeBlockSign.put(i);
        }

    }


    /**
     * the essential method of the class
     *
     * @return a ResizableArray instance
     */
    public ResizableArray getResizableArray() {
        Integer offset = this.smallBlockSign.take();
        if (offset != null) {
            return new ResizableArray(offset, this.smallBlockSize, this.smallContainer);
        }
        return null;
    }

    /**
     * expand the resizableArray bigger,this method will be called when the resizableArray's volume isn't
     * enough for writing data into it.
     *
     * @return isn't successful
     */
    public boolean expandArray(ResizableArray resizableArray) {
        int capacity = resizableArray.getCapacity();
        if (capacity == this.smallBlockSize) {
            return moveArrayTo(resizableArray, this.smallBlockSign, this.mediumBlockSign, this.mediumBlockSize, this.mediumContainer);
        } else
            return moveArrayTo(resizableArray, this.mediumBlockSign, this.largeBlockSign, this.largeBlockSize, this.largeContainer) && capacity == this.mediumBlockSize;
    }

    private boolean moveArrayTo(ResizableArray resizableArray, IRingBufferArray<Integer> srcBuffer, IRingBufferArray<Integer> descBuffer, int newCapacity, byte[] container) {
        // get bigger index
        Integer nextOffset = descBuffer.take();
        if (nextOffset == null) {
            return false;
        }
        //copy data
        System.arraycopy(resizableArray.getContainer(), resizableArray.getOffset(), container, nextOffset, resizableArray.getLength());

        //reset properties
        resizableArray.setCapacity(newCapacity);
        resizableArray.setOffset(nextOffset);
        resizableArray.setContainer(container);

        //release src index
        srcBuffer.put(resizableArray.getOffset());
        return true;
    }

    /**
     * free the buffer,especially when expand the buffer bigger ,the smaller buffer should be freed.
     *
     * @param resizableArray resizableArray which will be free
     */
    public void free(ResizableArray resizableArray) {
        int capacity = resizableArray.getCapacity();
        int offset = resizableArray.getOffset();
        if (capacity == this.smallBlockSize) {
            this.smallBlockSign.put(offset);
        } else if (capacity == this.mediumBlockSize) {
            this.mediumBlockSign.put(offset);
        } else {
            this.largeBlockSign.put(offset);
        }
    }

}
