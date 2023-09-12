./mvnw install
sudo docker build --build-arg JAR_FILE=target/*.jar -t osuswe/mdc .
