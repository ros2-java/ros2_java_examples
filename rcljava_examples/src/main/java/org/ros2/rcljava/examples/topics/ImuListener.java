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

import org.ros2.rcljava.Consumer;
import org.ros2.rcljava.Node;
import org.ros2.rcljava.QoSProfile;
import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.Subscription;

public class ImuListener {
    private static final String NODE_NAME = Listener.class.getName();

    private static void imuCb(final sensor_msgs.msg.Imu msg)
    {
        System.out.println(String.format(" accel: [%+6.3f %+6.3f %+6.3f]\n",
            msg.getLinear_acceleration().getX(),
            msg.getLinear_acceleration().getY(),
            msg.getLinear_acceleration().getZ()));
    }

    public static void main(String[] args) throws InterruptedException {
        // Initialize RCL
        RCLJava.rclJavaInit();

        // Let's create a new Node
        Node node = RCLJava.createNode(NODE_NAME);

        // Subscriptions are type safe, so we'll pass the message type.
        Subscription<sensor_msgs.msg.Imu> sub = node.<sensor_msgs.msg.Imu>createSubscription(
                sensor_msgs.msg.Imu.class,
                "imu",
                new Consumer<sensor_msgs.msg.Imu>() {
                    // We define the callback inline, this works with Java 8's lambdas too, but we use
                    // our own Consumer interface because Android supports lambdas via retrolambda, but not
                    // the lambda API
                    @Override
                    public void accept(sensor_msgs.msg.Imu msg) {
                        ImuListener.imuCb(msg);
                    }
                },
                QoSProfile.PROFILE_SENSOR_DATA);

        RCLJava.spin(node);

        sub.dispose();
        node.dispose();
        RCLJava.shutdown();
    }

}
