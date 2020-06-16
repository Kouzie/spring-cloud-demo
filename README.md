# sample-spring-cloud

`Hoxton.SR4` 기반 spring cloud 예제   

`account`, `customer` `ribbon` 을 사용한 client side load balancing 지원
`order` `feign` 을 사용한 client side load balancing

`spring gateway` 를 사용한 `reverse proxy`


## install zipkin

```$xslt
docker run -d -p 9411:9411 openzipkin/zipkin
```
## install ELK

> https://github.com/deviantony/docker-elk

### disable paid features

`elasticsearch.yml`  
`xpack.license.self_generated.type: trial` ->  
`xpack.license.self_generated.type: basic`  

```$xslt
ELK_VERSION=7.6.2
docker-compose up -d 
```

## install rabitMq

```$xslt
docker run -d --name rabbit -p 5672:5672 -p 15672:15672 rabbitmq:management
```
> http://localhost:15672/

guest/guest 로그인 

## install kafka

```$xslt

```

### hystrix 접근

> http://127.0.0.1:19000/hystrix
> localhost:19000/turbine.stream