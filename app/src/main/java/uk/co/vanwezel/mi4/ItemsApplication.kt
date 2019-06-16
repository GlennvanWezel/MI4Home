package uk.co.vanwezel.mi4

import android.app.Application
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import uk.co.vanwezel.mi4.data.repository.*
import uk.co.vanwezel.mi4.ui.items.list.ItemsListViewModelFactory

class ItemsApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        bind<ItemRepository>() with singleton { ItemRepositoryImpl() }
        bind<RoomRepository>() with singleton { RoomRepositoryImpl() }
        bind<TypeRepository>() with singleton { TypeRepositoryImpl() }
        bind() from provider { ItemsListViewModelFactory(instance()) }
    }
}