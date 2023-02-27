package com.example.quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.quiz.entity.Quiz;
import com.example.quiz.repository.QuizRepository;

import java.util.Optional;

@SpringBootApplication
public class QuizApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizApplication.class, args)
				.getBean(QuizApplication.class).execute();

	}
	@Autowired
	QuizRepository repository;
	private void execute(){
		//setup();
		//showList();
		//showOne();
		//updateQuiz();
		deleteQuiz();
	}
	private void setup(){
		Quiz quiz1 = new Quiz(null,"Spring은 프레임워크입니까?", true, "홍길동");
		quiz1 = repository.save(quiz1);
		System.out.println("등록한 퀴즈는 "+quiz1+"입니다.");

		Quiz quiz2 = new Quiz(null, "스프링MVC는 배치 처리를 제공합니까?", false, "홍길동");
		quiz2 =repository.save(quiz2);
		System.out.println("등록한 퀴즈는 "+quiz2+"입니다.");
	}
	private void showList(){
		System.out.println("---모든 데이터 취득 개시 ---");
		Iterable<Quiz> quizzes =repository.findAll();
		for( Quiz quiz : quizzes){
			System.out.println(quiz);
		}
		System.out.println("---모든 데이터 취득 완료---");
	}
	private void showOne(){
		System.out.println("-- 1건 취득 개시 --");
		Optional<Quiz> quizOpt = repository.findById(1);
		if (quizOpt.isPresent()){
			System.out.println(quizOpt.get());
		}
		else{
			System.out.println("해당 데이터는 존재하지 않습니다.");
		}
		System.out.println(" --- 1건 취득 완료 ---");
	}
	private void updateQuiz(){
		System.out.println( " -- 변경 처리 개시 -- ");
		Quiz quiz1 = new Quiz(1,"스프링은 프레임워크 입니까?", true, "변경 담당");
		quiz1 = repository.save(quiz1);
		System.out.println("변경된 데이터는"+quiz1+"입니다.");
		System.out.println(" -- 변경 처리 완료 --");
	}
	private void deleteQuiz(){
		System.out.println("-- 삭제 처리 개시 --");
		repository.deleteById(2);
		System.out.println("-- 삭제 처리 완료 --");
	}
}
