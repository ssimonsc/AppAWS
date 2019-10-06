import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.*;


import java.util.HashMap;
import java.util.Map;

public class CreadorDeColasFIFO {
    public static void creadorColaFIFO(String nomeColaSQS, AmazonSQS clienteSQS) {
        /* Creamos unha cola FIFO en AWS*/
        System.out.println("Creando a Cola SQS FIFO: " + nomeColaSQS);
        final Map<String, String> attributes = new HashMap<String, String>();

        //Unha cola FIFO debe ter o atributo FIFOQueue en true
        attributes.put("FifoQueue", "true");

        //Se o usuario non provee un MessageDeduplicationId, xerando un MessageDeduplicationId basado no contido
        attributes.put("ContentBasedDeduplication", "true");

        //Creamos a cola enviando unha solicitude co nome e os atributos engadidos
        final CreateQueueRequest createQueueRequest = new
                CreateQueueRequest(nomeColaSQS)
                .withAttributes(attributes);
        final String myQueueUrl = clienteSQS.createQueue(createQueueRequest).getQueueUrl();
        System.out.println("Cola " + nomeColaSQS + " creada con éxito");
    }
}
