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

import java.util.concurrent.Future;

import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.node.Node;
import org.ros2.rcljava.node.service.Client;

import example_interfaces.srv.AddTwoInts;
import example_interfaces.srv.AddTwoInts_Request;
import example_interfaces.srv.AddTwoInts_Response;

/**
 *
 * @author Mickael Gaillard <mick.gaillard@gmail.com>
 */
public class AddTwoIntsClient {
    private static final String NODE_NAME = AddTwoIntsClient.class.getSimpleName().toLowerCase();
//    private static Logger logger = Logger.getLogger(RCLJava.LOG_NAME);

    public static void main(String[] args) throws Exception {
//        logger.setLevel(Level.ALL);
//        ConsoleHandler handler = new ConsoleHandler();
//        handler.setFormatter(new SimpleFormatter());
//        logger.addHandler(handler);
//        handler.setLevel(Level.ALL);

        // Initialize RCL
        RCLJava.rclJavaInit();

        // Let's create a new Node
        Node node = RCLJava.createNode(NODE_NAME);

        // Create client.
        Client<AddTwoInts> client = node.<AddTwoInts>createClient(AddTwoInts.class, "add_two_ints");

        // Set request.
        AddTwoInts_Request request = new AddTwoInts_Request();
        request.setA(2);
        request.setB(3);

        // Call service...
        Future<AddTwoInts_Response> future = client.sendRequest(request);
        if (future != null) {
            System.out.println(String.format("Result of add_two_ints: %d", future.get().getSum()));
        } else {
            System.out.println("add_two_ints_client was interrupted. Exiting.");
        }

        // Release all.
        client.dispose();
        node.dispose();
        RCLJava.shutdown();
    }

}
