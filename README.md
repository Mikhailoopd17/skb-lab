
1. Стек Java 11, Spring data, Spring Boot, H2 (in memory), maven, junit4
2. Приложение разделено на 2 модуля, фронтенд (форма регистрации) на Angular (11), бэкенд (см. п1)
3. Взаимодействие через HTTP (REST), формат данных JSON
4. Сборка и запуск

        4.1 Сборка проектка mvn clean install
        
        4.2 Запуск сервисов:
        
            4.2.1 Из IDE дефолтными конфигурациями (BackendApplication, Angular CLI)
            
            4.2.2 CMD
            
                - cd <target root backend>
                - java -jar backend-1.0.jar --spring.config.location=application.properties
                
                - cd <root frontend>
                - npm run start
5. После запуска по url http://localhost:8090/swagger-ui.html доступна Open api doc

6. Описание rest-сервиса:

        Сервис на входе получает заявку с пользовательскими данными, сохраняет ее в бд и добавляет в очередь на последующую 
        обработку. Очередь реализована по расписанию (заданный интервал времени). Обработчик очереди RequestService,
        в соответствии с типом обработки (ActionType), запускает процессы обработки (messaging and sendEmail). 
        В упрощенном виде реализован механизм заданного количества повторов обработки элемента очереди, 
        в случае возникновения исключений.
        
