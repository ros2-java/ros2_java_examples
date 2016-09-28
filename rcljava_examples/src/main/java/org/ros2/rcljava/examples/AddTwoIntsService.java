/* Copyright 2016 Esteve Fernandez <esteve@apache.org>
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
package org.ros2.rcljava.examples;

import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.Node;
import org.ros2.rcljava.Subscription;
import org.ros2.rcljava.TriConsumer;
import org.ros2.rcljava.Service;
import org.ros2.rcljava.RMWRequestId;


public class AddTwoIntsService {

    public static void main(String[] args) throws InterruptedException, Exception {
        // Initialize RCL
        RCLJava.rclJavaInit();

        // Let's create a new Node
        Node node = RCLJava.createNode("add_two_ints_server");

        Service<example_interfaces.srv.AddTwoInts> service = node.<
            example_interfaces.srv.AddTwoInts
        >createService(
            example_interfaces.srv.AddTwoInts.class, "add_two_ints", new TriConsumer<
                RMWRequestId,
                example_interfaces.srv.AddTwoInts_Request,
                example_interfaces.srv.AddTwoInts_Response
            >() {

            // We define the callback inline, this works with Java 8's lambdas too, but we use
            // our own Consumer interface because Android supports lambdas via retrolambda, but not
            // the lambda API
            @Override
            public void accept(
                RMWRequestId header,
                example_interfaces.srv.AddTwoInts_Request request,
                example_interfaces.srv.AddTwoInts_Response response
            ) {
                System.out.println("Incoming request");
                System.out.println("a: " + request.getA() + " b: " + request.getB());
                response.setSum(request.getA() + request.getB());
            }
        });

        while(RCLJava.ok()) {
            RCLJava.spinOnce(node);
        }
    }
}
