module ScoreService
{
    requires spring.boot.autoconfigure;
    requires spring.web;
    requires spring.beans;
    requires spring.context;
    requires java.base;
    requires java.sql;
    requires jakarta.servlet;
    requires spring.boot;

    exports dk.sdu.mmmi.cbse.scoreservice;
    opens dk.sdu.mmmi.cbse.scoreservice to spring.core, spring.beans, spring.context;
}