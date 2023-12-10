package com.authentication.infrastruture.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(name = "tb_application_secret_authority")
class ApplicationSecretAuthorityEntity {

    @Id
    @Column("client_id")
    var idClient: String = ""

    @Column("cd_authority")
    var cdAuthority: String = ""
}