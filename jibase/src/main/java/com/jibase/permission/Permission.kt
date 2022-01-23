package com.jibase.permission

class Permission {
    val name: String
    val granted: Boolean
    val shouldShowRequestPermissionRationale: Boolean

    @JvmOverloads
    constructor(
        name: String,
        granted: Boolean,
        shouldShowRequestPermissionRationale: Boolean = false
    ) {
        this.name = name
        this.granted = granted
        this.shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale
    }

    constructor(permissions: List<Permission>) {
        name = combineName(permissions)
        granted = combineGranted(permissions)
        shouldShowRequestPermissionRationale =
            combineShouldShowRequestPermissionRationale(permissions)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as Permission
        if (granted != that.granted) return false
        return if (shouldShowRequestPermissionRationale != that.shouldShowRequestPermissionRationale) false else name == that.name
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + if (granted) 1 else 0
        result = 31 * result + if (shouldShowRequestPermissionRationale) 1 else 0
        return result
    }

    override fun toString(): String {
        return "Permission{" +
                "name='" + name + '\'' +
                ", granted=" + granted +
                ", shouldShowRequestPermissionRationale=" + shouldShowRequestPermissionRationale +
                '}'
    }

    private fun combineName(permissions: List<Permission>): String {
        return permissions.joinToString(", ")
    }

    private fun combineGranted(permissions: List<Permission>): Boolean {
        return permissions.all { it.granted }
    }

    private fun combineShouldShowRequestPermissionRationale(permissions: List<Permission>): Boolean {
        return permissions.any { it.shouldShowRequestPermissionRationale }
    }
}