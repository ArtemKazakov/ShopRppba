package by.bsuir.shop.filter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Class {@code LocaleFilter} is the class, that implements {@code Filter} interface to
 * deal with localization in the system.
 */
public class LocaleFilter implements Filter{
    private static final String ATTR_LOCALE = "locale";
    private static final String EN_LOCALE = "eng";
    private static final String RU_LOCALE = "ru";
    private String locale;

    private final static Logger logger = LogManager.getLogger(LocaleFilter.class.getName());

    public LocaleFilter(){}

    /**
     * <p>Sets initial locale for the system.</p>
     * <p>
     * @param filterConfig is the configuration of the filter.
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        locale = EN_LOCALE;
    }

    @Override
    public void destroy() {

    }

    /**
     * <p>Sets necessary locale for the next pages.</p>
     * @param servletRequest is necessary to get actual locale and set next one.
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        if (req.getSession().getAttribute(ATTR_LOCALE) == null) {
            req.getSession().setAttribute(ATTR_LOCALE, locale);
        }
        if (req.getSession().getAttribute(ATTR_LOCALE) == EN_LOCALE) {
            req.getSession().setAttribute(ATTR_LOCALE, EN_LOCALE);
        }
        if (req.getSession().getAttribute(ATTR_LOCALE) == RU_LOCALE) {
            req.getSession().setAttribute(ATTR_LOCALE, RU_LOCALE);
        }

        filterChain.doFilter(servletRequest, servletResponse);

    }
}
