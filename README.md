# Simple MQTT Client in Java [![Build Status](https://travis-ci.org/tebemis/simple-java-mqtt-client.svg?branch=master)](https://travis-ci.org/tebemis/simple-java-mqtt-client)
A simple asynchronous MQTT client written in Java.  

## Download
You can either download the zip file from the releases on Github [here](https://github.com/ltg-uic/simple-java-mqtt-client/releases) or use Maven. If you are using Maven, add the following to your project `pom.xml` file.
```xml
<project ...>
...
<repositories>
    <repository>
        <id>LTG</id>
        <url>http://ltg.evl.uic.edu/artifactory/repo</url>
    </repository>
</repositories>
...
<dependencies>
    <dependency>
        <groupId>ltg</groupId>
        <artifactId>simple-java-mqtt-client</artifactId>
        <version>1.0.3</version>
    </dependency>
</dependencies>
...
</project>
```

## Hello world
```java
import ltg.commons.SimpleMQTTClient;

public class MQTTClientDemo {

	public static void main(String[] args) {
        // Create a new client and connect to a broker
		SimpleMQTTClient sc = new SimpleMQTTClient("localhost");

        // Subscribe to a channel and register a callback for it
        sc.subscribe("myChannel", new MessageListener() {
            @Override
            public void processMessage(String message) {
                System.out.println(message);
            }
        });
    
        // Publish to a channel
        sc.publish("myOtherChannel", "A message from me");
    
        // Unsubscribe from a channel
        sc.unsubscribe("myChannel");

		// Disconnect from broker
		sc.disconnect();

		// Do something else or the client will die...
        // while (!Thread.currentThread().isInterrupted()) {
        // }
	}
}

```

For a complete example see `MQTTClientDemo.java` in `ltg.commons.examples`.

# Important note
This library is based off of the [fusesource mqtt library ](https://github.com/fusesource/mqtt-client).

