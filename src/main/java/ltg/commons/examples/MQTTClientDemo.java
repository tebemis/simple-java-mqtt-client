package ltg.commons.examples;

import ltg.commons.MessageListener;
import ltg.commons.SimpleMQTTClient;

/**
 * This example demonstrates the use of SimpleMQTTClient class.
 *
 * @author tebemis
 *
 */
public class MQTTClientDemo {

    public static void main(String[] args) {

        // Let's create a new client and connect to a broker
        final SimpleMQTTClient sc = new SimpleMQTTClient("localhost");

        // Subscribe to channel "demo1" and set a callback that is
        // fired whenever a new message is received.
        // This particular callback takes the message and publishes it to "demo1-bounce" channel.
        sc.subscribe("quakes", new MessageListener() {
            @Override
            public void processMessage(String message) {
                System.out.println("Received \"" + message + "\" on quakes.");
            }
        });
        System.out.println("Subscribed to channels " + sc.getSubscribedChannels());


        // We are now connected to the MQTT broker and we registered some callbacks.
        // If we don't do something main will terminate and we'll never receive a message
        System.out.println("Waiting for new messages");

        // So, just for fun, let's wait indefinitely.
        while (!Thread.currentThread().isInterrupted()) {
        }

        // Whenever we are done with our task we can disconnect from the broker and terminate
        sc.disconnect();
    }

}
