# ROS2 Java examples

This is a collection of examples that use the Java bindings for ROS2. They aim to mimic the rclcpp examples, but using the [ROS2 Java](https://github.com/esteve/ros2_java) bindings. Please follow the instructions on https://github.com/esteve/ros2_java/blob/master/README.md#desktop to build the ROS2 Java bindings, you can try out any of the following examples by typing the folllowing:

```sh
. ~/ros2_java_ws/install_isolated/local_setup.sh
java FULLY_QUALIFIED_NAME_OF_THE_EXAMPLE
```

For example, to run the Publisher and Subscriber examples, open two terminals and type this on one of them to run the subscriber example:

```sh
. ~/ros2_java_ws/install_isolated/local_setup.sh
java org.ros2.rcljava.examples.subscriber.SubscriberLambda
```

and the following to run the publisher examples on the other terminal:

```sh
. ~/ros2_java_ws/install_isolated/local_setup.sh
java org.ros2.rcljava.examples.publisher.PublisherLambda
```

you should now see a bunch of messages showing up on the first terminal.

## Publisher

- [PublisherNotComposable](rcljava_examples/src/main/java/org/ros2/rcljava/examples/publisher/PublisherNotComposable.java). An old-style publisher, resembling the ROS1 API.
- [PublisherLambda](rcljava_examples/src/main/java/org/ros2/rcljava/examples/publisher/PublisherLambda.java). A composable publisher that uses a lambda function and a timer to publish `std_msgs.msg.String` messages.
- [PublisherMemberFunction](rcljava_examples/src/main/java/org/ros2/rcljava/examples/publisher/PublisherMemberFunction.java). A composable publisher that uses a member function and a timer to publish `std_msgs.msg.String` messages.

## Subscriber

- [SubscrberNotComposable](rcljava_examples/src/main/java/org/ros2/rcljava/examples/subscriber/SubscriberNotComposable.java). An old-style subscriber, resembling the ROS1 API.
- [SubscriberLambda](rcljava_examples/src/main/java/org/ros2/rcljava/examples/subscriber/SubscriberLambda.java). A composable subscriber that uses a lambda function to process `std_msgs.msg.String` messages.
- [SubscriberMemberFunction](rcljava_examples/src/main/java/org/ros2/rcljava/examples/subscriber/SubscriberMemberFunction.java). A composable subscriber that uses a member function to process `std_msgs.msg.String` messages.

## Composition

- [PublisherNode](rcljava_examples/src/main/java/org/ros2/rcljava/examples/composition/PublisherNode.java). A composable publisher node that can be reused standalone or as part of a multi-node program.
- [SubscriberNode](rcljava_examples/src/main/java/org/ros2/rcljava/examples/composition/SubscriberNode.java). A composable subscriber node that can be reused standalone or as part of a multi-node program.
- [Composed](rcljava_examples/src/main/java/org/ros2/rcljava/examples/composition/Composed.java). A multi-node program that runs both a `SubscriberNode` and a `PublisherNode`.

## Timer

- [TimerLambda](rcljava_examples/src/main/java/org/ros2/rcljava/examples/timer/TimerLambda.java). A timer that calls a lambda function to print out a message to the console repeatedly.
- [TimerMemberFunction](rcljava_examples/src/main/java/org/ros2/rcljava/examples/timer/TimerMemberFunction.java). A timer that calls a member function to print out a message to the console repeatedly.

## Service

- [AddTwoIntsService](rcljava_examples/src/main/java/org/ros2/rcljava/examples/service/AddTwoIntsService.java). A service that will return the sum of two integers.

## Client

- [AddTwoIntsClient](rcljava_examples/src/main/java/org/ros2/rcljava/examples/client/AddTwoIntsClient.java). A client that will submit a request to the [AddTwoIntsService](rcljava_examples/src/main/java/org/ros2/rcljava/examples/service/AddTwoIntsService.java) to sum two integers.
