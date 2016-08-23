package org.ros2.rcljava.examples;

import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.Node;
import org.ros2.rcljava.Subscription;
import org.ros2.rcljava.Consumer;


public class Listener {

    public static void chatterCallback(std_msgs.msg.String msg) {
        System.out.println("I heard: " + msg.getData());
    }

    public static void main(String[] args) throws InterruptedException {
        // Initialize RCL
        RCLJava.rclJavaInit();

        // Let's create a new Node
        Node node = RCLJava.createNode("listener");

        // Subscriptions are type safe, so we'll pass the message type. We use the fully qualified
        // class name to avoid any collision with Java's String class
        Subscription<std_msgs.msg.String> sub = node.<std_msgs.msg.String>createSubscription(
            std_msgs.msg.String.class, "chatter", new Consumer<std_msgs.msg.String>() {

            // We define the callback inline, this works with Java 8's lambdas too, but we use
            // our own Consumer interface because Android supports lambdas via retrolambda, but not
            // the lambda API
            @Override
            public void accept(std_msgs.msg.String msg) {
                chatterCallback(msg);
            }
        });

        while(RCLJava.ok()) {
            RCLJava.spinOnce(node);
        }
    }
}
