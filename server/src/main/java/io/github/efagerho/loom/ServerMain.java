package io.github.efagerho.loom;

import static io.helidon.nima.grpc.webserver.ResponseHelper.complete;

import io.github.efagerho.loom.grpc.Echo;
import io.github.efagerho.loom.grpc.HelloRequest;
import io.github.efagerho.loom.grpc.HelloResponse;
import io.grpc.stub.StreamObserver;
import io.helidon.logging.common.LogConfig;
import io.helidon.nima.grpc.webserver.GrpcRouting;
import io.helidon.nima.webserver.WebServer;

public class ServerMain {

    private static void hello(HelloRequest request, StreamObserver<HelloResponse> observer) {
        String name = request.getName();
        complete(observer, HelloResponse.newBuilder().setResponse(String.format("Hello %s", name)).build());
    }

    public static void main(String[] args) {
        LogConfig.configureRuntime();

        WebServer ws = WebServer
            .builder()
            .port(8080)
            .addRouting(GrpcRouting.builder().unary(Echo.getDescriptor(), "EchoService", "Hello", ServerMain::hello))
            .build();
        ws.start();
    }
}
