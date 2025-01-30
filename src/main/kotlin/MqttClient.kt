package de.keepmealive3d

import de.keepmealive3d.config.Config
import org.eclipse.paho.client.mqttv3.MqttAsyncClient
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import java.io.File

fun mqttClientInit(): MqttClient {
    val config = Config.load(File("config.yml")).getOrElse {
        println("Could not load config.yml")
        throw it
    }

    val client = MqttClient(
        "tcp://${config.databases.mqtt.host}:${config.databases.mqtt.port}",
        MqttAsyncClient.generateClientId(),
    )

    val connectionOptions = MqttConnectOptions()

    connectionOptions.userName = config.databases.mqtt.clientId
    connectionOptions.password = config.databases.mqtt.password.toCharArray()
    connectionOptions.isCleanSession = true

    client.connect(connectionOptions)

    return client
}