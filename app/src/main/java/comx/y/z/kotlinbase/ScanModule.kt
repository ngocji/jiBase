package comx.y.z.kotlinbase

import com.jibase.di.DefaultPrefName
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ScanModule {
    @DefaultPrefName
    @Provides
    @Singleton
    fun providePreferenceName() = BuildConfig.APPLICATION_ID

    @Provides
    @Singleton
    fun providerTestShare() = TestShare()
}