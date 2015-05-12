package com.freelance.maraay.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.freelance.maraay.beans.LoginBean;
import com.freelance.maraay.utils.Performance;

/**
 * Servlet Filter implementation class AuthenticationFilter
 */
public class AuthenticationFilter implements Filter {

	private static List<String> publisherPriv = new ArrayList<String>();

	/**
	 * Default constructor.
	 */
	public AuthenticationFilter() {

	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {

	}

	static {
		publisherPriv.add("/home");
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();

		Performance.releaseMemory();

		if (!checkAuthentication(request, session))
			response.sendRedirect(request.getContextPath() + "/noPrivilege.jsf");
		if (!checkUser(session))
			response.sendRedirect(request.getContextPath() + "/login.xhtml");
		chain.doFilter(req, res);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {

	}

	public boolean checkUser(HttpSession session) {
		if (session.getAttribute(LoginBean.AUTH_KEY) != null)
			return true;

		return false;
	}

	public boolean checkAuthentication(HttpServletRequest request,
			HttpSession session) {
		String loginRole = (String) session.getAttribute(LoginBean.AUTH_KEY);
		String context = request.getContextPath();
		String url = request.getRequestURI();

		if ("2".equals(loginRole)) {
			for (String priv : publisherPriv) {
				if (url.startsWith(context + priv)) {
					return true;
				}
			}
			return false;
		}
		return true;
	}
}
