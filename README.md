
Contents
 
E-Commerce Microservices - Complete Training Notes	7
Project scope	7
Services	7
Core requirement of this project	7
1.	Monolithic Architecture	8
What?	8
Why use a monolith?	9
Before and after	9
Before - Monolith	9
After - Microservices	9
When to use	9
Pros	9
Cons	10
2.	Microservices	10
What?	10
Why?	10
Before and after	11
When to use	11
Pros	11
Cons	11
How to build	11
3.	Eureka Service Discovery	12
What?	12
Why?	12
Before Eureka	12
After Eureka	12
Architecture	12
When to use	13
Pros	13
Cons	13
How to configure Eureka Server	13
How to configure a client	14
Execution flow	14
Endpoint	14
4.	API Gateway	14
What?	14
Why?	14
Before	14
After	15
Architecture	15
Gateway filter flow in this project	15
Pros	16
Cons	16
Configure routes	16
Gateway endpoint	16
5.	Inter-Service Communication	17
What?	17
Why?	17
WebClient when the caller needs an immediate answer	17
Kafka when a producer can publish an event and continue	17
Before and after	17
Direct coupling	17
Discovery-based communication	17
Event-driven communication	17
Pros	18
Cons	18
6.	WebClient - Order to Inventory	18
What?	18
Why?	18
Exact business purpose in this project	18
Dependency	19
Configure a load-balanced WebClient	19
Example call	19
Flow	19
When to use WebClient	20
7.	Circuit Breaker - Inventory Failure Protection	20
What?	20
Why?	20
Before	20
After	20
Architecture	21
States	21
Dependency	22
Example configuration	22
Example usage	22
Important business rule	23
Failure demonstration	23
8.	Spring Security with JWT	23
What?	23
Why?	23
Before	23
After	23
What is inside a JWT?	24
Gateway security flow	24
Configure security dependency	24
Security rules	25
JWT execution	25
Pros	25
Cons	25
Security rules	26
9.	Spring Boot Actuator	26
What?	26
Why?	26
Before	26
After	26
Dependency	26
Configuration	27
Important endpoints	27
10.	Prometheus	27
What?	27
Why?	27
Architecture	27
Dependency	28
Configuration	28
Test	29
Pros	29
Cons	29
11.	Grafana	29
What?	29
Why?	29
Architecture	29
Run	30
Configure	30
Recommended dashboards for this project	30
Demo	30
12.	Apache Kafka	30
What?	30
Why?	30
Before	31
After	31
Architecture	31
Important Kafka terms	31
Dependency	32
Producer configuration	32
Publish event	32
Consume event	32
Execution flow	33
WebClient vs Kafka	33
Important reliability points	33
13.	ELK Stack	33
What?	33
Elasticsearch	33
Logstash	34
Kibana	34
Why?	34
Architecture	34
Docker folder structure	34
ELK Docker Compose	35
Start ELK	35
Elasticsearch endpoints	36
Logstash pipeline	36
Structured log format	36
Do not log	37
Kibana	37
ELK failure demonstration	37
14.	Dockerizing the Entire Project	37
What?	37
Why?	38
Before	38
After	38
Dockerfile for each Spring Boot service	38
Build	39
Run one service	39
Important Docker networking rule	39
Docker Compose target	39
Recommended startup order	40
15.	Swagger / OpenAPI	40
What?	40
Why?	40
Before	41
After	41
Typical endpoints	41
Dependency	41
Swagger + JWT	41
Pros	42
Cons	42
16.	Config Server Using GitHub	42
What?	42
Why?	42
Recommended separate repository	42
Config Server	43
Client import	43
Endpoint	43
Security warning	44
17.	Complete Business API Endpoints	44
Auth Service	44
Product Service	44
Inventory Service	45
Order Service	45
Notification Service	45
18.	Infrastructure Endpoint Catalogue	45
Actuator endpoints by default training ports	46
19.	Complete Order Execution Flow	46
Normal successful execution	47
20.	Complete Inventory Failure Flow	48
21.	Complete Order + Kafka Flow	49
22.	Kubernetes Service Registry	49
What?	49
Why move away from Eureka in Kubernetes?	49
Eureka model	49
Kubernetes-native model	50
Kubernetes architecture	50
Example Deployment	50
Example Kubernetes Service	51
Order to Inventory in K8s	51
What happens to Eureka?	51
Training recommendation	51
Phase 1 - Spring Cloud learning	51
Phase 2 - Kubernetes learning	52
23.	Kubernetes + Kafka + Circuit Breaker	52
24.	Step-by-Step Complete Setup Plan	52
Phase 1 - Verify existing services	52
Phase 2 - Eureka	52
Phase 3 - Config Server	53
Phase 4 - API Gateway and filters	53
Phase 5 - WebClient	53
Phase 6 - Circuit breaker	53
Phase 7 - Kafka	53
Phase 8 - Metrics	53
Phase 9 - ELK	54
Phase 10 - Docker	54
Phase 11 - Swagger	54
Phase 12 - Kubernetes	54
25.	Complete Run Checklist	54
26.	Full Project Endpoint Cheat Sheet	55
Gateway	55
Auth	56
Products	56
Inventory	56
Orders	56
Eureka	56
Config Server	56
Actuator	56
Prometheus	57
Grafana	57
Elasticsearch	57
Kibana	57
Logstash monitoring	57
Swagger	57
27.	Quick Viva / Interview Questions	57
Monolith	57
Microservices	57
Eureka	58
Gateway	58
WebClient	58
Circuit breaker	58
JWT	58
Actuator	58
Prometheus/Grafana	58
Kafka	58
ELK	59
Docker	59
Kubernetes	59
Swagger	59
28.	Final Architecture to Remember	59
Final principle	60
Reference links	61
 

E-Commerce Microservices - Complete Training Notes
Project scope
This manual is written around the existing ecommerce-microservices training project and the target architecture requested for the project.
Repository:
https://github.com/vijaymannava-design/ecommerce-microservices.git

Services
•	api-gateway
•	auth-service
•	discovery-server
•	product-service
•	inventory-service
•	order-service
•	notification-service

Core requirement of this project
The most important business flow is:
Client
| v
API Gateway
| v
Order Service
|
| WebClient v
Inventory Service
The Inventory Service is protected by a circuit breaker in Order Service:
Order Service
| v
Circuit  Breaker
| v
WebClient
|
X---- Inventory Service DOWN
 


| v
Fallback / controlled failure
Kafka is used independently for asynchronous notification:
Order Service
|
| publish OrderPlaced / OrderCreated event v
Kafka
| v
Notification Service
Observability is split into two pipelines:
Metrics:	Spring Boot -> Actuator -> Prometheus -> Grafana
Logs:	Spring Boot -> structured logs -> Logstash -> Elasticsearch -> Kibana
The local training setup uses Eureka for discovery and Docker for infrastruc-ture. Kubernetes is explained later as a second deployment model and uses Kubernetes Service/DNS for native discovery.


1.	Monolithic Architecture
What?
A monolithic application packages major business functions into one deployable application.
For an e-commerce application, authentication, products, inventory, orders and notifications may all live in one codebase and one deployable JAR.
E-COMMERCE APPLICATION
+	+
|	|
|	Authentication	|
|	Product Management	|
|	Inventory	|
|	Order Management	|
|	Notification	|
|	|
|	Controllers -> Services -> Repositories	|
|	|
+-----------------------------+	+
|
 


v Database

Why use a monolith?
A monolith is often easier when the system is small, the team is small, and fast initial development matters more than independent deployment and scaling.
Before and after Before - Monolith Client
|
v
Single E-Commerce Application
+-- Auth
+-- Product
+-- Inventory
+-- Order
+-- Notification
| v
Database

After - Microservices
Client
| v
API Gateway
+-- Auth Service
+-- Product Service
+-- Order Service
+-- Inventory Service
+-- Notification Service

When to use
Use a monolith for smaller applications, prototypes, simple internal systems, or when the team does not yet need independent service deployment.

Pros
•	Simple deployment
•	Simple local development
•	Easy in-process method calls
 



•	Centralized transactions can be easier

Cons
•	Large codebase as the system grows
•	Diﬀicult to scale only one business capability
•	One deployment unit creates coupling
•	A problem in one module can affect the complete application


2.	Microservices
What?
Microservices divide a system into independently deployable services organized around business capabilities.
E-COMMERCE
| v
+	+
| API Gateway |
+------+	+
|
+-----------+	+
|	|	|
v	v	v
Product	Order		Auth Service	Service	Service
|
| WebClient v
Inventory Service
|
| Kafka event v
Kafka
| v
Notification Service
Why?
Different services can be developed, tested, deployed and scaled separately.
 



Example:
Order Service	-> high traffic during checkout Product Service	-> read-heavy
Notification	-> event-driven/background work

Before and after

Concern	Monolith	Microservices
Deployment	One unit	Multiple units
Communication	Method calls	Network calls/events
Scaling	Whole application	Individual service
Failure boundary	Large	Smaller Ownership	Centralized	Service-oriented

When to use
Use microservices when independent deployment, independent scaling, team ownership, or fault isolation provides enough benefit to justify the additional operational complexity.

Pros
•	Independent deployment
•	Independent scaling
•	Smaller codebases
•	Better fault isolation
•	Technology and team autonomy

Cons
•	Network latency
•	Distributed failures
•	More deployment and monitoring work
•	Data consistency becomes harder
•	More infrastructure

How to build
1.	Identify business capabilities.
2.	Split them into service boundaries.
3.	Create separate Spring Boot projects.
4.	Give each service its own API and data ownership.
5.	Add discovery and communication.
6.	Add gateway, security and resilience.
 



7.	Add observability.
8.	Containerize and deploy.


3.	Eureka Service Discovery
What?
Eureka is a service registry used by Spring Cloud applications. Services register themselves and other services can discover available instances by logical service name.

Why?
Without discovery, a service may have to know a fixed host and port.

Before Eureka
Order -> http://10.0.0.25:8083
If Inventory moves, configuration must change.

After Eureka
Order
|
| find INVENTORY-SERVICE
v Eureka
| v
Inventory instance

Architecture
+	+
| Eureka Server	|
|	:8761	|
+-------+	+
|
+----------------+	+
|	|	|
v	v	v
ORDER-SERVICE	INVENTORY-SERVICE  PRODUCT-SERVICE
 


When to use
Eureka is very useful for learning Spring Cloud service registration/discovery without Kubernetes. It is also appropriate for systems that intentionally use Spring Cloud discovery.

Pros
•	Easy to teach and understand
•	Logical service names
•	Dynamic instance registration
•	Works well with Spring Cloud

Cons
•	Additional infrastructure
•	Operational dependency
•	Often unnecessary inside a Kubernetes-native environment

How to configure Eureka Server
The project already contains discovery-server. Typical configuration:
server: port: 8761
spring: application:
name: discovery-server
eureka: client:
register-with-eureka: false fetch-registry: false
Main class:
@SpringBootApplication @EnableEurekaServer
public class DiscoveryServerApplication {
public static void main(String[] args) { SpringApplication.run(DiscoveryServerApplication.class, args);
}
}
 


How to configure a client
Example for Inventory:
spring: application:
name: inventory-service
eureka: client:
service-url:
defaultZone: http://localhost:8761/eureka/
Use the corresponding service name in Order and Product.

Execution flow
1.	Start Eureka.
2.	Start Inventory.
3.	Inventory registers itself.
4.	Start Order.
5.	Order discovers Inventory by service name.
6.	Order calls the discovered instance.

Endpoint
Eureka UI:
http://localhost:8761


4.	API Gateway
What?
API Gateway is the single external entry point. Spring Cloud Gateway supports routing and cross-cutting concerns such as filters, security and resilience.

Why?
Without a gateway, a client must know every service address.

Before
Client -> Product :8082 Client -> Order	:8084 Client -> Inventory :8083 Client -> Auth		8081
 


After
Client
| v
API Gateway :8080
+-- /api/auth/**
+-- /api/products/**
+-- /api/inventory/**
+-- /api/orders/**

Architecture
Client
| v
+	+
| API Gateway :8080	|
|	|
| Routing	|
| Filters	|
| JWT Authentication	|
| Circuit Breaker integration |
+---------------+	+
|
+---------+	+
|	|	|
v	v	v
Auth	Product	Order

Gateway filter flow in this project
The existing Gateway contains an authentication filter. Its purpose is to inspect the Authorization header before the request is forwarded.
Request
| v
AuthenticationFilter
|
+-- header missing ----> 401
|
+-- not Bearer ---------> 401
|
+-- invalid JWT --------> 401
|
+-- valid JWT ----------> continue
 


Pros
•	Single external entry point
•	Centralized routing
•	Centralized authentication/filtering
•	Can add rate limiting, headers and resilience

Cons
•	Additional network hop
•	Gateway becomes important infrastructure
•	Poorly designed gateway can become a bottleneck

Configure routes
A discovery-aware route typically uses:
spring: cloud:
gateway: routes:
-	id: product-service
uri: lb://PRODUCT-SERVICE
predicates:
-	Path=/api/products/**
-	id: inventory-service
uri: lb://INVENTORY-SERVICE
predicates:
-	Path=/api/inventory/**
-	id: order-service
uri: lb://ORDER-SERVICE predicates:
-	Path=/api/orders/**
-	id: auth-service
uri: lb://AUTH-SERVICE predicates:
-	Path=/api/auth/**
The exact route configuration must remain consistent with the current Gateway files in the repository.

Gateway endpoint
http://localhost:8080
 


The client normally uses Gateway endpoints instead of directly using service ports.


5.	Inter-Service Communication
What?
Communication between microservices is called inter-service communication. Your project deliberately uses two styles:
Synchronous	-> WebClient Asynchronous -> Kafka

Why?
Different business situations need different communication models.

WebClient when the caller needs an immediate answer Order needs inventory information before accepting an order. Order -> Inventory -> response -> Order continues
Kafka when a producer can publish an event and continue
Order creation can publish an event for notification.
Order -> Kafka -> Notification

Before and after
Direct coupling
Order -> fixed Inventory IP

Discovery-based communication
Order -> service name -> discovery/load balancing -> Inventory

Event-driven communication
Order -> Kafka -> Notification
 


Pros
•	Services remain separated
•	Each service owns its own business logic
•	Events reduce direct coupling

Cons
•	Network failures
•	Serialization/deserialization
•	Timeouts
•	Distributed debugging
•	Event ordering and duplicate processing


6.	WebClient - Order to Inventory
What?
Spring WebClient is an HTTP client used to call another service.

Why?
Order must synchronously verify inventory.
Order Service
|
| WebClient v
Inventory Service
| v
Inventory result

Exact business purpose in this project
The current Inventory controller exposes:
GET /api/inventory/{skuCode}
and the current Order controller exposes:
POST /api/orders
The intended execution is:
POST /api/orders
| v
 


Order Service
|
| WebClient v
GET /api/inventory/{skuCode}
| v
Inventory Service

Dependency
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-webflux</artifactId>
</dependency>

Configure a load-balanced WebClient
@Bean @LoadBalanced
public WebClient.Builder webClientBuilder() {
return WebClient.builder();
}

Example call
public Mono<InventoryResponse> checkInventory(String skuCode) {
return webClientBuilder.build()
.get()
.uri("http://inventory-service/api/inventory/{skuCode}", skuCode)
.retrieve()
.bodyToMono(InventoryResponse.class);
}
If the surrounding code is blocking, .block() may be used carefully, but a fully reactive application should normally propagate the Mono/Flux rather than block.

Flow
1.	Client calls Gateway.
2.	Gateway validates JWT.
3.	Gateway forwards to Order.
4.	Order starts order processing.
5.	Order calls Inventory using WebClient.
6.	Inventory returns availability.
7.	Order continues only when business conditions are satisfied.
 


When to use WebClient
Use it for request/response interactions where the caller requires information immediately.
Do not use it when the caller only needs to publish an event and does not need an immediate response.


7.	Circuit Breaker - Inventory Failure Protection
What?
A circuit breaker detects repeated downstream failures and stops sending re-quests to a failing service for a period.

Why?
If Inventory is down, repeated network calls can cause:
•	long response times
•	connection exhaustion
•	thread/resource pressure
•	cascading failures

Before
Order request
| v
Inventory call
|
X  Inventory  down
| v
Timeout
| v
More requests
| v
More timeouts

After
Order
|
 


v
Circuit  Breaker
| v
WebClient
|
X  Inventory  down
| v
Failures  recorded
| v
Circuit  OPEN
| v
Fail fast / fallback

Architecture
Order Service
| v
+	+
|  Circuit  Breaker	|
+--------+	+
| v
WebClient
| v
Inventory Service

States
CLOSED
|
| failure threshold exceeded v
OPEN
|
| wait duration v
HALF-OPEN
|	|
success	failure
|	|
 


v	v
CLOSED	OPEN

Dependency
<dependency>
<groupId>org.springframework.cloud</groupId>
<artifactId>spring-cloud-starter-circuitbreaker-resilience4j</artifactId>
</dependency>

Example configuration
resilience4j: circuitbreaker:
instances: inventoryService:
failure-rate-threshold:  50
minimum-number-of-calls: 5
sliding-window-size: 10
wait-duration-in-open-state: 10s

Example usage
@CircuitBreaker(
name = "inventoryService", fallbackMethod = "inventoryFallback"
)
public InventoryResponse checkInventory(String skuCode) {
return webClientBuilder.build()
.get()
.uri("http://inventory-service/api/inventory/{skuCode}", skuCode)
.retrieve()
.bodyToMono(InventoryResponse.class)
.block();
}
public InventoryResponse inventoryFallback( String skuCode, Throwable ex) {
throw new InventoryUnavailableException( "Inventory service is currently unavailable"
);
}
 


Important business rule
Do not make the fallback say “stock available” when Inventory is down. A fall-back must preserve business correctness, for example by returning a controlled error such as:
Inventory service temporarily unavailable. Please retry.

Failure demonstration
1.	Start Inventory.
2.	Create an order successfully.
3.	Stop Inventory.
4.	Send several order requests.
5.	Observe failures.
6.	Observe the circuit opening.
7.	Send another request and observe fail-fast/fallback behavior.
8.	Restart Inventory.
9.	Observe HALF-OPEN and recovery to CLOSED.


8.	Spring Security with JWT
What?
JWT (JSON Web Token) is a signed token commonly used to carry authenti-cated identity/claims between a client and protected services.

Why?
The system must determine who is calling protected APIs and whether the caller is allowed to access them.

Before
Client -> /api/orders
No strong authentication boundary.

After
Client
|
| username/password v
Auth Service
|
 


| JWT
v Client
|
| Authorization: Bearer <JWT> v
API Gateway
|
| validate token v
Protected Service

What is inside a JWT?
A typical JWT contains header, payload and signature. The payload can contain claims such as subject, issuer, expiration and roles/scopes.
Do not put secrets or sensitive data into claims simply because the payload is encoded.

Gateway security flow
Request
| v
Authorization header
|
+-- missing -> 401
| v
Bearer token
| v
JWT validation
|
+-- invalid/expired -> 401
| v
Forward request

Configure security dependency
Depending on the service’s role, use Spring Security and JWT resource-server/Jose support.
Example Maven dependencies:
 



<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
</dependency>

Security rules
A common training rule is:
Public:
POST /api/auth/register POST /api/auth/token
Protected:
/api/products/**
/api/inventory/**
/api/orders/**
The exact authorization rules should match the security configuration in the repository.

JWT execution
1.	Register a user.
2.	Request a token from Auth Service.
3.	Copy the JWT.
4.	Add Authorization: Bearer <token>.
5.	Call a protected endpoint through Gateway.
6.	Gateway filter validates the token.
7.	Valid token -> route is allowed.
8.	Invalid/missing token -> 401.

Pros
•	Stateless request authentication
•	Easy to pass through an API gateway
•	Works well across distributed services

Cons
•	Token revocation needs additional design
•	Secret/key rotation needs planning
 



•	Large tokens add request overhead
•	Bad claim/security design can create authorization problems

Security rules
Never commit:
JWT secret private key DB password API token refresh token
Use environment variables or a secret manager for real deployments.


9.	Spring Boot Actuator
What?
Actuator exposes operational endpoints such as health and metrics.

Why?
Microservices must be observable and discoverable as healthy/unhealthy.

Before
Application

After
Application
+-- /actuator/health
+-- /actuator/info
+-- /actuator/metrics
+-- /actuator/prometheus

Dependency
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
 


Configuration
management: endpoints:
web:
exposure:
include: health,info,metrics,prometheus

Important endpoints
GET /actuator/health GET /actuator/info GET /actuator/metrics
GET /actuator/prometheus
Example for Order:
http://localhost:8084/actuator/health http://localhost:8084/actuator/prometheus
Only expose management endpoints that are appropriate for the environment.


10.	Prometheus
What?
Prometheus collects and stores time-series metrics.

Why?
It can answer questions such as:
•	Is the service up?
•	How many requests are arriving?
•	How many HTTP errors occur?
•	How is JVM memory behaving?
•	Are circuit breaker calls failing?

Architecture
Spring Boot Service
| v
Actuator / Prometheus endpoint
|
| scrape
 


v Prometheus :9090

Dependency
<dependency>
<groupId>io.micrometer</groupId>
<artifactId>micrometer-registry-prometheus</artifactId>
</dependency>

Configuration
Actuator must expose the Prometheus endpoint. Prometheus configuration example:
global: scrape_interval: 15s
scrape_configs:
-	job_name: order-service metrics_path: /actuator/prometheus static_configs:
-	targets: ['host.docker.internal:8084']
-	job_name: inventory-service metrics_path: /actuator/prometheus static_configs:
-	targets: ['host.docker.internal:8083']
-	job_name: product-service metrics_path: /actuator/prometheus static_configs:
-	targets: ['host.docker.internal:8082']
-	job_name: auth-service metrics_path: /actuator/prometheus static_configs:
-	targets: ['host.docker.internal:8081']
-	job_name: api-gateway metrics_path: /actuator/prometheus static_configs:
-	targets: ['host.docker.internal:8080']
The host name must be changed when Prometheus and services use a different network arrangement.
 


Test
Prometheus: http://localhost:9090  Try:
up
and inspect each target in Status -> Targets.

Pros
•	Strong time-series monitoring ecosystem
•	PromQL is powerful
•	Works well with Spring Boot/Micrometer

Cons
•	Does not replace centralized logging
•	Long-term metric retention needs planning
•	Dashboard design needs care


11.	Grafana
What?
Grafana visualizes data from systems such as Prometheus.

Why?
Humans understand dashboards faster than raw metric text.

Architecture
Services
| v
Actuator
| v
Prometheus
| v
Grafana
 


Run
http://localhost:3000

Configure
1.	Open Grafana.
2.	Add a Prometheus data source.
3.	For a local host setup use http://localhost:9090.
4.	For container-to-container access use the Prometheus service name, for example http://prometheus:9090.
5.	Test the data source.
6.	Create dashboards.

Recommended dashboards for this project
Service availability Request rate
4xx and 5xx rate Response time JVM memory
JVM CPU
Circuit breaker state
Kafka consumer/producer activity

Demo
Stop Inventory and create orders. Then compare:
Prometheus -> raw failure metrics Grafana	->  visual  failure  trend


12.	Apache Kafka
What?
Kafka is an event-streaming platform used in this project for asynchronous communication.

Why?
Order does not need to synchronously call Notification Service. It can publish an event.
 



Order Service
|
| publish v
Kafka topic
|
| consume v
Notification Service

Before
Order -> HTTP -> Notification -> response -> Order
This creates direct runtime coupling.

After
Order -> Kafka event -> Notification

Architecture
+	+
|	Kafka Broker	|
| order-created	|
|	topic	|
+--------+	+
^
| producer
|
Order Service
|
+	+
| consumer
| v
Notification Service
Important Kafka terms

Term	Meaning

Broker	Kafka server
Topic	Named event stream
 



 
Term	Meaning

Partition	Ordered sequence inside a topic
Offset	Position of a record
Producer	Publishes records
Consumer	Reads records Consumer Group	Coordinates consumers

Dependency
<dependency>
<groupId>org.springframework.kafka</groupId>
<artifactId>spring-kafka</artifactId>
</dependency>

Producer configuration
spring: kafka:
bootstrap-servers: localhost:9092 producer:
key-serializer:  org.apache.kafka.common.serialization.StringSerializer
value-serializer:  org.springframework.kafka.support.serializer.JsonSerializer

Publish event
Example:
kafkaTemplate.send( "order-created", orderPlacedEvent
);

Consume event
@KafkaListener(
topics = "order-created", groupId = "notification-service"
)
public void consume(OrderPlacedEvent event) {
// process notification
}
The current repository already contains OrderPlacedEvent in Notification Ser-vice, matching this intended architecture.
 


Execution flow
1.	User sends order.
2.	Order Service calls Inventory synchronously.
3.	Inventory confirms stock.
4.	Order is created.
5.	Order Service publishes event.
6.	Kafka stores the event.
7.	Notification Service consumes the event.
8.	Notification processing occurs independently.

WebClient vs Kafka

Need	Technology

Immediate answer	WebClient
Inventory availability	WebClient
Event notification	Kafka Background/asynchronous action	Kafka Request/response	WebClient
Something happened	Kafka


Important reliability points
•	Design consumers to handle duplicates safely.
•	Consider idempotency.
•	Decide how failures are retried.
•	Consider dead-letter handling.
•	Do not assume an event is processed exactly once simply because a busi-ness action happened once.


13.	ELK Stack
What?
ELK means:
E = Elasticsearch L = Logstash
K = Kibana

Elasticsearch
Stores and searches log documents.
 



Logstash
Receives, transforms and routes logs.

Kibana
Provides search and visualization for Elasticsearch data.

Why?
Without centralized logging:
Order terminal Inventory terminal Gateway terminal Notification terminal
With ELK:
All services
| v
Logstash
| v
Elasticsearch
| v
Kibana

Architecture
Product Service --+ Order Service --+ Inventory Service -+
Auth Service	--+--> Logstash --> Elasticsearch --> Kibana Gateway	--+
Notification	--+

Docker folder structure
elk/
+-- docker-compose.yml
+-- logstash/
+-- pipeline/
+-- logstash.conf
 


ELK Docker Compose
Use one version for all Elastic components.
services: elasticsearch:
image: docker.elastic.co/elasticsearch/elasticsearch:<VERSION> container_name: elasticsearch
environment:
-	discovery.type=single-node
-	xpack.security.enabled=false ports:
- "9200:9200"
logstash:
image: docker.elastic.co/logstash/logstash:<VERSION> container_name: logstash
depends_on:
-	elasticsearch ports:
- "5000:5000"
- "9600:9600"
volumes:
-	./logstash/pipeline:/usr/share/logstash/pipeline
kibana:
image: docker.elastic.co/kibana/kibana:<VERSION> container_name: kibana
depends_on:
-	elasticsearch environment:
-	ELASTICSEARCH_HOSTS=http://elasticsearch:9200 ports:
- "5601:5601"
The unsecured setting is for local training only.

Start ELK
From the elk directory: docker compose up -d Check:
docker compose ps
 


Elasticsearch endpoints
http://localhost:9200 http://localhost:9200/_cluster/health http://localhost:9200/_cat/indices?v

Logstash pipeline
Example:
input { tcp {
port => 5000
codec => json_lines
}
}
filter { date {
match => ["timestamp", "ISO8601"] target => "@timestamp"
}
}
output { elasticsearch {
hosts => ["http://elasticsearch:9200"] index => "ecommerce-logs-%{+YYYY.MM.dd}"
}
stdout {
codec => rubydebug
}
}
Structured log format
Recommended event:
{
"timestamp": "2026-09-08T20:20:00Z",
"level": "INFO",
"service": "order-service", "environment": "local", "traceId": "abc123",
"requestId": "req-1001", "httpMethod": "POST",
 


"path": "/api/orders", "status": 201,
"durationMs": 125, "message": "Order created"
}
Do not log
passwords JWTs
refresh tokens credit card data private keys
API secrets

Kibana
Open:
http://localhost:5601
Create data view:
ecommerce-logs-*
Useful searches:
service : "order-service" level : "ERROR"
service : "inventory-service" AND level : "ERROR"

ELK failure demonstration
1.	Stop Inventory.
2.	Send an order request.
3.	Order fails due to downstream unavailability.
4.	Structured error log is emitted.
5.	Logstash receives it.
6.	Elasticsearch stores it.
7.	Kibana can search it.


14.	Dockerizing the Entire Project
What?
Docker packages an application and its runtime into a container image.
 


Why?
It provides repeatable local environments and prepares the services for later Kubernetes deployment.

Before
Install Java Install  Kafka
Install Elasticsearch Install Logstash Install Kibana Install Prometheus Install Grafana
Run every application manually

After
Docker / Docker Compose
+-- application containers
+-- Kafka
+-- ELK
+-- Prometheus
+-- Grafana

Dockerfile for each Spring Boot service
Example Order Service:
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/order-service.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]
Repeat for:
api-gateway config-server discovery-server auth-service product-service inventory-service
 


order-service notification-service
Adjust the JAR name and port per service.

Build
mvn clean package
Then:
docker build -t order-service:1.0 .

Run one service
docker run -p 8084:8084 order-service:1.0

Important Docker networking rule
Inside a container:
localhost
means the same container.
It does not mean another service. So do not configure:
http://localhost:8083
inside Order just because Inventory is exposed on host port 8083.
For container-to-container communication use service names, for example:
http://inventory-service:8083
or use Eureka/load balancing where appropriate.

Docker Compose target
The final Compose environment can contain:
api-gateway config-server discovery-server auth-service product-service inventory-service order-service
notification-service kafka
prometheus
 


grafana elasticsearch logstash kibana
This allows:
docker compose up -d
and:
docker compose down

Recommended startup order
1.	Docker
2.	Config Server
3.	Eureka
4.	Kafka
5.	Elasticsearch
6.	Logstash
7.	Kibana
8.	Prometheus
9.	Grafana
10.	Auth
11.	Product
12.	Inventory
13.	Order
14.	Notification
15.	API Gateway
Infrastructure may start concurrently, but the dependency order is useful for training and debugging.


15.	Swagger / OpenAPI
What?
OpenAPI describes REST APIs and Swagger UI provides an interactive browser interface for those APIs.

Why?
Developers can discover request fields, responses and security requirements with-out reading controller code manually.
 


Before
Developer -> Postman -> manually build request

After
Browser -> Swagger UI -> Try it out -> API

Typical endpoints
/swagger-ui/index.html
/v3/api-docs
Example:
http://localhost:8084/swagger-ui/index.html http://localhost:8084/v3/api-docs
The exact port and path depend on the service and springdoc setup.

Dependency
For Spring MVC:
<dependency>
<groupId>org.springdoc</groupId>
<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
<version>${springdoc.version}</version>
</dependency>
For WebFlux, use the matching WebFlux starter.

Swagger + JWT
Define a Bearer authentication scheme so Swagger UI can send:
Authorization:  Bearer  <JWT>
Flow:
Swagger UI
|
| Authorize with JWT v
API Gateway
| v
Protected service
 


Pros
•	Interactive API documentation
•	Easy developer onboarding
•	Easy request/response discovery
•	Useful for demos

Cons
•	Documentation can become stale
•	Should not expose sensitive internal endpoints unintentionally
•	Security configuration still needs to be correct


16.	Config Server Using GitHub
What?
Spring Cloud Config Server centralizes application configuration and can load it from a Git repository.

Why?
Instead of putting environment configuration into every service separately, use:
GitHub config repository
| v
Config Server
|
+----+	+
|	|	| Order Inv Product

Recommended separate repository
ecommerce-config/
+-- application.yml
+-- api-gateway.yml
+-- auth-service.yml
+-- product-service.yml
+-- inventory-service.yml
+-- order-service.yml
+-- notification-service.yml
 


Config Server
server: port: 8888
spring: application:
name: config-server cloud:
config: server:
git:
uri: https://github.com/<username>/ecommerce-config.git default-label: main
Main class:
@SpringBootApplication @EnableConfigServer
public class ConfigServerApplication {
public static void main(String[] args) { SpringApplication.run(ConfigServerApplication.class, args);
}
}
Client import
A service can import Config Server using:
spring: application:
name: order-service config:
import: optional:configserver:http://localhost:8888

Endpoint
http://localhost:8888/order-service/default
Other examples:
http://localhost:8888/inventory-service/default http://localhost:8888/product-service/default http://localhost:8888/api-gateway/default
 


Security warning
Do not place secrets in a public GitHub configuration repository. Use environ-ment variables or a proper secret-management system.


17.	Complete Business API Endpoints
These endpoints are documented from the current repository controllers where they were visible in the project.

Auth Service
Base path:
/api/auth
Current endpoints:
POST /api/auth/register POST /api/auth/token
Gateway form:
POST http://localhost:8080/api/auth/register POST http://localhost:8080/api/auth/token

Product Service
Base path:
/api/products
Current endpoints:
POST /api/products GET   /api/products
GET /api/products/{id} PUT /api/products/{id} DELETE  /api/products/{id}
Gateway form:
POST http://localhost:8080/api/products GET    http://localhost:8080/api/products
GET  http://localhost:8080/api/products/{id} PUT http://localhost:8080/api/products/{id} DELETE   http://localhost:8080/api/products/{id}
 


Inventory Service
Base path:
/api/inventory
Current endpoints:
GET	/api/inventory/{skuCode} POST /api/inventory
Important Order -> Inventory call:
GET /api/inventory/{skuCode}
Gateway form:
GET http://localhost:8080/api/inventory/{skuCode} POST http://localhost:8080/api/inventory

Order Service
Base path:
/api/orders
Current endpoint:
POST /api/orders
Gateway form:
POST  http://localhost:8080/api/orders

Notification Service
The repository contains the event model used for order notification. Notification is intended as a Kafka consumer rather than as an externally called REST service.
Event flow:
Order Service -> Kafka -> Notification Service
Do not invent REST endpoints for Notification unless a controller is actually added.


18.	Infrastructure Endpoint Catalogue
 



 
Component	URL

API Gateway	http://localhost:8080
Discovery Server	http://localhost:8761
Config Server	http://localhost:8888
Prometheus	http://localhost:9090
Grafana	http://localhost:3000
Elasticsearch	http://localhost:9200
Kibana	http://localhost:5601 Logstash monitoring API http://localhost:9600 Kafka broker	localhost:9092

Actuator endpoints by default training ports
Gateway:	http://localhost:8080/actuator/health Auth:	http://localhost:8081/actuator/health Product:	http://localhost:8082/actuator/health Inventory:	http://localhost:8083/actuator/health Order:	http://localhost:8084/actuator/health Notification:   http://localhost:8085/actuator/health
Prometheus endpoint:
http://localhost:<service-port>/actuator/prometheus
These port values are the recommended training map. Keep any existing project ports if they already differ.


19.	Complete Order Execution Flow
This is the most important flow for the project.
CLIENT
|
| POST /api/orders
| Authorization: Bearer JWT v
+	+
| API Gateway	|
| JWT Filter	|
+-------+	+
| v
ORDER SERVICE
|
 


v CIRCUIT BREAKER
| v
WebClient
| v
INVENTORY SERVICE
| v
Stock result
| v
Order logic
|
+----------+	+
|	|
v	v
Database	Kafka
| v
NOTIFICATION SERVICE
Normal successful execution
1.	Client logs in or obtains a token.
2.	Client sends POST /api/orders through Gateway.
3.	Gateway authentication filter checks the bearer token.
4.	Gateway routes to Order Service.
5.	Order Service starts order processing.
6.	Circuit breaker wraps the Inventory call.
7.	WebClient calls Inventory.
8.	Inventory checks stock.
9.	Inventory responds.
10.	Order continues and is saved.
11.	Order Service publishes an order event to Kafka.
12.	Notification Service consumes the event.
13.	Metrics are scraped by Prometheus.
14.	Logs are shipped to Logstash.
15.	Grafana displays metrics.
16.	Kibana displays searchable logs.

 

20.	Complete Inventory Failure Flow
Client
| v
API Gateway
| v
Order Service
| v
Circuit  Breaker
| v
WebClient
| X
Inventory  DOWN
| v
Failure recorded
| v
Circuit  OPEN
| v
Fallback / error response
Observability at the same time:
Failure
+--> Actuator/Micrometer -> Prometheus -> Grafana
|
+--> Application log -> Logstash -> Elasticsearch -> Kibana
When Inventory recovers:
OPEN
|
| after wait duration v
HALF-OPEN
|
| successful test request v
CLOSED

 

21.	Complete Order + Kafka Flow
POST /api/orders
| v
Order Service
|
| WebClient v
Inventory Service
|
| stock available v
Order Service
|
| publish event v
Kafka topic: order-created
|
| consume v
Notification Service
The important teaching point is that Inventory is synchronous while Noti-fication is asynchronous.


22.	Kubernetes Service Registry
What?
Kubernetes provides native service discovery through Kubernetes Services and cluster DNS. A Service gives Pods a stable network endpoint, and DNS resolves the service name to that endpoint.

Why move away from Eureka in Kubernetes?
Kubernetes already solves service discovery at the platform level.

Eureka model
Order -> Eureka -> Inventory instance
 


Kubernetes-native model
Order Pod
| v
inventory-service
| v
Kubernetes Service
| v
Inventory  Pod

Kubernetes architecture
KUBERNETES CLUSTER
+	+
|	|
|	API Gateway Deployment	|
|	|	|
|	v	|
|	order-service	|
|	|	|
|	v	|
|	inventory-service	|
|	|	|
|	v	|
|	Inventory Pods	|
|	|
+	+

Example Deployment
apiVersion: apps/v1 kind: Deployment metadata:
name: inventory-service spec:
replicas: 2 selector:
matchLabels:
app: inventory-service template:
metadata: labels:
app: inventory-service
 


spec:
containers:
- name: inventory-service
image: yourdockerhub/inventory-service:1.0 ports:
- containerPort: 8083

Example Kubernetes Service
apiVersion: v1 kind: Service metadata:
name: inventory-service spec:
selector:
app: inventory-service ports:
- port: 8083
targetPort: 8083 type: ClusterIP

Order to Inventory in K8s
Order Pod
|
| http://inventory-service:8083 v
Kubernetes Service
| v
Inventory  Pod

What happens to Eureka?
For Kubernetes-native discovery, Eureka can be removed from the communica-tion path.
You can still deploy Eureka, but it becomes redundant for service discovery if every service already uses Kubernetes Service/DNS.

Training recommendation
Teach two phases:

Phase 1 - Spring Cloud learning
Eureka -> WebClient -> Order -> Inventory
 


Phase 2 - Kubernetes learning
Kubernetes Service/DNS -> WebClient -> Order -> Inventory
This shows exactly what changes when moving from application-level discovery to platform-level discovery.


23.	Kubernetes + Kafka + Circuit Breaker
Kubernetes changes deployment/discovery, but the business design remains:
Order
|
+--> Kubernetes Service -> Inventory
|
+--> Kafka -> Notification
|
+--> Circuit Breaker around Inventory call JWT and Gateway remain part of the application design. Metrics and logs can also be deployed in Kubernetes:
Services -> Actuator -> Prometheus -> Grafana Services -> logs -> Logstash/Elasticsearch -> Kibana


24.	Step-by-Step Complete Setup Plan
Use this sequence to build the project from the beginning.

Phase 1 - Verify existing services
1.	Clone the repository.
2.	Build each Spring Boot module.
3.	Run the existing services one by one.
4.	Verify controllers and ports.
5.	Verify the Order and Inventory relationship.

Phase 2 - Eureka
6.	Start Discovery Server on 8761.
7.	Register Gateway and microservices.
8.	Verify services appear in Eureka.
 


Phase 3 - Config Server
9.	Create a GitHub config repository.
10.	Add configuration files.
11.	Create Config Server on 8888.
12.	Connect services  using spring.config.import.
13.	Test /order-service/default and other configuration endpoints.

Phase 4 - API Gateway and filters
14.	Configure Gateway routes.
15.	Add/verify the authentication filter.
16.	Verify public authentication endpoints.
17.	Verify protected endpoints return 401 without JWT.
18.	Verify valid JWT requests are forwarded.

Phase 5 - WebClient
19.	Add WebClient to Order.
20.	Resolve Inventory by service name.
21.	Call  GET  /api/inventory/{skuCode}.
22.	Test successful inventory lookup.

Phase 6 - Circuit breaker
23.	Add Resilience4j.
24.	Configure the inventoryService breaker.
25.	Wrap the Order -> Inventory call.
26.	Add controlled fallback.
27.	Stop Inventory and test failures.
28.	Observe OPEN and recovery states.

Phase 7 - Kafka
29.	Start Kafka.
30.	Create the order event topic.
31.	Add Kafka producer to Order.
32.	Publish order event after successful order creation.
33.	Add Kafka consumer to Notification.
34.	Verify notification event processing.

Phase 8 - Metrics
35.	Add Actuator to services.
36.	Expose /actuator/prometheus.
37.	Start Prometheus.
38.	Configure scrape targets.
 



39.	Start Grafana.
40.	Connect Grafana to Prometheus.
41.	Create dashboards.

Phase 9 - ELK
42.	Start Elasticsearch.
43.	Start Logstash.
44.	Start Kibana.
45.	Add structured logging.
46.	Send JSON logs to Logstash.
47.	Store logs in Elasticsearch.
48.	Create Kibana data view.
49.	Search order failures.

Phase 10 - Docker
50.	Add Dockerfile to every service.
51.	Build service images.
52.	Create Docker Compose.
53.	Replace container localhost references with service names or discovery-aware configuration.
54.	Start the complete environment with Compose.

Phase 11 - Swagger
55.	Add springdoc.
56.	Verify Swagger UI.
57.	Document JWT security.
58.	Test APIs through Swagger.

Phase 12 - Kubernetes
59.	Build/push Docker images.
60.	Create Deployments.
61.	Create Kubernetes Services.
62.	Use Kubernetes service names instead of Eureka for native discovery.
63.	Deploy Gateway.
64.	Deploy application services.
65.	Deploy Kafka and observability components as needed.


25.	Complete Run Checklist
Before starting:
 



[ ] Docker running [ ] Kafka available
[ ] GitHub config repository available [ ] Java/Maven configured
Start infrastructure:
[ ] Config Server [ ] Eureka
[ ] Kafka
[ ] Elasticsearch [ ] Logstash
[ ] Kibana
[ ] Prometheus [ ] Grafana
Start application services:
[ ] Auth
[ ] Product
[ ] Inventory [ ] Order
[ ] Notification [ ] API Gateway
Verify:
[ ] Eureka shows services
[ ] Config Server returns service configuration [ ] Auth token can be generated
[ ] Gateway rejects missing JWT [ ] Product API works
[ ] Order calls Inventory
[ ] Inventory failure opens circuit [ ] Order event reaches Kafka
[ ] Notification consumes event [ ] Prometheus targets are UP
[ ] Grafana displays metrics [ ] Elasticsearch stores logs [ ] Kibana searches logs
[ ] Swagger UI loads


26.	Full Project Endpoint Cheat Sheet
Gateway
http://localhost:8080
 


Auth
POST http://localhost:8080/api/auth/register POST http://localhost:8080/api/auth/token

Products
POST http://localhost:8080/api/products GET    http://localhost:8080/api/products
GET  http://localhost:8080/api/products/{id} PUT http://localhost:8080/api/products/{id} DELETE   http://localhost:8080/api/products/{id}

Inventory
GET	http://localhost:8080/api/inventory/{skuCode}  POST http://localhost:8080/api/inventory
Internal Order -> Inventory example:
GET  http://inventory-service/api/inventory/{skuCode}
or in a Kubernetes cluster:
GET   http://inventory-service:8083/api/inventory/{skuCode}
depending on whether the application is using Eureka/load-balancer naming or direct Kubernetes Service DNS.

Orders
POST  http://localhost:8080/api/orders

Eureka
http://localhost:8761

Config Server
http://localhost:8888/order-service/default http://localhost:8888/inventory-service/default http://localhost:8888/product-service/default http://localhost:8888/api-gateway/default

Actuator
http://localhost:8080/actuator/health http://localhost:8081/actuator/health http://localhost:8082/actuator/health
 


http://localhost:8083/actuator/health http://localhost:8084/actuator/health http://localhost:8085/actuator/health

Prometheus
http://localhost:9090

Grafana
http://localhost:3000

Elasticsearch
http://localhost:9200 http://localhost:9200/_cluster/health http://localhost:9200/_cat/indices?v

Kibana
http://localhost:5601

Logstash monitoring
http://localhost:9600

Swagger
Typical per-service endpoints:
http://localhost:<service-port>/swagger-ui/index.html http://localhost:<service-port>/v3/api-docs


27.	Quick Viva / Interview Questions
Monolith
•	What is a monolithic architecture?
•	Why is independent scaling diﬀicult in a monolith?

Microservices
•	Why split an e-commerce system into services?
•	What problems are introduced by microservices?
 


Eureka
•	What is service discovery?
•	Why does Order need Eureka?
•	Eureka vs Kubernetes Service/DNS?

Gateway
•	Why not expose every service directly?
•	What is a Gateway filter?
•	Where is JWT checked in this project?

WebClient
•	Why does Order use WebClient?
•	Why should Order not access the Inventory database directly?
•	What happens if Inventory is slow?

Circuit breaker
•	Why is the circuit breaker placed around Order -> Inventory?
•	What are CLOSED, OPEN and HALF-OPEN?
•	What is a fallback?

JWT
•	Authentication vs authorization?
•	What does Bearer mean?
•	What happens when the token is missing or invalid?

Actuator
•	What is /actuator/health?
•	Why expose /actuator/prometheus?

Prometheus/Grafana
•	What does Prometheus collect?
•	What does Grafana do?
•	Why are logs not the same as metrics?

Kafka
•	Why is Kafka used between Order and Notification?
•	What is a topic?
•	Producer vs consumer?
•	Why can duplicate events happen?
 


ELK
•	What does Logstash do?
•	What does Elasticsearch do?
•	What does Kibana do?
•	Why use structured logs?

Docker
•	Why containerize microservices?
•	What does localhost mean inside a container?
•	Why use service names in Docker Compose?

Kubernetes
•	Why can Eureka be removed in Kubernetes?
•	What is a Kubernetes Service?
•	How does DNS help service discovery?

Swagger
•	What is OpenAPI?
•	Why use Swagger UI?
•	How do you send a JWT from Swagger?


28.	Final Architecture to Remember
CLIENT
| v
API GATEWAY :8080
| Authentication Filter
| JWT
|
+-------------------+	+
|	|	|
v	v	v
AUTH SERVICE	PRODUCT SERVICE	ORDER SERVICE
| v
CIRCUIT  BREAKER
|
 


v WebClient
| v
INVENTORY SERVICE
| v
Inventory  DB
ORDER SERVICE
|
| Kafka producer v
KAFKA
|
| consumer v
NOTIFICATION SERVICE
SERVICE DISCOVERY (LOCAL TRAINING): EUREKA SERVER :8761
CENTRAL CONFIGURATION:
GitHub Config Repo
| v
CONFIG SERVER :8888
METRICS:
Services -> Actuator -> Prometheus :9090 -> Grafana :3000
LOGS:
Services -> JSON Logs -> Logstash :5000
-> Elasticsearch :9200 -> Kibana :5601
Final principle
For this project, remember the role of each technology:

Technology	Question it answers

Monolith		How does one deployable application work?
Microservices	How do we split the system into independently deployable capabilities?
 



 
Technology	Question it answers

Eureka	Where is another service?
API Gateway	Where does the external request enter?
Gateway Filter	Should this request continue?
JWT	Who is the caller?
WebClient	How do I synchronously call another service?
Circuit Breaker	What do I do when that downstream service fails?
Kafka	How do I publish an asynchronous event?
Actuator	What is the health/operational state of my application?
Prometheus	How do I collect metrics?
Grafana	How do I visualize metrics?
ELK	How do I centrally search and
visualize logs?
Docker	How do I package and run all components consistently?
Kubernetes Service/DNS	How do services discover each other in a K8s cluster?
Config Server		Where do centralized configuration values come from?
Swagger/OpenAPI	How do I document and interactively test APIs?

The central Order use case ties the architecture together:
Order -> WebClient -> Inventory
|
+-- if DOWN -> Circuit Breaker Order -> Kafka -> Notification
All services -> Actuator -> Prometheus -> Grafana All services -> Logstash -> Elasticsearch -> Kibana


Reference links
•	Project repository: https://github.com/vijaymannava-design/ecommerce-microservices.git
•	Spring Cloud: https://spring.io/projects/spring-cloud
 



•	Spring Cloud Gateway: https://docs.spring.io/spring-cloud-gateway/reference/
•	Spring Cloud Netflix / Eureka:	https://docs.spring.io/spring-cloud-netflix/docs/current/reference/html/
•	Spring Cloud Config: https://docs.spring.io/spring-cloud-config/docs/current/reference/html/
•	Spring Security: https://docs.spring.io/spring-security/reference/
•	Spring Boot Actuator: https://docs.spring.io/spring-boot/reference/actuator/
•	Spring for Apache Kafka: https://docs.spring.io/spring-kafka/reference/
•	Elastic Stack: https://www.elastic.co/guide/en/elastic-stack/current/overview.html
•	Kubernetes Service/DNS: https://kubernetes.io/docs/concepts/services-networking/dns-pod-service/
•	OpenAPI / Swagger UI: https://springdoc.org/
