echo "Starting various"
java -jar sil_eval.jar various.conf various.csv
echo "End various"

echo "Starting degraded"
java -jar sil_eval.jar degraded.conf degraded.csv
echo "End degraded"

echo "Starting environment"
java -jar sil_eval.jar environment.conf environment.csv
echo "End environment"

echo "Starting feedback"
java -jar sil_eval.jar feedback.conf feedback.csv
echo "End feedback"


