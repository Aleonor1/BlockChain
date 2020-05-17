package blockchain;
 
<<<<<<< Upstream, based on origin/master
import java.util.concurrent.Executors;
=======
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
>>>>>>> 0073c33 executor multi-threading add chatter bot add
import java.util.concurrent.TimeUnit;
 
public class Main {
    public static void main(String[] args) {
        try {
<<<<<<< Upstream, based on origin/master
            var threadCount = Runtime.getRuntime().availableProcessors();
            var executor = Executors.newFixedThreadPool(threadCount);
            var blockChain = new BlockChain();
            for (var i = 0; i < threadCount / 2; i++) {
                executor.submit(new Miner(blockChain, i));
                executor.submit(new Chatter(blockChain, "Chatter " + i));
            }
            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.SECONDS);
            var i = 0;
            for (var block: blockChain.blocks()) {
                if (i++ == 5) {
=======
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
>>>>>>> 0073c33 executor multi-threading add chatter bot add
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