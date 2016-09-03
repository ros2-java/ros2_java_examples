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

import org.ros2.rcljava.Node;
import org.ros2.rcljava.QoSProfile;
import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.service.Service;
import org.ros2.rcljava.service.ServiceConsumer;

import example_interfaces.srv.AddTwoInts;

/**
 *
 * @author Mickael Gaillard <mick.gaillard@gmail.com>
 */
public class AddTwoIntsServer {
    private static final String NODE_NAME = AddTwoIntsServer.class.getName();

    public static void handleAddTwoInts(
            final example_interfaces.srv.AddTwoInts.Request request,
            example_interfaces.srv.AddTwoInts.Response response) {

        System.out.println("Incoming request");
        System.out.println(String.format("a: %d b: %d", request.getA(), request.getB()));
        response.setSum(request.getA() + request.getB());
    }

    public static void main(String[] args) throws InterruptedException {
        // Initialize RCL
        RCLJava.rclJavaInit();

        // Let's create a new Node
        Node node = RCLJava.createNode(NODE_NAME);

        // Create a service.
        Service<AddTwoInts> service = node.<AddTwoInts>createService(
            AddTwoInts.class,
            "add_two_ints",
            new ServiceConsumer<AddTwoInts.Request, AddTwoInts.Response>() {
                // We define the callback inline, this works with Java 8's lambdas too, but we use
                // our own Consumer interface because Android supports lambdas via retrolambda, but not
                // the lambda API
                @Override
                public void call(AddTwoInts.Request request, AddTwoInts.Response response) {
                    handleAddTwoInts(request, response);
                }
            },
            QoSProfile.PROFILE_SERVICES_DEFAULT);

        // Wait...
        RCLJava.spin(node);

        // Release all.
        service.dispose();
        node.dispose();
        RCLJava.shutdown();
    }

}
