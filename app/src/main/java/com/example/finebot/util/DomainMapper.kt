package com.example.finebot.util

interface DomainMapper<T,DomainlModel> {

    fun mapToDomainModel(model : T) : DomainlModel

    fun mapFromDomainModel(domainlModel: DomainlModel) : T
}