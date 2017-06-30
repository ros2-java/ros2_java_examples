/* Copyright 2016-2017 Esteve Fernandez <esteve@apache.org>
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

package org.ros2.rcljava.examples.client;

import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.client.Client;
import org.ros2.rcljava.node.Node;

import java.util.concurrent.Future;

public class AddTwoIntsClient {
  public static void main(final String[] args) throws InterruptedException, Exception {
    // Initialize RCL
    RCLJava.rclJavaInit();

    // Let's create a new Node
    Node node = RCLJava.createNode("minimal_client");

    Client<example_interfaces.srv.AddTwoInts> client =
        node.<example_interfaces.srv.AddTwoInts>createClient(
            example_interfaces.srv.AddTwoInts.class, "add_two_ints");

    example_interfaces.srv.AddTwoInts_Request request =
        new example_interfaces.srv.AddTwoInts_Request();
    request.setA(2);
    request.setB(3);

    Future<example_interfaces.srv.AddTwoInts_Response> future =
        client.asyncSendRequest(request);

    System.out.println(
        "result of " + request.getA() + " + " + request.getB() + " = " + future.get().getSum());

    RCLJava.shutdown();
  }
}
