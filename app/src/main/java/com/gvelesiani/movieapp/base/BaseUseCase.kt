package com.gvelesiani.movieapp.base

import kotlinx.coroutines.*

abstract class BaseUseCase<Params, Result> {
    abstract suspend fun doWork(params: Params): Result
}

//abstract class BaseUseCase<Params, T> constructor(private val executionDispatcher: CoroutineDispatcher) {
//
//    protected abstract suspend fun buildUseCase(params: Params): T
//
//    open fun execute(
//        scope: CoroutineScope,
//        params: Params,
//        onSuccess: (T) -> Unit = {},
//        onFailure: (Exception) -> Unit = {}
//    ) {
//        val backgroundJob = scope.async(executionDispatcher) { buildUseCase(params) }
//        scope.launch(Dispatchers.Main) {
//            try {
//                onSuccess(backgroundJob.await())
//            } catch (e: Exception) {
//                onFailure(e)
//            }
//        }
//    }
//}