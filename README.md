# Solution
It is recommended to find "com.mostafa.codechallenge.rpncalculator.Main" class first and 
investigate through the code accordingly.

As it was mentioned in the problem spec, only the first stage was implemented. But the design 
approach is in the way to support the future stages. For example, right now, in-memory repositories 
were implemented. For the next stages, we can implement other kinds of repositories implementing 
ItemRepository as well as OperatorItemRepository for the sake of making calculator as an online
service. Hence, it is not necessary to change the service classes.

The Operator class can also support more operators. We can easily add new operators to the enum then 
implement the proper operation for it.

Besides, the domain classes can be extended to support more features in the future stages 
such as color for item. For the sake of this extension support, the whole services and repositories 
are generified. Hence, service and repository classes can involve new futures without any 
implementation changes.

## Prerequisite

- Java 11
- Maven 3.6+
- Docker

## Tech stack
- Java 11
- Docker

## Build
According to pom configuration, if you try to package the project, the proper docker image will
be created accordingly.

### Build and run tests:
```shell
mvn clean verify
```

### Build application without tests:

```shell
mvn clean install -DskipTests
```

## Run the project

After building, a simple way of running the application is:
```shell
java -jar target/rpncalculator-1.0-SNAPSHOT.jar
```

You can also run it by creating a docker container. The command is:
```shell
docker run -it --name rpncalculator --volume $PWD/logs:/logs rpncalculator:1.0-SNAPSHOT
```
As you can see above, you can mount log folder to grab the populated logs generated from the inside 
of the container.

I also provided a shell script to manage the whole building as well as running stuff together:
```shell
./runDockerContainer.sh
```

## Check the result
A logger is also configured to log the whole inputs and outputs in the file. You can find the 
results in logs folder

