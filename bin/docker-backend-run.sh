docker run -i --rm \
  --network host \
  -p 8080:8080 \
  -e DB_HOST_URL="localhost:5432" \
  -e DB_USER_NAME="localuser" \
  -e DB_USER_PASSWORD="localpassword" \
  -e DB_DATABASE="postgres" \
  -e DB_SCHEMA="public" \
  quarkus/instant-crud bashd