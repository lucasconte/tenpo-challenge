package com.lucasconte.tenpochallenge.security.filter;

import com.lucasconte.tenpochallenge.actuator.CustomTraceRepository;

import org.springframework.boot.actuate.trace.http.HttpExchangeTracer;
import org.springframework.boot.actuate.trace.http.Include;
import org.springframework.boot.actuate.web.trace.servlet.HttpTraceFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public class HistoryFilter extends HttpTraceFilter {
    private final Set<String> shouldNotFilterUris;
    private static final Set<Include> INCLUDES = Set.of(
                    Include.REQUEST_HEADERS,
                    Include.RESPONSE_HEADERS
    );

    public HistoryFilter(CustomTraceRepository customTraceRepository,
                         Set<String> shouldNotFilterUris) {
        super(customTraceRepository, new HttpExchangeTracer(INCLUDES));
        this.shouldNotFilterUris = shouldNotFilterUris;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return shouldNotFilterUris.stream().anyMatch(uri -> uri.equals(request.getRequestURI()));
    }

}
