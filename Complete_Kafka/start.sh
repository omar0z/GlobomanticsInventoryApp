#Build the project
mvn clean install

#Start Zookeeper and Kafka
docker-compose up -d kafka

#Wait for kafka to initialize
echo "Kafka is starting..."
sleep 5

#Create kafka topic
docker-compose exec kafka kafka-topics --bootstrap-server broker:9092 --create --topic restock

#Start Payara Application Server
docker-compose up payara