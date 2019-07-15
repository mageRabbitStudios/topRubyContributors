package com.kinzlstanislav.topcontributors.architecture.core.usecase

import com.kinzlstanislav.topcontributors.architecture.core.coroutines.AppCoroutineScope

abstract class BaseCoroutineUseCase(appCoroutineScope: AppCoroutineScope): BaseUseCase(),
    AppCoroutineScope by appCoroutineScope