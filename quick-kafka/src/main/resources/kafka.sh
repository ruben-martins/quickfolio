docker run -d -p 9092:9092 apache/kafka:4.0.0
bin/kafka-topics.sh --create --topic prices --bootstrap-server localhost:9092
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic prices --from-beginning
