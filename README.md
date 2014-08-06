# Simple MQTT Client in Java [![Build Status](https://travis-ci.org/ltg-uic/simple-java-mqtt-client.svg?branch=master)](https://travis-ci.org/ltg-uic/simple-java-mqtt-client)
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
        <version>1.0.0</version>
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
This library is based off of a fixed version of the [Eclipse Paho java library](http://www.eclipse.org/paho/clients/java/). Hopefully they will fix their broken Maven repo and release system one day.

