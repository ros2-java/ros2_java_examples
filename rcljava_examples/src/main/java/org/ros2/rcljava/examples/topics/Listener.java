/* Copyright 2016 Open Source Robotics Foundation, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ros2.rcljava.examples.topics;

import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.node.Node;
import org.ros2.rcljava.node.topic.Consumer;
import org.ros2.rcljava.node.topic.Subscription;
import org.ros2.rcljava.QoSProfile;

public class Listener {
    private static final String NODE_NAME = Listener.class.getName();

    public static void chatterCallback(std_msgs.msg.String msg) {
        System.out.println("I heard: " + msg.getData());
    }

    public static void main(String[] args) throws InterruptedException {
        // Initialize RCL
        RCLJava.rclJavaInit();

        // Let's create a new Node
        Node node = RCLJava.createNode(NODE_NAME);

        // Subscriptions are type safe, so we'll pass the message type. We use the fully qualified
        // class name to avoid any collision with Java's String class
        Subscription<std_msgs.msg.String> sub = node.<std_msgs.msg.String>createSubscription(
            std_msgs.msg.String.class,
            "chatter",
            new Consumer<std_msgs.msg.String>() {
                // We define the callback inline, this works with Java 8's lambdas too, but we use
                // our own Consumer interface because Android supports lambdas via retrolambda, but not
                // the lambda API
                @Override
                public void accept(std_msgs.msg.String msg) {
                    Listener.chatterCallback(msg);
                }
            },
            QoSProfile.PROFILE_DEFAULT);

        RCLJava.spin(node);

        sub.dispose();
        node.dispose();
        RCLJava.shutdown();
    }
}
