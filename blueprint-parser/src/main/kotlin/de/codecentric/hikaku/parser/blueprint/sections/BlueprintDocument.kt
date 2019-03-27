package de.codecentric.hikaku.parser.blueprint.sections

data class BlueprintDocument(
        var metaDataSection: MetaDataSection? = null,
        var apiNameAndOverview: ApiNameAndOverviewSection? = null,
        var resourceResourceGroups: List<ResourceGroupSection> = emptyList(),
        var resources: MutableList<ResourceSection> = mutableListOf(),
        var dataStructures: MutableList<DataStructuresSections> = mutableListOf()
)