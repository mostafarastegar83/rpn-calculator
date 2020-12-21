 #!/bin/sh
 mvn clean install -DskipTests
 docker run -it --name rpncalculator --volume $PWD/logs:/logs rpncalculator:1.0-SNAPSHOT
 docker rm -f rpncalculator