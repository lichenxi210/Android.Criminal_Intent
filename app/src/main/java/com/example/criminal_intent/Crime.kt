package com.example.criminal_intent

import android.service.quicksettings.Tile
import java.util.Date
import java.util.UUID

data class Crime (val id:UUID = UUID.randomUUID(),
                  var title:String ="",
                  var date:Date =Date(),
                  var isSolved: Boolean = false)

//更改回档测试