# Simple MQTT Client in Java

A simple asynchronous MQTT client written in Java.  

## Hello world

```java
import ltg.commons.SimpleMQTTClient;

public class MQTTClientDemo {

	public static void main(String[] args) {
    // Create a new client and connect to a broker
		SimpleMQTTClient sc = new SimpleMQTTClient("localhost", "test-bot");

    // Subscribe to a channel and register a callback for it
    sc.subscribe("myChannel", message -> System.out.println(message));
    
    // Do something...
    
    // Publish to a channel
    sc.publish("myOtherChannel", "A message from me");
    
    // Do something else...
    
    // Unsubscribe from a channel
    sc.unsubscribe("myChannel");

		// Disconnect from broker
		sc.disconnect();
	}
}

```

For a complete example see `MQTTClientDemo.java` in `ltg.commons.examples`.

# Important note
This library is based off of a fixed version of the [Eclipse Paho java library](http://www.eclipse.org/paho/clients/java/).

