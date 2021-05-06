package me.kotlin.sample.house.domain.entity

import me.kotlin.sample.staff.domain.entity.Staff
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

/**
 * Created by LYT to 2021/05/01
 */
@Entity
@DynamicUpdate
@DynamicInsert
class House(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    val address: String,
    var name: String,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "house")
    val staffs: MutableList<Staff>? = mutableListOf()
)