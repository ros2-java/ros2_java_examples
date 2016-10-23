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
package org.ros2.rcljava.examples.parameters;

import java.util.Arrays;
import java.util.List;

import org.ros2.rcljava.QoSProfile;
import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.node.Node;
import org.ros2.rcljava.node.parameter.ParameterVariant;
import org.ros2.rcljava.node.parameter.SyncParametersClient;

/**
 *
 * @author Mickael Gaillard <mick.gaillard@gmail.com>
 */
public class SetAndGetParameters {
    private static final String NODE_NAME = SetAndGetParameters.class.getSimpleName().toLowerCase();

    /**
     * @param args
     */
    public static void main(String[] args) {
     // Initialize RCL
        RCLJava.rclJavaInit();

        // Let's create a new Node
        Node node = RCLJava.createNode(NODE_NAME);

        SyncParametersClient parameters_client = new SyncParametersClient(node, QoSProfile.PROFILE_PARAMETER);

        // Set several different types of parameters.
        List<rcl_interfaces.msg.SetParametersResult> set_parameters_results =
            parameters_client.setParameters(
                    Arrays.<ParameterVariant<?>>asList(
                        new ParameterVariant<Long>("foo", 2L),
                        new ParameterVariant<String>("bar", "hello"),
                        new ParameterVariant<Double>("baz", 1.45),
                        new ParameterVariant<Boolean>("foobar", true)
        ));
        // Check to see if they were set.
        for (rcl_interfaces.msg.SetParametersResult result : set_parameters_results) {
          if (!result.getSuccessful()) {
              System.out.println(String.format("Failed to set parameter: %s", result.getReason()));
          }
        }

        // Get a few of the parameters just set.
        for (ParameterVariant<?> parameter : parameters_client.getParameters(Arrays.asList("foo", "baz"))) { //  "bar", "foobar"
            System.out.println(
                    String.format("Parameter name %s = value (%s): %s",
                        parameter.getName(),
                        parameter.getTypeName(),
                        parameter.valueToString()));
        }

        node.dispose();
        RCLJava.shutdown();
    }

}
