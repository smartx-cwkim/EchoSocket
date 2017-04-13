import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.DockerCmdExecFactory;
import com.github.dockerjava.api.command.ListContainersCmd;
import com.github.dockerjava.api.command.PauseContainerCmd;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.async.ResultCallbackTemplate;
import com.github.dockerjava.core.command.BuildImageResultCallback;
import com.github.dockerjava.core.command.EventsResultCallback;
import com.github.dockerjava.core.command.WaitContainerResultCallback;
import com.github.dockerjava.jaxrs.JerseyDockerCmdExecFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * Created by chorwon on 17. 3. 31.
 */

// 반복문을 통한 여러 개의 컨테이너 생성 및 리소스 사용량 확인.
//

//public class hello {
//    private static int NUM_STATS = 5;
//    public static int CONTAINER_COUNT=3;
//
//    public static void main(String[] args) {
//        String containerId = null;
//        CountDownLatch countDownLatch = new CountDownLatch(CONTAINER_COUNT);
//
//        try {
//            TimeUnit.SECONDS.sleep(1);
////            for(int i=0;i<100;i++) {
////                String[] containerName2 = null;
////                containerName2[i] = "generated_" + new SecureRandom().nextInt();
////            }
//
//            // Initialize docker client to use Docker REST API
//            DockerCmdExecFactory dockerCmdExecFactory = new JerseyDockerCmdExecFactory()
//                    .withReadTimeout(1000)
//                    .withConnectTimeout(1000)
//                    .withMaxTotalConnections(100)
//                    .withMaxPerRouteConnections(10);
//
//            DockerClient dockerClient = DockerClientBuilder.getInstance("tcp://localhost:2376")
//                    // .withDockerCmdExecFactory(dockerCmdExecFactory)
//                    .build();
//
//            // Get Docker info (only docker client information, not docker container information)
//            Info info = dockerClient.infoCmd().exec();
//            // System.out.print(info);
//
////            // 해당 부분은 DockerfileFixture 테스트 코드를 돌린 후에 나오는 directory 값을 확인해야 작업이 가능할 것 같다.
////            dockerClient.buildImageCmd(new File("/home/chorwon/example", directory)).withNoCache(true)
////                    .exec(new BuildImageResultCallback()).awaitImageId();
//
//            // Set variable to the File directory that has Dockerfile.
//            File baseDir = new File("/home/chorwon/example");
//
//            // Check File directory result.
//            System.out.println("Test" + baseDir.toString());
//
//            // Check build progress callback function.
//            BuildImageResultCallback callback = new BuildImageResultCallback() {
//                public void onNext(BuildResponseItem item) {
//                    // System.out.println("" + item);
//                    super.onNext(item);
//                }
//            };
//
//            EventsResultCallback callback1 = new EventsResultCallback() {
//                public void onNext(Event event) {
//                    super.onNext(event);
//                }
//            };
//
//            // docker build command. We can use callback to check build progress.
//            dockerClient.buildImageCmd(baseDir)
//                    .withTag("socket")
//                    .exec(callback).awaitImageId();
//
//            // Check docker images. 'get(0)' means bring 1st images to the docker images command list.
//            Image lastCreatedImage = dockerClient.listImagesCmd().exec().get(0);
//
////            // Setting ports  -> test
////            ExposedPort tcp22 = ExposedPort.tcp(22);
////            Ports portBindings = new Ports();
////            portBindings.bind(tcp22, Ports.Binding.bindPort(12345));
//
//            String[] containerName = new String[CONTAINER_COUNT];
//
//            for (int i=0;i<CONTAINER_COUNT;i++) {
//
//                // Set a Container Name. It can be changed by Array to save multiple container name.
//                // containerName[i] = "generated_" + new SecureRandom().nextInt();
//                containerName[i] = "tt_" + i;
//
//                // create new Docker container, not start.
////                containerId = dockerClient.createContainerCmd(lastCreatedImage.getId())
////                        //.withCmd("./Intro_javaClient.sh", "testClient", "192.168.0.66", "12345")
////                        .withCmd("top")
////                        .withName(containerName[i])
////                        .withMemory((long)2000000)
////                        .withCpusetCpus("2,3")
////                        //.withExposedPorts(tcp22)
////                        //.withPortBindings(portBindings)
////                        .exec().getId();
//
//                CreateContainerResponse cont = dockerClient
//                        .createContainerCmd(lastCreatedImage.getId())
//                        .withCmd("testClient", "192.168.0.66", "12345")
//                        // 192.168.0.66으로 직접 넣어줘야 한다. 해당 부분을 localhost로 할 경우 인식을 못함.
//                        // docker network에 따른 문제로 예상.
//                        .withName(containerName[i])
//                        .withMemory((long)200000000)
//                        .withCpusetCpus("2,3")
//                        .exec();
//
//
//                // Start a container after created container
//                dockerClient.startContainerCmd(cont.getId()).exec();
//
//                // Make a Action code before stop and remove container when the condition occur.
//                // StatsCallbackTest
////                StatsCallback statsCallback = dockerClient.statsCmd(cont.getId()).exec(
////                        new StatsCallback(countDownLatch, cont.getId()));
////
////                countDownLatch.await(3, TimeUnit.SECONDS);
////                Boolean gotStats = statsCallback.gotStats();
////
////                System.out.println(statsCallback.toString() + cont.getId());
//
//
////                // Stop a container.
////                dockerClient.stopContainerCmd(cont.getId()).exec();
////
////                // Remove a container.
////                dockerClient.removeContainerCmd(cont.getId()).exec();
//
//                System.out.println("complete " + cont.getId());
//            }
//
//            // Create new Docker container, not start.
////            CreateContainerResponse container = dockerClient.createContainerCmd("alpine:latest")
////                    .withCmd("top")
////                    .withName(containerName)
////                    .withCpusetCpus("2,3")
////                    .withMemory((long)(20000000))
////                    .exec();
//
//        } catch(InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static class StatsCallback extends ResultCallbackTemplate<StatsCallback, Statistics> {
//        private CountDownLatch countDownLatch;
//        public static String containerID = null;
//
//        private Boolean gotStats = false;
//
//        public StatsCallback(CountDownLatch countDownLatch, String containerId) {
//            this.countDownLatch = countDownLatch;
//            this.containerID = containerId;
//        }
//
//        public void onNext(Statistics stats) {
//            String string = stats.getCpuStats().toString();
//            String memory = stats.getMemoryStats().toString();
//            int count = 0;
////            System.out.println("testCPU : " + string);
////            System.out.println("testMemory : " + memory);
//            if(stats != null) {
//
//
//                System.out.println(containerID + " test " + string);
//                count++;
//
//                if(count >=3 ) {
//                    onComplete();
//                }
//
//                // gotStats = true;
//            }
//            countDownLatch.countDown();
//
////            Set<Map.Entry<String, Object>> entries = stats.getCpuStats().entrySet();
////            for (Map.Entry<String, Object> entry : entries) {
////                System.out.println(entry.getKey() + " : " + entry.getValue());
////            }
//        }
//
//        public Boolean gotStats(){
//            return gotStats;
//        }
//    }
//}

// 여러 개의 컨테이너를 생성하지만, 반복문을 스레드 부분에 없앰으로써 순서가 지켜지지 않게끔.

//public class hello {
//    public static int CONTAINER_COUNT=3;
//
//    public static void main(String[] args) {
//        String containerId = null;
//        CountDownLatch countDownLatch = new CountDownLatch(CONTAINER_COUNT);
//
//        try {
//            TimeUnit.SECONDS.sleep(1);
////            for(int i=0;i<100;i++) {
////                String[] containerName2 = null;
////                containerName2[i] = "generated_" + new SecureRandom().nextInt();
////            }
//
//            // Initialize docker client to use Docker REST API
//            DockerCmdExecFactory dockerCmdExecFactory = new JerseyDockerCmdExecFactory()
//                    .withReadTimeout(1000)
//                    .withConnectTimeout(1000)
//                    .withMaxTotalConnections(100)
//                    .withMaxPerRouteConnections(10);
//
//            DockerClient dockerClient = DockerClientBuilder.getInstance("tcp://localhost:2376")
//                    // .withDockerCmdExecFactory(dockerCmdExecFactory)
//                    .build();
//
//            // Get Docker info (only docker client information, not docker container information)
//            Info info = dockerClient.infoCmd().exec();
//            // System.out.print(info);
//
////            // 해당 부분은 DockerfileFixture 테스트 코드를 돌린 후에 나오는 directory 값을 확인해야 작업이 가능할 것 같다.
////            dockerClient.buildImageCmd(new File("/home/chorwon/example", directory)).withNoCache(true)
////                    .exec(new BuildImageResultCallback()).awaitImageId();
//
//            // Set variable to the File directory that has Dockerfile.
//            File baseDir = new File("/home/chorwon/example");
//
//            // Check File directory result.
//            System.out.println("Test" + baseDir.toString());
//
//            // Check build progress callback function.
//            BuildImageResultCallback callback = new BuildImageResultCallback() {
//                public void onNext(BuildResponseItem item) {
//                    // System.out.println("" + item);
//                    super.onNext(item);
//                }
//            };
//
//            EventsResultCallback callback1 = new EventsResultCallback() {
//                public void onNext(Event event) {
//                    super.onNext(event);
//                }
//            };
//
//            // docker build command. We can use callback to check build progress.
//            dockerClient.buildImageCmd(baseDir)
//                    .withTag("test")
//                    .exec(callback).awaitImageId();
//
//            // Check docker images. 'get(0)' means bring 1st images to the docker images command list.
//            Image lastCreatedImage = dockerClient.listImagesCmd().exec().get(0);
//
////            // Setting ports  -> test
////            ExposedPort tcp22 = ExposedPort.tcp(22);
////            Ports portBindings = new Ports();
////            portBindings.bind(tcp22, Ports.Binding.bindPort(12345));
//
//            String[] containerName = new String[CONTAINER_COUNT];
//            String[] cont = new String[CONTAINER_COUNT];
//
//            for (int i=0;i<CONTAINER_COUNT;i++) {
//
//                // Set a Container Name. It can be changed by Array to save multiple container name.
//                // containerName[i] = "generated_" + new SecureRandom().nextInt();
//                containerName[i] = "tt_" + i;
//
//                // create new Docker container, not start.
//                cont[i]= dockerClient.createContainerCmd(lastCreatedImage.getId())
//                        //.withCmd("./Intro_javaClient.sh", "testClient", "192.168.0.66", "12345")
//                        .withCmd("sh", "-c", "while :; do sleep 1; done")
//                        .withName(containerName[i])
//                        .withMemory((long)20000000)
//                        .withCpusetCpus("2,3")
//                        //.withExposedPorts(tcp22)
//                        //.withPortBindings(portBindings)
//                        .exec().getId();
//
////                CreateContainerResponse cont = dockerClient
////                        .createContainerCmd(lastCreatedImage.getId())
////                        .withCmd("sh", "-c", "while :; do sleep 1; done")
////                        // .withCmd("./Intro_javaClient.sh", "testClient", "192.168.0.66", "12345")
////                        .withName(containerName[i])
////                        .withMemory((long)200000000)
////                        .withCpusetCpus("2,3")
////                        .exec();
//
//                // Start a container after created container
//                dockerClient.startContainerCmd(cont[i]).exec();
//            }
//
//
//
//            for (int i=0;i<CONTAINER_COUNT;i++) {
//                // Make a Action code before stop and remove container when the condition occur.
//                // StatsCallbackTest
//                StatsCallback statsCallback = dockerClient.statsCmd(cont[i]).exec(
//                        new StatsCallback(countDownLatch, cont[i]));
//
//                try {
//                    countDownLatch.await(CONTAINER_COUNT, TimeUnit.SECONDS);
//                    Boolean gotStats = statsCallback.gotStats();
//
//                    System.out.println(statsCallback.toString());
//                } catch(InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            for (int i=0;i<CONTAINER_COUNT;i++) {
//                // Stop a container.
//                dockerClient.stopContainerCmd(cont[i]).exec();
//
//                // Remove a container.
//                dockerClient.removeContainerCmd(cont[i]).exec();
//
//                System.out.println("complete " + cont[i]);
//            }
//
//
//
//
//
//
//            // Create new Docker container, not start.
////            CreateContainerResponse container = dockerClient.createContainerCmd("alpine:latest")
////                    .withCmd("top")
////                    .withName(containerName)
////                    .withCpusetCpus("2,3")
////                    .withMemory((long)(20000000))
////                    .exec();
//
//            // Took container information by list and save the data to List structure
//            List<Container> containers = dockerClient.listContainersCmd().exec();
//
//        } catch(InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static class StatsCallback extends ResultCallbackTemplate<StatsCallback, Statistics> {
//        private CountDownLatch countDownLatch;
//        public static String containerID = null;
//
//        private Boolean gotStats = false;
//
//        public StatsCallback(CountDownLatch countDownLatch, String containerId) {
//            this.countDownLatch = countDownLatch;
//            this.containerID = containerId;
//        }
//
//        public void onNext(Statistics stats) {
//            String string = stats.getCpuStats().toString();
//            String memory = stats.getMemoryStats().toString();
////            System.out.println("testCPU : " + string);
////            System.out.println("testMemory : " + memory);
//            if(stats != null) {
//                System.out.println(containerID + " test" + string);
//                gotStats = true;
//            }
//            countDownLatch.countDown();
//
////            Set<Map.Entry<String, Object>> entries = stats.getCpuStats().entrySet();
////            for (Map.Entry<String, Object> entry : entries) {
////                System.out.println(entry.getKey() + " : " + entry.getValue());
////            }
//        }
//
//        public Boolean gotStats(){
//            return gotStats;
//        }
//    }
//}

//public class hello {
//    private static int NUM_STATS = 5;
//    public static int CONTAINER_COUNT=5;
//
//    public static void main(String[] args) {
//        String containerId = null;
//        CountDownLatch countDownLatch = new CountDownLatch(CONTAINER_COUNT);
//
//        try {
//            TimeUnit.SECONDS.sleep(1);
////            for(int i=0;i<100;i++) {
////                String[] containerName2 = null;
////                containerName2[i] = "generated_" + new SecureRandom().nextInt();
////            }
//
//            // Initialize docker client to use Docker REST API
//            DockerCmdExecFactory dockerCmdExecFactory = new JerseyDockerCmdExecFactory()
//                    .withReadTimeout(1000)
//                    .withConnectTimeout(1000)
//                    .withMaxTotalConnections(100)
//                    .withMaxPerRouteConnections(10);
//
//            DockerClient dockerClient = DockerClientBuilder.getInstance("tcp://localhost:2376")
//                    // .withDockerCmdExecFactory(dockerCmdExecFactory)
//                    .build();
//
//            // Get Docker info (only docker client information, not docker container information)
//            Info info = dockerClient.infoCmd().exec();
//            // System.out.print(info);
//
////            // 해당 부분은 DockerfileFixture 테스트 코드를 돌린 후에 나오는 directory 값을 확인해야 작업이 가능할 것 같다.
////            dockerClient.buildImageCmd(new File("/home/chorwon/example", directory)).withNoCache(true)
////                    .exec(new BuildImageResultCallback()).awaitImageId();
//
//            // Set variable to the File directory that has Dockerfile.
//            File baseDir = new File("/home/chorwon/example");
//
//            // Check File directory result.
//            System.out.println("Test" + baseDir.toString());
//
//            // Check build progress callback function.
//            BuildImageResultCallback callback = new BuildImageResultCallback() {
//                public void onNext(BuildResponseItem item) {
//                    // System.out.println("" + item);
//                    super.onNext(item);
//                }
//            };
//
//            EventsResultCallback callback1 = new EventsResultCallback() {
//                public void onNext(Event event) {
//                    super.onNext(event);
//                }
//            };
//
//            // docker build command. We can use callback to check build progress.
//            dockerClient.buildImageCmd(baseDir)
//                    .withTag("socket")
//                    .exec(callback).awaitImageId();
//
//            // Check docker images. 'get(0)' means bring 1st images to the docker images command list.
//            Image lastCreatedImage = dockerClient.listImagesCmd().exec().get(0);
//
////            // Setting ports  -> test
////            ExposedPort tcp22 = ExposedPort.tcp(22);
////            Ports portBindings = new Ports();
////            portBindings.bind(tcp22, Ports.Binding.bindPort(12345));
//
//            String[] containerName = new String[CONTAINER_COUNT];
//            String[] containerID = new String[CONTAINER_COUNT];
//
//            for (int i=0;i<CONTAINER_COUNT;i++) {
//                containerName[i] = "tt_" + i;
//
//                containerID[i] = dockerClient.createContainerCmd(lastCreatedImage.getId())
//                        .withCmd("testClient", "192.168.0.66", "12345")
//                        .withName(containerName[i])
//                        .withMemory((long)200000000)
//                        .withCpusetCpus("2,3")
//                        .exec().getId();
//
//
////                당연한 이야기겠지만, CreateContainerResponse id[]로 할 경우에는 이 부분은 CreateContainerResponse 형의 변수 id를 선언하는 것.
////                이렇게 할 경우, 위의 String 선언과 겹치게 된다.
////                CreateContainerResponse id = dockerClient
////                        .createContainerCmd(lastCreatedImage.getId())
////                        .withCmd("testClient", "192.168.0.66", "12345")
////                        .withName(containerName[i])
////                        .withMemory((long)200000000)
////                        .withCpusetCpus("2,3")
////                        .exec();
//
//                dockerClient.startContainerCmd(containerID[i]).exec();
//
//                // Make a Action code before stop and remove container when the condition occur.
//                // StatsCallbackTest
//                StatsCallback statsCallback = dockerClient.statsCmd(containerID[i]).exec(
//                        new StatsCallback(countDownLatch, containerID[i]));
//
//                countDownLatch.await();
//
////                countDownLatch.await(3, TimeUnit.SECONDS);
//                // Boolean gotStats = statsCallback.gotStats();
//
//                System.out.println(statsCallback.toString() + containerID[i]);
//            }
//
//            for (int i=0;i<CONTAINER_COUNT;i++) {
//                // Stop a container.
//                dockerClient.stopContainerCmd(containerID[i]).exec();
//
//                // Remove a container.
//                dockerClient.removeContainerCmd(containerID[i]).exec();
//
//                System.out.println("complete " + containerID[i]);
//            }
//
//////            수정 전의 반복문
////            for (int i=0;i<CONTAINER_COUNT;i++) {
////
////                // Set a Container Name. It can be changed by Array to save multiple container name.
////                // containerName[i] = "generated_" + new SecureRandom().nextInt();
////                containerName[i] = "tt_" + i;
////
////                // create new Docker container, not start.
//////                containerId = dockerClient.createContainerCmd(lastCreatedImage.getId())
//////                        //.withCmd("./Intro_javaClient.sh", "testClient", "192.168.0.66", "12345")
//////                        .withCmd("top")
//////                        .withName(containerName[i])
//////                        .withMemory((long)2000000)
//////                        .withCpusetCpus("2,3")
//////                        //.withExposedPorts(tcp22)
//////                        //.withPortBindings(portBindings)
//////                        .exec().getId();
////
////                CreateContainerResponse cont = dockerClient
////                        .createContainerCmd(lastCreatedImage.getId())
////                        .withCmd("testClient", "192.168.0.66", "12345")
////                        // 192.168.0.66으로 직접 넣어줘야 한다. 해당 부분을 localhost로 할 경우 인식을 못함.
////                        // docker network에 따른 문제로 예상.
////                        .withName(containerName[i])
////                        .withMemory((long)200000000)
////                        .withCpusetCpus("2,3")
////                        .exec();
////
////
////                // Start a container after created container
////                dockerClient.startContainerCmd(cont.getId()).exec();
////
////                // Make a Action code before stop and remove container when the condition occur.
////                // StatsCallbackTest
//////                StatsCallback statsCallback = dockerClient.statsCmd(cont.getId()).exec(
//////                        new StatsCallback(countDownLatch, cont.getId()));
//////
//////                countDownLatch.await(3, TimeUnit.SECONDS);
//////                Boolean gotStats = statsCallback.gotStats();
//////
//////                System.out.println(statsCallback.toString() + cont.getId());
////
////
//////                // Stop a container.
//////                dockerClient.stopContainerCmd(cont.getId()).exec();
//////
//////                // Remove a container.
//////                dockerClient.removeContainerCmd(cont.getId()).exec();
////
////                System.out.println("complete " + cont.getId());
////            }
//
//        } catch(InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static class StatsCallback extends ResultCallbackTemplate<StatsCallback, Statistics> {
//        private CountDownLatch countDownLatch;
//        public static String containerID = null;
//
//        private Boolean gotStats = false;
//
//        public StatsCallback(CountDownLatch countDownLatch, String containerId) {
//            this.countDownLatch = countDownLatch;
//            this.containerID = containerId;
//        }
//
//        public void onNext(Statistics stats) {
//            String string = stats.getCpuStats().toString();
//            String memory = stats.getMemoryStats().toString();
//            int count = 0;
////            System.out.println("testCPU : " + string);
////            System.out.println("testMemory : " + memory);
//            if(stats != null) {
//                System.out.println(containerID + " test " + string);
//                // gotStats = true;
//            }
//            countDownLatch.countDown();
//
//
////            Set<Map.Entry<String, Object>> entries = stats.getCpuStats().entrySet();
////            for (Map.Entry<String, Object> entry : entries) {
////                System.out.println(entry.getKey() + " : " + entry.getValue());
////            }
//        }
//
//        public Boolean gotStats(){
//            return gotStats;
//        }
//    }
//}

//public class hello {
//    public static int CONTAINER_COUNT=5;
//
//    public static void main(String[] args) {
//        String containerId = null;
//        CyclicBarrier barrier = new CyclicBarrier(CONTAINER_COUNT);
//
//        try {
//            TimeUnit.SECONDS.sleep(1);
////            for(int i=0;i<100;i++) {
////                String[] containerName2 = null;
////                containerName2[i] = "generated_" + new SecureRandom().nextInt();
////            }
//
//            // Initialize docker client to use Docker REST API
//            DockerCmdExecFactory dockerCmdExecFactory = new JerseyDockerCmdExecFactory()
//                    .withReadTimeout(1000)
//                    .withConnectTimeout(1000)
//                    .withMaxTotalConnections(100)
//                    .withMaxPerRouteConnections(10);
//
//            DockerClient dockerClient = DockerClientBuilder.getInstance("tcp://localhost:2376")
//                    // .withDockerCmdExecFactory(dockerCmdExecFactory)
//                    .build();
//
//            // Get Docker info (only docker client information, not docker container information)
//            Info info = dockerClient.infoCmd().exec();
//            // System.out.print(info);
//
////            // 해당 부분은 DockerfileFixture 테스트 코드를 돌린 후에 나오는 directory 값을 확인해야 작업이 가능할 것 같다.
////            dockerClient.buildImageCmd(new File("/home/chorwon/example", directory)).withNoCache(true)
////                    .exec(new BuildImageResultCallback()).awaitImageId();
//
//            // Set variable to the File directory that has Dockerfile.
//            File baseDir = new File("/home/chorwon/example");
//
//            // Check File directory result.
//            System.out.println("Test" + baseDir.toString());
//
//            // Check build progress callback function.
//            BuildImageResultCallback callback = new BuildImageResultCallback() {
//                public void onNext(BuildResponseItem item) {
//                    // System.out.println("" + item);
//                    super.onNext(item);
//                }
//            };
//
//            EventsResultCallback callback1 = new EventsResultCallback() {
//                public void onNext(Event event) {
//                    super.onNext(event);
//                }
//            };
//
//            // docker build command. We can use callback to check build progress.
//            dockerClient.buildImageCmd(baseDir)
//                    .withTag("socket")
//                    .exec(callback).awaitImageId();
//
//            // Check docker images. 'get(0)' means bring 1st images to the docker images command list.
//            Image lastCreatedImage = dockerClient.listImagesCmd().exec().get(0);
//
////            // Setting ports  -> test
////            ExposedPort tcp22 = ExposedPort.tcp(22);
////            Ports portBindings = new Ports();
////            portBindings.bind(tcp22, Ports.Binding.bindPort(12345));
//
//            String[] containerName = new String[CONTAINER_COUNT];
//            String[] containerID = new String[CONTAINER_COUNT];
//
//            for (int i=0;i<CONTAINER_COUNT;i++) {
//                containerName[i] = "tt_" + i;
//
//                containerID[i] = dockerClient.createContainerCmd(lastCreatedImage.getId())
//                        .withCmd("testClient", "192.168.0.66", "12345")
//                        .withName(containerName[i])
//                        .withMemory((long)200000000)
//                        .withCpusetCpus("2,3")
//                        .exec().getId();
//
//
////                당연한 이야기겠지만, CreateContainerResponse id[]로 할 경우에는 이 부분은 CreateContainerResponse 형의 변수 id를 선언하는 것.
////                이렇게 할 경우, 위의 String 선언과 겹치게 된다.
////                CreateContainerResponse id = dockerClient
////                        .createContainerCmd(lastCreatedImage.getId())
////                        .withCmd("testClient", "192.168.0.66", "12345")
////                        .withName(containerName[i])
////                        .withMemory((long)200000000)
////                        .withCpusetCpus("2,3")
////                        .exec();
//
//                dockerClient.startContainerCmd(containerID[i]).exec();
//
//                // Make a Action code before stop and remove container when the condition occur.
//                // StatsCallbackTest
////                StatsCallback statsCallback = dockerClient.statsCmd(containerID[i]).exec(
////                        new StatsCallback(barrier, containerID[i]));
//
////                countDownLatch.await(3, TimeUnit.SECONDS);
//                // Boolean gotStats = statsCallback.gotStats();
//
//                // System.out.println(statsCallback.toString() + containerID[i]);
//            }
//
//            // 그냥 이 부분을 while로 해서 특정 컨테이너에 대해서만 확인하는 걸로 바꾸는 게 나을 것 같다....
//            for (int i=0;i<CONTAINER_COUNT;i++) {
//                dockerClient.statsCmd(containerID[i]).exec(new StatsCallback(barrier, containerID[i]));
//            }
//
////            for (int i=0;i<CONTAINER_COUNT;i++) {
////                // Stop a container.
////                dockerClient.stopContainerCmd(containerID[i]).exec();
////
////                // Remove a container.
////                dockerClient.removeContainerCmd(containerID[i]).exec();
////
////                System.out.println("complete " + containerID[i]);
////            }
//
//////            수정 전의 반복문
////            for (int i=0;i<CONTAINER_COUNT;i++) {
////
////                // Set a Container Name. It can be changed by Array to save multiple container name.
////                // containerName[i] = "generated_" + new SecureRandom().nextInt();
////                containerName[i] = "tt_" + i;
////
////                // create new Docker container, not start.
//////                containerId = dockerClient.createContainerCmd(lastCreatedImage.getId())
//////                        //.withCmd("./Intro_javaClient.sh", "testClient", "192.168.0.66", "12345")
//////                        .withCmd("top")
//////                        .withName(containerName[i])
//////                        .withMemory((long)2000000)
//////                        .withCpusetCpus("2,3")
//////                        //.withExposedPorts(tcp22)
//////                        //.withPortBindings(portBindings)
//////                        .exec().getId();
////
////                CreateContainerResponse cont = dockerClient
////                        .createContainerCmd(lastCreatedImage.getId())
////                        .withCmd("testClient", "192.168.0.66", "12345")
////                        // 192.168.0.66으로 직접 넣어줘야 한다. 해당 부분을 localhost로 할 경우 인식을 못함.
////                        // docker network에 따른 문제로 예상.
////                        .withName(containerName[i])
////                        .withMemory((long)200000000)
////                        .withCpusetCpus("2,3")
////                        .exec();
////
////
////                // Start a container after created container
////                dockerClient.startContainerCmd(cont.getId()).exec();
////
////                // Make a Action code before stop and remove container when the condition occur.
////                // StatsCallbackTest
//////                StatsCallback statsCallback = dockerClient.statsCmd(cont.getId()).exec(
//////                        new StatsCallback(countDownLatch, cont.getId()));
//////
//////                countDownLatch.await(3, TimeUnit.SECONDS);
//////                Boolean gotStats = statsCallback.gotStats();
//////
//////                System.out.println(statsCallback.toString() + cont.getId());
////
////
//////                // Stop a container.
//////                dockerClient.stopContainerCmd(cont.getId()).exec();
//////
//////                // Remove a container.
//////                dockerClient.removeContainerCmd(cont.getId()).exec();
////
////                System.out.println("complete " + cont.getId());
////            }
//
//        } catch(InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static class StatsCallback extends ResultCallbackTemplate<StatsCallback, Statistics> {
//        private CyclicBarrier cyclicBarrier;
//        public static String containerID = null;
//
//        private Boolean gotStats = false;
//
//        public StatsCallback(CyclicBarrier cyclicBarrier, String containerId) {
//            this.cyclicBarrier = cyclicBarrier;
//            this.containerID = containerId;
//        }
//
//        public void onNext(Statistics stats) {
//            String string = stats.getCpuStats().toString();
//            String memory = stats.getMemoryStats().toString();
//            int count = 0;
////            System.out.println("testCPU : " + string);
////            System.out.println("testMemory : " + memory);
//
////            if(stats != null) {
////                try {
////                    cyclicBarrier.await();
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                } catch (BrokenBarrierException e) {
////                    e.printStackTrace();
////                } finally {
////                    System.out.println(containerID + " test " + string);
////                    // gotStats = true;
////                }
////            }
//
//            if(stats != null) {
//                try {
//                    cyclicBarrier.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (BrokenBarrierException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(containerID + " test " + string);
////                    // gotStats = true;
//            }
//
////            Set<Map.Entry<String, Object>> entries = stats.getCpuStats().entrySet();
////            for (Map.Entry<String, Object> entry : entries) {
////                System.out.println(entry.getKey() + " : " + entry.getValue());
////            }
//        }
//
//        public Boolean gotStats(){
//            return gotStats;
//        }
//    }
//}

//public class hello {
//    // The number of containers will be made.
//    public static int CONTAINER_COUNT=5;
//
//    public static void main(String[] args) {
//        // 이것도 조금만 더 생각을 해보자....
//        // 근데 아마도 넣게 된다면.... 'CountDownLatch'가 들어갈 것으로 예상
//        CyclicBarrier barrier = new CyclicBarrier(CONTAINER_COUNT);
//
//        try {  // TimeUnit.
//            TimeUnit.SECONDS.sleep(1);
//
//            /* 컨테이너 이름 생성 부분. 임의의 숫자를 만들고, 이를 컨테이너 이름으로 사용하기 위함.
//            for(int i=0;i<CONTAINER_COUNT;i++) {
//                String[] containerName2 = null;
//                containerName2[i] = "generated_" + new SecureRandom().nextInt();
//            }
//            */
//
//            // Initialize docker client to use Docker REST API
//            // 맥 버전에서는 조금 더 추가되는 부분이 있음.
//            DockerCmdExecFactory dockerCmdExecFactory = new JerseyDockerCmdExecFactory()
//                    .withReadTimeout(1000)
//                    .withConnectTimeout(1000)
//                    .withMaxTotalConnections(100)
//                    .withMaxPerRouteConnections(10);
//
//            /* Make a docker client.*/
//            DockerClient dockerClient = DockerClientBuilder.getInstance("tcp://localhost:2376")
//                    // .withDockerCmdExecFactory(dockerCmdExecFactory)
//                    .build();
//
//            /* Get Docker info (only docker client information, not docker container information) */
//            Info info = dockerClient.infoCmd().exec();
//            // System.out.print(info);
//
//            /* Set variable to the File directory that has Dockerfile. */
//            File baseDir = new File("/home/chorwon/example");
//
//            // Check File directory result.
//            System.out.println("Dockerfile directory : " + baseDir.toString());
//
//            /* Check build progress callback function. */
//            BuildImageResultCallback callback = new BuildImageResultCallback() {
//                public void onNext(BuildResponseItem item) {
//                    // System.out.println("" + item);
//                    super.onNext(item);
//                }
//            };
//
//            /* Make a event to use Callback function. */
//            EventsResultCallback callback1 = new EventsResultCallback() {
//                public void onNext(Event event) {
//                    super.onNext(event);
//                }
//            };
//
//            // docker build command. We can use callback to check build progress.
//            dockerClient.buildImageCmd(baseDir)
//                    .withTag("socket")
//                    .exec(callback).awaitImageId();
//
//            // Check docker images. 'get(0)' means bring 1st images to the docker images command list.
//            Image lastCreatedImage = dockerClient.listImagesCmd().exec().get(0);
//
//            /* Setting ports  -> need to test
//            ExposedPort tcp22 = ExposedPort.tcp(22);
//            Ports portBindings = new Ports();
//            portBindings.bind(tcp22, Ports.Binding.bindPort(12345));
//            */
//
//            String[] containerName = new String[CONTAINER_COUNT];
//            String[] containerID = new String[CONTAINER_COUNT];
//
//            /* CONTAINER_COUNT 만큼 컨테이너 생성하는 반복문 */
//            for (int i=0;i<CONTAINER_COUNT;i++) {
//                containerName[i] = "tt_" + i;
//
//                containerID[i] = dockerClient.createContainerCmd(lastCreatedImage.getId())
//                        .withCmd("testClient", "192.168.0.66", "12345")
//                        .withName(containerName[i])
//                        .withMemory((long)200000000)
//                        .withCpusetCpus("2,3")
//                        .exec().getId();
//
//
////                당연한 이야기겠지만, CreateContainerResponse id[]로 할 경우에는 이 부분은 CreateContainerResponse 형의 변수 id를 선언하는 것.
////                이렇게 할 경우, 위의 String 선언과 변수가 겹치게 된다.
////                CreateContainerResponse id = dockerClient
////                        .createContainerCmd(lastCreatedImage.getId())
////                        .withCmd("testClient", "192.168.0.66", "12345")
////                        .withName(containerName[i])
////                        .withMemory((long)200000000)
////                        .withCpusetCpus("2,3")
////                        .exec();
//
//                dockerClient.startContainerCmd(containerID[i]).exec();
//            }
//
//            /* Make a Action code before stop and remove container when the condition occur. */
//            /* // StatsCallbackTest
//                StatsCallback statsCallback = dockerClient.statsCmd(containerID[i]).exec(
//                        new StatsCallback(barrier, containerID[i]));
//
//                countDownLatch.await(3, TimeUnit.SECONDS);
//            // Boolean gotStats = statsCallback.gotStats();
//
//            // System.out.println(statsCallback.toString() + containerID[i]);
//            */
//
////            // 그냥 이 부분을 while 로 해서 특정 컨테이너에 대해서만 확인하는 걸로 바꾸는 게 나을 것 같다....
////            for (int i=0;i<CONTAINER_COUNT;i++) {
////                dockerClient.statsCmd(containerID[i]).exec(new StatsCallback(barrier, containerID[i]));
////            }
//
//            /* Stop and remove container */
//            for (int i=0;i<CONTAINER_COUNT;i++) {
//                // Stop a container.
//                // dockerClient.stopContainerCmd(containerID[i]).exec();
//
//                // Wait Containers to stop and remove
////                WaitContainerResultCallback waitContainerResultCallback = dockerClient.waitContainerCmd(containerID[i])
////                        .exec(new WaitContainerResultCallback());
//
//                dockerClient.killContainerCmd(containerID[i]).exec();
//            }
//
//            for (int i=0;i<CONTAINER_COUNT;i++) {
//                // Remove a container.
//                dockerClient.removeContainerCmd(containerID[i]).exec();
//
//                // Check to remove container
//                System.out.println("complete " + containerID[i]);
//            }
//
//        } catch(InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static class StatsCallback extends ResultCallbackTemplate<StatsCallback, Statistics> {
//        private CyclicBarrier cyclicBarrier;
//        public static String containerID = null;
//
//        private Boolean gotStats = false;
//
//        public StatsCallback(CyclicBarrier cyclicBarrier, String containerId) {
//            this.cyclicBarrier = cyclicBarrier;
//            this.containerID = containerId;
//        }
//
//        public void onNext(Statistics stats) {
//            String string = stats.getCpuStats().toString();
//            String memory = stats.getMemoryStats().toString();
//            int count = 0;
////            System.out.println("testCPU : " + string);
////            System.out.println("testMemory : " + memory);
//
////            if(stats != null) {
////                try {
////                    cyclicBarrier.await();
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                } catch (BrokenBarrierException e) {
////                    e.printStackTrace();
////                } finally {
////                    System.out.println(containerID + " test " + string);
////                    // gotStats = true;
////                }
////            }
//
//            if(stats != null) {
//                try {
//                    cyclicBarrier.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (BrokenBarrierException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(containerID + " test " + string);
////                    // gotStats = true;
//            }
//
////            Set<Map.Entry<String, Object>> entries = stats.getCpuStats().entrySet();
////            for (Map.Entry<String, Object> entry : entries) {
////                System.out.println(entry.getKey() + " : " + entry.getValue());
////            }
//        }
//
//        public Boolean gotStats(){
//            return gotStats;
//        }
//    }
//}













//    //// 클래스화
//public class hello {
//    // The number of containers will be made.
//    public static int CONTAINER_COUNT=5;
//
//    public static void main(String[] args) {
//        // 이것도 조금만 더 생각을 해보자....
//        // 근데 아마도 넣게 된다면.... 'CountDownLatch'가 들어갈 것으로 예상
//        CyclicBarrier barrier = new CyclicBarrier(CONTAINER_COUNT);
//
//        try {  // TimeUnit.
//            TimeUnit.SECONDS.sleep(1);
//
//            /* 컨테이너 이름 생성 부분. 임의의 숫자를 만들고, 이를 컨테이너 이름으로 사용하기 위함.
//            for(int i=0;i<CONTAINER_COUNT;i++) {
//                String[] containerName2 = null;
//                containerName2[i] = "generated_" + new SecureRandom().nextInt();
//            }
//            */
//
//            // Initialize docker client to use Docker REST API
//            // 맥 버전에서는 조금 더 추가되는 부분이 있음.
//            DockerCmdExecFactory dockerCmdExecFactory = new JerseyDockerCmdExecFactory()
//                    .withReadTimeout(1000)
//                    .withConnectTimeout(1000)
//                    .withMaxTotalConnections(100)
//                    .withMaxPerRouteConnections(10);
//
//            /* Make a docker client.*/
//            DockerClient dockerClient = DockerClientBuilder.getInstance("tcp://localhost:2376")
//                    // .withDockerCmdExecFactory(dockerCmdExecFactory)
//                    .build();
//
//            /* Take Docker Client Information */
//            Information infm = new Information(dockerClient);
//            // infm.Inform();
//
////            /* Set variable to the File directory that has Dockerfile. */
////            File baseDir = new File("/home/chorwon/example");
////
////            // Check File directory result.
////            System.out.println("Dockerfile directory : " + baseDir.toString());
////
////            /* Check build progress callback function. */
////            BuildImageResultCallback callback = new BuildImageResultCallback() {
////                public void onNext(BuildResponseItem item) {
////                    // System.out.println("" + item);
////                    super.onNext(item);
////                }
////            };
////
////            /* Make a event to use Callback function. */
////            EventsResultCallback callback1 = new EventsResultCallback() {
////                public void onNext(Event event) {
////                    super.onNext(event);
////                }
////            };
////
////            // docker build command. We can use callback to check build progress.
////            dockerClient.buildImageCmd(baseDir)
////                    .withTag("socket")
////                    .exec(callback).awaitImageId();
////
////            // Check docker images. 'get(0)' means bring 1st images to the docker images command list.
////            Image lastCreatedImage = dockerClient.listImagesCmd().exec().get(0);
//
//            /* Select docker images and build */
//            dockerfile df = new dockerfile(dockerClient, "/home/chorwon/example", "socket");
//            df.buildImages();
//
//
//
//            /* Setting ports  -> need to test
//            ExposedPort tcp22 = ExposedPort.tcp(22);
//            Ports portBindings = new Ports();
//            portBindings.bind(tcp22, Ports.Binding.bindPort(12345));
//            */
//
//            String[] containerName = new String[CONTAINER_COUNT];
//            String[] containerID = new String[CONTAINER_COUNT];
//
//            /* CONTAINER_COUNT 만큼 컨테이너 생성하는 반복문 */
//            for (int i=0;i<CONTAINER_COUNT;i++) {
//                containerName[i] = "tt_" + i;
//
//                containerID[i] = dockerClient.createContainerCmd(df.buildImages(dockerClient).getId())
//                        .withCmd("testClient", "192.168.0.66", "12345")
//                        .withName(containerName[i])
//                        .withMemory((long)200000000)
//                        .withCpusetCpus("2,3")
//                        .exec().getId();
//
//
////                당연한 이야기겠지만, CreateContainerResponse id[]로 할 경우에는 이 부분은 CreateContainerResponse 형의 변수 id를 선언하는 것.
////                이렇게 할 경우, 위의 String 선언과 변수가 겹치게 된다.
////                CreateContainerResponse id = dockerClient
////                        .createContainerCmd(lastCreatedImage.getId())
////                        .withCmd("testClient", "192.168.0.66", "12345")
////                        .withName(containerName[i])
////                        .withMemory((long)200000000)
////                        .withCpusetCpus("2,3")
////                        .exec();
//
//                // dockerClient.startContainerCmd(containerID[i]).exec();
//                startContainer st = new startContainer(dockerClient, containerID[i], CONTAINER_COUNT);
//                st.startcont();
//            }
//
//            /* Make a Action code before stop and remove container when the condition occur. */
//            /* // StatsCallbackTest
//                StatsCallback statsCallback = dockerClient.statsCmd(containerID[i]).exec(
//                        new StatsCallback(barrier, containerID[i]));
//
//                countDownLatch.await(3, TimeUnit.SECONDS);
//            // Boolean gotStats = statsCallback.gotStats();
//
//            // System.out.println(statsCallback.toString() + containerID[i]);
//            */
//
////            // 그냥 이 부분을 while 로 해서 특정 컨테이너에 대해서만 확인하는 걸로 바꾸는 게 나을 것 같다....
////            for (int i=0;i<CONTAINER_COUNT;i++) {
////                dockerClient.statsCmd(containerID[i]).exec(new StatsCallback(barrier, containerID[i]));
////            }
//
//            /* Stop and remove container */
//            for (int i=0;i<CONTAINER_COUNT;i++) {
//                // Stop a container.
//                // dockerClient.stopContainerCmd(containerID[i]).exec();
//
//                // Wait Containers to stop and remove
////                WaitContainerResultCallback waitContainerResultCallback = dockerClient.waitContainerCmd(containerID[i])
////                        .exec(new WaitContainerResultCallback());
//
//                dockerClient.killContainerCmd(containerID[i]).exec();
//            }
//
//            for (int i=0;i<CONTAINER_COUNT;i++) {
//                // Remove a container.
//                dockerClient.removeContainerCmd(containerID[i]).exec();
//
//                // Check to remove container
//                System.out.println("complete " + containerID[i]);
//            }
//
//        } catch(InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static class StatsCallback extends ResultCallbackTemplate<StatsCallback, Statistics> {
//        private CyclicBarrier cyclicBarrier;
//        public static String containerID = null;
//
//        private Boolean gotStats = false;
//
//        public StatsCallback(CyclicBarrier cyclicBarrier, String containerId) {
//            this.cyclicBarrier = cyclicBarrier;
//            this.containerID = containerId;
//        }
//
//        public void onNext(Statistics stats) {
//            String string = stats.getCpuStats().toString();
//            String memory = stats.getMemoryStats().toString();
//            int count = 0;
////            System.out.println("testCPU : " + string);
////            System.out.println("testMemory : " + memory);
//
////            if(stats != null) {
////                try {
////                    cyclicBarrier.await();
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                } catch (BrokenBarrierException e) {
////                    e.printStackTrace();
////                } finally {
////                    System.out.println(containerID + " test " + string);
////                    // gotStats = true;
////                }
////            }
//
//            if(stats != null) {
//                try {
//                    cyclicBarrier.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (BrokenBarrierException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(containerID + " test " + string);
////                    // gotStats = true;
//            }
//
////            Set<Map.Entry<String, Object>> entries = stats.getCpuStats().entrySet();
////            for (Map.Entry<String, Object> entry : entries) {
////                System.out.println(entry.getKey() + " : " + entry.getValue());
////            }
//        }
//
//        public Boolean gotStats(){
//            return gotStats;
//        }
//    }
//}
//
//class Information {
//    private DockerClient dockerClient;
//    private String container = null;
//
//    public Information(DockerClient dockerClient) {
//        this.dockerClient = dockerClient;
//    }
//
//    public Information(DockerClient dockerClient, String container) {
//        this.container = container;
//    }
//
//    public void Inform() {
//        /* Get Docker info (only docker client information, not docker container information) */
//        Info info = dockerClient.infoCmd().exec();
//        System.out.print(info);
//    }
//}
//
//class dockerfile {
//    private String path, tag;
//    private File baseDir;
//    private DockerClient dockerClient;
//    private Image lastCreatedImage;
//
//    public dockerfile(DockerClient dockerClient, String path, String tag) {
//        this.dockerClient = dockerClient;
//        this.path = path;
//        this.tag = tag;
//    }
//
//    public void buildImages() {
//        /* Set variable to the File directory that has Dockerfile. */
//        baseDir = new File(path);
//
//        System.out.println("Dockerfile directory : " + baseDir.toString());
//
//        /* Check build progress callback function. */
//        BuildImageResultCallback callback = new BuildImageResultCallback() {
//            public void onNext(BuildResponseItem item) {
//                // System.out.println("" + item);
//                super.onNext(item);
//            }
//        };
//
//        /* Make a event to use Callback function. */
//        EventsResultCallback callback1 = new EventsResultCallback() {
//            public void onNext(Event event) {
//                super.onNext(event);
//            }
//        };
//
//        // docker build command. We can use callback to check build progress.
//        dockerClient.buildImageCmd(baseDir)
//                .withTag(tag)
//                .exec(callback).awaitImageId();
//    }
//
//    public Image buildImages(DockerClient dockerClient) {
//
//        // Check docker images. 'get(0)' means bring 1st images to the docker images command list.
//        Image lastCreatedImage = dockerClient.listImagesCmd().exec().get(0);
//
//        return lastCreatedImage;
//    }
//}
//
//class startContainer {
//    private DockerClient dockerClient;
//    private String containerID;
//    private int startcount;
//
//
//    public startContainer(DockerClient dockerClient, String containerID, int COUNT) {
//        this.startcount = COUNT;
//        this.dockerClient = dockerClient;
//        this.containerID = containerID;
//    }
//
//    public void startcont() {
//        dockerClient.startContainerCmd(containerID).exec();
//    }
//
////    for (int i=0;i<CONTAINER_COUNT;i++) {
////        containerName[i] = "tt_" + i;
////
////        containerID[i] = dockerClient.createContainerCmd(lastCreatedImage.getId())
////                .withCmd("testClient", "192.168.0.66", "12345")
////                .withName(containerName[i])
////                .withMemory((long)200000000)
////                .withCpusetCpus("2,3")
////                .exec().getId();
////
////
//////                당연한 이야기겠지만, CreateContainerResponse id[]로 할 경우에는 이 부분은 CreateContainerResponse 형의 변수 id를 선언하는 것.
//////                이렇게 할 경우, 위의 String 선언과 변수가 겹치게 된다.
//////                CreateContainerResponse id = dockerClient
//////                        .createContainerCmd(lastCreatedImage.getId())
//////                        .withCmd("testClient", "192.168.0.66", "12345")
//////                        .withName(containerName[i])
//////                        .withMemory((long)200000000)
//////                        .withCpusetCpus("2,3")
//////                        .exec();
////
////        // dockerClient.startContainerCmd(containerID[i]).exec();
////        startContainer st = new startContainer(dockerClient, containerID[i], CONTAINER_COUNT);
////        st.startcont();
////    }
//}

//public class hello {
//    // The number of containers will be made.
//    public static int CONTAINER_COUNT=5;
//
//    public static void main(String[] args) {
//        CyclicBarrier barrier = new CyclicBarrier(CONTAINER_COUNT);
//
//        try {  // TimeUnit.
//            TimeUnit.SECONDS.sleep(1);
//
//            /* 컨테이너 이름 생성 부분. 임의의 숫자를 만들고, 이를 컨테이너 이름으로 사용하기 위함.
//            for(int i=0;i<CONTAINER_COUNT;i++) {
//                String[] containerName2 = null;
//                containerName2[i] = "generated_" + new SecureRandom().nextInt();
//            }
//            */
//
//            // Initialize docker client to use Docker REST API
//            // 맥 버전에서는 조금 더 추가되는 부분이 있음.
//            DockerCmdExecFactory dockerCmdExecFactory = new JerseyDockerCmdExecFactory()
//                    .withReadTimeout(1000)
//                    .withConnectTimeout(1000)
//                    .withMaxTotalConnections(100)
//                    .withMaxPerRouteConnections(10);
//
//            /* Make a docker client.*/
//            DockerClient dockerClient = DockerClientBuilder.getInstance("tcp://localhost:2376")
//                    // .withDockerCmdExecFactory(dockerCmdExecFactory)
//                    .build();
//
//            /* Take Docker Client Information */
//            Information infm = new Information(dockerClient);
//            // infm.Inform();
//
////            /* Set variable to the File directory that has Dockerfile. */
////            File baseDir = new File("/home/chorwon/example");
////
////            // Check File directory result.
////            System.out.println("Dockerfile directory : " + baseDir.toString());
////
////            /* Check build progress callback function. */
////            BuildImageResultCallback callback = new BuildImageResultCallback() {
////                public void onNext(BuildResponseItem item) {
////                    // System.out.println("" + item);
////                    super.onNext(item);
////                }
////            };
////
////            /* Make a event to use Callback function. */
////            EventsResultCallback callback1 = new EventsResultCallback() {
////                public void onNext(Event event) {
////                    super.onNext(event);
////                }
////            };
////
////            // docker build command. We can use callback to check build progress.
////            dockerClient.buildImageCmd(baseDir)
////                    .withTag("socket")
////                    .exec(callback).awaitImageId();
////
////            // Check docker images. 'get(0)' means bring 1st images to the docker images command list.
////            Image lastCreatedImage = dockerClient.listImagesCmd().exec().get(0);
//
//            /* Select docker images and build */
//            dockerfile df = new dockerfile(dockerClient, "/home/chorwon/example", "socket");
//            df.buildImages();
//
//
//
//            /* Setting ports  -> need to test
//            ExposedPort tcp22 = ExposedPort.tcp(22);
//            Ports portBindings = new Ports();
//            portBindings.bind(tcp22, Ports.Binding.bindPort(12345));
//            */
//
//            String[] containerName = new String[CONTAINER_COUNT];
//            String[] containerID = new String[CONTAINER_COUNT];
//
//            /* CONTAINER_COUNT 만큼 컨테이너 생성하는 반복문 */
//            for (int i=0;i<CONTAINER_COUNT;i++) {
//                containerName[i] = "tt_" + i;
//
//                containerID[i] = dockerClient.createContainerCmd(df.buildImages(dockerClient).getId())
//                        .withCmd("testClient", "192.168.0.66", "12345")
//                        .withName(containerName[i])
//                        .withMemory((long)200000000)
//                        .withCpusetCpus("2,3")
//                        .exec().getId();
//
//
////                당연한 이야기겠지만, CreateContainerResponse id[]로 할 경우에는 이 부분은 CreateContainerResponse 형의 변수 id를 선언하는 것.
////                이렇게 할 경우, 위의 String 선언과 변수가 겹치게 된다.
////                CreateContainerResponse id = dockerClient
////                        .createContainerCmd(lastCreatedImage.getId())
////                        .withCmd("testClient", "192.168.0.66", "12345")
////                        .withName(containerName[i])
////                        .withMemory((long)200000000)
////                        .withCpusetCpus("2,3")
////                        .exec();
//
//                // dockerClient.startContainerCmd(containerID[i]).exec();
//                startContainer st = new startContainer(dockerClient, containerID[i], CONTAINER_COUNT);
//                st.startcont();
//            }
//
//            /* Make a Action code before stop and remove container when the condition occur. */
//            /* // StatsCallbackTest
//                StatsCallback statsCallback = dockerClient.statsCmd(containerID[i]).exec(
//                        new StatsCallback(barrier, containerID[i]));
//
//                countDownLatch.await(3, TimeUnit.SECONDS);
//            // Boolean gotStats = statsCallback.gotStats();
//
//            // System.out.println(statsCallback.toString() + containerID[i]);
//            */
//
////            // 그냥 이 부분을 while 로 해서 특정 컨테이너에 대해서만 확인하는 걸로 바꾸는 게 나을 것 같다....
////            for (int i=0;i<CONTAINER_COUNT;i++) {
////                dockerClient.statsCmd(containerID[i]).exec(new StatsCallback(barrier, containerID[i]));
////            }
//
//            /* Stop and remove container */
//            for (int i=0;i<CONTAINER_COUNT;i++) {
//                // Stop a container.
//                // dockerClient.stopContainerCmd(containerID[i]).exec();
//
//                // Wait Containers to stop and remove
////                WaitContainerResultCallback waitContainerResultCallback = dockerClient.waitContainerCmd(containerID[i])
////                        .exec(new WaitContainerResultCallback());
//
//                dockerClient.killContainerCmd(containerID[i]).exec();
//            }
//
//            for (int i=0;i<CONTAINER_COUNT;i++) {
//                // Remove a container.
//                dockerClient.removeContainerCmd(containerID[i]).exec();
//
//                // Check to remove container
//                System.out.println("complete " + containerID[i]);
//            }
//
//        } catch(InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static class StatsCallback extends ResultCallbackTemplate<StatsCallback, Statistics> {
//        private CyclicBarrier cyclicBarrier;
//        public static String containerID = null;
//
//        private Boolean gotStats = false;
//
//        public StatsCallback(CyclicBarrier cyclicBarrier, String containerId) {
//            this.cyclicBarrier = cyclicBarrier;
//            this.containerID = containerId;
//        }
//
//        public void onNext(Statistics stats) {
//            String string = stats.getCpuStats().toString();
//            String memory = stats.getMemoryStats().toString();
//            int count = 0;
////            System.out.println("testCPU : " + string);
////            System.out.println("testMemory : " + memory);
//
////            if(stats != null) {
////                try {
////                    cyclicBarrier.await();
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                } catch (BrokenBarrierException e) {
////                    e.printStackTrace();
////                } finally {
////                    System.out.println(containerID + " test " + string);
////                    // gotStats = true;
////                }
////            }
//
//            if(stats != null) {
//                try {
//                    cyclicBarrier.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (BrokenBarrierException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(containerID + " test " + string);
////                    // gotStats = true;
//            }
//
////            Set<Map.Entry<String, Object>> entries = stats.getCpuStats().entrySet();
////            for (Map.Entry<String, Object> entry : entries) {
////                System.out.println(entry.getKey() + " : " + entry.getValue());
////            }
//        }
//
//        public Boolean gotStats(){
//            return gotStats;
//        }
//    }
//}
//
//class Information {
//    private DockerClient dockerClient;
//    private String container = null;
//
//    public Information(DockerClient dockerClient) {
//        this.dockerClient = dockerClient;
//    }
//
//    public Information(DockerClient dockerClient, String container) {
//        this.container = container;
//    }
//
//    public void Inform() {
//        /* Get Docker info (only docker client information, not docker container information) */
//        Info info = dockerClient.infoCmd().exec();
//        System.out.print(info);
//    }
//}
//
//class dockerfile {
//    private String path, tag;
//    private File baseDir;
//    private DockerClient dockerClient;
//    private Image lastCreatedImage;
//
//    public dockerfile(DockerClient dockerClient, String path, String tag) {
//        this.dockerClient = dockerClient;
//        this.path = path;
//        this.tag = tag;
//    }
//
//    public void buildImages() {
//        /* Set variable to the File directory that has Dockerfile. */
//        baseDir = new File(path);
//
//        System.out.println("Dockerfile directory : " + baseDir.toString());
//
//        /* Check build progress callback function. */
//        BuildImageResultCallback callback = new BuildImageResultCallback() {
//            public void onNext(BuildResponseItem item) {
//                // System.out.println("" + item);
//                super.onNext(item);
//            }
//        };
//
//        /* Make a event to use Callback function. */
//        EventsResultCallback callback1 = new EventsResultCallback() {
//            public void onNext(Event event) {
//                super.onNext(event);
//            }
//        };
//
//        // docker build command. We can use callback to check build progress.
//        dockerClient.buildImageCmd(baseDir)
//                .withTag(tag)
//                .exec(callback).awaitImageId();
//    }
//
//    public Image buildImages(DockerClient dockerClient) {
//
//        // Check docker images. 'get(0)' means bring 1st images to the docker images command list.
//        Image lastCreatedImage = dockerClient.listImagesCmd().exec().get(0);
//
//        return lastCreatedImage;
//    }
//}
//
//class startContainer {
//    private DockerClient dockerClient;
//    private String containerID;
//    private int startcount;
//
//
//    public startContainer(DockerClient dockerClient, String containerID, int COUNT) {
//        this.startcount = COUNT;
//        this.dockerClient = dockerClient;
//        this.containerID = containerID;
//    }
//
//    public void startcont() {
//        dockerClient.startContainerCmd(containerID).exec();
//    }
//
////    for (int i=0;i<CONTAINER_COUNT;i++) {
////        containerName[i] = "tt_" + i;
////
////        containerID[i] = dockerClient.createContainerCmd(lastCreatedImage.getId())
////                .withCmd("testClient", "192.168.0.66", "12345")
////                .withName(containerName[i])
////                .withMemory((long)200000000)
////                .withCpusetCpus("2,3")
////                .exec().getId();
////
////
//////                당연한 이야기겠지만, CreateContainerResponse id[]로 할 경우에는 이 부분은 CreateContainerResponse 형의 변수 id를 선언하는 것.
//////                이렇게 할 경우, 위의 String 선언과 변수가 겹치게 된다.
//////                CreateContainerResponse id = dockerClient
//////                        .createContainerCmd(lastCreatedImage.getId())
//////                        .withCmd("testClient", "192.168.0.66", "12345")
//////                        .withName(containerName[i])
//////                        .withMemory((long)200000000)
//////                        .withCpusetCpus("2,3")
//////                        .exec();
////
////        // dockerClient.startContainerCmd(containerID[i]).exec();
////        startContainer st = new startContainer(dockerClient, containerID[i], CONTAINER_COUNT);
////        st.startcont();
////    }
//}


/* 도커 컨테이너의 생성부터 삭제까지 클래스화 */
//public class hello {
//    // The number of containers will be made.
//    public static int CONTAINER_COUNT=5;
//
//    public static void main(String[] args) {
//        CyclicBarrier barrier = new CyclicBarrier(CONTAINER_COUNT);
//        Map<String, String> map = new HashMap<String, String>();
//        int i = 0;
//
//        try {  // TimeUnit.
//            TimeUnit.SECONDS.sleep(1);
//
//            // Initialize docker client to use Docker REST API
//            // 맥 버전에서는 조금 더 추가되는 부분이 있음.
//            DockerCmdExecFactory dockerCmdExecFactory = new JerseyDockerCmdExecFactory()
//                    .withReadTimeout(1000)
//                    .withConnectTimeout(1000)
//                    .withMaxTotalConnections(100)
//                    .withMaxPerRouteConnections(10);
//
////            /* 위에서 언급된 맥에서의 추가된 부분 */
////            DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
////                    .withDockerHost("tcp://localhost:2375")
////                    .withDockerTlsVerify(true)
////                    .withDockerCertPath("/Users/Axlis/.docker")
////                    .withRegistryUsername("Axlis")
////                    .withRegistryPassword("CjfdnjS")
////                    .withRegistryEmail("cwkim@smartx.kr")
////                    .withRegistryUrl(null)
////                    .build();
//
//            /* Make a docker client.*/
//            // 1. In Linux
//            DockerClient dockerClient = DockerClientBuilder.getInstance("tcp://localhost:2376")
//                    // .withDockerCmdExecFactory(dockerCmdExecFactory)
//                    .build();
//
////            // 2. In Mac
////            DockerClient dockerClient = DockerClientBuilder.getInstance(config)
////                    .withDockerCmdExecFactory(dockerCmdExecFactory)
////                    .build();
//
//            /* Take Docker Client Information */
//            Information information = new Information(dockerClient);
//            information.Inform();
//
//
//            /* Select docker images and build */
//            dockerfile df = new dockerfile(dockerClient, "/home/chorwon/example", "socket");
//            df.buildImages();
//
//            settingContainer setcont = new settingContainer(dockerClient, df, map, i);
//            map = setcont.setContainer(CONTAINER_COUNT, "testClient", "192.168.0.66", "12345", "2,3", 200000000);
//
//            /* Make a Action code before stop and remove container when the condition occur. */
//            /* // StatsCallbackTest
//                StatsCallback statsCallback = dockerClient.statsCmd(containerID[i]).exec(
//                        new StatsCallback(barrier, containerID[i]));
//                countDownLatch.await(3, TimeUnit.SECONDS);
//            // Boolean gotStats = statsCallback.gotStats();
//            // System.out.println(statsCallback.toString() + containerID[i]);
//            */
//
//            startContainer stc = new startContainer(dockerClient, map);
//            stc.startMulticontainer();
//
//
////            killContainer kic = new killContainer(dockerClient, map);
////            kic.killing();
//
//            removeContainer rec = new removeContainer(dockerClient, map);
//            rec.removing();
//
//        } catch(InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static class StatsCallback extends ResultCallbackTemplate<StatsCallback, Statistics> {
//        private CyclicBarrier cyclicBarrier;
//        public static String containerID = null;
//
//        private Boolean gotStats = false;
//
//        public StatsCallback(CyclicBarrier cyclicBarrier, String containerId) {
//            this.cyclicBarrier = cyclicBarrier;
//            this.containerID = containerId;
//        }
//
//        public void onNext(Statistics stats) {
//            String string = stats.getCpuStats().toString();
//            String memory = stats.getMemoryStats().toString();
//            int count = 0;
////            System.out.println("testCPU : " + string);
////            System.out.println("testMemory : " + memory);
//
////            if(stats != null) {
////                try {
////                    cyclicBarrier.await();
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                } catch (BrokenBarrierException e) {
////                    e.printStackTrace();
////                } finally {
////                    System.out.println(containerID + " test " + string);
////                    // gotStats = true;
////                }
////            }
//
//            if(stats != null) {
//                try {
//                    cyclicBarrier.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (BrokenBarrierException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(containerID + " test " + string);
////                    // gotStats = true;
//            }
//
////            Set<Map.Entry<String, Object>> entries = stats.getCpuStats().entrySet();
////            for (Map.Entry<String, Object> entry : entries) {
////                System.out.println(entry.getKey() + " : " + entry.getValue());
////            }
//        }
//
//        public Boolean gotStats(){
//            return gotStats;
//        }
//    }
//}
//
//class Information {
//    private DockerClient dockerClient;
//    private String container = null;
//
//    public Information(DockerClient dockerClient) {
//        this.dockerClient = dockerClient;
//    }
//
//    public Information(DockerClient dockerClient, String container) {
//        this.container = container;
//    }
//
//    public void Inform() {
//        /* Get Docker info (only docker client information, not docker container information) */
//        Info info = dockerClient.infoCmd().exec();
//        System.out.print(info);
//    }
//}
//
//class dockerfile {
//    private String path, tag;
//    private File baseDir;
//    private DockerClient dockerClient;
//    private Image lastCreatedImage;
//
//    public dockerfile(DockerClient dockerClient, String path, String tag) {
//        this.dockerClient = dockerClient;
//        this.path = path;
//        this.tag = tag;
//    }
//
//    public void buildImages() {
//        /* Set variable to the File directory that has Dockerfile. */
//        baseDir = new File(path);
//
//        System.out.println("Dockerfile directory : " + baseDir.toString());
//
//        /* Check build progress callback function. */
//        BuildImageResultCallback callback = new BuildImageResultCallback() {
//            public void onNext(BuildResponseItem item) {
//                // System.out.println("" + item);
//                super.onNext(item);
//            }
//        };
//
//        /* Make a event to use Callback function. */
//        EventsResultCallback callback1 = new EventsResultCallback() {
//            public void onNext(Event event) {
//                super.onNext(event);
//            }
//        };
//
//        // docker build command. We can use callback to check build progress.
//        dockerClient.buildImageCmd(baseDir)
//                .withTag(tag)
//                .exec(callback).awaitImageId();
//    }
//
//    public Image buildImages(DockerClient dockerClient) {
//
//        // Check docker images. 'get(0)' means bring 1st images to the docker images command list.
//        Image lastCreatedImage = dockerClient.listImagesCmd().exec().get(0);
//
//        return lastCreatedImage;
//    }
//}
//
//class settingContainer {
//    private DockerClient dockerClient;
//    private dockerfile df;
//    private Map<String, String> map;
//    private int i;
//
//    public settingContainer(DockerClient dockerClient, dockerfile df, Map arr, int number) {
//        this.dockerClient = dockerClient;
//        this.df = df;
//        this.map = arr;
//        this.i = number;
//    }
//
//    public Map<String, String> setContainer(int count, String testClient, String IPaddress, String PortNUM, String CPU, int Memory) {
//        String containerName;
//        String containerID;
//        map = new HashMap<String, String>();
//
//        for (int j=i;j<i+count;j++) {
//
//            containerName= "xxx_" + j;  // 이름 중복의 가능성?
//
//            System.out.println(containerName);
//
//            containerID= dockerClient.createContainerCmd(df.buildImages(dockerClient).getId())
//                    .withCmd(testClient, IPaddress, PortNUM)
//                    .withName(containerName)
//                    .withMemory((long)Memory)
//                    .withCpusetCpus(CPU)
//                    .exec().getId();
//
//            map.put(containerName, containerID);
//        }
//
//        return map;
//    }
//
//    public String getContainerId(String name) {
//
//        if(map.containsKey(name)) {
//            return map.get(name);
//        } else {
//            return "Not Exist";
//        }
//    }
//}
//
//class startContainer {
//    private DockerClient dockerClient;
//    private Map<String, String> map;
//
//    public startContainer(DockerClient dockerClient, Map MAP) {
//        this.map = MAP;
//        this.dockerClient = dockerClient;
//    }
//
//    // 한 개의 컨테이너만 만들 경우
//    public void startSinglecontainer(String name) {
//
//        if(map.containsKey(name)) {
//            dockerClient.startContainerCmd(map.get(name)).exec();
//        } else {
//            System.out.println("Not starting, check the container name...");
//        }
//    }
//
//    // 다수의 컨테이너 만들 경우
//    public void startMulticontainer() {
//
//        Set<Map.Entry<String, String>> set = map.entrySet();
//        Iterator<Map.Entry<String, String>> it = set.iterator();
//
//        while(it.hasNext()) {
//            Map.Entry<String, String> e = it.next();
//            dockerClient.startContainerCmd(e.getValue()).exec();
//        }
//    }
//}
//
//class killContainer {
//    private int count;
//    private DockerClient dockerClient;
//    private Map<String, String> map;
//
//    public killContainer(DockerClient dockerClient, Map MAP) {
//        this.dockerClient = dockerClient;
//        this.map = MAP;
//    }
//
//    public void killing() {
//        Set<Map.Entry<String, String>> set = map.entrySet();
//        Iterator<Map.Entry<String, String>> it = set.iterator();
//
//        try {
//            Thread.sleep((map.size()) * 5000);
//            // Stop a container.
//            // dockerClient.stopContainerCmd(containerID[i]).exec();
//
//            // Wait Containers to stop and remove
////                WaitContainerResultCallback waitContainerResultCallback = dockerClient.waitContainerCmd(containerID[i])
////                        .exec(new WaitContainerResultCallback());
//
//            while(it.hasNext()) {
//                Map.Entry<String, String> e = it.next();
//                dockerClient.killContainerCmd(e.getValue()).exec();
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void killing(String name) {
//        try {
//            Thread.sleep((map.size()) * 5000);
//
//            if(map.containsKey(name)) {
//                dockerClient.killContainerCmd(map.get(name)).exec();
//            } else {
//                System.out.println("Not Killing, check the container name...");
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}
//
//class removeContainer {
//    private DockerClient dockerClient;
//    private Map<String, String> map;
//
//    public removeContainer(DockerClient dockerClient, Map MAP) {
//        this.dockerClient = dockerClient;
//        this.map = MAP;
//    }
//
//    public void removing() {
//        Set<Map.Entry<String, String>> set = map.entrySet();
//        Iterator<Map.Entry<String, String>> it = set.iterator();
//
//        try {
//            Thread.sleep((map.size()) * 5000);
//
//            while(it.hasNext()) {
//                Map.Entry<String, String> e = it.next();
//                dockerClient.removeContainerCmd(e.getValue()).exec();
//                System.out.println("Remove complete " + e.getKey());
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void removing(String name) {
//        try {
//            Thread.sleep((map.size()) * 5000);
//
//            if(map.containsKey(name)) {
//                dockerClient.removeContainerCmd(map.get(name)).exec();
//                System.out.println("complete " + map.get(name));
//            } else {
//                System.out.println("Not removing, check the container name...");
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}

/* 도커 컨테이너를 생성하기 위한 클래스화 */
//public class hello {
//    // The number of containers will be made.
//    public static int CONTAINER_COUNT=5;
//
//    public static void main(String[] args) {
//        CyclicBarrier barrier = new CyclicBarrier(CONTAINER_COUNT);
//        Map<String, String> map = new HashMap<String, String>();
//        int i = 0;
//
//        try {  // TimeUnit.
//            TimeUnit.SECONDS.sleep(1);
//
//            // Initialize docker client to use Docker REST API
//            // 맥 버전에서는 조금 더 추가되는 부분이 있음.
//            DockerCmdExecFactory dockerCmdExecFactory = new JerseyDockerCmdExecFactory()
//                    .withReadTimeout(1000)
//                    .withConnectTimeout(1000)
//                    .withMaxTotalConnections(100)
//                    .withMaxPerRouteConnections(10);
//
////            /* 위에서 언급된 맥에서의 추가된 부분 */
////            DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
////                    .withDockerHost("tcp://localhost:2375")
////                    .withDockerTlsVerify(true)
////                    .withDockerCertPath("/Users/Axlis/.docker")
////                    .withRegistryUsername("Axlis")
////                    .withRegistryPassword("CjfdnjS")
////                    .withRegistryEmail("cwkim@smartx.kr")
////                    .withRegistryUrl(null)
////                    .build();
//
//            /* Make a docker client.*/
//            // 1. In Linux
//            DockerClient dockerClient = DockerClientBuilder.getInstance("tcp://localhost:2376")
//                    // .withDockerCmdExecFactory(dockerCmdExecFactory)
//                    .build();
//
////            // 2. In Mac
////            DockerClient dockerClient = DockerClientBuilder.getInstance(config)
////                    .withDockerCmdExecFactory(dockerCmdExecFactory)
////                    .build();
//
//            /* Take Docker Client Information */
//            Information information = new Information(dockerClient);
//            information.Inform();
//
//
//            /* Select docker images and build */
//            dockerfile df = new dockerfile(dockerClient, "/home/chorwon/example", "socket");
//            df.buildImages();
//
//            settingContainer setcont = new settingContainer(dockerClient, df, map, i);
//            map = setcont.setContainer(CONTAINER_COUNT, "testClient", "192.168.0.66", "12345", "2,3", 200000000);
//
//            /* Make a Action code before stop and remove container when the condition occur. */
//            /* // StatsCallbackTest
//                StatsCallback statsCallback = dockerClient.statsCmd(containerID[i]).exec(
//                        new StatsCallback(barrier, containerID[i]));
//                countDownLatch.await(3, TimeUnit.SECONDS);
//            // Boolean gotStats = statsCallback.gotStats();
//            // System.out.println(statsCallback.toString() + containerID[i]);
//            */
//
//            startContainer stc = new startContainer(dockerClient, map);
//            stc.startMulticontainer();
//
//
////            killContainer kic = new killContainer(dockerClient, map);
////            kic.killing();
//
//            removeContainer rec = new removeContainer(dockerClient, map);
//            rec.removing();
//
//        } catch(InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static class StatsCallback extends ResultCallbackTemplate<StatsCallback, Statistics> {
//        private CyclicBarrier cyclicBarrier;
//        public static String containerID = null;
//
//        private Boolean gotStats = false;
//
//        public StatsCallback(CyclicBarrier cyclicBarrier, String containerId) {
//            this.cyclicBarrier = cyclicBarrier;
//            this.containerID = containerId;
//        }
//
//        public void onNext(Statistics stats) {
//            String string = stats.getCpuStats().toString();
//            String memory = stats.getMemoryStats().toString();
//            int count = 0;
////            System.out.println("testCPU : " + string);
////            System.out.println("testMemory : " + memory);
//
////            if(stats != null) {
////                try {
////                    cyclicBarrier.await();
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                } catch (BrokenBarrierException e) {
////                    e.printStackTrace();
////                } finally {
////                    System.out.println(containerID + " test " + string);
////                    // gotStats = true;
////                }
////            }
//
//            if(stats != null) {
//                try {
//                    cyclicBarrier.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (BrokenBarrierException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(containerID + " test " + string);
////                    // gotStats = true;
//            }
//
////            Set<Map.Entry<String, Object>> entries = stats.getCpuStats().entrySet();
////            for (Map.Entry<String, Object> entry : entries) {
////                System.out.println(entry.getKey() + " : " + entry.getValue());
////            }
//        }
//
//        public Boolean gotStats(){
//            return gotStats;
//        }
//    }
//}
//
//class Information {
//    private DockerClient dockerClient;
//    private String container = null;
//
//    public Information(DockerClient dockerClient) {
//        this.dockerClient = dockerClient;
//    }
//
//    public Information(DockerClient dockerClient, String container) {
//        this.container = container;
//    }
//
//    public void Inform() {
//        /* Get Docker info (only docker client information, not docker container information) */
//        Info info = dockerClient.infoCmd().exec();
//        System.out.print(info);
//    }
//}
//
//class dockerfile {
//    private String path, tag;
//    private File baseDir;
//    private DockerClient dockerClient;
//    private Image lastCreatedImage;
//
//    public dockerfile(DockerClient dockerClient, String path, String tag) {
//        this.dockerClient = dockerClient;
//        this.path = path;
//        this.tag = tag;
//    }
//
//    public void buildImages() {
//        /* Set variable to the File directory that has Dockerfile. */
//        baseDir = new File(path);
//
//        System.out.println("Dockerfile directory : " + baseDir.toString());
//
//        /* Check build progress callback function. */
//        BuildImageResultCallback callback = new BuildImageResultCallback() {
//            public void onNext(BuildResponseItem item) {
//                // System.out.println("" + item);
//                super.onNext(item);
//            }
//        };
//
//        /* Make a event to use Callback function. */
//        EventsResultCallback callback1 = new EventsResultCallback() {
//            public void onNext(Event event) {
//                super.onNext(event);
//            }
//        };
//
//        // docker build command. We can use callback to check build progress.
//        dockerClient.buildImageCmd(baseDir)
//                .withTag(tag)
//                .exec(callback).awaitImageId();
//    }
//
//    public Image buildImages(DockerClient dockerClient) {
//
//        // Check docker images. 'get(0)' means bring 1st images to the docker images command list.
//        Image lastCreatedImage = dockerClient.listImagesCmd().exec().get(0);
//
//        return lastCreatedImage;
//    }
//}
//
//class settingContainer {
//    private DockerClient dockerClient;
//    private dockerfile df;
//    private Map<String, String> map;
//    private int i;
//
//    public settingContainer(DockerClient dockerClient, dockerfile df, Map arr, int number) {
//        this.dockerClient = dockerClient;
//        this.df = df;
//        this.map = arr;
//        this.i = number;
//    }
//
//    public Map<String, String> setContainer(int count, String testClient, String IPaddress, String PortNUM, String CPU, int Memory) {
//        String containerName;
//        String containerID;
//        map = new HashMap<String, String>();
//
//        for (int j=i;j<i+count;j++) {
//
//            containerName= "xxx_" + j;  // 이름 중복의 가능성?
//
//            System.out.println(containerName);
//
//            containerID= dockerClient.createContainerCmd(df.buildImages(dockerClient).getId())
//                    .withCmd(testClient, IPaddress, PortNUM)
//                    .withName(containerName)
//                    .withMemory((long)Memory)
//                    .withCpusetCpus(CPU)
//                    .exec().getId();
//
//            map.put(containerName, containerID);
//        }
//
//        return map;
//    }
//
//    public String getContainerId(String name) {
//
//        if(map.containsKey(name)) {
//            return map.get(name);
//        } else {
//            return "Not Exist";
//        }
//    }
//}
//
//class startContainer {
//    private DockerClient dockerClient;
//    private Map<String, String> map;
//
//    public startContainer(DockerClient dockerClient, Map MAP) {
//        this.map = MAP;
//        this.dockerClient = dockerClient;
//    }
//
//    // 한 개의 컨테이너만 만들 경우
//    public void startSinglecontainer(String name) {
//
//        if(map.containsKey(name)) {
//            dockerClient.startContainerCmd(map.get(name)).exec();
//        } else {
//            System.out.println("Not starting, check the container name...");
//        }
//    }
//
//    // 다수의 컨테이너 만들 경우
//    public void startMulticontainer() {
//
//        Set<Map.Entry<String, String>> set = map.entrySet();
//        Iterator<Map.Entry<String, String>> it = set.iterator();
//
//        while(it.hasNext()) {
//            Map.Entry<String, String> e = it.next();
//            dockerClient.startContainerCmd(e.getValue()).exec();
//        }
//    }
//}
//
//class killContainer {
//    private int count;
//    private DockerClient dockerClient;
//    private Map<String, String> map;
//
//    public killContainer(DockerClient dockerClient, Map MAP) {
//        this.dockerClient = dockerClient;
//        this.map = MAP;
//    }
//
//    public void killing() {
//        Set<Map.Entry<String, String>> set = map.entrySet();
//        Iterator<Map.Entry<String, String>> it = set.iterator();
//
//        try {
//            Thread.sleep((map.size()) * 5000);
//            // Stop a container.
//            // dockerClient.stopContainerCmd(containerID[i]).exec();
//
//            // Wait Containers to stop and remove
////                WaitContainerResultCallback waitContainerResultCallback = dockerClient.waitContainerCmd(containerID[i])
////                        .exec(new WaitContainerResultCallback());
//
//            while(it.hasNext()) {
//                Map.Entry<String, String> e = it.next();
//                dockerClient.killContainerCmd(e.getValue()).exec();
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void killing(String name) {
//        try {
//            Thread.sleep((map.size()) * 5000);
//
//            if(map.containsKey(name)) {
//                dockerClient.killContainerCmd(map.get(name)).exec();
//            } else {
//                System.out.println("Not Killing, check the container name...");
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}
//
//class removeContainer {
//    private DockerClient dockerClient;
//    private Map<String, String> map;
//
//    public removeContainer(DockerClient dockerClient, Map MAP) {
//        this.dockerClient = dockerClient;
//        this.map = MAP;
//    }
//
//    public void removing() {
//        Set<Map.Entry<String, String>> set = map.entrySet();
//        Iterator<Map.Entry<String, String>> it = set.iterator();
//
//        try {
//            Thread.sleep((map.size()) * 5000);
//
//            while(it.hasNext()) {
//                Map.Entry<String, String> e = it.next();
//                dockerClient.removeContainerCmd(e.getValue()).exec();
//                System.out.println("Remove complete " + e.getKey());
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void removing(String name) {
//        try {
//            Thread.sleep((map.size()) * 5000);
//
//            if(map.containsKey(name)) {
//                dockerClient.removeContainerCmd(map.get(name)).exec();
//                System.out.println("complete " + map.get(name));
//            } else {
//                System.out.println("Not removing, check the container name...");
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}

// 클래스화 완료....
//public class hello {
//    // The number of containers will be made.
//    public static int CONTAINER_COUNT=5;
//
//    public static void main(String[] args) {
//        Map<String, String> map = new HashMap<String, String>();
//        Scanner scan = new Scanner(System.in);
//        int i = 0;
//
//        try {  // TimeUnit.
//
//            // Initialize docker client to use Docker REST API
//            // Need to other setting to use Mac
//            DockerCmdExecFactory dockerCmdExecFactory = new JerseyDockerCmdExecFactory()
//                    .withReadTimeout(1000)
//                    .withConnectTimeout(1000)
//                    .withMaxTotalConnections(100)
//                    .withMaxPerRouteConnections(10);
//
//            // in Mac
////            DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
////                    .withDockerHost("tcp://localhost:2375")
////                    .withDockerTlsVerify(true)
////                    .withDockerCertPath("/Users/Axlis/.docker")
////                    .withRegistryUsername("Axlis")
////                    .withRegistryPassword("CjfdnjS")
////                    .withRegistryEmail("cwkim@smartx.kr")
////                    .withRegistryUrl(null)
////                    .build();
//
//            /* Make a docker client.*/
//            // 1. In Linux
//            DockerClient dockerClient = DockerClientBuilder.getInstance("tcp://localhost:2375")
//                    // .withDockerCmdExecFactory(dockerCmdExecFactory)
//                    .build();
//
////            // 2. In Mac
////            DockerClient dockerClient = DockerClientBuilder.getInstance(config)
////                    .withDockerCmdExecFactory(dockerCmdExecFactory)
////                    .build();
//
//            /* Take Docker Client Information */
//            Information information = new Information(dockerClient);
//            information.Inform();
//
//
//            /* Select docker images and build */
//            dockerfile df = new dockerfile(dockerClient, "/home/zester/client", "socket");
//            df.buildImages();
//
//            settingContainer setcont = new settingContainer(dockerClient, df, map, i);
//            map = setcont.setContainer(CONTAINER_COUNT, "testClient", "192.168.0.173", "12345", "2,3", 20000000);
//
//            /* Make a Action code before stop and remove container when the condition occur. */
//            /* // StatsCallbackTest
//                StatsCallback statsCallback = dockerClient.statsCmd(containerID[i]).exec(
//                        new StatsCallback(barrier, containerID[i]));
//                countDownLatch.await(3, TimeUnit.SECONDS);
//            // Boolean gotStats = statsCallback.gotStats();
//            // System.out.println(statsCallback.toString() + containerID[i]);
//            */
//
//            startContainer stc = new startContainer(dockerClient, map);
//            stc.startMulticontainer();
//
//            CountDownLatch countDownLatch = new CountDownLatch(CONTAINER_COUNT);
//            String name = "xxx_3";
//
//            if (map.get(name) != null) {
//                System.out.println(map.get(name) + "  start");
//            }
//
//            Thread.sleep(1000);
//            StatsCallback statsCallback = dockerClient.statsCmd(map.get(name))
//                    .exec(new StatsCallback(countDownLatch, name, map.size()));
//
//            System.out.println(map.get(name) + "fin????");
//
//            countDownLatch.await();
//            statsCallback.close();
//
//            System.out.println("fin");
//
////            killContainer kic = new killContainer(dockerClient, map);
////            kic.killing();
//
//            removeContainer rec = new removeContainer(dockerClient, map);
//            rec.removing();
//
//        } catch(InterruptedException e) {
//            // TimeUnit
//            e.printStackTrace();
//        } catch (IOException e) {
//            // statsCallback.close();
//            e.printStackTrace();
//        }
//    }
//}
//
//class Information {
//    private DockerClient dockerClient;
//    private String container = null;
//
//    public Information(DockerClient dockerClient) {
//        this.dockerClient = dockerClient;
//    }
//
//    public Information(DockerClient dockerClient, String container) {
//        this.container = container;
//    }
//
//    public void Inform() {
//        /* Get Docker info (only docker client information, not docker container information) */
//        Info info = dockerClient.infoCmd().exec();
//        System.out.print(info);
//    }
//}
//
//class dockerfile {
//    private String path, tag;
//    private File baseDir;
//    private DockerClient dockerClient;
//    private Image lastCreatedImage;
//
//    public dockerfile(DockerClient dockerClient, String path, String tag) {
//        this.dockerClient = dockerClient;
//        this.path = path;
//        this.tag = tag;
//    }
//
//    public void buildImages() {
//        /* Set variable to the File directory that has Dockerfile. */
//        baseDir = new File(path);
//
//        System.out.println("Dockerfile directory : " + baseDir.toString());
//
//        /* Check build progress callback function. */
//        BuildImageResultCallback callback = new BuildImageResultCallback() {
//            public void onNext(BuildResponseItem item) {
//                // System.out.println("" + item);
//                super.onNext(item);
//            }
//        };
//
//        /* Make a event to use Callback function. */
//        EventsResultCallback callback1 = new EventsResultCallback() {
//            public void onNext(Event event) {
//                super.onNext(event);
//            }
//        };
//
//        // docker build command. We can use callback to check build progress.
//        dockerClient.buildImageCmd(baseDir)
//                .withTag(tag)
//                .exec(callback).awaitImageId();
//    }
//
//    public Image buildImages(DockerClient dockerClient) {
//
//        // Check docker images. 'get(0)' means bring 1st images to the docker images command list.
//        Image lastCreatedImage = dockerClient.listImagesCmd().exec().get(0);
//
//        return lastCreatedImage;
//    }
//}
//
//class settingContainer {
//    private DockerClient dockerClient;
//    private dockerfile df;
//    private Map<String, String> map;
//    private int i;
//
//    public settingContainer(DockerClient dockerClient, dockerfile df, Map arr, int number) {
//        this.dockerClient = dockerClient;
//        this.df = df;
//        this.map = arr;
//        this.i = number;
//    }
//
//    public Map<String, String> setContainer(int count, String testClient, String IPaddress, String PortNUM, String CPU, int Memory) {
//        String containerName;
//        String containerID;
//        map = new HashMap<String, String>();
//
//        for (int j=i;j<i+count;j++) {
//
//            containerName= "xxx_" + j;
//
//            containerID= dockerClient.createContainerCmd(df.buildImages(dockerClient).getId())
//                    .withCmd(testClient, IPaddress, PortNUM)
//                    .withName(containerName)
//                    .withMemory((long)Memory)
//                    //.withCpusetCpus(CPU)
//                    .exec().getId();
//
//            map.put(containerName, containerID);
//        }
//
//        return map;
//    }
//
//    public String getContainerId(String name) {
//
//        if(map.containsKey(name)) {
//            return map.get(name);
//        } else {
//            return "Not Exist";
//        }
//    }
//}
//
//class startContainer {
//    private DockerClient dockerClient;
//    private Map<String, String> map;
//
//    public startContainer(DockerClient dockerClient, Map MAP) {
//        this.map = MAP;
//        this.dockerClient = dockerClient;
//    }
//
//    // just To make one container
//    public void startSinglecontainer(String name) {
//
//        if(map.containsKey(name)) {
//            dockerClient.startContainerCmd(map.get(name)).exec();
//        } else {
//            System.out.println("Not starting, check the container name...");
//        }
//    }
//
//    // To make multiple containers
//    public void startMulticontainer() {
//
//        Set<Map.Entry<String, String>> set = map.entrySet();
//        Iterator<Map.Entry<String, String>> it = set.iterator();
//
//        while(it.hasNext()) {
//            Map.Entry<String, String> e = it.next();
//            dockerClient.startContainerCmd(e.getValue()).exec();
//        }
//    }
//}
//
//class killContainer {
//    private int count;
//    private DockerClient dockerClient;
//    private Map<String, String> map;
//
//    public killContainer(DockerClient dockerClient, Map MAP) {
//        this.dockerClient = dockerClient;
//        this.map = MAP;
//    }
//
//    public void killing() {
//        Set<Map.Entry<String, String>> set = map.entrySet();
//        Iterator<Map.Entry<String, String>> it = set.iterator();
//
//        try {
//            Thread.sleep((map.size()) * 5000);
//            // Stop a container.
//            // dockerClient.stopContainerCmd(containerID[i]).exec();
//
//            // Wait Containers to stop and remove
////                WaitContainerResultCallback waitContainerResultCallback = dockerClient.waitContainerCmd(containerID[i])
////                        .exec(new WaitContainerResultCallback());
//
//            while(it.hasNext()) {
//                Map.Entry<String, String> e = it.next();
//                dockerClient.killContainerCmd(e.getValue()).exec();
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void killing(String name) {
//        try {
//            Thread.sleep((map.size()) * 5000);
//
//            if(map.containsKey(name)) {
//                dockerClient.killContainerCmd(map.get(name)).exec();
//            } else {
//                System.out.println("Not Killing, check the container name...");
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}
//
//class removeContainer {
//    private DockerClient dockerClient;
//    private Map<String, String> map;
//
//    public removeContainer(DockerClient dockerClient, Map MAP) {
//        this.dockerClient = dockerClient;
//        this.map = MAP;
//    }
//
//    public void removing() {
//        Set<Map.Entry<String, String>> set = map.entrySet();
//        Iterator<Map.Entry<String, String>> it = set.iterator();
//
//        try {
//            Thread.sleep((map.size()) * 5000);
//
//            while(it.hasNext()) {
//                Map.Entry<String, String> e = it.next();
//                dockerClient.removeContainerCmd(e.getValue()).exec();
//                System.out.println("Remove complete " + e.getKey());
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void removing(String name) {
//        try {
//            Thread.sleep((map.size()) * 5000);
//
//            if(map.containsKey(name)) {
//                dockerClient.removeContainerCmd(map.get(name)).exec();
//                System.out.println("complete " + map.get(name));
//            } else {
//                System.out.println("Not removing, check the container name...");
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}
//
//// class to watch the container stats
//class StatsCallback extends ResultCallbackTemplate<StatsCallback, Statistics> {
//    private final CountDownLatch countDownLatch;
//    private String contid;
//    private int count;
//
//    private Boolean gotStats = false;
//
//    // COUNT means map.size, not CONTAINERS_COUNT because of case of single container
//    public StatsCallback(CountDownLatch countDownLatch, String CONTID, int COUNT) {
//        this.countDownLatch = countDownLatch;
//        this.contid = CONTID;
//        this.count = COUNT;
//    }
//
//    public void onNext(Statistics stats) {
//        String string = stats.getCpuStats().toString();
//        String memory = stats.getMemoryStats().toString();
////            System.out.println("testCPU : " + string);
////            System.out.println("testMemory : " + memory);
//
////            if(stats != null) {
////                try {
////                    cyclicBarrier.await();
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                } catch (BrokenBarrierException e) {
////                    e.printStackTrace();
////                } finally {
////                    System.out.println(containerID + " test " + string);
////                    // gotStats = true;
////                }
////            }
//
//        if(stats != null) {
//            System.out.println(countDownLatch.getCount() + "th");
//            System.out.println(contid + "cpu : " + string);
//            System.out.println(contid + "memory : " + memory);
//        }
//        countDownLatch.countDown();
//
////            Set<Map.Entry<String, Object>> entries = stats.getCpuStats().entrySet();
////            for (Map.Entry<String, Object> entry : entries) {
////                System.out.println(entry.getKey() + " : " + entry.getValue());
////            }
//    }
//
//    public Boolean gotStats(){
//        return gotStats;
//    }
//}

public class demo5 {
    // The number of containers will be made.

    public static void main(String[] args) {
        Data data = new Data();
        Map<String, String> map = new HashMap<String, String>();
        Map<String, String> startcont_map = new HashMap<String, String>();
        Map<String, String> killcont_map = new HashMap<String, String>();
        dockerfile df = null;
        int cont_number= 0;

        long maxCPU = 0;
        int maxRAM = 0;
        Scanner scan = new Scanner(System.in);

        // Initialize docker client to use Docker REST API
        DockerCmdExecFactory dockerCmdExecFactory = new JerseyDockerCmdExecFactory()
                .withReadTimeout(1000)
                .withConnectTimeout(1000)
                .withMaxTotalConnections(100)
                .withMaxPerRouteConnections(10);

//            /* Need to update in Mac */
//            DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
//                    .withDockerHost("tcp://localhost:2375")
//                    .withDockerTlsVerify(true)
//                    .withDockerCertPath("/Users/Axlis/.docker")
//                    .withRegistryUsername("Axlis")
//                    .withRegistryPassword("CjfdnjS")
//                    .withRegistryEmail("cwkim@smartx.kr")
//                    .withRegistryUrl(null)
//                    .build();

            /* Make a docker client.*/
        // 1. In Linux
        DockerClient dockerClient = DockerClientBuilder.getInstance("tcp://localhost:2376")
                // .withDockerCmdExecFactory(dockerCmdExecFactory)
                .build();

//            // 2. In Mac
//            DockerClient dockerClient = DockerClientBuilder.getInstance(config)
//                    .withDockerCmdExecFactory(dockerCmdExecFactory)
//                    .build();

        while(true) {
            String sss = null;

            System.out.println("0. = Setting");
            System.out.println("1. = Show the info of docker client");
            System.out.println("2. = Bulid the docker image that is selected");
            System.out.println("3. = Create the docker container");
            System.out.println("4. = Start the docker container");
            System.out.println("5. = Stats specific docker container");
            System.out.println("6. = Kill the docker container");
            System.out.println("7. = Remove the docker container");
            System.out.println("44. = Program exit");

            System.out.print("Press the number: ");

            sss = scan.nextLine();
            sss = sss.trim();

            int tp = Integer.parseInt(sss);

            if (tp == 0) {
                data.getData();
                continue;
            }
            if (tp == 1) {
                /* Take Docker Client Information */
                Information information = new Information(dockerClient);
                information.Inform();
            } else if (tp == 2) {
                Scanner scan2 = new Scanner(System.in);
                // To classify openjdk6,7,8. It can't be classify only Dockerfile.
                System.out.println("Input the Path that has a Dockerfile");
                String path = scan2.nextLine();

                System.out.println("Input the Dockerfile tag");
                String tag = scan2.nextLine();

                /* Select docker images and build */
                df = new dockerfile(dockerClient, path, tag);
                df.buildImages();
            } else if (tp == 3) {
                if (df == null) {
                    System.out.println("Docker images build first!!");
                    System.out.println("return to intro");
                    continue;
                }

                // make a container based on user's input
                settingContainer setcont = new settingContainer(dockerClient, df, map, cont_number);
                map = setcont.setContainer(data.getCount(), data.getCommand(), data.getIP(), data.getPort(), data.getCPUs(), data.getMemory());
                cont_number = setcont.setContainerNumber();

            } else if (tp == 4) {
                if (map.isEmpty() && startcont_map.isEmpty() && killcont_map.isEmpty()) {
                    System.out.println("Setting the docker container first!!");
                    System.out.println("return to intro");
                    continue;
                }

                // After to run a stopped container
                map.putAll(killcont_map);

                Scanner scan4 = new Scanner(System.in);
                System.out.println("Input to start the container name: ");
                System.out.print("Container Name is xxx_number. ex) xxx_5 : ");
                String name = scan4.next();

                startContainer stc = new startContainer(dockerClient, map, startcont_map);

                if(name.equals("all")) {
                    startcont_map = stc.startMulticontainer();
                    map.clear();
                    killcont_map.clear();
                } else if (map.containsKey(name)) {
                    startcont_map = stc.startSinglecontainer(name);
                    map.remove(name);
                    killcont_map.remove(name);
                }

//                // Prevent ConcurrentException
//                if(name.equals("all")) {
//                    map.clear();
//                    killcont_map.clear();
//                } else if (map.containsKey(name)) {
//                    map.clear();
//                    killcont_map.remove(name);
//                }

            } else if (tp == 5) {
                CountDownLatch countDownLatch = new CountDownLatch(data.getCount());

                Scanner scan5 = new Scanner(System.in);
                System.out.println("Input the docker container Name to see the stats");
                System.out.print("Container Name is xxx_number. ex) xxx_5 : ");
                String name = scan5.nextLine();

                System.out.println(name);
                System.out.println(startcont_map.size());

                if (startcont_map.get(name) != null) {
                    System.out.println(name + "  start");
                } else {
                    System.out.println("Not started Container.. Return to Intro");
                    continue;
                }

//                try { // To synchronized with started container, but it doesn't need.
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

                StatsCallback statsCallback = dockerClient.statsCmd(startcont_map.get(name))
                        .exec(new StatsCallback(countDownLatch, name, startcont_map.size(), data.getMemory()));

                try {
                    // start next line, when go by map.size() * 3000 MILLISECONDS.
                    countDownLatch.await(startcont_map.size() * 3000, TimeUnit.MILLISECONDS);
                    // prevent infinite loop
                    statsCallback.close();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("Stating container fin");

            } else if (tp == 6) {
                Scanner scan6 = new Scanner(System.in);
                System.out.println("Input the docker container name to finish");
                System.out.println("If you want just one container, input the container name like 'xxx_5'. ");
                System.out.println("If you want several container, input just numbers like '1,3,6,7'. ");
                System.out.println("If you want all container, input the string 'all'. ");
                String name = scan6.nextLine();

                if(name.equals("all")) {
                    killContainer kic = new killContainer(dockerClient, startcont_map, killcont_map);
                    killcont_map = kic.killing();
                    startcont_map.clear();
                } else if(name.contains(",")) {
                    String[] targets;
                    targets = name.split(",");
                    killContainer kic = new killContainer(dockerClient, startcont_map, killcont_map);

                    for(int j=0;j<targets.length;j++) {
                        String temp = "xxx_" + Integer.parseInt(targets[j]);
                        killcont_map = kic.killing(temp);
                        startcont_map.remove(temp);
                    }

                } else if (startcont_map.containsKey(name)) {
                    killContainer kic = new killContainer(dockerClient, startcont_map, killcont_map);
                    killcont_map = kic.killing(name);
                    startcont_map.remove(name);
                }

//                // Prevent ConcurrentException
//                if(name.equals("all")) {
//                    startcont_map.clear();
//                } else if(name.contains(",")) {
//                    String[] targets;
//                    targets = name.split(",");
//
//                    for(int j=0;j<targets.length;j++) {
//                        String temp = "xxx_" + Integer.parseInt(targets[j]);
//                        startcont_map.remove(temp);
//                    }
//
//                } else if (startcont_map.containsKey(name)) {
//                    startcont_map.remove(name);
//                }

            } else if (tp == 7) {
                Scanner scan7 = new Scanner(System.in);
                System.out.println("deleting the docker container was made");
                System.out.println("If you want just one container, input the container name like 'xxx_5'. ");
                System.out.println("If you want several container, input just numbers like '1,3,6,7'. ");
                System.out.println("If you want all container, input the string 'all'. ");
                String name = scan7.nextLine();

                // Removing container, startcont_map setting
                if (name.equals("all")) {
                    removeContainer rec = new removeContainer(dockerClient, killcont_map);
                    killcont_map = rec.removing();
                    killcont_map.clear();
                } else if(name.contains(",")) {
                    String[] targets;
                    targets = name.split(",");

                    removeContainer rec = new removeContainer(dockerClient, killcont_map);

                    for(int j=0;j<targets.length;j++) {
                        String temp = "xxx_" + Integer.parseInt(targets[j]);
                        killcont_map = rec.removing(temp);
                    }
                } else if(killcont_map.containsKey(name)) {
                    removeContainer rec = new removeContainer(dockerClient, killcont_map);
                    killcont_map = rec.removing(name);
                }

            } else if (tp == 44){
                System.out.println("ByeBye");
                break;
            } else {
                System.out.println("Input 1~7 or 44");
                System.out.println("Return to first!!");
            }
        }

        System.out.println("End");
    }
}

class Data {
    private String Count;
    private String CMD;
    private String IP;
    private String Port;
    private String setCpus;
    private String Memory;

//    public Data(String CMD, String IP, String Port, String setCpus, String Memory) {
//        this.CMD = CMD;
//        this.IP = IP;
//        this.Port = Port;
//        this.setCpus = setCpus;
//        this.Memory = Memory;
//    }

    public void getData() {

        System.out.println("Input the number how many to start container : ");
        Scanner scan = new Scanner(System.in);
        this.Count = scan.nextLine();

        System.out.println("Input the command to start in container : ");
        this.CMD = scan.nextLine();

        System.out.println("Input the IP address to connect host machine in container : ");
        this.IP = scan.nextLine();

        System.out.println("Input the Ports number to connect host machine in container : ");
        this.Port = scan.nextLine();

        System.out.println("Input the CPUs to allocate for container(ex 2,3) : ");
        this.setCpus = scan.nextLine();

        System.out.println("Input the Memory to allocate for container(ex 200000, 2kb, 2MB..) : ");
        this.Memory = scan.nextLine();
    }

    public int getCount() {
        int count = Integer.parseInt(Count);

        return count;
    }

    public String getCommand() {
        return CMD;
    }

    public String getIP() {
        return IP;
    }

    public String getPort() {
        return Port;
    }

    public String getCPUs() {
        return setCpus;
    }

    public Long getMemory() {
        long temp;

        // To delete other char except number for parsing
        temp = Long.parseLong(Memory.replaceAll("[^0-9]", ""));

        if(Memory.contains("K") || Memory.contains("k")) {
            temp *= 1000;
        } else if(Memory.contains("M") || Memory.contains("m")) {
            temp *= 1000000;
        } else if(Memory.contains("G") || Memory.contains("g")) {
            temp *= 1000000000;
        }

        return temp;
    }
}

class Information {
    private DockerClient dockerClient;

    public Information(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    public void Inform() {
        /* Get Docker info (only docker client information, not docker container information) */
        Info info = dockerClient.infoCmd().exec();
        System.out.println(info);
    }
}

class dockerfile {
    private String path, tag;
    private File baseDir;
    private DockerClient dockerClient;

    public dockerfile(DockerClient dockerClient, String path, String tag) {
        this.dockerClient = dockerClient;
        this.path = path;
        this.tag = tag;
    }

    public void buildImages() {
        /* Set variable to the File directory that has Dockerfile. */
        baseDir = new File(path);

        System.out.println("Dockerfile directory : " + baseDir.toString());

        /* Check build progress callback function. */
        BuildImageResultCallback callback = new BuildImageResultCallback() {
            public void onNext(BuildResponseItem item) {
                // System.out.println("" + item);
                super.onNext(item);
            }
        };

        /* Make a event to use Callback function. */
        EventsResultCallback callback1 = new EventsResultCallback() {
            public void onNext(Event event) {
                super.onNext(event);
            }
        };

        // docker build command. We can use callback to check build progress.
        dockerClient.buildImageCmd(baseDir)
                .withTag(tag)
                .exec(callback).awaitImageId();
    }

    public Image buildImages(DockerClient dockerClient) {

        // Check docker images. 'get(0)' means bring 1st images to the docker images command list.
        Image lastCreatedImage = dockerClient.listImagesCmd().exec().get(0);

        return lastCreatedImage;
    }
}

class settingContainer {
    private DockerClient dockerClient;
    private dockerfile df;
    private Map<String, String> map;
    private int i;

    public settingContainer(DockerClient dockerClient, dockerfile df, Map<String, String> arr, int number) {
        this.dockerClient = dockerClient;
        this.df = df;
        this.map = arr;
        this.i = number;
    }

    public void adjustContainer() {

    }

    public Map<String, String> setContainer(int count, String command, String IPaddress, String Port, String CPU, long Memory) {
        String containerName;
        String containerID;
        int j;

        for (j=i;j<i+count;j++) {

            containerName= "xxx_" + j;

            containerID= dockerClient.createContainerCmd(df.buildImages(dockerClient).getId())
                    .withCmd(command, IPaddress, Port)
                    .withName(containerName)
                    .withMemory(Memory)
                    .withCpusetCpus(CPU)
                    .exec().getId();

            map.put(containerName, containerID);
        }

        i=j;

        return map;
    }

    public int setContainerNumber() {
        return i;
    }

    public String getContainerId(String name) {

        if(map.containsKey(name)) {
            return map.get(name);
        } else {
            return "Not Exist";
        }
    }
}

class startContainer {
    private DockerClient dockerClient;
    private Map<String, String> map;
    private Map<String, String> startcont_map;

    public startContainer(DockerClient dockerClient, Map<String, String> MAP, Map<String, String> MAP1) {
        this.map = MAP;
        this.dockerClient = dockerClient;
        this.startcont_map = MAP1;
    }

    // when you make a single container
    public Map<String, String> startSinglecontainer(String name) {
        if(map.containsKey(name) && !(startcont_map.containsKey(name))) {
            dockerClient.startContainerCmd(map.get(name)).exec();
            startcont_map.put(name, map.get(name));
        } else {
            System.out.println("Not starting, check the container name...");
        }

        return startcont_map;
    }

    // When you make a multi container
    public Map<String, String> startMulticontainer() {

        Set<Map.Entry<String, String>> set = map.entrySet();
        Iterator<Map.Entry<String, String>> it = set.iterator();

        while(it.hasNext()) {
            Map.Entry<String, String> e = it.next();

            if(startcont_map.containsKey(e.getKey())) {
                continue;
            }
            dockerClient.startContainerCmd(e.getValue()).exec();
            startcont_map.put(e.getKey(), e.getValue());
        }

        return startcont_map;
    }

//    public Map<String, String> startMulticontainer_test(int number, int number2) {
//        for(int j=number-number2;j<number;j++) {
//            String temp = "xxx_" + j;
//
//            if(map.containsKey(temp)) {
//                dockerClient.startContainerCmd(map.get(temp)).exec();
//                startcont_map.put(temp, map.get(temp));
//                map.remove(temp);
//            } else {
//                continue;
//            }
//        }
//
//        return startcont_map;
//    }
}

class killContainer {
    private int count;
    private DockerClient dockerClient;
    // Using mains startcont_map. Not mains map!!!!
    private Map<String, String> map;
    private Map<String, String> killcont_map;

    public killContainer(DockerClient dockerClient, Map<String, String> MAP, Map<String, String> MAP1) {
        this.dockerClient = dockerClient;
        this.map = MAP;
        this.killcont_map = MAP1;
    }

    public Map<String, String> killing() {

        Set<Map.Entry<String, String>> set = map.entrySet();
        Iterator<Map.Entry<String, String>> it = set.iterator();

        try {
            //Thread.sleep((map.size()) * 5000);
            Thread.sleep(1000);
            // Stop a container.
            // dockerClient.stopContainerCmd(containerID[i]).exec();

            // Wait Containers to stop and remove
//                WaitContainerResultCallback waitContainerResultCallback = dockerClient.waitContainerCmd(containerID[i])
//                        .exec(new WaitContainerResultCallback());

            while(it.hasNext()) {
                Map.Entry<String, String> e = it.next();

                // Do not overlap map(startcont_map) and killcont_map
                if(killcont_map.containsKey(e.getKey())) {
                    continue;
                }
                dockerClient.killContainerCmd(e.getValue()).exec();
                killcont_map.put(e.getKey(), e.getValue());
            }

            System.out.println("kill the started container complete");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return killcont_map;
    }

    public Map<String, String> killing(String name) {
        try {
            //Thread.sleep((map.size()) * 5000);
            Thread.sleep(1000);

            if(map.containsKey(name) && !(killcont_map.containsKey(name))) {
                dockerClient.killContainerCmd(map.get(name)).exec();
                killcont_map.put(name, map.get(name));
                System.out.println("kill the container complete");
            } else {
                System.out.println("Not Killing, check the container name...");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return killcont_map;
    }
}

class removeContainer {
    private DockerClient dockerClient;
    // Using mains startcont_map. Not mains map!!!!
    private Map<String, String> map;

    public removeContainer(DockerClient dockerClient, Map<String, String> MAP) {
        this.dockerClient = dockerClient;
        this.map = MAP;
    }

    public Map<String, String> removing() {
        Set<Map.Entry<String, String>> set = map.entrySet();
        Iterator<Map.Entry<String, String>> it = set.iterator();

        try {
            Thread.sleep((map.size()) * 500);

            while(it.hasNext()) {
                Map.Entry<String, String> e = it.next();

                dockerClient.removeContainerCmd(e.getValue()).exec();
                System.out.println("Remove complete " + e.getKey());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
        return map;
    }

    public Map<String, String> removing(String name) {
        try {
            Thread.sleep((map.size() * 500));

            if(map.containsKey(name)) {
                dockerClient.removeContainerCmd(map.get(name)).exec();
                map.remove(name);
                System.out.println("Remove complete " + name);
            } else {
                System.out.println("Not removing, check the container name...");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return map;
    }
}

class StatsCallback extends ResultCallbackTemplate<StatsCallback, Statistics> {
    private final CountDownLatch countDownLatch;
    private String contid;
    private int count;
    private String beCPU_total = "0";
    private String beCPU_system = "0";
    private String beMemory_usage = "0";
    private long Memory_total = 0;

    private Boolean gotStats = false;

    // COUNT means map.size, not CONTAINERS_COUNT because of case of single container
    public StatsCallback(CountDownLatch countDownLatch, String CONTID, int COUNT, long Memory) {
        this.countDownLatch = countDownLatch;
        this.contid = CONTID;
        this.count = COUNT;
        this.Memory_total = Memory;
    }

    public void onNext(Statistics stats) {

//        Set<Map.Entry<String, Object>> entries = stats.getCpuStats().entrySet();
//        for (Map.Entry<String, Object> entry : entries) {
//            System.out.println(entry.getKey() + " : " + entry.getValue());
//        }
//
//        Set<Map.Entry<String, Object>> Mentries = stats.getMemoryStats().entrySet();
//        for (Map.Entry<String, Object> entry : Mentries) {
//            System.out.println(entry.getKey() + " : " + entry.getValue());
//        }


//        while(it.hasNext()) {
//            Map.Entry<String, String> e = it.next();
//            dockerClient.startContainerCmd(e.getValue()).exec();
//        }

        String afCPU_total = stats.getCpuStats().get("cpu_usage").toString();
        String afCPU_system = stats.getCpuStats().get("system_cpu_usage").toString();
        String afMemory_usage = stats.getMemoryStats().get("usage").toString();

        int in = afCPU_total.indexOf('=');
        int in2 = afCPU_total.indexOf(',');
        int countofPer = afCPU_total.indexOf('[');
        int countofPer2 = afCPU_total.indexOf(']');

        String afCPU_middle = afCPU_total.substring(countofPer+1, countofPer2);
        String afCPU_value = afCPU_total.substring(in+1, in2);

        String[] array = afCPU_middle.split(",");
        double cpuNumber = array.length;

        long cpuDelta = Long.parseLong(afCPU_value) - Long.parseLong(beCPU_total);
        long systemDelta = Long.parseLong(afCPU_system) - Long.parseLong(beCPU_system);
        int memoryDelta = Integer.parseInt(afMemory_usage) - Integer.parseInt(beMemory_usage);

        double cpuPercent = ((double) cpuDelta / (double) systemDelta) * cpuNumber * 100.0;
        double memoryPercent = (((double) memoryDelta / (double) Memory_total) * 100.0);

        if (stats != null) {
            if(memoryPercent < 0.0) {
                memoryPercent = 0.0;
            }

            System.out.println(contid + " cpu : " + cpuPercent + "%");
            System.out.println(contid + " memory : " + memoryPercent + "%");
        }

        beCPU_total = afCPU_value;
        beCPU_system = afCPU_system;
        beMemory_usage = afMemory_usage;

        countDownLatch.countDown();
    }

    public Boolean gotStats(){
        return gotStats;
    }
}