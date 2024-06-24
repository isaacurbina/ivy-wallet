package com.ivy.common.androidtest

import com.ivy.common.di.CommonModuleDI
import com.ivy.common.time.provider.TimeProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CommonModuleDI::class]
)
abstract class TestCommonModuleDi {

    @Binds
    abstract fun timeProvider(provider: TimeProviderFake): TimeProvider
}
