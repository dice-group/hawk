export FUSEKI_BASE=./
export FUSEKI_HOME=./apache-jena-fuseki-3.4.0

java -Xmx32G -jar apache-jena-fuseki-3.4.0/fuseki-server.jar --conf=fuseki_text.ttl --timeout=60000 --verbose 
