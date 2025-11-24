# 학습내용 정리

## 스프링 MVC, 스프링 프레임워크 동작 방식

<details>
<summary>학습내용</summary>
### 동작 방식
- **클라이언트 요청**
    
    브라우저 또는 앱에서 HTTP 요청이 들어온다.
    
- **Front Controller: DispatcherServlet**
    
    모든 요청은 DispatcherServlet이 제일 먼저 받아 처리 흐름을 시작한다.
    
- **Handler Mapping (매핑 검색)**
    
    DispatcherServlet이 요청 URL + HTTP 메서드 등을 보고
    
    어떤 컨트롤러 메서드가 이 요청을 처리할지 결정한다.
    
- **Handler Adapter (실행 준비)**
    
    매핑된 컨트롤러가 어떤 방식으로 실행될지 알기 위해 적절한 어댑터가 준비되고 실행된다.
    
- **Controller 실행 및 비즈니스 로직 호출**
    
    컨트롤러 메서드가 실행되고, 내부에서 서비스 계층을 호출한다.
    
- **Service → Repository → DB**
    
    비즈니스 로직 수행 후 데이터베이스 접근 등이 일어난다.
    
- **결과 반환**
    
    컨트롤러는 결과를 ModelAndView 형태 또는 객체(JSON 등) 형태로 반환한다.
    
- **ViewResolver → View 렌더링**
    
    화면이 필요한 경우(HTML 등) ViewResolver가 어떤 View를 실제로 사용할지 결정하고 렌더링한다.
    
    API(JSON) 방식인 경우엔 이 과정이 간소화된다.
    
- **응답 전송**
    
    최종적으로 HTTP 응답이 클라이언트에게 보내진다.


### 정리
- 프로젝트 구조가 DispatcherServlet 통해서 서버로부터 온 데이터 전달받아서 Handler Mapping통해 url이랑 Controller 맵핑, Handler Adapter통해서 어떤 방식으로 실행될지 정한뒤 그동안 해왔던 MVC패턴 통해 결과를 전송하는 방식이다.
- 즉 구현해야되는 부분은 
1. DispatcherServlet : 서버에서 온 요청을 받고 응답을 전송하는 객체
2. Handler Mapping : Controller와 메소드를 맵핑해둔 객체, 즉 어떤 메소드를 실행할지 정하는 역할
3. Handler Adapter : Http에서 전달받은 데이터들을 컨트롤러에서 사용할수 있도록 파싱해주고 컨트롤러 메소드 실행, 반환값을 DispatcherServlet에 전달
4. MVC로직 : 기존 로직과 같다
5. ViewResolver : 어떤 HTMl파일을 사용할지 결정
6. DispatcherServlet : 응답 전송
- DispatcherServlet, Handler Mapping, Handler Adapter, ViewResolver등이 프레임워크 개발자가 만든 함수라면 나는 그동안 Controller, Service, Repository라는 메소드만 만들었던 느낌인것 같다.

**출처**
- https://white63ser.tistory.com/10
- https://ss-o.tistory.com/160

</details>


## IoC (Inversion of Control) & DI (Dependency Injection)

<details>
<summary>학습 내용</summary>

### 개념 정리

### **IoC (제어의 역전)**

- 원래 객체 생성, 의존성 연결, 실행 흐름 제어는 **개발자가 직접 new 하면서 컨트롤**해야 했다.
- IoC는 이 제어권을 프레임워크에게 넘기는 개념이다.
- 즉, **객체의 생성 → 조립 → 생명주기 관리 → 실행 흐름**까지 프레임워크가 담당한다.
- 스프링에서는 IoC 컨테이너(ApplicationContext)가 이 역할을 수행한다.

**핵심 정리:**

> 기존에는 개발자가 new로 객체를 만들고 연결했지만, IoC에서는 프레임워크가 대신 객체를 만들고 연결한다.
> 

---

### **DI (의존성 주입)**

- IoC를 구체적으로 구현하는 방식 중 하나.
- 필요한 객체(의존성)를 개발자가 new 하지 않고, 프레임워크가 자동으로 주입한다.
- 주입 방식: 생성자 주입(권장), 필드 주입, 세터 주입 등이 있다.
- DI를 통해 **결합도를 낮추고, 테스트가 쉬워지고, 확장성이 높아진다.**

**핵심 정리:**

> 어떤 객체를 사용할지는 개발자가 만들지 않고, 프레임워크가 대신 넣어주는 구조.
> 

---

### IoC와 DI의 관계

- IoC가 큰 개념(제어권을 프레임워크에 넘김)
- DI는 IoC를 실현하는 기술(객체를 스프링이 주입)

즉,

```
IoC = 전체 흐름의 제어권을 프레임워크가 가짐
DI = 그 제어권으로 의존성을 자동으로 넣어주는 과정

```

---

## 왜 IoC / DI가 필요한가?

### IoC/DI 미적용 시 문제점

- Controller → ServiceImpl → RepositoryImpl 모두 직접 new로 연결되어 강결합
- ServiceImpl을 교체하거나 정책을 바꾸려면 코드 수십 곳을 수정해야 함
- Mock 객체 주입이 불가능하여 테스트 작성이 어려움
- 클래스 수가 많아질수록 new와 의존성 연결이 복잡해져 유지보수가 거의 불가능
- 객체 생명주기(싱글톤 유지, 초기화, 메모리 관리)를 직접 관리해야 해서 실전 규모 프로젝트에 부적합

### IoC/DI 적용 시 장점

- 구현체를 바꿔도 Controller나 다른 계층은 전혀 손댈 필요 없음
- MockRepository 등 테스트용 객체로 쉽게 교체 가능
- 스프링이 객체 생성, 조립, 초기화, 싱글톤 유지까지 모두 관리
- 큰 규모의 프로젝트에서도 구조가 흔들리지 않고 확장 가능
- 코드가 역할별로 분리되어 유지보수성이 극대화됨

---

## 내가 이해한 구조 (정확한 핵심)

- **Controller는 역할만 정의하고 실제 로직은 ServiceImpl에서 처리한다.**
- Controller는 “MemberService” 같은 인터페이스만 알고 있고,
    
    구현체(MemberServiceImpl)는 프레임워크가 자동으로 연결해 준다.
    
- ServiceImpl은 Repository 같은 하위 객체를 new 하지 않고,
    
    @Autowired로 프레임워크가 주입한 객체를 사용한다.
    
- 전체 연결 흐름을 프레임워크가 맡는 것이 IoC,
    
    그 과정에서 필요한 객체를 주입해주는 것이 DI.
    

---

## 스프링의 IoC/DI 흐름 요약

1. @Controller, @Service, @Repository, @Component 스캔
2. 해당 클래스 인스턴스를 스프링 IoC 컨테이너가 생성
3. 필드/생성자에서 @Autowired 감지
4. 맞는 타입의 Bean을 찾아 자동으로 주입(DI)
5. Controller → Service → Repository 구조가 자동으로 연결
6. 개발자는 로직만 작성하면 되고 객체 생성/조립/관리 필요 없음

---

## 결론

- 작은 프로젝트에서는 new로 직접 객체를 만들어도 문제가 없다.
- 하지만 웹 서비스처럼 규모가 커지고 클래스 수가 많아지면 IoC 없이 유지보수가 사실상 불가능하다.
- DI를 통해 구현체 교체, 테스트, 확장성이 극대화되며
    
    IoC는 이러한 구조적 기반을 제공해준다.
    
- 결국 IoC/DI는 스프링 프레임워크가 큰 규모의 애플리케이션을 안정적으로 운영할 수 있게 해주는 핵심 개념이다.

</details>

## 구현 기능 정리

<details>
<summary>학습 내용</summary>

- 처음에 정리했던 단계들어가기전 전처리 단계가 있다.
1. 어노테이션 스캔
2. Bean 생성
3. 의존성 주입
4. HandlerMapping 등록
- 그 이후에 DispatcherServlet이 요청 처리

### annotation 생성 방법
1. 기본 문법
- public @interface 클래스명{ }
2. 구성 요소
- 속성을 가질수 있다.
```java
public @interface 클래스명 {
    타입 속성명(); // 어노테이션 속성
}
```
3. @Target : 어디에 붙일 수 있는지 지정
4. @Retention : 언제까지 유지되는지 지정
5. Documented(Optional) : JavaDoc 문서에 포함될지 여부
6. Inherited(Optional) : 부모 클래스의 어노테이션이 자식 클래스에도 상속되는지 여부
7. Repeatable(Optional) : 같은 어노테이션을 한 곳에 여러 번 붙일 수 있게 함

</details>