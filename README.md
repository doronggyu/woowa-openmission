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

## 📂 관련 문서 리스트 (Documents)

모든 문서는 `/docs` 디렉토리에서 관리한다.

| 문서명 | 설명 |
| --- | --- |
| **/docs/learning-summary.md** | 학습내용 정리 |
| **/docs/구현과정e.md** | 구현내용 |
