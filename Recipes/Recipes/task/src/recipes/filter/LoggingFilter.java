package recipes.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

public class LoggingFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);

    /**
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        ContentCachingRequestWrapper contentCachingRequestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper contentCachingResponseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(contentCachingRequestWrapper, contentCachingResponseWrapper);

        String requestBody = getStrValue(contentCachingRequestWrapper.getContentAsByteArray(), request.getCharacterEncoding());

        String responseBody = getStrValue(contentCachingResponseWrapper.getContentAsByteArray(), response.getCharacterEncoding());

        LOGGER.info("Filter logs : DATE_TIME = {}; METHOD = {}; REQUESTURI = {}; REQUEST BODY = {}; RESPONSE CODE = {}; RESPONSE BODY = {}",
                LocalDateTime.now(), request.getMethod(), request.getRequestURI(), requestBody, response.getStatus(), responseBody);

        contentCachingResponseWrapper.copyBodyToResponse();
    }

    private String getStrValue(byte[] contentAsByteArray, String characterEncoding) {
        try {
            return new String(contentAsByteArray, 0, contentAsByteArray.length, characterEncoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "";
    }
}
