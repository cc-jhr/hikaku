package io.github.ccjhr.hikaku.converters.spring.extensions

import org.springframework.web.servlet.mvc.method.RequestMappingInfo

internal fun RequestMappingInfo.paths(): Set<String> {
    return (this.patternsCondition?.patterns ?: this.pathPatternsCondition?.patternValues) ?: emptySet()
}
