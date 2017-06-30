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

package org.ros2.rcljava.examples.service;

import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.consumers.TriConsumer;
import org.ros2.rcljava.node.Node;
import org.ros2.rcljava.service.RMWRequestId;
import org.ros2.rcljava.service.Service;

public class AddTwoIntsService {
  public static void handleService(final RMWRequestId header,
      final example_interfaces.srv.AddTwoInts_Request request,
      final example_interfaces.srv.AddTwoInts_Response response) {
    System.out.println("request: " + request.getA() + " + " + request.getB());
    response.setSum(request.getA() + request.getB());
  }

  public static void main(final String[] args) throws InterruptedException, Exception {
    // Initialize RCL
    RCLJava.rclJavaInit();

    // Let's create a new Node
    Node node = RCLJava.createNode("minimal_service");

    Service<example_interfaces.srv.AddTwoInts> service =
        node.<example_interfaces.srv.AddTwoInts>createService(
            example_interfaces.srv.AddTwoInts.class, "add_two_ints",
            (RMWRequestId header, example_interfaces.srv.AddTwoInts_Request request,
                example_interfaces.srv.AddTwoInts_Response response)
                -> AddTwoIntsService.handleService(header, request, response));

    RCLJava.spin(node);
  }
}
