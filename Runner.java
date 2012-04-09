import java.awt.*;

public class Runner implements Runnable {

   

   Thread runner;
   int pause = 1000;
   private World world;
   
   public Runner(World w) {
	   world = w;
   }

   
   public void start() {
     if (runner == null) {
       runner = new Thread(this);
       runner.start();
     }
   }

   public void run() {
     Thread thisThread = Thread.currentThread();
     while (runner == thisThread) {
       //world.runRound();
       try {
         Thread.sleep(pause);
       } catch (InterruptedException e) { }
     }
   }

   public void stop() {
     if (runner != null) {
       runner = null;
     }
   }
 }