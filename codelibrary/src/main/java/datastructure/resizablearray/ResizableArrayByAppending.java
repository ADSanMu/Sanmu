package datastructure.resizablearray;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO the key of the problem is how to expend the buffer size ,how big buffer should i choose
 *
 * @Author Sanmu
 * @Date 2017/5/22 0022.
 */
public class ResizableArrayByAppending {

    private static final int KB = 1024;
    private static final int MB = KB << 10;
    private static final int DEFAULT_SMALL_BLOCK_COUNT = 1024;
    private static final int DEFAULT_MEDIUM_BLOCK_COUNT = 32;
    private static final int DEFAULT_LARGE_BLOCK_COUNT = 4;
    private static final int DEFAULT_SMALL_BLOCK_SIZE = KB << 2;
    private static final int DEFAULT_MEDIUM_BLOCK_SIZE = KB << 7;
    private static final int DEFAULT_LARGE_BLOCK_SIZE = MB;
    private final List<byte[]> smallArrays;
    private final List<byte[]> mediumArrays;
    private final List<byte[]> largeArrays;

    public ResizableArrayByAppending() {
        this(DEFAULT_SMALL_BLOCK_SIZE, DEFAULT_SMALL_BLOCK_COUNT, DEFAULT_MEDIUM_BLOCK_SIZE, DEFAULT_MEDIUM_BLOCK_COUNT, DEFAULT_LARGE_BLOCK_SIZE, DEFAULT_LARGE_BLOCK_COUNT);
    }

    public ResizableArrayByAppending(int smallBlockSize, int smallBlockCount, int mediumBlockSize, int mediumBlockCount, int largeBlockSize, int largeBlockCount) {
        this.smallArrays = new ArrayList<>(smallBlockCount);
        this.mediumArrays = new ArrayList<>(mediumBlockCount);
        this.largeArrays = new ArrayList<>(largeBlockCount);
        for (int i = 0; i < smallArrays.size(); i++) {
            smallArrays.add(new byte[smallBlockSize]);
        }
        for (int i = 0; i < mediumArrays.size(); i++) {
            smallArrays.add(new byte[mediumBlockSize]);
        }
        for (int i = 0; i < largeArrays.size(); i++) {
            smallArrays.add(new byte[largeBlockSize]);
        }

    }


}
