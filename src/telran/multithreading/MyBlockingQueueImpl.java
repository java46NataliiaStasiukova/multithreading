package telran.multithreading;

import java.util.*;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

public class MyBlockingQueueImpl<E> implements BlockingQueue<E> {
private List<E> queue = new LinkedList<>();
private Lock monitor = new ReentrantLock();
private Condition producerWaitingCondition = monitor.newCondition();
private Condition consumerWaitingCondition = monitor.newCondition();
private final int HEAD = 0;
private int capacity;

	public MyBlockingQueueImpl(int capacity) {
		this.capacity = capacity;
	}
	public MyBlockingQueueImpl() {
		this(Integer.MAX_VALUE);
	}
	
	@Override
	public E remove() {
		monitor.lock();
		try {
			if(queue.size() == 0) {
				throw new NoSuchElementException();
			}
			return queue.remove(HEAD);
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public E poll() {
		monitor.lock();
		try {
			E res = null;
			if(queue.size() != 0) {
				res = queue.get(HEAD);
				queue.remove(HEAD);
			}
			producerWaitingCondition.signal();
			return res;
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public E element() {
		monitor.lock();
		try {
			return queue.get(HEAD);
		} catch(Exception e) {
			throw new NoSuchElementException();
		} finally {
			monitor.unlock();
		} 
	}

	@Override
	public E peek() {
		monitor.lock();
		try {
			return queue.size() == 0 ? null : queue.get(HEAD);
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public int size() {
		monitor.lock();
		try {
			return queue.size();
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public boolean isEmpty() {
		monitor.lock();
		try {
			return queue.size() == 0;
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public Iterator<E> iterator() {
		monitor.lock();
		try {
			return queue.iterator();
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public Object[] toArray() {
		monitor.lock();
		try {
			return queue.toArray();
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public <T> T[] toArray(T[] a) {
		monitor.lock();
		try {
			return queue.toArray(a);
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		monitor.lock();
		try {
			return queue.containsAll(c);
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		monitor.lock();
		try {
			if(capacity - queue.size() < c.size()) {
				return false;
			}
			return queue.addAll(c);
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		monitor.lock();
		try {
			return queue.removeAll(c);
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		monitor.lock();
		try {
			return queue.retainAll(c);
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public void clear() {
		monitor.lock();
		try {
			queue.clear();
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public boolean add(E e) {
		monitor.lock();
		try {
			if(queue.size() == capacity) {
				throw new IllegalStateException();
			}
			boolean res = queue.add(e);
			consumerWaitingCondition.signal();
			return res;
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public boolean offer(E e) {
		monitor.lock();
		try {
			if(queue.size() == capacity) {
				return false;
			}
			queue.add(e);
			consumerWaitingCondition.signal();
			return true;
		} finally {
			monitor.unlock();
		}
		
	}

	@Override
	public void put(E e) throws InterruptedException {
		monitor.lock();
		try {
			while(queue.size() == capacity) {
				producerWaitingCondition.await();
			}
			queue.add(e);
			consumerWaitingCondition.signal();
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
		monitor.lock();
		try {
			while(queue.size() == capacity) {
				producerWaitingCondition.await(timeout, unit);
			}
			if(queue.size() == capacity) {
				return false;
			}
			queue.add(e);
			consumerWaitingCondition.signal();
			return true;
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public E take() throws InterruptedException {
		monitor.lock();
		try {	
			while(queue.size() == 0) {
				consumerWaitingCondition.await();
			}
			E res = queue.get(HEAD);
			queue.remove(HEAD);
			producerWaitingCondition.signal();
			return res;
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public E poll(long timeout, TimeUnit unit) throws InterruptedException {
		monitor.lock();
		try {
			E res = null;
			while(queue.size() == 0) {
				consumerWaitingCondition.await(timeout, unit);
			}
			if(queue.size() != 0) {
				res = queue.get(HEAD);
				queue.remove(HEAD);
			}
			producerWaitingCondition.signal();
			return res;
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public int remainingCapacity() {		
		monitor.lock();
		try {
			return capacity - queue.size();
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public boolean remove(Object o) {
		// No implement
		return false;
	}

	@Override
	public boolean contains(Object o) {	
		monitor.lock();
		try {
			return queue.contains(o);
		} finally {
			monitor.unlock();
		}
	}

	@Override
	public int drainTo(Collection<? super E> c) {
		// No implement
		return 0;
	}

	@Override
	public int drainTo(Collection<? super E> c, int maxElements) {
		// No implement
		return 0;
	}

}
