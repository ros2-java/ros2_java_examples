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
package org.ros2.rcljava.examples.services;

import org.ros2.rcljava.QoSProfile;
import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.node.INode;
import org.ros2.rcljava.node.service.Client;

import example_interfaces.srv.AddTwoInts;

/**
 *
 * @author Mickael Gaillard <mick.gaillard@gmail.com>
 */
public class AddTwoIntsClient {
    private static final String NODE_NAME = AddTwoIntsClient.class.getName();

    public static void main(String[] args) throws InterruptedException {
        // Initialize RCL
        RCLJava.rclJavaInit();

        // Let's create a new Node
        INode node = RCLJava.createNode(NODE_NAME);

        // Create client.
        Client<AddTwoInts> client = node.<AddTwoInts>createClient(
                AddTwoInts.class,
                "add_two_ints",
                QoSProfile.PROFILE_SERVICES_DEFAULT);

        // Set request.
        AddTwoInts.Request request = new AddTwoInts.Request();
        request.setA(2);
        request.setB(3);

        // Wait service are available.
        while (!client.waitForService(1000)) {
            if (!RCLJava.ok()) {
                System.out.println("add_two_ints_client was interrupted while waiting for the service. Exiting.");
                break;
            }
            System.out.println("service not available, waiting again...");
        }

        // Call service...
        AddTwoInts.Response result = client.<AddTwoInts.Request, AddTwoInts.Response>sendRequest(request);
        if (result != null) {
            System.out.println(String.format("Result of add_two_ints: %zd", result.getSum()));
        } else {
            System.out.println("add_two_ints_client was interrupted. Exiting.");
        }

        // Release all.
        client.dispose();
        node.dispose();
        RCLJava.shutdown();
    }

}
