package io.github.ccjhr.hikaku.reporters

/**
 * A [Reporter] will receive the [MatchResult] before the test terminates.
 */
interface Reporter {
    fun report(endpointMatchResult: MatchResult)
}