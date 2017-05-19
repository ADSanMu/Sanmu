package datastructure.ringarraybuffer;

/**
 * A IRingBufferArray is an array which is used as a queue,it has read position and
 * write position which make the next position where to read from or write to this ringBufferArray.
 * When the write position arrives the end of the array,reset the writing position to 0,the same to
 * the read position.
 * The action setting then write position or read position to zero is sometimes referred as "wrapped around",
 * hence the name is ring buffer.
 *
 * @Author Sanmu
 * @Date 2017/5/15 0015.
 */
public interface IRingBufferArray<E> {

    int DEFAULT_SIZE = 128;

    /**
     * @param element the element which may be put in ring buffer.
     * @return isn`t successfully
     */
    boolean put(E element);

    /**
     * @param elements the same as put.
     * @return the numbers of effecting
     */
    default int putAll(E[] elements) {
        return this.putAll(elements, elements.length);
    }

    /**
     * @param elements array contains a lot of container
     * @param length   the size put into ring buffer from container.
     * @return the numbers of effecting
     */
    int putAll(E[] elements, int length);

    /**
     * retrieve and remove the container in read position.
     * if it`s null in readPos,the readPos will not move on.
     *
     * @return the element which be retrieve and remove.
     */
    E take();

    /**
     * taking the container`s length number of container.
     *
     * @param container the container.
     * @return the number you actual you take.
     */
    default int takeAll(E[] container) {
        return this.takeAll(container, container.length);
    }

    /**
     * taking specify number of container.
     *
     * @param container the container.
     * @param length    the number you want take .
     * @return the number you actual you take.
     */
    default int takeAll(E[] container, int length) {
        return takeAll(container, 0, length);
    }

    /**
     * taking specify number of container.
     *
     * @param container the container.
     * @param length    the number you want take .
     * @param from      the start index insert into container.
     * @return the number you actual you take.
     */
    int takeAll(E[] container, int from, int length);

    /**
     * currently the numbers of container you could get from ring buffer.
     *
     * @return the numbers.
     */
    int remainAvailable();

    /**
     * Returns the number of container in this ring buffer.
     *
     * @return the number of container in this ring buffer.
     */
    int capacity();

    /**
     * @return the numbers of buffer left
     */
    default int freedomSize() {
        return this.capacity() - this.remainAvailable();
    }

    /**
     * Returns <tt>true</tt> if this ring buffer contains no container.
     *
     * @return <tt>true</tt> if this ring buffer contains no container
     */
    default boolean isEmpty() {
        return this.remainAvailable() == 0;
    }

    /**
     * @return an array containing left container in this ring buffer.and the elements is queued.
     */

    E[] toArray();

    /**
     * reset buffer,the method will reset write position、read position and available
     */
    void reset();

    /**
     * @return whether the ring buffer is flip normally the write position < read position meaning is flip
     */
    boolean isFlip();

}
