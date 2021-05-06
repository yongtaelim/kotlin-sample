package me.kotlin.sample.store.domain.entity

import me.kotlin.sample.staff.domain.entity.Staff
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

/**
 * Created by LYT to 2021/05/01
 */
@Entity
@DynamicInsert
@DynamicUpdate
class Store(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    val name: String,
    val address: String,

    @OneToMany(mappedBy = "store", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val staffs: MutableList<Staff> = mutableListOf()

) {

    fun addStaff(staff: Staff) {
        if (!this.staffs.contains(staff))
            this.staffs.add(staff)

        if (staff.store != this)
            staff.store = this
    }

    fun addStaffs(staffs: List<Staff>) {
        this.staffs.addAll(staffs)

        for (staff in staffs) {
            staff.store = this
        }
    }
}