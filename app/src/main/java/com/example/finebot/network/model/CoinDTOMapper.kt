package com.example.finebot.network.model

import com.example.finebot.domain.model.Coin
import com.example.finebot.util.DomainMapper
import com.example.finebot.network.model.CoinDTO

class CoinDTOMapper : DomainMapper<CoinDTO,Coin>{
    override fun mapToDomainModel(entity: CoinDTO): Coin {

        return Coin(price = entity.price)
    }

    override fun mapFromDomainModel(domainlModel: Coin): CoinDTO {
        TODO("Not yet implemented")
    }

}