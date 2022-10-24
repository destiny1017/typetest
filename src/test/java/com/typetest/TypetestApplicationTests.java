package com.typetest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

class TypetestApplicationTests {

    @Test
    void queueTest() {
        int[] priorities = new int[] {1, 1, 2, 3, 2, 1};
        int target = 0;
        PriorityQueue<priorObject> queue = new PriorityQueue<>((o1, o2) -> {
            if(o1.getValue() < o2.getValue()) {
                return 1;
            } else if(o1.getValue() > o2.getValue()) {
                return -1;
            } else {
                return 0;
            }
        });


        for (int i = 0; i < priorities.length; i++) {
            queue.add(new priorObject(priorities[i], i));
        }

        for (int i = 1; i <= priorities.length; i++) {
            priorObject poll = queue.poll();
//            System.out.println("poll.getIndex() = " + poll.getIndex());
//            System.out.println("poll.getValue() = " + poll.getValue());
            if(poll.getIndex() == target) {
                System.out.println("i = " + i);
            }
        }

    }

    class priorObject {
        Integer value;
        int index;

        public priorObject(Integer value, int index) {
            this.value = value;
            this.index = index;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    @Test
    void queueTest2() {
        int[] priorities = new int[] {1, 1, 2, 3, 2, 1};
        int location = 0;

        Queue<priorObject> queue = new LinkedList<>();

        for (int i = 0; i < priorities.length; i++) {
            queue.add(new priorObject(priorities[i], i));
        }
        int cnt = 0;
        while(!queue.isEmpty()) {
            int top = 0;
            for (priorObject prior : queue) {
                int val = prior.getValue();
                if(top < val) {
                    top = val;
                }
            }
            priorObject peek = queue.peek();
            if(peek.getValue() >= top) {
                priorObject poll = queue.poll();
                cnt++;
                if(poll.getIndex() == location) {
//                    return cnt;
                    System.out.println(cnt);
                    break;
                }
            } else {
                queue.offer(queue.poll());
            }
        }

    }

    @Test
    void keySet() {
        HashMap<Integer, String> hashMap = new HashMap<>();
        hashMap.keySet();
        LinkedHashMap<Integer, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.keySet();
    }
}
