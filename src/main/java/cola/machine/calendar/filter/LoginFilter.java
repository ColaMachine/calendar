package cola.machine.calendar.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSON;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import cola.machine.util.ResultUtil;

import com.google.gson.Gson;

import core.action.ResultDTO;
import core.util.RequestUtil;


public class LoginFilter implements Filter {
	private static final Logger logger =LoggerFactory.getLogger(LoginFilter.class);
	protected FilterConfig filterConfig = null;
	private String redirectURL = null;
	List<String> notCheckURLList = new ArrayList<String>();
	// private String[] notCheckUrlArr;
	private String sessionKey = null;

	public void destroy() {
		// VIP Auto-generated method stub
		// notCheckUrlArr=null;
		notCheckURLList.clear();

	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		StringBuffer strb = new StringBuffer();
		servletRequest.setCharacterEncoding("utf-8");
		/*
		 * BufferedReader in = new BufferedReader(new InputStreamReader(
		 * servletRequest.getInputStream()));
		 */
		/*
		 * while (true) { String line = in.readLine(); if (line == null) {
		 * break; } line = URLDecoder.decode(line, "UTF-8"); strb.append(line);
		 * } System.out.println(strb);
		 */
		// VIP Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		HttpSession session = request.getSession();
		if (sessionKey == null) {
			filterChain.doFilter(request, response);
			return;
		}
		Object object = session.getAttribute(sessionKey);
		if ((!checkRequestURIIntNotFilterList(request))
				&& session.getAttribute(sessionKey) == null) {
			Gson gson =new Gson();
			ResultDTO result=ResultUtil.getNotLogging();
			 if (RequestUtil.isAjaxRequest(request) ) {
		            try {
		                String s = gson.toJson(result);
		                response.setHeader("Cache-Control", "no-cache");
		                response.setCharacterEncoding("UTF-8");
		                response.setContentType("text/json;charset=UTF-8");
		                response.setContentType("text/json;charset=UTF-8");
		                response.getWriter().println(gson.toJson(result));
		                response.getWriter().flush();
		                response.getWriter().close();
		            } catch (IOException e1) {
		                logger.error("", e1);
		            }
		        	filterChain.doFilter(request, response);
		            return ;
		        } else {
		            // String msg = ErrorMessage.getErrorMsg(e.getMessage());
		            request.setAttribute("errormsg", result.getMsg());
		        	response.sendRedirect(request.getContextPath() + redirectURL);
		        }
			return;
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	private boolean checkRequestURIIntNotFilterList(HttpServletRequest request) {
		String uri = request.getServletPath()
				+ (request.getPathInfo() == null ? "" : request.getPathInfo());
		System.out.println(uri + "\t exists:" + notCheckURLList.contains(uri));
		// return notCheckURLList.contains(uri);
		for (String s : notCheckURLList) {
			if (uri.matches(s))
				return true;
		}
		System.out.println(" in controll");
		return false;
	}

	/* *
	 * 初始化
	 **/
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		redirectURL = filterConfig.getInitParameter("redirectURL");
		sessionKey = filterConfig.getInitParameter("checkSessionKey");

		String notCheckURLListStr = filterConfig
				.getInitParameter("notCheckURLList");
		notCheckURLListStr = notCheckURLListStr
				.replaceAll("[\\r\\n\\s\\t]", "");
		if (notCheckURLListStr != null) {
			StringTokenizer st = new StringTokenizer(notCheckURLListStr, ";");
			notCheckURLList.clear();
			while (st.hasMoreTokens()) {
				notCheckURLList.add(st.nextToken());
			}
		}

		// notCheckUrlArr=(String[])notCheckURLList.toArray();
	}

}
