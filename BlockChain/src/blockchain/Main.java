package blockchain;
 
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
 
public class Main {
    public static void main(String[] args) {
        try {
            int threadCount = Runtime.getRuntime().availableProcessors();
            ExecutorService executor = Executors.newFixedThreadPool(5);
            BlockChain blockChain = new BlockChain();
            for (int i = 0; i < threadCount / 2; i++) {
                executor.submit(new Miner(blockChain, i));
                executor.submit(new Chatter(blockChain, "Chatter " + i));
            }

            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
           // if (blockChain.blocks() instanceof Collection) {
             //   System.out.println( ((Collection<?>) blockChain.blocks()).size());
            //}
            int i = 0;
            for (Block block: blockChain.blocks()) {
                System.out.println(block);
                System.out.println();
                if (i++ == 4) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}