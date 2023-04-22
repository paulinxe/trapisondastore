USERID = `id -u`
USERNAME = `id -un`
GROUPID = `id -g`
GROUPNAME = `id -gn`

up:
	sed -i "s/<userId>/$(USERID)/;\
	s/<userName>/$(USERNAME)/;\
	s/<groupId>/$(GROUPID)/;\
	s/<groupName>/$(GROUPNAME)/" .env
	docker compose up --build -d
down:
	docker compose down --remove-orphans
