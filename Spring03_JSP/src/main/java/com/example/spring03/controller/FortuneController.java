package com.example.spring03.controller;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class FortuneController {

	/*@GetMapping("/fortune2")
	public String fortune(HttpServletRequest request) {
		
		// DB 에서 읽어온 오느르이 운세라고 가정 
		String fortuneToday = "서쪽으로 가면 장대비를 만나요!";
		request.setAttribute("fortuneToday", fortuneToday);
		
		// WEB-INF/views/fortune.jsp 로 응답을 위임한다는 의미 
		return "fortune";
	}*/
	
	/*
	 * 	HttpServletRequest 는 HTTP 의 모든 기능을 다루는 객체
	 *  Model 은 view page 로 넘길 데이터만 담는 객체 (더 편리하게 사용 가능) 
	 *  (내부적으로는 리퀘스트에 담긴다고 생각)
	 * 	Model 객체도 컨트롤러 메소드의 매개변수에 선언만 하면 자동으로 spring 이 전달해준다.
	 */
	
	@GetMapping("/fortune2")
	public String fortune2(Model model) { // model 인터페이스
		String fortuneToday = "동쪽으로 가면 귀인을 만나요~!";
		// Model 객체에 담으면 자동으로 HttpServlet 객체에 담긴다.
		model.addAttribute("fortuneToday", fortuneToday);
		
		return "fortune";
	}
	
	
	
	
    @GetMapping("/fortune")
    public String luck(HttpServletRequest request) {
        List<String> luckyList = List.of("대길","중길","소길","말길","반길","길","반흉","말흉","소흉","중흉","대흉","흉");

        int idx = ThreadLocalRandom.current().nextInt(luckyList.size());
        String todayLucky = luckyList.get(idx);

        request.setAttribute("luckyList", luckyList);
        request.setAttribute("fortune", todayLucky); // ← 이름 'fortune'으로!
        return "fortune"; // /WEB-INF/views/fortune.jsp
    }

    // 버튼 클릭 시 여기로 호출 → 순수 텍스트만 반환
    @GetMapping(value = "/fortune/random", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String randomFortune() {
        List<String> luckyList = List.of("대길","중길","소길","말길","반길","길","반흉","말흉","소흉","중흉","대흉","흉");
        return luckyList.get(ThreadLocalRandom.current().nextInt(luckyList.size()));
    }
}
