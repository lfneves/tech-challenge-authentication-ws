package com.authentication.infrastruture.repository.user

import com.authentication.infrastruture.entity.user.AddressEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AddressRepository : ReactiveCrudRepository<AddressEntity?, Long?>