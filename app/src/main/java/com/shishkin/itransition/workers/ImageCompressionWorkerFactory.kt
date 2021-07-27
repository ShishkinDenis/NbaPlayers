package com.shishkin.itransition.workers

//TODO implement WorkerFactory
/*
class ImageCompressionWorkerFactory @Inject constructor(
    private val stringUriToBitmapMapper: StringUriToBitmapMapper,
    private val bitmapProcessor: BitmapProcessor,
//    val reduceBitmapSizeStrategy: ReduceBitmapSizeStrategy,
    private val fileProcessor: FileProcessor,
    private val fileToUriMapper: FileToUriMapper
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {

        val workerClass = Class.forName(workerClassName).asSubclass(CoroutineWorker::class.java)
        val constructor =
            workerClass.getDeclaredConstructor(Context::class.java, WorkerParameters::class.java)
        val instance = constructor.newInstance(appContext, workerParameters)

        if (instance is ImageCompressionWorker) {
            instance.stringUriToBitmapMapper = stringUriToBitmapMapper
            instance.bitmapProcessor = bitmapProcessor
            instance.fileProcessor = fileProcessor
            instance.fileToUriMapper = fileToUriMapper
        }
        return instance
    }
}
*/