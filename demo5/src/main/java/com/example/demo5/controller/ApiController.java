package com.example.demo5.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo5.dto.User;

@RestController
@RequestMapping("/api")
public class ApiController {

//	METHOD : POST
//	http://localhost:8080/api/user
	@PostMapping("/user")
	public ResponseEntity<User> user(@RequestBody User user) {
//		Spring Boot 요청시 데이터 기본 파싱 전략은 key=value 구조
//		dto <-- 알아서 맵핑 <-- object mapper 동작을 해서 자동 파싱 처리해 준다.

//		유효성 검사 - 예전 방식
		if (user.getAge() < 1 || user.getAge() > 100) {
//			잘못된 입력값을 확인
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(user);
		}

//		서비스 만들어 로직 짜서 DAO로 던져서 DB insert 처리
//		정방향으로 가서 역방향으로 돌아옴  -> 응답 처리
		System.out.println(user);
		return ResponseEntity.ok(user);
	}

//	AOP 기반인 Validation library 활용하기

//	1. GET 방식일 때 사용방법 POST 방식일 때 사용방법이 약간 다르다
//	반드시 Valid 선언을 해주어야 한다
	@PostMapping("/user2")
	public ResponseEntity<User> user2(@Valid @RequestBody User user) {
//		관점 지향 패러다임 추가

//		AOP 기반의 Valid 라이브러리를 활용하면 공통적으로 들어가야하는 부분에 코드를 분리시킬 수 있다
		return ResponseEntity.ok(user);
	}

//	와일드 카드 -> ? 사용가능
	@PostMapping("/user3")
	public ResponseEntity<?> user3(@Valid @RequestBody User user, BindingResult bindingResult) {
//		BindingResult 클래스를 배워보자
//		BingingResult가 @Valid에 대한 결과 값을 가지고 있다.
		if (bindingResult.hasErrors()) {
//			에러 발생
//			필드 - 어떤 필드에서 에러 발생했는지
//			메세지 - 내용을 반환처리

			StringBuilder sb = new StringBuilder();

			bindingResult.getAllErrors().forEach(error -> {
				System.out.println(error.getCode());
				System.out.println(error.getDefaultMessage());
				System.out.println(error.getArguments());
				System.out.println(error.getObjectName());

				sb.append("field : " + error.getCode());
				sb.append("\n");
				sb.append("message : " + error.getDefaultMessage());
				sb.append("\n");
			});
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(sb.toString());
		}
//		정상 처리
		return ResponseEntity.ok(user);
	}

}
