package ltg.commons.examples;

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
		SimpleMQTTClient sc = new SimpleMQTTClient("localhost", "test-bot");

    // Subscribe to channel "demo1" and set a callback that is
    // fired whenever a new message is received.
    // This particular callback takes the message and publishes it to "demo1-bounce" channel.
    sc.subscribe("demo1", message -> {
      String bounce_channel = "demo1-bounce";
      System.out.println("Received \"" + message + "\" on demo1. Bouncing it back to \""+ bounce_channel + "\"");
      sc.publish(bounce_channel, message);
    });
    System.out.println("Subscribed to channels " + sc.getSubscribedChannels());

    // Subscribe to channel "demo2" and set a callback
    // that prints out the received message and unsubscribes
    // from the channel.
    sc.subscribe("demo2", message -> {
      System.out.println("Received \"" + message + "\" on demo2");
      sc.unsubscribe("demo2");
      System.out.println("Subscribed to channels " + sc.getSubscribedChannels());
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
