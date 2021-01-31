FROM openjdk:11
ENV MYSQL_HOST=localhost
ENV MYSQL_PORT=3306
ENV MYSQL_DB=blog-db
ENV MYSQL_USERNAME=root
ENV MYSQL_PASSWORD=root
ENV MAIL_USERNAME=cookingportal2
ENV MAIL_PASSWORD=cookingportalPassword2020.
ENV JWT_SECRET_KEY=leverxSecretKey
ENV JWT_EXPIRATION=86400000
EXPOSE 8080
ADD build/libs/blog-0.0.1-SNAPSHOT.jar blog.jar
ENTRYPOINT ["java","-jar","/blog.jar"]