import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;

import java.util.List;
import java.util.Map;

public class Mensaxe {
    public static void enviarMensaxeColaFIFO(AmazonSQS clienteSQS, String nomeColaSQS, String colaURL){
        System.out.println("Enviando mensaxe á cola " + nomeColaSQS + " con URL: "+ colaURL);
        final SendMessageRequest sendMessageRequest = new SendMessageRequest(colaURL, "Ola Mundo");
        // Debemos engadir un MessageGroupId, o cal eleximos nos
        sendMessageRequest.setMessageGroupId("messageGroup1");

        //Enviamos a mensaxe e obtemos a mensaxe de resposta
        final SendMessageResult sendMessageResult = clienteSQS.sendMessage(sendMessageRequest);
        final String sequenceNumber = sendMessageResult.getSequenceNumber();
        final String messageId = sendMessageResult.getMessageId();

        System.out.println("Mensaxe enviada correctamente con ID: " + messageId + " e numero de secuencia: "  + sequenceNumber + "\n");
    }

    public static void recibirMensaxeColaFIFO(AmazonSQS clienteSQS, String nomeColaSQS, String colaURL) throws InterruptedException {
        System.out.println("Recibindo mensaxe de " + nomeColaSQS);
        final ReceiveMessageRequest receiveMessageRequest = new
                ReceiveMessageRequest(colaURL);
        //   System.out.println("ten Mensaxe: " + clienteSQS.receiveMessage(receiveMessageRequest));
        // clienteSQS.receiveMessage(receiveMessageRequest).wait();
        while(true) {
            final List<Message> mensaxes = clienteSQS.receiveMessage(receiveMessageRequest).getMessages();
            if(mensaxes.isEmpty()) continue;
            for (final Message mensaxe : mensaxes) {
                System.out.println("Message");
                System.out.println(" MessageId: " + mensaxe.getMessageId());
                System.out.println(" ReceiptHandle: " + mensaxe.getReceiptHandle());
                System.out.println(" MD5OfBody: " + mensaxe.getMD5OfBody());
                System.out.println(" Body: " + mensaxe.getBody());
                for (final Map.Entry<String, String> entry : mensaxe.getAttributes().entrySet()) {
                    System.out.println("Attribute");
                    System.out.println(" Name: " + entry.getKey());
                    System.out.println(" Value: " + entry.getValue());
                }
            }
            System.out.println();
            eliminarMensaxeColaFIFO(clienteSQS,mensaxes,colaURL);
            break;
        }
    }

    private static void eliminarMensaxeColaFIFO(AmazonSQS clienteSQS, List<Message> mensaxes, String colaURL) {
        System.out.println("Eliminando a mensaxe.\n");
        final String messageReceiptHandle = mensaxes.get(0).getReceiptHandle();
        clienteSQS.deleteMessage(new DeleteMessageRequest(colaURL, messageReceiptHandle));
        System.out.println("Mensaxe eliminada.\n");
    }
}
