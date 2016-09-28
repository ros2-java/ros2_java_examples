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

import java.util.concurrent.Future;

import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.Node;
import org.ros2.rcljava.Subscription;
import org.ros2.rcljava.TriConsumer;
import org.ros2.rcljava.Client;
import org.ros2.rcljava.RMWRequestId;

public class AddTwoIntsClient {

  public static void main(String[] args) throws InterruptedException, Exception {
    // Initialize RCL
    RCLJava.rclJavaInit();

    // Let's create a new Node
    Node node = RCLJava.createNode("add_two_ints_client");

    Client<example_interfaces.srv.AddTwoInts> client = node.<
      example_interfaces.srv.AddTwoInts>createClient(
        example_interfaces.srv.AddTwoInts.class, "add_two_ints");

    example_interfaces.srv.AddTwoInts_Request request = new example_interfaces.srv.AddTwoInts_Request();
    request.setA(2);
    request.setB(3);

    Future<example_interfaces.srv.AddTwoInts_Response> future = client.sendRequest(request);

    System.out.println("Result of add_two_ints: " + future.get().getSum());

    RCLJava.shutdown();
  }
}
