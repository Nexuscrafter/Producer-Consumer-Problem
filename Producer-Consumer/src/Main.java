import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {

        Object key = new Object();
        Queue<Integer> queue = new LinkedList<>(); //storage place
        int size = 10; //max size of storage

       Thread producer = new Thread(new Runnable() {
           @Override
           public void run() {
               int count = 0;
               //critical section
               while(true){
                   synchronized (key){
                       try{
                           while (queue.size() == size){
                               key.wait();
                           }
                           queue.offer(count++);
                           key.notifyAll();   //to notify consumer to consume as storage has products to be consumed

                           Thread.sleep(1000);
                           System.out.println("Goods produced, storage size="+ queue.size());
                       }catch (Exception e){
                           e.printStackTrace();
                       }

                   }

               }

           }
       });

       Thread consumer = new Thread(new Runnable() {


           @Override
           public void run() {
               //critical section
               while(true){
                   synchronized (key){

                       try{
                           while (queue.size()==0){
                               key.wait();
                           }
                           queue.poll();
                           key.notifyAll(); //to notify producer thread to begin production once the storage has empty space
                           Thread.sleep(800);
                           System.out.println("Goods consumed, storage size="+ queue.size());
                       }catch (Exception e){
                           e.printStackTrace();
                       }


                   }

               }

           }
       });

       producer.start();
       consumer.start();
    }
}