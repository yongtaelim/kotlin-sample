package me.kotlin.sample.staff.domain.entity

import me.kotlin.sample.house.domain.entity.House
import me.kotlin.sample.store.domain.entity.Store
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

/**
 * Created by LYT to 2021/05/01
 */
@Entity
@DynamicInsert
@DynamicUpdate
class Staff(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    var name: String,
    val age: Int,
    val lastName: String,

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "store_id", foreignKey = ForeignKey(name = "fk_staff_store_id"))
    var store: Store? = null,

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "house_id", foreignKey = ForeignKey(name = "fk_staff_house_id"))
    var house: House? = null
)
