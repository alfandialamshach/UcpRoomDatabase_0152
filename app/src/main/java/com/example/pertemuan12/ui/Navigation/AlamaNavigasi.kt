package com.example.pertemuan12.ui.Navigation

interface Alamatnavigasi {
    val route : String
}

object DestinasiHomeDosen : Alamatnavigasi {
    override val route = "home dosen" //untuk mendefinisikan route sebagai home Dosen
}

object DestinasiHomeMataKuliah : Alamatnavigasi {
    override val route = "home matakuliah" //untuk mendefinisikan route sebagai home MataKuliah
}

object DestinasiDetailMatakuliah : Alamatnavigasi {
    override  val route = "detail" //untuk mendefinisikan route sebagai detail MataKuliah
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}

object DestinasiUpdateMataKuliah : Alamatnavigasi {
    override val route = "update" //untuk mendefinisikan route sebagai update MataKuliah
    const val KODE = "kode"
    val routeWithArg = "$route/{$KODE}"
}