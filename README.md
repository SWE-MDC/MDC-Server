#
## 1. Build Jar with Maven
`./mvnw install`
## 2. Build Docker Image
`sudo docker build --build-arg JAR_FILE=target/*.jar -t osuswe/mdc .`
## 3. Run
`sudo docker run --rm -p 8081:8080 osuswe/mdc`