# Consul Workshop
During workshop you will be developing weather-station and weather-forecast services that provides information about
weather conditions. To be able to complete tasks described in workshop you will need:

1. JDK8 or newer
2. IDE
3. Internet connection
4. Browser

**Note**: Workshop was created and tested using Intellij Community Edition with Lombok plugin and enabled annotation processing.


**Note**: In case of some exercises you will need to start multiple jvm instances. It's recommended to specify Xms/Xmx
parameters to limit memory usage (e.g. `-Xms256m -Xmx256m`)

Please start by compiling project:
```
./gradlew build
```

## Downloading Consul
[https://www.consul.io/downloads.html](https://www.consul.io/downloads.html)

## Running Consul
To start local instance of consul:
```
./consul/bin/consul agent --dev
```

**Note**: You may need to use `-client 0.0.0.0` argument to allow non-loopback connections

Then verify you can access consul ui, please type following url in browser:

[http://localhost:8500/ui/#/dc1/services](http://localhost:8500/ui/#/dc1/services)

You should be able to see that there is one healthy service called `consul`

## Weather Station: Running
Run weather-station application from ide, the main class is `WeatherStationApplication.java`. Observe how application
registers itself in consul by analyzing console logs (all of these parameters are configurable):
```
Registering service with consul: NewService{id='weather-station-8081', name='weather-station', tags=[], address='10.0.2.15', port=8081, enableTagOverride=null, check=Check{script='null', interval='10s', ttl='null', http='http://10.0.2.15:8081/health', tcp='null', timeout='null', deregisterCriticalServiceAfter='null', tlsSkipVerify=null, status='null'}, checks=null}
```
Then verify that application is visible in consul:

[http://localhost:8500/ui/#/dc1/services/weather-station](http://localhost:8500/ui/#/dc1/services/weather-station)

Then verify that health endpoint is working:

[http://localhost:8080/health](http://localhost:8080/health)

Then verify that weather endpoint is working:

[http://localhost:8080/weather](http://localhost:8080/weather)

**Note**: In case port 8080 (default) is already in use please change it to any other free port

## Weather Station: Health Check / Service Discovery
Thanks to `spring-cloud-consul` weather-station service is already registered and ready to be discovered by other
applications. However we have received following email from engineering team:

> We have noticed that temperature sensors returns invalid measurements when temperature exceeds thresholds predefined
by manufacturer (-30, 30). We recommend using different sensors and in the meantime discarding any measurements when
temperature exceeds thresholds.

We should handle this situation by changing health status of weather station from good (up) to bad (down) when temperature
is too low or too high. This way we will notify other services that weather-station is experiencing problems and should
not be used.

Please familiarize yourself with `weather` and `sensors` properties defined in `weather-station/src/main/resources/application.yml`
and then implement necessary functionality in `weather-station/src/main/java/weather/sensors/SensorsHealthIndicator.java`. 

To verify that your code works try changing weather temperature in `weather-station/src/main/resources/application.yml`,
restart application and observe status in consul. Moreover visit health endpoint and analyze output:

[http://localhost:8080/health](http://localhost:8080/health)

**Note**: Consider analyzing `org.springframework.boot.actuate.health.DiskSpaceHealthIndicator.java` which performs similar 
logic, but regarding available disk space.

## Weather Station: Key/Value Store
Although we have solved the problem after few hours we receive another email, this time it's from operations team:

> We have noticed that one instance of weather station is down (health check). At the same time rest of instances are
returning invalid temperature. Can you take a look at that?

Unfortunately we know what's the reason, our fix was applied only to single instance. One way of solving this issue would
be to redeploy all instances of weather-station service. It's cumbersome that we need to rebuild weather-station service
each time sensor threshold changes. Moreover we would need to ensure to redeploy all instances of weather-station and
keep their configuration in sync.

The decision was made to use `consul key/value store` to keep configuration regarding sensors and use `spring-cloud-consul-config`
to periodically query key/value store for changes. By default one of the queried keys is `/config/<application-name>` and all of
its subkeys. For example value of key `/config/weather-station/weather/temperature` would be mapped to `weather.temperature`
property in weather-station application.

We should create following keys in consul key/value store using consul ui:

Key | Value
--- | ---
/config/weather-station/sensors/min-temperature | -30
/config/weather-station/sensors/max-temperature | 30

**Note**: Properties defined in consul will take precedence over those defined in file (you don't need to remove them)

**Note**: You don't need to restart weather-station for changes to apply

**Note**: Make sure not to include any leading/trailing whitespace characters in the key name

To verify that problem is fixed try changing min and max sensors temperature in consul key/value store and then verify that
health status changes accordingly.

## Weather Forecast: Load Balancing / Failover
After few weeks of running without any issues we receive yet another email, it's from product team:

> Our weather station is a huge success. We would like to expand on this idea and provide initial demo of weather forecast.
Please be aware that the volume of users might increase by few orders of magnitude since marketing campaign is in progress.
Having said that, any downtime during this period is unacceptable.

It looks like weather-forecast should become an entry point to our platform and weather-station will be it's dependency.
Moreover we need to ensure that weather-forecast can handle much more traffic than original weather-station and gracefully
deal with failures.

To do that we use `ribbon` (client side software load balancer), `hystrix` (fault tolerance library) and switch between healthy
and unhealthy instances of weather-station. Configuration can be found in `weather-forecast/src/main/resources/application.yml`

Start two instances of weather station and one instance of weather-forecast (use different port). Forecast is available on
`/forecast` endpoint of weather-forecast service.

1. verify that traffic is load balanced between weather-station instances by looking at the forecast response
2. verify that if you stop one of the instances then traffic will be forwarded only to the other instance
3. verify that if you start that instance again then traffic will be load balanced again
4. verify what happens if there are no healthy instances

## Weather Forecast: Routing / Tags
We have finally received fixed sensors that are much more reliable in extreme weather conditions. Release team wrote us
an email:

> We would like deploy fixed sensors to production but first we need to verify that new weather forecast is accurate.
Is there a way to deploy dedicated weather-forecast service that will use only weather-stations with fixed sensors? This
way we would be able to compare forecast produced based on weather stations with/without fixed sensors.

It seems that we need to be more cleaver regarding routing between services than before. It's no longer sufficient to
forward traffic to any healthy instances of weather-station, we need to be able to distinguish them based on some criteria.
After studying consul API we have notice that services can be filtered by their tag. The decision was made to keep original
service name (weather-station) but to tag instances with version number. This way we can ensure that when weather forecast
service queries consul for weather stations it will retrieve only weather stations that match expected criteria.

To register weather-station service in consul with particular tag we need to define property:

`spring.cloud.consul.discovery.tags=<tag>`.

To filter weather-stations using tags we need to define following property in weather-forecast service:

`spring.cloud.consul.discovery.server-list-query-tags.<service-name>=<tag>`

Start two instances of weather-stations (one with tag v1, other with tag v2), and one instances of weather-forecast
that should use only weather stations tagged with v2.

1. verify how weather-stations register themselves in consul, can you find information regarding tag?
2. verify that forecast service will use only v2 weather-station

## Weather Forecast: Leader Election
It was few weeks since the day that we have allowed to easily load balance/failover/route between weather stations. Development
team decided to start multiple instances of weather-forecast and share the glory of our success. The same day during prime
time we have received a call from weather forecast presenter:

> Although we are grateful for having data from multiple weather sensors we don't appreciate receiving multiple different
weather forecasts at the same time. We can present only one weather forecast at a time, please fix it and do it fast!

We know that having multiple instance of weather forecast is a good thing. At the same time returning multiple slightly
different weather forecast is confusing for presenters. We could tag one of the services and let presenters receive
weather forecast only from instance with this particular tag. However this would be a single point of failure, we need
to come up with some better solution. After hours of discussions it was decided to implement leader election algorithm.
Distributed algorithms that are based on reaching consensus are one of those things you should never implement from scratch,
fortunately consul provides foundation that makes that possible.

Please familiarize yourself with content of `weather.election` package in weather-forecast service and implement necessary
functionality in `LeaderElection.java`. Enable election by changing value of `election.enabled` in `application.yml`

When you are ready start two instances of weather-forecast services (weather-sensors are not needed) and make sure that:
1. log output shows that one instance is a leader and second instance fails to win election
2. in consul ui verify which instance is a leader by looking for value of key `service/weather-forecast/leader`
3. after stopping (simulating crash) of leader instance verify that log output and value of key changes accordingly