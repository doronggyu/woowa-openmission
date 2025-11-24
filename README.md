# 📘 Mini Spring Framework – 동작 시뮬레이터 구현

---

## 🧭 미션 내용 (Mission Overview)

이 미션은 스프링 프레임워크를 그대로 만드는 것이 아니라,

**스프링이 동작하는 방식과 핵심 구조를 직접 구현해보는 것**을 목표로 한다.

스프링의 IoC, DI, 어노테이션 기반 라우팅, MVC 흐름 등

핵심 기능을 최소 단위로 재현하여

“프레임워크가 내부에서 어떻게 동작하는가”를 직접 체험하는 프로젝트다.

구현 범위는 다음과 같다:

- @Controller, @Service, @Component, @Autowired 등 어노테이션 처리
- Component Scan으로 Bean 자동 등록
- BeanFactory 기반 IoC/DI 컨테이너 구현
- 의존성 주입(DI) 흐름 및 Bean 생성 로직 구현
- @Get @Post 기반 라우팅 처리
- HandlerMapping → Dispatcher 구조 구현
- ServerSocket 기반 HTTP 요청/응답 처리
- RequestBody/ResponseBody 처리 및 JSON 직렬화
- Mini Spring 기반 간단한 샘플 웹페이지 제작

---

## 🎯 미션 선정 이유 (Why This Mission?)

졸업작품을 진행하면서 스프링 프레임워크를 사용했지만,

그 과정에서 **내부 구조나 동작 원리를 이해하지 못한 채
기능을 가져다 쓰기만 했다.**

이번 오픈미션에서는

스프링을 단순히 사용하는 것이 아니라,

**스프링처럼 동작하는 최소한의 구조를 직접 구현해보며
프레임워크의 기본 원리를 이해하고자** 한다.

이를 통해

스프링이 어떤 방식으로 요청을 처리하고,

어떻게 객체를 생성하고 관리하며,

어노테이션 기반으로 어떤 흐름이 만들어지는지

직접 만들어보는 경험을 얻기 위해 이 미션을 선택했다.

---

## 클래스 구조
# Mini Spring Framework – 클래스 구조 문서

---

## 1. 전체 패키지 구성

```
minispring
├── annotations
│   ├── Autowired.java
│   ├── Component.java
│   ├── Controller.java
│   ├── Get.java
│   ├── Post.java
│   └── Service.java
│
├── app
│   ├── controller
│   │   ├── MainController.java
│   │   ├── MemberController.java
│   │   └── MypageController.java
│   │
│   ├── dto
│   │   (파일 없음)
│   │
│   └── service
│       ├── MemberService.java
│       └── MemberServiceImpl.java
│
├── context
│   ├── AnnotationFilter.java
│   ├── ApplicationContext.java
│   ├── BeanFactory.java
│   ├── ComponentScanner.java
│   └── DIProcessor.java
│
└── web
    ├── DispatcherServlet.java
    ├── HandlerMapping.java
    ├── HandlerMethod.java
    ├── MiniRequest.java
    ├── MiniWebServer.java
    ├── ViewResolver.java
    └── MiniSpringApp.java

```

---

## 2. 패키지별 구성 요소 설명

### 2.1. minispring.annotations

### Component.java

일반적인 Bean 등록 대상 클래스에 사용되는 기본 어노테이션이다.

서비스, 컨트롤러와 같은 클래스들이 결국 Component로 취급된다.

### Controller.java

HTTP 요청을 처리하는 컨트롤러 클래스를 나타내는 어노테이션이다.

### Service.java

비즈니스 로직을 담당하는 서비스 클래스에 부여하는 어노테이션이다.

### Autowired.java

필드 기반 의존성 주입을 위해 사용된다.

DIProcessor가 이 어노테이션을 가진 필드를 찾아 Bean을 자동 주입한다.

### Get.java

GET 요청 매핑을 설정하기 위한 어노테이션이다.

HandlerMapping에서 URL과 HTTP 메서드를 기준으로 매핑 정보를 구성할 때 사용한다.

### Post.java

POST 요청 매핑을 제공하는 어노테이션이다.

---

### 2.2. minispring.app.controller

### MainController.java

메인 페이지 요청을 처리하는 컨트롤러다.

예: `/`, `/index`

### MemberController.java

회원 관련 기능을 담당하는 컨트롤러다.

로그인, 회원가입 같은 기능을 처리한다.

### MypageController.java

마이페이지 화면과 관련된 요청을 처리한다.

---

### 2.3. minispring.app.service

### MemberService.java

회원 로직을 정의하는 인터페이스다.

### MemberServiceImpl.java

MemberService를 구현한 클래스이며 실제 회원 관련 로직이 포함된다.

@Service 어노테이션으로 Bean으로 등록된다.

---

### 2.4. minispring.context

### ComponentScanner.java

지정된 패키지 경로를 스캔하며 @Component, @Service, @Controller 등을 가진 클래스를 찾아 BeanFactory에 전달한다.

### BeanFactory.java

Bean 생명주기와 저장을 담당한다.

IoC 컨테이너 역할의 핵심이다.

### ApplicationContext.java

BeanFactory와 DIProcessor를 통해 전체 애플리케이션 실행 환경을 구성한다.

### AnnotationFilter.java

특정 어노테이션을 가진 클래스인지 검사하는 도구 역할을 한다.

### DIProcessor.java

Autowired가 붙은 필드를 찾아 Bean을 주입한다.

Reflection을 사용해 의존성 주입을 수행한다.

---

### 2.5. minispring.web

### MiniWebServer.java

ServerSocket 기반의 간단한 웹 서버 구현체다.

요청을 받아 DispatcherServlet으로 전달한다.

### DispatcherServlet.java

요청 처리의 중심이 되는 클래스다.

요청을 HandlerMapping에 전달해 적절한 메서드를 찾고, 결과를 ViewResolver로 넘긴다.

### HandlerMapping.java

컨트롤러 내부의 @Get, @Post 메서드를 스캔해 URL과 메서드 기반의 매핑 정보를 구성한다.

### HandlerMethod.java

컨트롤러 인스턴스와 실행해야 할 메서드 정보를 담는 객체다.

### MiniRequest.java

HTTP 요청 내용을 파싱해 내부 요청 객체 형태로 변환한다.

### ViewResolver.java

템플릿 파일을 불러와 응답을 생성한다.

### MiniSpringApp.java

Mini Spring의 시작점이다.

ApplicationContext를 초기화하고 MiniWebServer를 실행한다.

---

## 📂 관련 문서 리스트 (Documents)

모든 문서는 `/docs` 디렉토리에서 관리한다.

| 문서명 | 설명 |
| --- | --- |
| **/docs/learning-summary.md** | 학습내용 정리 |
| **/docs/구현과정.md** | 구현내용 |


