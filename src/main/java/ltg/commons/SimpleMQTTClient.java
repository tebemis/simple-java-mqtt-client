package ltg.commons;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A ready to use, simple MQTT client.
 * 
 * @author tebemis
 *
 */
public class SimpleMQTTClient {

	// Client
	protected IMqttAsyncClient client = null;
  // List of channels we're subscribed to
  protected Map<String, MessageListener> channels = null;


	/**
	 * Creates a simple MQTT client and connects it to the specified MQTT broker
	 * 
	 * @param host
	 * @param clientId
	 */
	public SimpleMQTTClient(String host, String clientId) {
    // Create Paho client
    try {
      client = new MqttAsyncClient(hostToURI(host), clientId, new MemoryPersistence());
    } catch (MqttException e) {
      System.err.println("Are you sure you specified host correctly? Terminating...");
      System.exit(1);
    }
    // Connect to broker
    try {
      MqttConnectOptions connOpts = new MqttConnectOptions();
      connOpts.setCleanSession(true);
      client.connect(connOpts).waitForCompletion();
    } catch (MqttException e) {
      System.err.println("Impossible to CONNECT to the MQTT server, terminating");
      System.exit(1);
    }
    // Initialize channels
    channels = new HashMap<>();
    // Register callbacks
    client.setCallback(new MqttCallback() {
      @Override
      public void connectionLost(Throwable throwable) {
      }
      @Override
      public void messageArrived(String mqttChannel, MqttMessage mqttMessage) throws Exception {
        if (channels.containsKey(mqttChannel))
          channels.get(mqttChannel).processMessage(mqttMessage.toString());
      }
      @Override
      public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
      }
    });

  }


  /**
   * Subscribes to a channel and registers a callback that is fired
   * every time a new message is published on the channel.
   *
   * @param channel the channel we are subscribing to
   * @param callback the callback to be fired whenever a message is received on this channel
   */
  public void subscribe(String channel, MessageListener callback) {
    try {
      if (client==null || !client.isConnected())
        throw new MqttException(MqttException.REASON_CODE_CONNECTION_LOST);
      if (channels.containsKey(channel))
        return;
      channels.put(channel, callback);
      client.subscribe(channel, 0).waitForCompletion();
    } catch (MqttException e) {
      e.printStackTrace();
    }
  }


  /**
   * Unsubscribes from a channel.
   *
   * @param channel the channel we are unsubscribing to
   */
  public void unsubscribe(String channel) {
    try {
      if (client==null || !client.isConnected())
        throw new MqttException(MqttException.REASON_CODE_CONNECTION_LOST);
      if (!channels.containsKey(channel))
        return;
      client.unsubscribe(channel);
      channels.remove(channel);
    } catch (MqttException e) {
      e.printStackTrace();
    }
  }

  /**
   * Returns the channels the client is currently subscribed to.
   *
   * @return set of channels the client is currently subscribed to
   */
  public Set<String> getSubscribedChannels() {
    return channels.keySet();
  }


	/**
	 * Publish a message to a channel
	 *
	 * @param channel
   * @param message
	 */
	public void publish(String channel, String message) {
    try {
      if (client==null || !client.isConnected())
        throw new MqttException(MqttException.REASON_CODE_CONNECTION_LOST);
      client.publish(channel, message.getBytes(), 0, false);
    } catch (MqttException e) {
      System.out.println("Impossible to publish message to channel " + channel);
    }

  }


	/**
	 * Disconnects the client.
	 */
	public void disconnect() {
    if (client!= null && client.isConnected())
      try {
        client.disconnect(5000);
      } catch (MqttException e) {
        // Exception when trying to disconnect???? What? Ignore
      }
  }


  private String hostToURI(String host) {
    return "tcp://"+host+":1883";
  }

}
