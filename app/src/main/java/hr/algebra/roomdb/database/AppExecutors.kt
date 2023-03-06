package hr.algebra.roomdb.database

import android.os.Handler
import android.os.Looper
import org.jetbrains.annotations.NotNull
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors private constructor(
    private val diskIO     : Executor,
    private val networkIO  : Executor,
    private val mainThread : Executor,
) {
    fun diskIO( )     = diskIO
    fun networkIO( )  = networkIO
    fun mainThread( ) = mainThread

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler( Looper.getMainLooper( ) )

        override fun execute( @NotNull command: Runnable ) {
            mainThreadHandler.post( command )
        }
    }

    companion object {
        private val LOCK = Any( )
        private var sInstance : AppExecutors? = null

        val instance : AppExecutors?
            get( ) {
                if( sInstance==null ) {
                    synchronized( LOCK ) {
                        if( sInstance==null )
                            sInstance = AppExecutors(
                                Executors.newSingleThreadExecutor( ),
                                Executors.newFixedThreadPool( 3 ),
                                MainThreadExecutor( )
                            )
                    }
                }
                return sInstance
            }
    }
}