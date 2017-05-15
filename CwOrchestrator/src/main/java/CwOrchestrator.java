//import com.github.dockerjava.api.DockerClient;
//import com.github.dockerjava.api.command.DockerCmdExecFactory;
//import com.github.dockerjava.api.model.BuildResponseItem;
//import com.github.dockerjava.api.model.Image;
//import com.github.dockerjava.core.DockerClientBuilder;
//import com.github.dockerjava.core.command.BuildImageResultCallback;
//import com.github.dockerjava.jaxrs.JerseyDockerCmdExecFactory;
//
//import java.io.File;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Scanner;
//
///**
// * Created by chorwon on 17. 4. 27.
// */
//public class CwOrchestrator {
//    public static void main(String[] args) {
//        // To input data
//        Data data = new Data();
//        Orchestrator orch = new Orchestrator(data);
//        Container container = null;
//
//        int i = 0;
//
//        if(data.getArgument().equals("1")) {
//            container = orch.getSingleArgument();
//        } else if(Integer.parseInt(data.getArgument()) > 1) {
//            container = orch.getMultiArgument();
//        } else {
//            System.out.println("return to Data input");
//        }
//
//        container.createContainer(data, i);
//        container.startContainer();
////        container.killContainer();
////        container.removeContainer();
//    }
//}
//
//class Orchestrator {
//    private DockerClient dockerClient;
//
//    // 기본적인 DockerClient 생성하고, 부트스트랩 포함
//    public Orchestrator(Data data) {
//        DockerCmdExecFactory dockerCmdExecFactory = new JerseyDockerCmdExecFactory()
//                .withReadTimeout(1000)
//                .withConnectTimeout(1000)
//                .withMaxTotalConnections(100)
//                .withMaxPerRouteConnections(10);
//
//        /* Need to update in Mac */
////        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
////                .withDockerHost("tcp://localhost:2376")
////                .withDockerTlsVerify(true)
////                .withDockerCertPath("/Users/Axlis/.docker")
////                .withRegistryUsername("Axlis")
////                .withRegistryPassword("CjfdnjS")
////                .withRegistryEmail("cwkim@smartx.kr")
////                .withRegistryUrl(null)
////                .build();
//
//
//        /* Make a Docker Client */
//        /* 1. In Linux */
//        dockerClient = DockerClientBuilder.getInstance("tcp://localhost:2376")
//                .build();
//
//        /* 2. In Mac */
////        this.dockerClient = DockerClientBuilder.getInstance(config)
////                .withDockerCmdExecFactory(dockerCmdExecFactory)
////                .build();
//
//        /* Build docker images to use Dockerfile */
//        data.buildImage(dockerClient);
//    }
//
//    public Container getSingleArgument() {
//        return new SingleArgumentContainer(dockerClient);
//    }
//
//    public Container getMultiArgument() {
//        return new MultiArgumentContainer(dockerClient);
//    }
//}
//
//abstract class Container extends com.github.dockerjava.api.model.Container {
//    // package-private
//    DockerClient dockerClient;
//    CallbackEvent callbackEvent;
//    String containerName;
//    String containerId;
//    int status;
//
//    public void doWork() {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        callbackEvent.callbackMethod();
//    }
//
//    public String getName() {
//        return this.containerName;
//    }
//
//    public abstract void createContainer(Data data, int i);
//
//    public void startContainer() {
//        dockerClient.startContainerCmd(containerId).exec();
//        status = 1;
//
//        System.out.println("Container " + containerName + " start and status = " + status);
//    }
//
//    public void killContainer() {
//        dockerClient.killContainerCmd(containerId).exec();
//        status = 2;
//
//        System.out.println("Container " + containerName + " kill and status = " + status);
//    }
//
//    public void removeContainer() {
//        dockerClient.removeContainerCmd(containerId).exec();
//
//        System.out.println("Container " + containerName + " remove ");
//    }
//}
//
//class SingleArgumentContainer extends Container{
//
//    public SingleArgumentContainer(DockerClient dockerClient) {
//        this.dockerClient = dockerClient;
//    }
//
//    public void createContainer(Data data, int i) {
//        containerName = data.getContainerName() + i;
//
//        containerId = dockerClient.createContainerCmd(data.getImage().getId())
//                .withCmd(data.getCommand())
//                .withName(containerName)
//                .withCpusetCpus(data.getCPUs())
//                .withMemory(data.getMemory())
//                .exec().getId();
//
//        System.out.println(containerName + " create complete");
//    }
//}
//
//class MultiArgumentContainer extends Container {
//
//    public MultiArgumentContainer(DockerClient dockerClient) {
//        this.dockerClient = dockerClient;
//    }
//
//    public void createContainer(Data data, int i) {
//        containerName = data.getContainerName();
//
//        List<String> commandList = Arrays.asList(data.getCommand().split(" "));
//
//        System.out.println(data.getImage().getId());
//
//        containerId = dockerClient.createContainerCmd(data.getImage().getId())
//                .withCmd(commandList)
//                .withName(containerName)
//                .withCpusetCpus(data.getCPUs())
//                .withMemory(data.getMemory())
//                .exec().getId();
//
//        System.out.println(containerName + " create complete");
//    }
//}
//
//// Entered Data to Users
//class Data {
//    private String path;
//    private String tag;
//    private String count = "0";
//    private String containerUserName;
//    private String argument;
//    private String cmd;
//    private String setcpus;
//    private String memory;
//    private Image image;
//
//    private Long systemMemory;
//
//    public Data() {
//
//        Scanner scan = new Scanner(System.in);
//
//        System.out.println("Input the dockerfile path before create container: ");
//        this.path = scan.nextLine();
//
//        System.out.println("Input the tag for build images: ");
//        this.tag = scan.nextLine();
//
//        System.out.println("Input the number how many to start container: ");
//        this.count = scan.nextLine();
//
//        System.out.println("Input the name of container: ");
//        this.containerUserName = scan.nextLine();
//
//        System.out.println("Input the argument count to start functions ");
//        this.argument = scan.nextLine();
//
//        System.out.println("Input the command to start in container: ");
//        this.cmd = scan.nextLine();
//
//        System.out.println("Input the CPUs to allocate for container(ex 2,3): ");
//        this.setcpus = scan.nextLine();
//
//        System.out.println("Input the Memory to allocate for container(ex 200000, 2kb, 2MB..): ");
//        this.memory = scan.nextLine();
//    }
//
//    public String getPath() {
//        return path;
//    }
//
//    public String getTag() {
//        return tag;
//    }
//
//    public int getCount() {
//        int number = Integer.parseInt(count);
//
//        if(number <= 0) {
//            System.out.println("What are you doing? ");
//            System.out.println("Retry!!");
//            System.out.println("Input the number how many to start container: ");
//            new Data();
//        }
//
//        return number;
//    }
//
//    public String getContainerName() {
//        return containerUserName;
//    }
//
//    public String getArgument() {
//        return argument;
//    }
//
//    public String getCommand() {
//        return cmd;
//    }
//
//    public String getCPUs() {
//        return setcpus;
//    }
//
//    public String getEnteredMemory() {
//        return memory;
//    }
//
//    public Long getMemory() {
//        long temp;
//
//        // To delete other char except number for parsing
//        temp = Long.parseLong(memory.replaceAll("[^0-9]", ""));
//
//        if(memory.contains("K") || memory.contains("k")) {
//            temp *= 1000;
//        } else if(memory.contains("M") || memory.contains("m")) {
//            temp *= 1000000;
//        } else if(memory.contains("G") || memory.contains("g")) {
//            temp *= 1000000000;
//        }
//
//        return temp;
//    }
//
//    public Image getImage() {
//        return image;
//    }
//
//    public void buildImage(DockerClient dockerClient) {
//        File baseDir = new File(path);
//
//        System.out.println("Dockerfile directory : " + baseDir.toString());
//        System.out.println("Correct! Isn't it? [N/y]");
//
//        Scanner scan = new Scanner(System.in);
//        String check = scan.nextLine();
//
//        if (check.contains("n") || check.contains("N")) {
//        }
//
//        // Need to implement to check the program is dead or not.
//        BuildImageResultCallback callback = new BuildImageResultCallback() {
//            public void onNext(BuildResponseItem item) {
//                System.out.println("" + item.getStream());
//                super.onNext(item);
//            }
//        };
//
//        dockerClient.buildImageCmd(baseDir)
//                .withTag(tag)
//                .exec(callback).awaitImageId();
//
//        image = dockerClient.listImagesCmd().exec().get(0);
//    }
//}
//
//interface CallbackEvent {
//    void callbackMethod();
//}


import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.DockerCmdExecFactory;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.BuildResponseItem;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.BuildImageResultCallback;
import com.github.dockerjava.jaxrs.JerseyDockerCmdExecFactory;

import java.io.File;
import java.util.*;

/**
 * Created by chorwon on 17. 4. 27.
 */
public class CwOrchestrator {

    // Example Code how to use Orchestrator
    public static void main(String[] args) {
        // To input data
        Configuration configuration = new Configuration();

        Orchestrator orch = Orchestrator.getInstance(configuration);

        orch.createService(configuration);
        Map<String, Service> map = orch.getAllServices();

        Service checkService = map.get(configuration.getContainerName());

        checkService.startService();
        checkService.pauseService();
        checkService.unpauseService();
        checkService.killService();
        checkService.removeService();
    }
}

// SingleTone Pattern
class Orchestrator {
    private static Orchestrator orchestrator;
    private DockerClient dockerClient;
    private CallbackEvent callbackEvent;
    private Map<String, Service> containerMap;


    public static Orchestrator getInstance(Configuration configuration) {
        if(orchestrator == null) {
            orchestrator = new Orchestrator(configuration);
        }

        return orchestrator;
    }

    // Make a DockerClient and include BootStrap.
    private Orchestrator(Configuration configuration) {
        DockerCmdExecFactory dockerCmdExecFactory = new JerseyDockerCmdExecFactory()
                .withReadTimeout(1000)
                .withConnectTimeout(1000)
                .withMaxTotalConnections(100)
                .withMaxPerRouteConnections(10);

        /* Need to update in Mac */
//        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
//                .withDockerHost("tcp://localhost:2376")
//                .withDockerTlsVerify(true)
//                .withDockerCertPath("/Users/Axlis/.docker")
//                .withRegistryUsername("Axlis")
//                .withRegistryPassword("CjfdnjS")
//                .withRegistryEmail("cwkim@smartx.kr")
//                .withRegistryUrl(null)
//                .build();


        /* Make a Docker Client */
        /* 1. In Linux */
        dockerClient = DockerClientBuilder.getInstance("tcp://localhost:2376")
                .build();

        /* BootStrap to previous Container information */

        List<Container> containerList = dockerClient.listContainersCmd().withShowAll(true).exec();

        for(Container container : containerList) {

            // To use Type Conversion
            Container containerTemp = new Container();
            Service initialService = (Service)containerTemp;

            InspectContainerResponse inspectContainerResponse = dockerClient.inspectContainerCmd(container.getId()).exec();

            // 자식은 부모를 못 가리킨다. Container c = container(com.xxxx) [X]

            initialService.setId(container.getId());

            containerMap.put(initialService.setName(inspectContainerResponse.getName().replace("/", "")), initialService);

            System.out.println(initialService.getNames() + initialService.getId() + initialService.getState());
        }

        System.out.println("BootStrap Complete");



        /* 2. In Mac */
//        this.dockerClient = DockerClientBuilder.getInstance(config)
//                .withDockerCmdExecFactory(dockerCmdExecFactory)
//                .build();

        /* Build docker images to use Dockerfile */
        configuration.buildImage(dockerClient);
    }

    CallbackEvent getConditionListener() {

        callbackEvent = new CallbackEvent() {
            public void callbackMethod(String containerName, String state) {
//                System.out.println(containerName + " " + condition);

            }
        };

        return callbackEvent;
    }

    Service createService(Configuration configuration) {

        Service service = new Service();

        String containerName = configuration.getContainerName();
        String containerId;

        // Created container is accompanied by argument count.
        if(configuration.getArgument().contains(" ")) {
            List<String> commandList = Arrays.asList(configuration.getCommand().split(" "));

            containerId = dockerClient.createContainerCmd(configuration.getImage().getId())
                    .withCmd(commandList)
                    .withName(containerName)
                    .withCpusetCpus(configuration.getCPUs())
                    .withMemory(configuration.getMemory())
                    .exec().getId();
        } else {
            containerId = dockerClient.createContainerCmd(configuration.getImage().getId())
                    .withCmd(configuration.getCommand())
                    .withName(containerName)
                    .withCpusetCpus(configuration.getCPUs())
                    .withMemory(configuration.getMemory())
                    .exec().getId();
        }

        service.setId(containerId);

        // Save to Data Structure(Map)
        containerMap.put(configuration.getContainerName(), service);

        return service;
    }

    Map<String, Service> getAllServices() {

        return containerMap;
    }
}

//abstract class Service extends com.github.dockerjava.api.model.Container implements Runnable{
//    // package-private
//    DockerClient dockerClient;
//    CallbackEvent callbackEvent;
//    String containerName;
//    String containerId;
//    int condition;
//    // priority to start container.
//    int priority;
//
//    public abstract void createContainer(Configuration configuration, Map<String, Service> map);
//    public abstract void run();
//
//    public String getName() {
//        return this.containerName;
//    }
//
//    public String getStatus() {
//        return super.getStatus();
//    }
//
//    public String getId() {
//        return containerId;
//    }
//
//    public String setName(String str) {
//        containerName = str;
//
//        return containerName;
//    }
//
//    public String setId(String str) {
//        containerId = str;
//
//        return containerId;
//    }
//
//    void startContainer() {
//        dockerClient.startContainerCmd(containerId).exec();
//        condition = 1;
//
//        System.out.println("Container " + containerName + " start and status = " + condition);
//    }
//
//    void killContainer() {
//        dockerClient.killContainerCmd(containerId).exec();
//        condition = 2;
//
//        System.out.println("Container " + containerName + " kill and status = " + condition);
//    }
//
//    void removeContainer() {
//        dockerClient.removeContainerCmd(containerId).exec();
//
//        System.out.println("Container " + containerName + " remove ");
//    }
//}
//
//class SingleArgumentContainer extends Service{
//
//    SingleArgumentContainer(DockerClient dockerClient, CallbackEvent callback) {
//        this.dockerClient = dockerClient;
//        this.callbackEvent = callback;
//    }
//
//    public void createContainer(Configuration configuration, Map<String, Service> map) {
//        containerName = configuration.getContainerName();
//
//        containerId = dockerClient.createContainerCmd(configuration.getImage().getId())
//                .withCmd(configuration.getCommand())
//                .withName(containerName)
//                .withCpusetCpus(configuration.getCPUs())
//                .withMemory(configuration.getMemory())
//                .exec().getId();
//
//        map.put(containerName, this);
//
//        // When container started, start thread to send container condition at the same time 2 seconds period.
//        Thread sendCondition = new Thread(this);
//        sendCondition.start();
//    }
//
//    public void run() {
//        try {
//            Thread.sleep(3000);
//
//            while(condition == 1) {
//
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                callbackEvent.callbackMethod(containerName, condition);
//            }
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}
//
//class MultiArgumentContainer extends Service {
//
//    MultiArgumentContainer(DockerClient dockerClient, CallbackEvent callback) {
//        this.dockerClient = dockerClient;
//        this.callbackEvent = callback;
//    }
//
//    public void createContainer(Configuration configuration, Map<String, Service> map) {
//        containerName = configuration.getContainerName();
//
//        List<String> commandList = Arrays.asList(configuration.getCommand().split(" "));
//
//        containerId = dockerClient.createContainerCmd(configuration.getImage().getId())
//                .withCmd(commandList)
//                .withName(containerName)
//                .withCpusetCpus(configuration.getCPUs())
//                .withMemory(configuration.getMemory())
//                .exec().getId();
//
//        map.put(containerName, this);
//
//        // When container started, start thread to send container condition at the same time 2 seconds period.
//        Thread sendCondition = new Thread(this);
//        sendCondition.start();
//    }
//
//
//    public void run() {
//        try {
//            Thread.sleep(3000);
//
//            while(condition == 1) {
//
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                callbackEvent.callbackMethod(containerName, condition);
//            }
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}
//
//// Entered Configuration values to user
//class Configuration {
//    private String path;
//    private String tag;
//    private String count = "0";
//    private String containerUserName;
//    private String argument;
//    private String cmd;
//    private String setcpus;
//    private String memory;
//    private Image image;
//
//    private Long systemMemory;
//
//    Configuration() {
//
//        Scanner scan = new Scanner(System.in);
//
//        System.out.println("Input the dockerfile path before create container: ");
//        this.path = scan.nextLine();
//
//        System.out.println("Input the tag for build images: ");
//        this.tag = scan.nextLine();
//
//        System.out.println("Input the number how many to start container: ");
//        this.count = scan.nextLine();
//
//        System.out.println("Input the name of container: ");
//        this.containerUserName = scan.nextLine();
//
//        System.out.println("Input the argument count to start functions ");
//        this.argument = scan.nextLine();
//
//        System.out.println("Input the command to start in container: ");
//        this.cmd = scan.nextLine();
//
//        System.out.println("Input the CPUs to allocate for container(ex 2,3): ");
//        this.setcpus = scan.nextLine();
//
//        System.out.println("Input the Memory to allocate for container(ex 200000, 2kb, 2MB..): ");
//        this.memory = scan.nextLine();
//    }
//
//    public String getPath() {
//        return path;
//    }
//
//    public String getTag() {
//        return tag;
//    }
//
//    public int getCount() {
//        int number = Integer.parseInt(count);
//
//        if(number <= 0) {
//            System.out.println("What are you doing? ");
//            System.out.println("Retry!!");
//            System.out.println("Input the number how many to start container: ");
//            new Configuration();
//        }
//
//        return number;
//    }
//
//    String getContainerName() {
//        return containerUserName;
//    }
//
//    String getArgument() {
//        return argument;
//    }
//
//    String getCommand() {
//        return cmd;
//    }
//
//    String getCPUs() {
//        return setcpus;
//    }
//
//    public String getEnteredMemory() {
//        return memory;
//    }
//
//    Long getMemory() {
//        long temp;
//
//        // To delete other char except number for parsing
//        temp = Long.parseLong(memory.replaceAll("[^0-9]", ""));
//
//        if(memory.contains("K") || memory.contains("k")) {
//            temp *= 1000;
//        } else if(memory.contains("M") || memory.contains("m")) {
//            temp *= 1000000;
//        } else if(memory.contains("G") || memory.contains("g")) {
//            temp *= 1000000000;
//        }
//
//        return temp;
//    }
//
//    Image getImage() {
//        return image;
//    }
//
//    void buildImage(DockerClient dockerClient) {
//        File baseDir = new File(path);
//
//        System.out.println("Dockerfile directory : " + baseDir.toString());
//        System.out.println("Correct! Isn't it? [N/y]");
//
//        Scanner scan = new Scanner(System.in);
//        String check = scan.nextLine();
//
//        if (check.contains("n") || check.contains("N")) {
//
//        }
//
//        // Need to implement to check the program is dead or not.
//        BuildImageResultCallback callback = new BuildImageResultCallback() {
//            public void onNext(BuildResponseItem item) {
//                System.out.println("" + item.getStream());
//                super.onNext(item);
//            }
//        };
//
//        dockerClient.buildImageCmd(baseDir)
//                .withTag(tag)
//                .exec(callback).awaitImageId();
//
//        image = dockerClient.listImagesCmd().exec().get(0);
//    }
//}
//
//interface CallbackEvent {
//    void callbackMethod(String containerName, int condition);
//}


class Service extends Container implements Runnable{
    // package-private
    DockerClient dockerClient;
    CallbackEvent callbackEvent;
    String containerName;
    String containerId;
    String state;

    public void run() {
        try {
            Thread.sleep(3000);

            while(state.equals("Running")) {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                callbackEvent.callbackMethod(containerName, state);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getState() {
        return state;
    }

    public String getId() {
        return containerId;
    }

    public String setName(String str) {
        containerName = str;

        return containerName;
    }

    public String setId(String str) {
        containerId = str;

        return containerId;
    }

    void startService() {
        dockerClient.startContainerCmd(containerId).exec();
        state = "Running";

        System.out.println("Container " + containerName + " start and status = " + state);
    }

    void killService() {
        dockerClient.killContainerCmd(containerId).exec();
        state = "Exited";

        System.out.println("Container " + containerName + " kill and status = " + state);
    }

    void removeService() {
        dockerClient.removeContainerCmd(containerId).exec();


        System.out.println("Container " + containerName + " remove ");
    }

    void pauseService() {
        dockerClient.pauseContainerCmd(containerId).exec();
        state = "Paused";

        System.out.println("Container " + containerName + " paused ");
    }

    void unpauseService() {
        dockerClient.unpauseContainerCmd(containerId).exec();
        state = "Running";

        System.out.println("Container " + containerName + " unpaused ");
    }
}

// Entered Configuration values to user
class Configuration {
    private String path;
    private String tag;
    private String count = "0";
    private String containerUserName;
    private String argument;
    private String cmd;
    private String setcpus;
    private String memory;
    private Image image;

    private Long systemMemory;

    Configuration() {

        Scanner scan = new Scanner(System.in);

        System.out.println("Input the dockerfile path before create container: ");
        this.path = scan.nextLine();

        System.out.println("Input the tag for build images: ");
        this.tag = scan.nextLine();

        System.out.println("Input the number how many to start container: ");
        this.count = scan.nextLine();

        System.out.println("Input the name of container: ");
        this.containerUserName = scan.nextLine();

        System.out.println("Input the argument count to start functions ");
        this.argument = scan.nextLine();

        System.out.println("Input the command to start in container: ");
        this.cmd = scan.nextLine();

        System.out.println("Input the CPUs to allocate for container(ex 2,3): ");
        this.setcpus = scan.nextLine();

        System.out.println("Input the Memory to allocate for container(ex 200000, 2kb, 2MB..): ");
        this.memory = scan.nextLine();
    }

    public String getPath() {
        return path;
    }

    public String getTag() {
        return tag;
    }

    public int getCount() {
        int number = Integer.parseInt(count);

        if(number <= 0) {
            System.out.println("What are you doing? ");
            System.out.println("Retry!!");
            System.out.println("Input the number how many to start container: ");
        }

        return number;
    }

    String getContainerName() {
        return containerUserName;
    }

    String getArgument() {
        return argument;
    }

    String getCommand() {
        return cmd;
    }

    String getCPUs() {



        return setcpus;
    }

    public String getEnteredMemory() {
        return memory;
    }

    Long getMemory() {
        long temp;

        // To delete other char except number for parsing
        temp = Long.parseLong(memory.replaceAll("[^0-9]", ""));

        if(memory.contains("K") || memory.contains("k")) {
            temp *= 1000;
        } else if(memory.contains("M") || memory.contains("m")) {
            temp *= 1000000;
        } else if(memory.contains("G") || memory.contains("g")) {
            temp *= 1000000000;
        }

        return temp;
    }

    Image getImage() {
        return image;
    }

    void buildImage(DockerClient dockerClient) {
        File baseDir = new File(path);

        System.out.println("Dockerfile directory : " + baseDir.toString());
        System.out.println("Correct! Isn't it? [N/y]");

        Scanner scan = new Scanner(System.in);
        String check = scan.nextLine();

        if (check.contains("n") || check.contains("N")) {
            System.out.println("Input the dockerfile path before create container: ");
            this.path = scan.nextLine();
            this.buildImage(dockerClient);
        } else if (check.contains("y") || check.contains("Y")) {
            // When user input only 'y' or 'Y', the build will go.

            // Need to implement to check the program is dead or not.
            BuildImageResultCallback callback = new BuildImageResultCallback() {
                public void onNext(BuildResponseItem item) {
                    System.out.println("" + item.getStream());
                    super.onNext(item);
                }
            };

            dockerClient.buildImageCmd(baseDir)
                    .withTag(tag)
                    .exec(callback).awaitImageId();

            image = dockerClient.listImagesCmd().exec().get(0);
        }
    }
}

interface CallbackEvent {
    void callbackMethod(String containerName, String state);
}