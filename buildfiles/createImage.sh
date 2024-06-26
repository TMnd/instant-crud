PROJECT_VERSION="1.0.0"
REMOTE_REPO="10.10.0.222:5000"
MODULES=()
MODULES+=("../Dockerfile-instant-crud")

#print_usage
print_usage() {
    echo "options are:"
    echo "-p to push images default false"
    echo "-m modules separated by commam default are ${ALL_MODULES[@]}"
}

while getopts 'pm:e:h' flag; do
  case "${flag}" in
    p) PUSH='true' ;;
    m) MODULES=(${OPTARG}) ;;
    h) print_usage
        exit 1 ;;
    *) print_usage
       exit 1 ;;
  esac
done

create_image() {
    start=$(date +%s)

    MODULE=$(echo $1 |sed  "s/.*Dockerfile-//")
    IMAGE_NAME=${MODULE}

    docker build \
      -f $PWD/../Dockerfile-instant-crud \
      -t $IMAGE_NAME:$PROJECT_VERSION \
      ..

    if [ $PUSH = "true" ]; then
        echo $IMAGE_NAME
        docker tag $IMAGE_NAME:$PROJECT_VERSION $REMOTE_REPO/$IMAGE_NAME:$PROJECT_VERSION
        docker push $REMOTE_REPO/$IMAGE_NAME:$PROJECT_VERSION
    fi
    end=(date +%s)
    echo "$IMAGE_NAME took: $( echo "$end - $start" | bc -l )s"
}

for ITEM in "${MODULES[@]}"; do
    echo "creating $ITEM";
    create_image $ITEM
done
