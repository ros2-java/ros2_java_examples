package org.ros2.rcljava.examples;

import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.Node;
import org.ros2.rcljava.Publisher;

import java.util.Arrays;
import java.util.ArrayList;

public class Talker {
    public static void main(String[] args) throws InterruptedException {
        // Initialize RCL
        RCLJava.rclJavaInit();

        // Let's create a Node
        Node node = RCLJava.createNode("talker");

        // Publishers are type safe, make sure to pass the message type
        Publisher<std_msgs.msg.String> chatter_pub =
            node.<std_msgs.msg.String>createPublisher(std_msgs.msg.String.class, "chatter");

        std_msgs.msg.String msg = new std_msgs.msg.String();

        int i = 1;

        while(RCLJava.ok()) {
            msg.setData("Hello World: " + i);
            i++;
            System.out.println("Publishing: \"" + msg.getData() + "\"");
            chatter_pub.publish(msg);

            // Sleep a little bit between each message
            Thread.sleep(1000);
        }
    }
}
