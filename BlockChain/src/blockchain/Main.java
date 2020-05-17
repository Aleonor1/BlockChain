package blockchain;
 
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
 
public class Main {
    public static void main(String[] args) {
        try {
            int threadCount = Runtime.getRuntime().availableProcessors();
            ExecutorService executor = Executors.newFixedThreadPool(threadCount);
            BlockChain blockChain = new BlockChain();
            for (int i = 0; i < threadCount / 2; i++) {
                executor.submit(new Miner(blockChain, i));
                executor.submit(new Chatter(blockChain, "Chatter " + i));
            }
            System.out.println(executor.getClass().getName());
            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.SECONDS);
            int i = 0;
            for (Block block: blockChain.blocks()) {
                if (i++ == 6) {
                    break;
                }
                System.out.println(block);
                System.out.println();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}