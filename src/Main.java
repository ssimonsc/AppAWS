import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.*;

import java.util.Map;


public class Main {

    public static void main (String[] args) {
        //Creamos o cliente para conectarnos a Amazonsqs
        final AmazonSQS clienteSQS = AmazonSQSClientBuilder.defaultClient();
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();
        String nomeColaSQSFIFOEntrada = "inboxSQS.fifo"; //O nome das colas FIFO sempre ten que acabar en ".fifo"
        String nomeColaSQSFIFOSaida = "outboxSQS.fifo";
        String colaUrlEntrada, colaUrlSaida;
        Map<String,String> colas;
        String nomeBucket = "simonsawsbucket";
        try {
            /* Creamos as colas FIFO en AWS*/
        //      CreadorDeColasFIFO.creadorColaFIFO(nomeColaSQSFIFOEntrada, clienteSQS);
         //     CreadorDeColasFIFO.creadorColaFIFO(nomeColaSQSFIFOSaida, clienteSQS);
            /* Mostrar colas creadas */
         //   colas = ListarColas.listarColas(clienteSQS);
//            /* Obtemos as urls das nosas colas*/
         //   colaUrlEntrada = colas.get(nomeColaSQSFIFOEntrada);
          //  colaUrlSaida = colas.get(nomeColaSQSFIFOSaida);
//            /* Enviamos unha mensaxe á cola Inbox */
       //     Mensaxe.enviarMensaxeColaFIFO(clienteSQS,nomeColaSQSFIFOEntrada,colaUrlEntrada);
            /* Recibimos unha mensaxe da cola Outbox */
         //   Mensaxe.recibirMensaxeColaFIFO(clienteSQS,nomeColaSQSFIFOSaida,colaUrlSaida);

            /* Obter conversación do bucket */
            Bucket.obterConversacions(s3);

        } catch (final AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means " +
                    "your request made it to Amazon SQS, but was " +
                    "rejected with an error response for some reason.");
            System.out.println("Error Message: " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code: " + ase.getErrorCode());
            System.out.println("Error Type: " + ase.getErrorType());
            System.out.println("Request ID: " + ase.getRequestId());
        } catch (final AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means " +
                    "the client encountered a serious internal problem while " +
                    "trying to communicate with Amazon SQS, such as not " +
                    "being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
