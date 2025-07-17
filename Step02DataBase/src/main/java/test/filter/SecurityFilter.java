package test.filter;
/*
 * 	웹 서버의 보안을 담당할 보안 필터 만들기
 * 
 * 
 * 
 */

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Set;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebFilter("/*") // 들어오는 모든 요청에 대해서 필터링을 하겠다.
public class SecurityFilter implements Filter{
	// 로그인 없이 접근 가능한 경로 목록
	Set<String> whiteList = Set.of(
		"/index.jsp",
		"/user/loginform.jsp","/user/login.jsp",
		"/user/signup-form.jsp", "/user/signup.jsp",
		"/images/",
		"/upload/"
	);
	

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 어떤 요청이든 이곳을 거친다.
		
		// 로그인을 했는지 확인작업
		// 세션이 필요해서 세션은 ServletRequest 통해 받아야 하는데 부모타입이라 없다. -> 캐스팅이 필요
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse res=(HttpServletResponse)response;
		
		// HttpSession 객체의 참조값 얻어내기
		HttpSession session=req.getSession();
		
		// context path
		String cPath=req.getContextPath();
		// 클라이언트의 요청 경로 얻어내기
		String uri=req.getRequestURI(); // ex : member/list.jsp
		// uri 에서 context path 를 제거한 순수 경로를 얻어낸다.
		String path=uri.substring(cPath.length());
		
		// 로그인 없이 접근 가능한 요청 경로면 필터링을 하지 않는다. 
		if(isWhitelist(path)) {
			chain.doFilter(request, response);
			return;
		}
		
		// 로그인 여부 확인
		String userName=(String)session.getAttribute("userName");
		// 만약 로그인 안 했다면
		//만일 로그인을 하지 않았다면 
		if(userName == null) {
			//로그인 페이지로 리다일렉트(새로운 경로로 요청을 다시하라고 응답) 이동 시킨다 
			//query 문자열이 있으면 읽어와서 
	        String query = req.getQueryString();
	        //인코딩을 한다음 
	        String encodedUrl = query == null ? URLEncoder.encode(uri, "UTF-8")
	                                          : URLEncoder.encode(uri + "?" + query, "UTF-8");
	        //리다일렉트 되는 경로뒤에 url 이라는 파라미터명으로 전달한다 
	        res.sendRedirect(req.getContextPath() + "/user/loginform.jsp?url=" + encodedUrl); 
			return; //메소드를 여기서 끝내기
		}
		
		// 요청의 흐름 이어가기
		chain.doFilter(request, response);
	}
	

    
    // 화이트리스트 검사. 경로(path)를 전달하면 얘가 화이트리스트인지 아닌지 리턴해주는 메소드 
    private boolean isWhitelist(String path) {
    	// 만약 최상위 경로 요청하면 허용
        if ("/".equals(path)) return true;  
        
        // 반복문 돌면서 모든 whiteList 를 불러내 prefix 에 담고 
        for (String prefix : whiteList) {
        	// 현재 요청경로와 대조한다.
            if (path.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }
}
 