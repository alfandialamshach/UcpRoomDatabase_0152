package com.example.pertemuan12.ui.Navigation

interface Alamatnavigasi {
    val route : String
}

//object DestinasiHome : Alamatnavigasi {
//    override val route = "home" //untuk mendefinisikan route sebagai home
//}

object DestinasiHomeDosen : Alamatnavigasi {
    override val route = "home dosen" //untuk mendefinisikan route sebagai home
}

object DestinasiHomeMataKuliah : Alamatnavigasi {
    override val route = "home matakuliah" //untuk mendefinisikan route sebagai home
}

object DestinasiDetailMatakuliah : Alamatnavigasi {
    override  val route = "detail" //untuk mendefinisikan route sebagai detail
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}

object DestinasiUpdateMataKuliah : Alamatnavigasi {
    override val route = "update" //untuk mendefinisikan route sebagai update
    const val KODE = "kode"
    val routeWithArg = "$route/{$KODE}"
}