package io.github.ccjhr.hikaku.reporters

/**
 * Receives the result and does nothing.
 */
class NoOperationReporter : Reporter {
    override fun report(endpointMatchResult: MatchResult) { }
}