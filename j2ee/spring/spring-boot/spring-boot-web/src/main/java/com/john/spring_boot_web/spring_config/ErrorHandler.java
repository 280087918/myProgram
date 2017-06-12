package com.john.spring_boot_web.spring_config;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "error")
@EnableConfigurationProperties({ ServerProperties.class })
public class ErrorHandler implements ErrorController {

	private ErrorAttributes errorAttributes;

	@Autowired
	private ServerProperties serverProperties;

	public ErrorHandler(ErrorAttributes errorAttributes) {
		Assert.notNull(errorAttributes, "ErrorAttributes must not be null");
		this.errorAttributes = errorAttributes;
	}

	@Override // 暂时搁置，不用这个
	public String getErrorPath() {
		return "";
	}

	@RequestMapping(value = "404")
	public String error404() {
		return "common_page/404";
	}

	@RequestMapping(value = "500")
	public ModelAndView error500(HttpServletRequest request) {
		Map<String, Object> model = getErrorAttributes(request, isIncludeStackTrace(request));
		return new ModelAndView("common_page/500", model);
	}

	@Bean // 方法产生一个Bean交给Spring去管理
	public EmbeddedServletContainerCustomizer containerCustomizer() {
		return new EmbeddedServletContainerCustomizer() {
			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {
				// 将容器错误页面的路径交给Servlet容器去管理
				container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/404"));
				container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500"));
				container.addErrorPages(new ErrorPage(java.lang.Throwable.class, "/error/500"));
			}
		};
	}

	protected boolean isIncludeStackTrace(HttpServletRequest request) {
		ErrorProperties.IncludeStacktrace include = this.serverProperties.getError().getIncludeStacktrace();
		System.out.println("+++++++++++++++:" + include);
		if (include == ErrorProperties.IncludeStacktrace.ALWAYS) {
			return true;
		}
		if (include == ErrorProperties.IncludeStacktrace.ON_TRACE_PARAM) {
			return getTraceParameter(request);
		}
		return false;
	}

	private boolean getTraceParameter(HttpServletRequest request) {
		String parameter = request.getParameter("trace");
		if (parameter == null) {
			return false;
		}
		return !"false".equals(parameter.toLowerCase());
	}

	/**
	 * 获取错误信息
	 * @param request
	 * @param includeStackTrace
	 * @return
	 */
	private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
		RequestAttributes requestAttributes = new ServletRequestAttributes(request);
		return this.errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
	}
}
