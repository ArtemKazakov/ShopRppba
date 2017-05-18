package by.bsuir.shop.filter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Class {@code CharsetFilter} is the class, that implements {@code Filter} interface to
 * process with different encodings of Russian and English languages.
 */
public class CharsetFilter implements Filter{
    private final static Logger logger = LogManager.getLogger(LocaleFilter.class.getName());

    private String encoding;
    private ServletContext context;

    @Override
    public void destroy(){
        context = null;
    }
    /**
     * <p>Sets initial encoding for the system.</p>
     * <p>
     * @param fConfig is the configuration of the encoding.
     */
    @Override
    public void init(FilterConfig fConfig){
        encoding = fConfig.getInitParameter("characterEncoding");
        context = fConfig.getServletContext();
    }
    /**
     * <p>Sets necessary encoding for the next pages through responses.</p>
     * @param request Request we are processing
     * @param response Response we are creating
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            ((HttpServletRequest) request).setCharacterEncoding(encoding);
            ((HttpServletResponse) response).setCharacterEncoding(encoding);
        } catch (UnsupportedEncodingException e) {
            logger.warn("Unsupported Encoding Exception catch.");
        }

        chain.doFilter(request, response);
    }
}