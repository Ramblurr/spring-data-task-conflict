# Spring data-jpa and cloud-task conflict test case


This is a simple test case to demonstrate how spring-data-jpa and spring-cloud-task are not working together.




## Configure

1. Edit `src/main/resources/application.properties` and configure your JDBC details. If you use a non-postgres DB, add the `runtime()` dep in build.gradle.

## With `@EnableTask`

1. `gradle bootRun`

```
2017-01-13 14:14:16.137 DEBUG 95354 --- [           main] org.hibernate.SQL                        : select nextval ('public.hibernate_sequence')
2017-01-13 14:14:16.253 DEBUG 95354 --- [           main] org.hibernate.SQL                        : select nextval ('public.hibernate_sequence')
2017-01-13 14:14:16.281  INFO 95354 --- [           main] s.c.a.AnnotationConfigApplicationContext : Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@101df177: startup date [Fri Jan 13 14:13:59 CET 2017]; root of context hierarchy
2017-01-13 14:14:16.294  INFO 95354 --- [           main] o.s.c.support.DefaultLifecycleProcessor  : Stopping beans in phase 0
2017-01-13 14:14:16.299  INFO 95354 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Unregistering JMX-exposed beans on shutdown
2017-01-13 14:14:16.307  INFO 95354 --- [           main] j.LocalContainerEntityManagerFactoryBean : Closing JPA EntityManagerFactory for persistence unit 'default'
2017-01-13 14:14:16.320  INFO 95354 --- [           main] c.e.SpringDataTaskConflictApplication    : Started SpringDataTaskConflictApplication in 27.755 seconds (JVM running for 28.642)

BUILD SUCCESSFUL

Total time: 32.716 secs
```

Notice the two `select nextval` but no `insert`


## Without `@EnableTask`

1. Comment out the `@EnableTask` line in  `SpringDataTaskConflictApplication.java`
2. `gradle bootRun`

```
   2017-01-13 14:12:23.407 DEBUG 86838 --- [           main] org.hibernate.SQL                        : select nextval ('public.hibernate_sequence')
   2017-01-13 14:12:23.537 DEBUG 86838 --- [           main] org.hibernate.SQL                        : insert into public.person (name, id) values (?, ?)
   2017-01-13 14:12:23.556 DEBUG 86838 --- [           main] org.hibernate.SQL                        : select nextval ('public.hibernate_sequence')
   2017-01-13 14:12:23.560 DEBUG 86838 --- [           main] org.hibernate.SQL                        : insert into public.person (name, id) values (?, ?)
   2017-01-13 14:12:23.567  INFO 86838 --- [           main] c.e.SpringDataTaskConflictApplication    : Started SpringDataTaskConflictApplication in 25.865 seconds (JVM running for 27.108)
   2017-01-13 14:12:23.569  INFO 86838 --- [       Thread-2] s.c.a.AnnotationConfigApplicationContext : Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@101df177: startup date [Fri Jan 13 14:12:09 CET 2017]; root of context hierarchy
   2017-01-13 14:12:23.572  INFO 86838 --- [       Thread-2] o.s.j.e.a.AnnotationMBeanExporter        : Unregistering JMX-exposed beans on shutdown
   2017-01-13 14:12:23.573  INFO 86838 --- [       Thread-2] j.LocalContainerEntityManagerFactoryBean : Closing JPA EntityManagerFactory for persistence unit 'default'
   
   BUILD SUCCESSFUL
```

Here we see the two insert statements occurring as expected.
