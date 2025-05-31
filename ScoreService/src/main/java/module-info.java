// ScoreService/src/main/java/module-info.java
module ScoreService {
    requires spring.boot.autoconfigure; // Or spring.boot, depending on exact needs
    requires spring.web;
    requires spring.beans; // May be needed if Spring Beans are used directly
    requires spring.context; // May be needed if Spring Context is used directly
    requires java.base; // implicit but good to have
    requires java.sql; // often needed for Spring Boot data
    requires jakarta.servlet;
    requires spring.boot; // needed by spring web for servlet api

    exports dk.sdu.mmmi.cbse.scoreservice;
    opens dk.sdu.mmmi.cbse.scoreservice to spring.core, spring.beans, spring.context;
}