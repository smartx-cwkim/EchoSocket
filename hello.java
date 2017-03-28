import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.*;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.async.ResultCallbackTemplate;
import com.github.dockerjava.core.command.BuildImageResultCallback;
import com.github.dockerjava.core.command.EventsResultCallback;
import com.github.dockerjava.core.dockerfile.Dockerfile;
import com.github.dockerjava.jaxrs.JerseyDockerCmdExecFactory;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by chorwon on 17. 3. 14.
 */
//public class cwDockerJavaTest {
//    private static int NUM_STATS = 2;
//
//    public static void main(String[] args) {
//        /*
//        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
//                .withDockerHost("tcp://localhost:2376")
//                .withDockerTlsVerify(true)
//                .withDockerCertPath("home/chorwon/.docker/certs")  // 해당 부분에 오류가 있음......
//                .withDockerConfig("home/chorwon/.docker")
//                .withRegistryUsername("chorwon")
//                .withRegistryPassword("cjfndjs")
//                .withRegistryEmail("cwkim@smartx.kr")
//                .withRegistryUrl(null)
//                .build();
//                */
//
//        // DockerClient dockerClient = DefaultDockerClientConfig.getInstance("tcp://localhost:2375").build();
//
//        DockerCmdExecFactory dockerCmdExecFactory = new JerseyDockerCmdExecFactory()
//                .withReadTimeout(1000)
//                .withConnectTimeout(1000)
//                .withMaxTotalConnections(100)
//                .withMaxPerRouteConnections(10);
//
//
//
//        DockerClient dockerClient = DockerClientBuilder.getInstance("tcp://localhost:2376")
//                // .withDockerCmdExecFactory(dockerCmdExecFactory)
//                .build();
//
//        Info info = dockerClient.infoCmd().exec();
//        System.out.print(info);
//
//
//        CreateContainerResponse container = dockerClient.createContainerCmd("alpine:latest")
//                .withCmd("touch","/test")
//                .exec();
//
//        // stat 관련 작업 분석중.
//
//        try {
//            TimeUnit.SECONDS.sleep(1);
//
//            CountDownLatch countDownLatch = new CountDownLatch(NUM_STATS);
//            // 생성자 파라미터는 다른 스레드 몇 개가 끝날때까지 대기할 것인가의 숫자
//
//            dockerClient.startContainerCmd(container.getId()).exec();
//
//            // StatsCallbackTest statsCallback = dockerClient.statsCmd(container.getId()).exec(new StatsCallbackTest(countDownLatch));
//
//            StatsCallbackTest statsCallback = dockerClient.statsCmd(container.getId()).exec(new StatsCallbackTest(countDownLatch));
//            countDownLatch.await(3, TimeUnit.SECONDS);
//
//            System.out.println();
//
//            Boolean gotStats = statsCallback.gotStats();
//
//            System.out.println(gotStats);
//
//            statsCallback.close();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//
//        // dockerClient.statsCmd(container.getId()).withContainerId(container.getId()).exec(statsCallback);
//
//        // System.out.println(statsCallback.getStatisticsList());
//
//        dockerClient.stopContainerCmd(container.getId()).exec();
////        dockerClient.waitContainerCmd(container.getId()).exec(null);
//    }
//
//    private static class StatsCallbackTest extends ResultCallbackTemplate<StatsCallbackTest, Statistics> {
//        private final CountDownLatch countDownLatch;
//
//        private Boolean gotStats = false;
//
//        public StatsCallbackTest(CountDownLatch countDownLatch) {
//            this.countDownLatch = countDownLatch;
//        }
//
//        @Override
//        public void onNext(Statistics stats) {
//            System.out.println(countDownLatch.getCount());
//            System.out.println(stats);
//
//            if(stats != null) {
//                gotStats = true;
//            }
//
//            countDownLatch.countDown();
// countDown해서 생성자 파라미터의 값이 하나씩 마이너스가 돼서 0이 되면 대기상태가 풀림.
//        }
//
////        public List<Statistics> getStatisticsList() {
////            return statisticsList;
////        }
//
//        public Boolean gotStats() {
//            return gotStats;
//        }
//    }
//}


// 도커파일을 통한 컨테이너의 생성은 들어가지 않은 부분.


//public class cwDockerJavaTest{
//    private static int NUM_STATS = 5;
//
//    public static void main(String[] args) {
//        try {
//            TimeUnit.SECONDS.sleep(1);
//
//            CountDownLatch countDownLatch = new CountDownLatch(NUM_STATS);
//
//            String containerName = "generated_" + new SecureRandom().nextInt();
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
//            // Create new Docker container, not start.
//            CreateContainerResponse container = dockerClient.createContainerCmd("alpine:latest")
//                    .withCmd("top")
//                    .withName(containerName)
//                    .withCpusetCpus("2,3")
//                    .withMemory((long)(20000000))
//                    .exec();
//
//            // Start a container before created container
//            dockerClient.startContainerCmd(container.getId()).exec();
//
//            // StatsCallbackTest
//            StatsCallback statsCallback = dockerClient.statsCmd(container.getId()).exec(
//                    new StatsCallback(countDownLatch));
//
//            countDownLatch.await(3, TimeUnit.SECONDS);
//            Boolean gotStats = statsCallback.gotStats();
//
//            // System.out.println(statsCallback.toString());
//
//            // dockerClient.stopContainerCmd(container.getId()).exec();
//
//        } catch(InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static class StatsCallback extends ResultCallbackTemplate<StatsCallback, Statistics> {
//        private final CountDownLatch countDownLatch;
//
//        private Boolean gotStats = false;
//
//        public StatsCallback(CountDownLatch countDownLatch) {
//            this.countDownLatch = countDownLatch;
//        }
//
//        @Override
//        public void onNext(Statistics stats) {
//            String string = stats.getCpuStats().toString();
//            // System.out.println(string);
//            if(stats != null) {
//                gotStats = true;
//            }
//            countDownLatch.countDown();
//
//
//            Set<Map.Entry<String, Object>> entries = stats.getCpuStats().entrySet();
//            for (Map.Entry<String, Object> entry : entries) {
//                System.out.println(entry.getKey() + " : " + entry.getValue());
//            }
//        }
//
//        public Boolean gotStats(){
//            return gotStats;
//        }
//    }
//}

public class cwDockerJavaTest{
    private static int NUM_STATS = 5;

    public static void main(String[] args) {
        String containerId = null;

        try {
            TimeUnit.SECONDS.sleep(1);

            CountDownLatch countDownLatch = new CountDownLatch(NUM_STATS);

            // Set a Container Name. It can be changed by Array to save multiple container name.
            String containerName = "generated_" + new SecureRandom().nextInt();

//            for(int i=0;i<100;i++) {
//                String[] containerName2 = null;
//                containerName2[i] = "generated_" + new SecureRandom().nextInt();
//            }

            // Initialize docker client to use Docker REST API
            DockerCmdExecFactory dockerCmdExecFactory = new JerseyDockerCmdExecFactory()
                    .withReadTimeout(1000)
                    .withConnectTimeout(1000)
                    .withMaxTotalConnections(100)
                    .withMaxPerRouteConnections(10);

            DockerClient dockerClient = DockerClientBuilder.getInstance("tcp://localhost:2376")
                    // .withDockerCmdExecFactory(dockerCmdExecFactory)
                    .build();

            // Get Docker info (only docker client information, not docker container information)
            Info info = dockerClient.infoCmd().exec();
            // System.out.print(info);

//            // 해당 부분은 DockerfileFixture 테스트 코드를 돌린 후에 나오는 directory 값을 확인해야 작업이 가능할 것 같다.
//            dockerClient.buildImageCmd(new File("/home/chorwon/example", directory)).withNoCache(true)
//                    .exec(new BuildImageResultCallback()).awaitImageId();

            // Set variable to the File directory that has Dockerfile.
            File baseDir = new File("/home/chorwon/example");

            // Check File directory result.
            System.out.println("Test" + baseDir.toString());

            // Check build progress callback function.
            BuildImageResultCallback callback = new BuildImageResultCallback() {
                public void onNext(BuildResponseItem item) {
                    System.out.println("" + item);
                    super.onNext(item);
                }
            };



            // docker build command. We can use callback to check build progress.
            dockerClient.buildImageCmd(baseDir)
                    .withTag("test")
                    .exec(callback).awaitImageId();

            // Check docker images. 'get(0)' means bring 1st images to the docker images command list.
            Image lastCreatedImage = dockerClient.listImagesCmd().exec().get(0);

//            // Setting ports  -> test
//            ExposedPort tcp22 = ExposedPort.tcp(22);
//            Ports portBindings = new Ports();
//            portBindings.bind(tcp22, Ports.Binding.bindPort(12345));

            // create new Docker container, not start.
            containerId = dockerClient.createContainerCmd(lastCreatedImage.getId())
                    .withCmd("./Intro_javaClient.sh", "testClient", "192.168.0.66", "12345")
                    .withName(containerName)
                    .withMemory((long)20000000)
                    .withCpusetCpus("2,3")
                    //.withExposedPorts(tcp22)
                    //.withPortBindings(portBindings)
                    .exec().getId();

            // Create new Docker container, not start.
//            CreateContainerResponse container = dockerClient.createContainerCmd("alpine:latest")
//                    .withCmd("top")
//                    .withName(containerName)
//                    .withCpusetCpus("2,3")
//                    .withMemory((long)(20000000))
//                    .exec();

            List<Container> containers = dockerClient.listContainersCmd().exec();

            // Start a container before created container
            dockerClient.startContainerCmd(containerId).exec();

//            // StatsCallbackTest
//            StatsCallback statsCallback = dockerClient.statsCmd(containerId).exec(
//                    new StatsCallback(countDownLatch));
//
//            countDownLatch.await(3, TimeUnit.SECONDS);
//            Boolean gotStats = statsCallback.gotStats();

            // System.out.println(statsCallback.toString());

            // dockerClient.stopContainerCmd(container.getId()).exec();

        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class StatsCallback extends ResultCallbackTemplate<StatsCallback, Statistics> {
        private final CountDownLatch countDownLatch;

        private Boolean gotStats = false;

        public StatsCallback(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void onNext(Statistics stats) {
            String string = stats.getCpuStats().toString();
            // System.out.println(string);
            if(stats != null) {
                gotStats = true;
            }
            countDownLatch.countDown();

            Set<Map.Entry<String, Object>> entries = stats.getCpuStats().entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
        }

        public Boolean gotStats(){
            return gotStats;
        }
    }
}