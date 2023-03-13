#Build the project
mvn clean install

#Build the Docker image
docker build -t globomantics .

#Run the Globomantics Docker container
docker run --name globomantics -p 8080:8080 -p 4848:4848 globomantics
