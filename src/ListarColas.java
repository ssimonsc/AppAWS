import com.amazonaws.services.sqs.AmazonSQS;

import java.util.HashMap;
import java.util.Map;

public class ListarColas {
    public static Map<String,String> listarColas(AmazonSQS clienteSQS) {
        System.out.println("Mostrando todas as colas");
        Map<String,String> colas = new HashMap<String, String>();
        String nomeCola;
        for (final String colaUrl : clienteSQS.listQueues().getQueueUrls()) {
            nomeCola = colaUrl.split("/")[4];
            colas.put(nomeCola,colaUrl);
            System.out.println(nomeCola + ": " + colaUrl);
        }
        System.out.println();
        return colas;
    }
}
