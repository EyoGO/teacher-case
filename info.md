Remove dependencies (lib) from Artifact, it will be added with maven on runtime (Tomcat).

org.postgresql.util.PSQLException: ПОМИЛКА: повторювані значення ключа порушують обмеження унікальності "users_first_name_last_name_key"
Detail: Ключ (first_name, last_name)=(123, 123213) вже існує.


Deployment:
1. (`or step 5`) Change `application.properties` file.
2. Build `war` using `mvn install` command.
3. Add `war` to `webapps` folder of `Tomcat`.
4. Start `Tomcat` using `startup.bat`.
5. (`or step 1`) Change `application.properties` file under `war_file_name/WEB-INF/classes` folder and restart `Tomcat`.
6. Access service using URL like `http://domain:port/war_file_name` (in case you deploy `war` as ROOT.war, then no need to use name)