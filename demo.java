import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.DockerCmdExecFactory;
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
import java.util.Iterator;
import java.util.List;
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













    //// 클래스화
public class demo {
    // The number of containers will be made.
    public static int CONTAINER_COUNT=5;

    public static void main(String[] args) {
        // 이것도 조금만 더 생각을 해보자....
        // 근데 아마도 넣게 된다면.... 'CountDownLatch'가 들어갈 것으로 예상
        CyclicBarrier barrier = new CyclicBarrier(CONTAINER_COUNT);

        try {  // TimeUnit.
            TimeUnit.SECONDS.sleep(1);

            /* 컨테이너 이름 생성 부분. 임의의 숫자를 만들고, 이를 컨테이너 이름으로 사용하기 위함.
            for(int i=0;i<CONTAINER_COUNT;i++) {
                String[] containerName2 = null;
                containerName2[i] = "generated_" + new SecureRandom().nextInt();
            }
            */

            // Initialize docker client to use Docker REST API
            // 맥 버전에서는 조금 더 추가되는 부분이 있음.
            DockerCmdExecFactory dockerCmdExecFactory = new JerseyDockerCmdExecFactory()
                    .withReadTimeout(1000)
                    .withConnectTimeout(1000)
                    .withMaxTotalConnections(100)
                    .withMaxPerRouteConnections(10);

            /* Make a docker client.*/
            DockerClient dockerClient = DockerClientBuilder.getInstance("tcp://localhost:2376")
                    // .withDockerCmdExecFactory(dockerCmdExecFactory)
                    .build();

            /* Get Docker info (only docker client information, not docker container information) */
            Info info = dockerClient.infoCmd().exec();
            // System.out.print(info);

            /* Set variable to the File directory that has Dockerfile. */
            File baseDir = new File("/home/chorwon/example");

            // Check File directory result.
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
                    .withTag("socket")
                    .exec(callback).awaitImageId();

            // Check docker images. 'get(0)' means bring 1st images to the docker images command list.
            Image lastCreatedImage = dockerClient.listImagesCmd().exec().get(0);

            /* Setting ports  -> need to test
            ExposedPort tcp22 = ExposedPort.tcp(22);
            Ports portBindings = new Ports();
            portBindings.bind(tcp22, Ports.Binding.bindPort(12345));
            */

            String[] containerName = new String[CONTAINER_COUNT];
            String[] containerID = new String[CONTAINER_COUNT];

            /* CONTAINER_COUNT 만큼 컨테이너 생성하는 반복문 */
            for (int i=0;i<CONTAINER_COUNT;i++) {
                containerName[i] = "tt_" + i;

                containerID[i] = dockerClient.createContainerCmd(lastCreatedImage.getId())
                        .withCmd("testClient", "192.168.0.66", "12345")
                        .withName(containerName[i])
                        .withMemory((long)200000000)
                        .withCpusetCpus("2,3")
                        .exec().getId();


//                당연한 이야기겠지만, CreateContainerResponse id[]로 할 경우에는 이 부분은 CreateContainerResponse 형의 변수 id를 선언하는 것.
//                이렇게 할 경우, 위의 String 선언과 변수가 겹치게 된다.
//                CreateContainerResponse id = dockerClient
//                        .createContainerCmd(lastCreatedImage.getId())
//                        .withCmd("testClient", "192.168.0.66", "12345")
//                        .withName(containerName[i])
//                        .withMemory((long)200000000)
//                        .withCpusetCpus("2,3")
//                        .exec();

                // dockerClient.startContainerCmd(containerID[i]).exec();
                startContainer st = new startContainer(dockerClient, containerID[i], CONTAINER_COUNT);
                st.startcont();
            }

            /* Make a Action code before stop and remove container when the condition occur. */
            /* // StatsCallbackTest
                StatsCallback statsCallback = dockerClient.statsCmd(containerID[i]).exec(
                        new StatsCallback(barrier, containerID[i]));

                countDownLatch.await(3, TimeUnit.SECONDS);
            // Boolean gotStats = statsCallback.gotStats();

            // System.out.println(statsCallback.toString() + containerID[i]);
            */

//            // 그냥 이 부분을 while 로 해서 특정 컨테이너에 대해서만 확인하는 걸로 바꾸는 게 나을 것 같다....
//            for (int i=0;i<CONTAINER_COUNT;i++) {
//                dockerClient.statsCmd(containerID[i]).exec(new StatsCallback(barrier, containerID[i]));
//            }

            /* Stop and remove container */
            for (int i=0;i<CONTAINER_COUNT;i++) {
                // Stop a container.
                // dockerClient.stopContainerCmd(containerID[i]).exec();

                // Wait Containers to stop and remove
//                WaitContainerResultCallback waitContainerResultCallback = dockerClient.waitContainerCmd(containerID[i])
//                        .exec(new WaitContainerResultCallback());

                dockerClient.killContainerCmd(containerID[i]).exec();
            }

            for (int i=0;i<CONTAINER_COUNT;i++) {
                // Remove a container.
                dockerClient.removeContainerCmd(containerID[i]).exec();

                // Check to remove container
                System.out.println("complete " + containerID[i]);
            }

        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class StatsCallback extends ResultCallbackTemplate<StatsCallback, Statistics> {
        private CyclicBarrier cyclicBarrier;
        public static String containerID = null;

        private Boolean gotStats = false;

        public StatsCallback(CyclicBarrier cyclicBarrier, String containerId) {
            this.cyclicBarrier = cyclicBarrier;
            this.containerID = containerId;
        }

        public void onNext(Statistics stats) {
            String string = stats.getCpuStats().toString();
            String memory = stats.getMemoryStats().toString();
            int count = 0;
//            System.out.println("testCPU : " + string);
//            System.out.println("testMemory : " + memory);

//            if(stats != null) {
//                try {
//                    cyclicBarrier.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (BrokenBarrierException e) {
//                    e.printStackTrace();
//                } finally {
//                    System.out.println(containerID + " test " + string);
//                    // gotStats = true;
//                }
//            }

            if(stats != null) {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(containerID + " test " + string);
//                    // gotStats = true;
            }

//            Set<Map.Entry<String, Object>> entries = stats.getCpuStats().entrySet();
//            for (Map.Entry<String, Object> entry : entries) {
//                System.out.println(entry.getKey() + " : " + entry.getValue());
//            }
        }

        public Boolean gotStats(){
            return gotStats;
        }
    }
}

class startContainer {
    private DockerClient dockerClient;
    private String containerID;
    private int startcount;


    public startContainer(DockerClient dockerClient, String containerID, int COUNT) {
        this.startcount = COUNT;
        this.dockerClient = dockerClient;
        this.containerID = containerID;
    }

    public void startcont() {
        dockerClient.startContainerCmd(containerID).exec();
    }
}
