**Explore with me**
========================
Free time is a valuable resource. Every day we plan how to spend it - where and with whom to go. The most difficult thing in such planning is the search for information and negotiations. What events are planned, whether friends are free at this moment, how to invite everyone and where to gather. This application is a poster where you can offer any event from an exhibition to going to the cinema and recruit a company to participate in it.

**The application is divided into two parts:**
* The main service that implements the main functionality.
* Statistic service for collecting statistics that collects information about incoming GET requests regarding events.

**Main`s functionality (_dedicated port: 8080_):**
* Events and work with them - creation, moderation;
* User requests to participate in the event - request, confirmation, rejection.
* Create and manage compilations.
* Viewing events without authorization;
* Ability to create and manage categories;

**Statistic`s functionality (_dedicated port: 9090_):**
* Stores the number of views and allows you to make selections to analyze the application.

**Specifications:**
* [Main service](https://raw.githubusercontent.com/yandex-praktikum/java-explore-with-me/main/ewm-main-service-spec.json)
* [Statistic service](https://raw.githubusercontent.com/yandex-praktikum/java-explore-with-me/main/ewm-stats-service-spec.json)

**How to start:**
* Run docker-compose.yml from *\java-explore-with-me\docker-compose.yml
* If you need to run the application without Docker - just change the data source port in application.properties to 5432 in the main and static directory. After that run it with your IDE.

![Architect](https://sun9-70.userapi.com/impg/QpkVY9I4DpQPzSGnyv0CtIJZ4x-zWztba4AEmg/a5Jsa5TlGmo.jpg?size=1656x1028&quality=96&sign=f97bab5616672ed3b0144adf4d99d366&type=album)
