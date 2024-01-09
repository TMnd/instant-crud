docker run -ti --rm --name postgres \
	--network host \
	-e POSTGRES_USER=localuser \
	-e POSTGRES_PASSWORD=localpassword \
	-d postgres:15