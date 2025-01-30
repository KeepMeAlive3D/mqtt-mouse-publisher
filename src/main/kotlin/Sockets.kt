package de.keepmealive3d

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import org.eclipse.paho.client.mqttv3.MqttMessage
import kotlin.time.Duration.Companion.seconds

fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = 15.seconds
        timeout = 15.seconds
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    val mqttClient = mqttClientInit()
    val topic = "mqtt-mouse-publisher.position"

    routing {
        webSocket("/ws") { // websocketSession
            for (frame in incoming) {
                if (frame is Frame.Text) {
                    val (subTopic, data) = frame.readText().split(":")
                    println("$subTopic: $data")
                    mqttClient.publish("${topic}.${subTopic}", MqttMessage(data.toByteArray()))
                }
            }
        }
    }
}
