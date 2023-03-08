package com.example.quiz.controller;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.quiz.entity.Quiz;
import com.example.quiz.form.QuizForm;
import com.example.quiz.service.QuizService;
@Controller
@RequestMapping("/quiz")
public class QuizController {
    @Autowired
    QuizService service;

    @ModelAttribute
    public QuizForm setUpForm(){
        QuizForm form = new QuizForm();
        form.setAnswer(true);
        return form;
    }
    @GetMapping
    public String showList(QuizForm quizForm, Model model){
        quizForm.setNewQuiz(true);
        Iterable<Quiz> list = service.selectAll();

        model.addAttribute("list",list);
        model.addAttribute("title", "등록 폼");

        return "crud";
    }
    @PostMapping("/insert")
    public String insert(@Validated QuizForm quizForm, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
        Quiz quiz = new Quiz();
        quiz.setQuestion(quizForm.getQuestion());
        quiz.setAnswer(quizForm.getAnswer());
        quiz.setAuthor(quizForm.getAuthor());

        if(!bindingResult.hasErrors()){
            service.insertQuiz(quiz);
            redirectAttributes.addFlashAttribute("complete","등록이 완료되었습니다");
            return "redirect:/quiz";
        }
        else {
            return showList(quizForm, model);
        }
    }
    /** Quiz 데이터를 1건 취득해서 폼 안에 표시 */
    @GetMapping("/{id}")
    public String showUpdate(QuizForm quizForm, @PathVariable Integer id, Model model) {
        // Quiz를 취득(Optional로 래핑)
        Optional<Quiz> quizOpt = service.selectOneById(id);

        // QuizForm에 채워넣기
        Optional<QuizForm> quizFormOpt = quizOpt.map(t -> makeQuizForm(t));

        // QuizForm이 null이 아니라면 값을 취득
        if(quizFormOpt.isPresent()) {
            quizForm = quizFormOpt.get();
        }

        // 변경용 모델 생성
        makeUpdateModel(quizForm, model);
        return "crud";
    }

    /** 변경용 모델 생성 */
    private void makeUpdateModel(QuizForm quizForm, Model model) {
        model.addAttribute("id", quizForm.getId());
        quizForm.setNewQuiz(false);
        model.addAttribute("quizForm", quizForm);
        model.addAttribute("title", "변경 폼");
    }

    /** id를 키로 사용해 데이터를 변경 */
    @PostMapping("/update")
    public String update(
            @Validated QuizForm quizForm,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {
        // QuizForm에서 Quiz으로 채우기
        Quiz quiz = makeQuiz(quizForm);
        // 입력 체크
        if (!result.hasErrors()) {
            // 변경 처리, Flash scope를 사용해서 리다이렉트 설정
            service.updateQuiz(quiz);
            redirectAttributes.addFlashAttribute("complete", "변경이 완료되었습니다");
            // 변경 화면을 표시
            return "redirect:/quiz/" + quiz.getId();
        } else {
            // 변경용 모델을 생성
            makeUpdateModel(quizForm, model);
            return "crud";
        }
    }

// ----------【 아래는 Form과 도메인 객체를 다시 채우기】 ----------
    /** QuizForm에서 Quiz로 다시 채우기, 반환값으로 돌려줌 */
    private Quiz makeQuiz(QuizForm quizForm) {
        Quiz quiz = new Quiz();
        quiz.setId(quizForm.getId());
        quiz.setQuestion(quizForm.getQuestion());
        quiz.setAnswer(quizForm.getAnswer());
        quiz.setAuthor(quizForm.getAuthor());
        return quiz;
    }

    /** Quiz에서 QuizForm로 다시 채우기, 반환값으로 돌려줌 */
    private QuizForm makeQuizForm(Quiz quiz) {
        QuizForm form = new QuizForm();
        form.setId(quiz.getId());
        form.setQuestion(quiz.getQuestion());
        form.setAnswer(quiz.getAnswer());
        form.setAuthor(quiz.getAuthor());
        form.setNewQuiz(false);
        return form;
    }
    /** id를 키로 사용해 데이터를 삭제 */
    @PostMapping("/delete")
    public String delete(
            @RequestParam("id") String id,
            Model model,
            RedirectAttributes redirectAttributes) {
        // 퀴즈 정보를 1건 삭제하고 리다이렉트
        service.deleteQuizById(Integer.parseInt(id));
        redirectAttributes.addFlashAttribute("delcomplete", "삭제 완료했습니다");
        return "redirect:/quiz";
    }
}
